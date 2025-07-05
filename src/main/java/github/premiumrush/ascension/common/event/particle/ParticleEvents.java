package github.premiumrush.ascension.common.event.particle;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.particle.BubbleParticle;
import github.premiumrush.ascension.common.world.particle.DarknessParticle;
import github.premiumrush.ascension.common.world.particle.InfusionParticle;
import github.premiumrush.ascension.common.world.particle.ShadowDodgeParticle;
import github.premiumrush.ascension.common.init.ParticleInit;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = Ascension.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ParticleEvents {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleInit.SHADOW_DODGE_PARTICLE.get(), ShadowDodgeParticle.CosyProvider::new);
        event.registerSpriteSet(ParticleInit.BUBBLE_PARTICLE.get(), BubbleParticle.Provider::new);
        event.registerSpriteSet(ParticleInit.INFUSION_PARTICLE.get(), InfusionParticle.Provider::new);
        event.registerSpriteSet(ParticleInit.DARKNESS_PARTICLE.get(), DarknessParticle.CosyProvider::new);
    }
}
