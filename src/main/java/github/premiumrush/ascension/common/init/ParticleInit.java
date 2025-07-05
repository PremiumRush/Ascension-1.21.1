package github.premiumrush.ascension.common.init;

import github.premiumrush.ascension.Ascension;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Ascension.MODID);

    public static final Supplier<SimpleParticleType> BUBBLE_PARTICLE = PARTICLE_TYPES.register("bubble_particle",
            () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> SHADOW_DODGE_PARTICLE = PARTICLE_TYPES.register("shadow_dodge_particle",
            () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> INFUSION_PARTICLE = PARTICLE_TYPES.register("infusion_particle",
            () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> DARKNESS_PARTICLE = PARTICLE_TYPES.register("darkness_particle",
            () -> new SimpleParticleType(true));
}
