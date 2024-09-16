package com.elysiasilly.cavemod.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class SweetwoodLeafParticle extends TextureSheetParticle {

    final int RANDOM = level.random.nextIntBetweenInclusive(1, 6);
    boolean VALID;

    protected SweetwoodLeafParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);

        VALID = level.getBlockState(new BlockPos((int) x, (int) y, (int) z)).isAir();

        this.setSprite(spriteSet.get(this.random.nextInt(12), 12));
        this.gravity = 0.0F;
        this.lifetime = 10000;
        this.hasPhysics = false;
    }

    public float getQuadSize(float scaleFactor) {
        return 2F;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {

        if(VALID) {
            Quaternionf quaternionf = new Quaternionf();

            if (this.roll != 0.0F) {
                quaternionf.rotateZ(Mth.lerp(partialTicks, this.oRoll, this.roll));
            } // TODO : ?

            switch (RANDOM) {
                case 1 : quaternionf.rotateY(0); break;
                case 2 : quaternionf.rotateY(900); break;
                case 3 : quaternionf.rotateY(1800); break;
                case 4 : quaternionf.rotateY(2700); break;
                case 5 : quaternionf.rotateX(900); break;
                case 6 : quaternionf.rotateX(2700); break;
            }
            this.renderRotatedQuad(buffer, renderInfo, quaternionf, partialTicks);
        }
    }

    @Override
    protected void renderRotatedQuad(VertexConsumer buffer, Camera camera, Quaternionf quaternion, float partialTicks) {
        Vec3 vec3 = camera.getPosition();
        //Vec3 help = vec3.add(1, 1, 1)
        /*
        float f = (float)(Mth.lerp((double)partialTicks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp((double)partialTicks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp((double)partialTicks, this.zo, this.z) - vec3.z());

         */
        float f  = (float)(Mth.lerp(partialTicks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp(partialTicks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp(partialTicks, this.zo, this.z) - vec3.z());
        this.renderRotatedQuad(buffer, quaternion, f, f1, f2, partialTicks);
    }

    @Override
    protected void renderRotatedQuad(VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float partialTicks) {
        float f = this.getQuadSize(partialTicks);

        float f1 = this.getU0();
        float f2 = this.getU1();
        float f3 = this.getV0();
        float f4 = this.getV1();
        //int i = this.getLightColor(partialTicks);
        this.renderVertex(buffer, quaternion, x, y, z, 1.0F, -1.0F, f, f2, f4, 12);
        this.renderVertex(buffer, quaternion, x, y, z, 1.0F, 1.0F, f, f2, f3, 12);
        this.renderVertex(buffer, quaternion, x, y, z, -1.0F, 1.0F, f, f1, f3, 12);
        this.renderVertex(buffer, quaternion, x, y, z, -1.0F, -1.0F, f, f1, f4, 12);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SweetwoodLeafParticle particle = new SweetwoodLeafParticle(level, x, y, z, spriteSet);
            particle.pickSprite(this.spriteSet);
            particle.setColor(1.0F, 1.0F, 1.0F);
            return particle;
        }
    }
}
