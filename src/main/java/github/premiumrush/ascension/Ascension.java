package github.premiumrush.ascension;

import github.premiumrush.ascension.init.*;
import github.premiumrush.ascension.loot.ModLootModifiers;
import github.premiumrush.ascension.init.MobEffectInit;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Ascension.MODID)
public class Ascension {
    public static final String MODID = "ascension";

    public Ascension(IEventBus bus) {
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        CreativeTabInit.TABS.register(bus);
        ParticleInit.PARTICLE_TYPES.register(bus);
        ArmorMaterialInit.ARMOR_MATERIALS.register(bus);
        MobEffectInit.MOB_EFFECTS.register(bus);

        ModLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(bus);
    }
}
