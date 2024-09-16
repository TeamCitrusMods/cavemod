package com.elysiasilly.cavemod.block.custom.detoriatedworkbench;

import com.elysiasilly.cavemod.registry.BlockRegistry;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Optional;

public class DeterioratedCraftingMenu extends RecipeBookMenu<CraftingInput, CraftingRecipe> {
    public static final int RESULT_SLOT = 0;
    private static final int CRAFT_SLOT_START = 1;
    private static final int CRAFT_SLOT_END = 10;
    private static final int INV_SLOT_START = 10;
    private static final int INV_SLOT_END = 37;
    private static final int USE_ROW_SLOT_START = 37;
    private static final int USE_ROW_SLOT_END = 46;
    private final CraftingContainer craftSlots;
    private final ResultContainer resultSlots;
    private final ContainerLevelAccess access;
    private final Player player;
    private boolean placingRecipe;

    public DeterioratedCraftingMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public DeterioratedCraftingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(MenuType.CRAFTING, containerId);
        this.craftSlots = new TransientCraftingContainer(this, 3, 3);
        this.resultSlots = new ResultContainer();
        this.access = access;
        this.player = playerInventory.player;
        this.addSlot(new ResultSlot(playerInventory.player, this.craftSlots, this.resultSlots, 0, 124, 35));

        int brokenslot = 4;

        int l;
        int i1;
        for(l = 0; l < 3; ++l) {
            for(i1 = 0; i1 < 3; ++i1) {
                if((l + i1 * 3) != brokenslot) {
                    System.out.println(true);

                    this.addSlot(new Slot(this.craftSlots, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
                } else {
                    System.out.println(false);
                }
            }
        }


        /*
        for(int slot = 0; slot < 9; slot++) {
            if(slot != brokenslot) {
                this.addSlot(new Slot(this.craftSlots, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
            }
        }

         */

        for(l = 0; l < 3; ++l) {
            for(i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }

        for(l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }

    }

    protected static void slotChangedCraftingGrid(AbstractContainerMenu menu, Level level, Player player, CraftingContainer craftSlots, ResultContainer resultSlots, @Nullable RecipeHolder<CraftingRecipe> recipe) {
        if (!level.isClientSide) {
            CraftingInput craftinginput = craftSlots.asCraftInput();
            ServerPlayer serverplayer = (ServerPlayer)player;
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<RecipeHolder<CraftingRecipe>> optional = level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftinginput, level, recipe);
            if (optional.isPresent()) {
                RecipeHolder<CraftingRecipe> recipeholder = (RecipeHolder)optional.get();
                CraftingRecipe craftingrecipe = (CraftingRecipe)recipeholder.value();
                if (resultSlots.setRecipeUsed(level, serverplayer, recipeholder)) {
                    ItemStack itemstack1 = craftingrecipe.assemble(craftinginput, level.registryAccess());
                    if (itemstack1.isItemEnabled(level.enabledFeatures())) {
                        itemstack = itemstack1;
                    }
                }
            }

            resultSlots.setItem(0, itemstack);
            menu.setRemoteSlot(0, itemstack);
            serverplayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemstack));
        }

    }

    public void slotsChanged(Container inventory) {
        if (!this.placingRecipe) {
            this.access.execute((p_344363_, p_344364_) -> {
                slotChangedCraftingGrid(this, p_344363_, this.player, this.craftSlots, this.resultSlots, (RecipeHolder)null);
            });
        }

    }

    public void beginPlacingRecipe() {
        this.placingRecipe = true;
    }

    public void finishPlacingRecipe(RecipeHolder<CraftingRecipe> recipe) {
        this.placingRecipe = false;
        this.access.execute((p_344361_, p_344362_) -> {
            slotChangedCraftingGrid(this, p_344361_, this.player, this.craftSlots, this.resultSlots, recipe);
        });
    }

    public void fillCraftSlotsStackedContents(StackedContents itemHelper) {
        this.craftSlots.fillStackedContents(itemHelper);
    }

    public void clearCraftingContent() {
        this.craftSlots.clearContent();
        this.resultSlots.clearContent();
    }

    public boolean recipeMatches(RecipeHolder<CraftingRecipe> recipe) {
        return ((CraftingRecipe)recipe.value()).matches(this.craftSlots.asCraftInput(), this.player.level());
    }

    public void removed(Player player) {
        super.removed(player);
        this.access.execute((p_39371_, p_39372_) -> {
            this.clearContainer(player, this.craftSlots);
        });
    }

    public boolean stillValid(Player player) {
        return stillValid(this.access, player, BlockRegistry.DETERIORATED_CRAFTING_TABLE.get());
    }

    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 0) {
                this.access.execute((p_39378_, p_39379_) -> {
                    itemstack1.getItem().onCraftedBy(itemstack1, p_39378_, player);
                });
                if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index >= 10 && index < 46) {
                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
            if (index == 0) {
                player.drop(itemstack1, false);
            }
        }

        return itemstack;
    }

    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    public int getResultSlotIndex() {
        return 0;
    }

    public int getGridWidth() {
        return this.craftSlots.getWidth();
    }

    public int getGridHeight() {
        return this.craftSlots.getHeight();
    }

    public int getSize() {
        return 10;
    }

    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    public boolean shouldMoveToInventory(int slotIndex) {
        return slotIndex != this.getResultSlotIndex();
    }
}
