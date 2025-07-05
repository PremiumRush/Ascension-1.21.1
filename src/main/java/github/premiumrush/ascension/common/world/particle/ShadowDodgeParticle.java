package github.premiumrush.ascension.common.world.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class ShadowDodgeParticle extends TextureSheetParticle {
    public ShadowDodgeParticle(ClientLevel p_105856_, double p_105857_, double p_105858_, double p_105859_, double p_105860_, double p_105861_, double p_105862_) {
        super(p_105856_, p_105857_, p_105858_, p_105859_);
        this.scale(3.0F);
        this.setSize(0.25F, 0.25F);
        this.lifetime = this.random.nextInt(50) + 80;

        this.rCol = 0.3515625F;
        this.gCol = 0.3515625F;
        this.bCol = 0.3515625F;
        this.gravity = 3.0E-6F;
        this.xd = p_105860_;
        this.yd = p_105861_ + (double)(this.random.nextFloat() / 400.0F);
        this.zd = p_105862_;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ < this.lifetime && !(this.alpha <= 0.0F)) {
            this.xd += (double)(this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1));
            this.zd += (double)(this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1));
            this.yd -= (double)this.gravity;
            this.move(this.xd, this.yd, this.zd);
            if (this.age >= this.lifetime - 60 && this.alpha > 0.01F) {
                this.alpha -= 0.015F;
            }

        } else {
            this.remove();
        }
    }

    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class CosyProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public CosyProvider(SpriteSet p_105878_) {
            this.sprites = p_105878_;
        }

        public Particle createParticle(SimpleParticleType p_105889_, ClientLevel p_105890_, double p_105891_, double p_105892_, double p_105893_, double p_105894_, double p_105895_, double p_105896_) {
            ShadowDodgeParticle $$8 = new ShadowDodgeParticle(p_105890_, p_105891_, p_105892_, p_105893_, p_105894_, p_105895_, p_105896_);
            $$8.setAlpha(0.9F);
            $$8.pickSprite(this.sprites);
            return $$8;
        }
    }
}
