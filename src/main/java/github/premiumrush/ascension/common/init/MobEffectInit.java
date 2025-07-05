package github.premiumrush.ascension.common.init;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.effect.BleedingMobEffect;
import github.premiumrush.ascension.common.world.effect.RadiationMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MobEffectInit {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Ascension.MODID);

    public static final Holder<MobEffect> BLEEDING = MOB_EFFECTS.register("bleeding", () -> new BleedingMobEffect(
            MobEffectCategory.HARMFUL,
            9240576
    ));
    public static final Holder<MobEffect> RADIATION = MOB_EFFECTS.register("radiation", () -> new RadiationMobEffect(
            MobEffectCategory.HARMFUL,
            9728
    ));
}
