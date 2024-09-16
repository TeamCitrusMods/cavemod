package com.elysiasilly.cavemod.item.lanternbulbslice;

import com.elysiasilly.cavemod.registry.BlockRegistry;
import com.elysiasilly.cavemod.registry.EntityTypeRegistry;
import com.elysiasilly.cavemod.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

public class LanternBulbSliceEntity extends ThrowableItemProjectile {

    public LanternBulbSliceEntity(Level pLevel, LivingEntity pShooter) {
        super(EntityTypeRegistry.LANTERN_BULB_SLICE.get(), pShooter, pLevel);
    }

    public LanternBulbSliceEntity(EntityType<? extends ThrowableItemProjectile> entityEntityType, Level level) {
        super(entityEntityType, level);
    }

    public float DAMAGE;

    public LanternBulbSliceEntity(Level pLevel, double pX, double pY, double pZ, float damage) {
        super(EntityTypeRegistry.LANTERN_BULB_SLICE.get(), pX, pY, pZ, pLevel);
        DAMAGE = damage;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegistry.LANTERN_BULB_SLICE.get();
    }

    public void shootFromRotationNoShooter(float x, float y, float z, float velocity, float inaccuracy) {
        float var = 0.017453292F;

        float f  = -Mth.sin(y * var) * Mth.cos(x * var);
        float f1 = -Mth.sin((x + z) * var);
        float f2 =  Mth.cos(y * var) * Mth.cos(x * var);
        this.shoot(f, f1, f2, velocity, inaccuracy);
        this.setDeltaMovement(this.getDeltaMovement());
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        Entity entity = result.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), DAMAGE);
        this.level().broadcastEntityEvent(this, (byte) 3);
        this.discard();


        Level level = level();

        BlockPos hi = BlockPos.containing(result.getLocation());

        ParticleUtils.spawnParticleInBlock(level, hi, 10, ParticleTypes.GLOW_SQUID_INK);

    }

    @Override
    public void handleEntityEvent(byte id) {
        ItemStack entityStack = new ItemStack(this.getDefaultItem());
        if (id == 3) {
            ParticleOptions iparticledata = new ItemParticleOption(ParticleTypes.ITEM, entityStack);

            for (int i = 0; i < 12; ++i) {
                this.level().addParticle(iparticledata, this.getX(), this.getY(), this.getZ(),
                        ((double) this.random.nextFloat() * 2.0D - 1.0D) * 0.1F,
                        ((double) this.random.nextFloat() * 2.0D - 1.0D) * 0.1F + 0.1F,
                        ((double) this.random.nextFloat() * 2.0D - 1.0D) * 0.1F);
            }
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        Level level = level();
        BlockPos adjustedPos = result.getBlockPos().relative(result.getDirection());

        boolean canBeReplaced = level.getBlockState(adjustedPos).canBeReplaced();
        boolean isFaceSturdy = level.getBlockState(result.getBlockPos()).isFaceSturdy(level, result.getBlockPos(), result.getDirection());

        this.level().broadcastEntityEvent(this, (byte) 3);
        ParticleUtils.spawnParticleInBlock(level, adjustedPos, 10, ParticleTypes.GLOW_SQUID_INK);

        if(!level.isClientSide && canBeReplaced && isFaceSturdy) {

            boolean flag = level.getFluidState(adjustedPos).getType() == Fluids.WATER;

            level.setBlockAndUpdate(adjustedPos, BlockRegistry.LANTERN_PASTE.get().defaultBlockState().setValue(LanternPasteBlock.FACING, result.getDirection()).setValue(LanternPasteBlock.WATERLOGGED, flag));
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
            this.hitTargetOrDeflectSelf(hitresult);
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();
        float f;
        if (this.isInWater()) {
            for(int i = 0; i < 4; ++i) {
                float f1 = 0.25F;
                this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25, d1 - vec3.y * 0.25, d2 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
            }

            f = 0.8F;
        } else {
            f = 0.99F;
        }

        this.level().addParticle(ParticleTypes.GLOW_SQUID_INK, d0, d1, d2, 0, 0, 0);

        this.setDeltaMovement(vec3.scale((double)f));
        this.applyGravity();
        this.setPos(d0, d1, d2);
    }
}
