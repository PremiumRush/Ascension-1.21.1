package github.premiumrush.ascension.common.util;

import github.premiumrush.ascension.common.init.ItemInit;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import oshi.util.tuples.Quartet;

import java.util.List;
import java.util.Map;

public class AscensionData {
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
}
