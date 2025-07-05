package github.premiumrush.ascension.common.world.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class InfusionParticle extends TextureSheetParticle {
    private boolean hasHitGround;
    private final SpriteSet sprites;

    protected InfusionParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z);
        this.friction = 0.96F;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.rCol = Mth.nextFloat(this.random, 0.4745098F, 0.6568627F);
        this.gCol = Mth.nextFloat(this.random, 0.0F, 0.0F);
        this.bCol = Mth.nextFloat(this.random, 0.9490196F, 0.9564706F);
        this.quadSize *= 0.75F;
        this.lifetime = (int)(20.0 / ((double)this.random.nextFloat() * 0.6 + 0.4));
        this.hasHitGround = true;
        this.alpha = 0.75f;
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
            if (this.onGround) {
                this.yd = 0.0;
                this.hasHitGround = true;
            }

            this.alpha -= 0.02f;

            this.move(this.xd, this.yd, this.zd);
            if (this.y == this.yo) {
                this.xd *= 1.1;
                this.zd *= 1.1;
            }

            this.xd *= this.friction;
            this.zd *= this.friction;
            if (this.hasHitGround) {
                this.yd *= this.friction;
            }
        }

    }

    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public float getQuadSize(float scaleFactor) {
        return this.quadSize * Mth.clamp(((float)this.age + scaleFactor) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new InfusionParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
        }
    }
}
