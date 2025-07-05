package github.premiumrush.ascension;

import github.premiumrush.ascension.common.init.LootModifierInit;
import github.premiumrush.ascension.common.event.block.BlockDropsEvents;
import github.premiumrush.ascension.common.event.block.GolemSpawnBlockEvent;
import github.premiumrush.ascension.common.event.entity.LivingChangeTargetEvents;
import github.premiumrush.ascension.common.event.player.PlayerInteractEvents;
import github.premiumrush.ascension.common.event.player.PlayerXpEvents;
import github.premiumrush.ascension.common.event.player.UseItemOnBlockEvents;
import github.premiumrush.ascension.common.init.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Ascension.MODID)
public class Ascension {
    public static final String MODID = "ascension";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public Ascension(IEventBus bus) {
        CreativeTabInit.TABS.register(bus);
        ItemInit.ITEMS.register(bus);
        ArmorMaterialInit.ARMOR_MATERIALS.register(bus);
        BlockInit.BLOCKS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);

        EntityInit.ENTITY_TYPES.register(bus);
        MobEffectInit.MOB_EFFECTS.register(bus);
        ParticleInit.PARTICLE_TYPES.register(bus);

        RecipeInit.MOD_RECIPE_TYPES.register(bus);
        RecipeInit.MOD_RECIPE_SERIALIZERS.register(bus);

        LootModifierInit.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(bus);


        // Setup
        commonSetup();
    }

    public static void commonSetup() {
        LOGGER.info("Common Setup Initialized");

        registerEvents();
    }

    public static void registerEvents() {
        NeoForge.EVENT_BUS.register(new GolemSpawnBlockEvent());
        NeoForge.EVENT_BUS.register(new LivingChangeTargetEvents());
        NeoForge.EVENT_BUS.register(new UseItemOnBlockEvents());
        NeoForge.EVENT_BUS.register(new PlayerXpEvents());
        NeoForge.EVENT_BUS.register(new PlayerInteractEvents());
        NeoForge.EVENT_BUS.register(new BlockDropsEvents());
    }
}
