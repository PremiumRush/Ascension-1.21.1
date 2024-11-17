package github.premiumrush.ascension.event;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.init.ParticleInit;
import github.premiumrush.ascension.world.particle.BubbleParticle;
import github.premiumrush.ascension.world.particle.ShadowDodgeParticle;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = Ascension.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ParticleEvents {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleInit.SHADOW_DODGE_PARTICLE.get(), ShadowDodgeParticle.CosyProvider::new);
        event.registerSpriteSet(ParticleInit.BUBBLE_PARTICLE.get(), BubbleParticle.Provider::new);
    }
}
