package github.premiumrush.ascension.common.world.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class BubbleParticle extends TextureSheetParticle {
    final SpriteSet animation;

    public BubbleParticle(ClientLevel level, SpriteSet animation, double posX, double posY, double posZ, double xd, double yd, double zd) {
        super(level, posX, posY, posZ, xd, yd, zd);
        this.animation = animation;
        this.rCol = 1;
        this.gCol = 1;
        this.bCol = 1;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.lifetime = 20;
        this.quadSize *= 0.75f;
        setSpriteFromAge(this.animation);
    }

    @Override
    public void tick() {
        super.tick();
        this.yd *= 0.95;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet animation;
        public Provider(SpriteSet animation) {
            this.animation = animation;
        }

        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            return new BubbleParticle(level, this.animation, x, y, z, dx, dy, dz);
        }
    }
}
