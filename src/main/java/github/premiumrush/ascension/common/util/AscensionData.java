package github.premiumrush.ascension.common.util;

import github.premiumrush.ascension.common.init.ItemInit;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import oshi.util.tuples.Quartet;

import java.util.List;
import java.util.Map;

public class AscensionData {
    /*// Enchantment Refiner Data
    public static final List<Pair<ResourceKey<Enchantment>, Item>> ENCHANTMENT_TO_ITEMSTACK_LIST = List.of(
            new Pair<>(Enchantments.PROTECTION, Items.SHULKER_SHELL),
            new Pair<>(Enchantments.FIRE_PROTECTION, Items.FIRE_CHARGE),
            new Pair<>(Enchantments.BLAST_PROTECTION, Items.TNT),
            new Pair<>(Enchantments.PROJECTILE_PROTECTION, Items.ARROW),
            new Pair<>(Enchantments.PIERCING, ItemInit.SHARK_TOOTH.get()),
            new Pair<>(Enchantments.BANE_OF_ARTHROPODS, Items.COBWEB),
            new Pair<>(Enchantments.POWER, Items.NETHER_STAR),
            new Pair<>(Enchantments.DENSITY, Items.IRON_BLOCK),
            new Pair<>(Enchantments.DEPTH_STRIDER, Items.TURTLE_SCUTE),
            new Pair<>(Enchantments.EFFICIENCY, Items.DRAGON_BREATH),
            new Pair<>(Enchantments.FEATHER_FALLING, Items.FEATHER),
            new Pair<>(Enchantments.FIRE_ASPECT, Items.BLAZE_POWDER),
            new Pair<>(Enchantments.LURE, Items.TROPICAL_FISH),
            new Pair<>(Enchantments.FORTUNE, Items.EMERALD_BLOCK),
            new Pair<>(Enchantments.FROST_WALKER, ItemInit.ICE_BLAZE_POWDER.get()),
            new Pair<>(Enchantments.IMPALING, Items.PRISMARINE_SHARD),
            new Pair<>(Enchantments.LUCK_OF_THE_SEA, Items.HEART_OF_THE_SEA),
            new Pair<>(Enchantments.LOOTING, Items.GLOWSTONE_DUST),
            new Pair<>(Enchantments.LOYALTY, Items.ENDER_PEARL),
            new Pair<>(Enchantments.SOUL_SPEED, Items.RABBIT_FOOT),
            new Pair<>(Enchantments.SWEEPING_EDGE, ItemInit.OBSIDIAN_ROD.get()),
            new Pair<>(Enchantments.SWIFT_SNEAK, Items.ARMADILLO_SCUTE),
            new Pair<>(Enchantments.THORNS, Items.CACTUS),
            new Pair<>(Enchantments.UNBREAKING, Items.NETHERITE_INGOT),
            new Pair<>(Enchantments.WIND_BURST, Items.WIND_CHARGE),
            new Pair<>(Enchantments.SMITE, Items.GOLDEN_APPLE),
            new Pair<>(Enchantments.PUNCH, ItemInit.GOLEM_GYRO.get()),
            new Pair<>(Enchantments.QUICK_CHARGE, Items.CLOCK),
            new Pair<>(Enchantments.RESPIRATION, Items.TURTLE_HELMET),
            new Pair<>(Enchantments.RIPTIDE, Items.PRISMARINE_CRYSTALS),
            new Pair<>(Enchantments.BREACH, Items.FLINT),
            new Pair<>(Enchantments.SHARPNESS, Items.AMETHYST_SHARD),
            new Pair<>(Enchantments.KNOCKBACK, ItemInit.GOLEM_GYRO.get())
    );*/

    // ThunderHit Data
    public static final Map<ResourceKey<Attribute>, Float> LIGHTNING_ATTRIBUTE_TO_MODIFIER_MAP = Map.of(
            Attributes.ARMOR.getKey(), 2f,
            Attributes.ARMOR_TOUGHNESS.getKey(), 0.5f,
            Attributes.ATTACK_DAMAGE.getKey(), 2f,
            Attributes.ATTACK_SPEED.getKey(), 0.2f
    );
    public static final List<Quartet<Holder<Attribute>, AttributeModifier, String, Item>> LIGHTNING_NEW_ATTRIBUTES_LIST = List.of(
            new Quartet<>(
                    Attributes.ENTITY_INTERACTION_RANGE,
                    new AttributeModifier(ResourceLocation.withDefaultNamespace("player.entity_interaction_range"), 0.5f, AttributeModifier.Operation.ADD_VALUE),
                    "flerovium_crystal_added",
                    ItemInit.FLEROVIUM_CRYSTAL.get()
            ),
            new Quartet<>(
                    Attributes.BLOCK_INTERACTION_RANGE,
                    new AttributeModifier(ResourceLocation.withDefaultNamespace("player.block_interaction_range"), 1, AttributeModifier.Operation.ADD_VALUE),
                    "flerovium_shard_added",
                    ItemInit.FLEROVIUM_SHARD.get()
            )
    );
    public static final List<Item> LIGHTNING_INVULNERABLE_INGREDIENT_LIST = List.of(
            ItemInit.FLEROVIUM_SHARD.get(),
            ItemInit.FLEROVIUM_CRYSTAL.get(),
            ItemInit.BLAZE_GEM.get()
    );
    public static CompoundTag getLightningCT() {
        CompoundTag ct = new CompoundTag();
        ct.putInt("can_summon_lightning", 1000);
        return ct;
    }

    // Bleeding Invulnerability
    public static final List<EntityType<?>> BLEEDING_INVULNERABLE = List.of(
            EntityType.SKELETON_HORSE,
            EntityType.SKELETON,
            EntityType.WITHER,
            EntityType.WITHER_SKELETON,
            EntityType.STRAY,
            EntityType.IRON_GOLEM,
            EntityType.SNOW_GOLEM,
            EntityType.SHULKER,
            EntityType.ENDERMITE,
            EntityType.ENDERMAN,
            EntityType.ENDER_DRAGON
    );
}
