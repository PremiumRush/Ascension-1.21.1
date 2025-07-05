package github.premiumrush.ascension.mixin;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.util.AscensionData;
import github.premiumrush.ascension.common.util.AscensionUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import oshi.util.tuples.Quartet;

import java.util.List;
import java.util.Map;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin implements AscensionUtil {
    @Unique
    public void thunderHit(ServerLevel level, LightningBolt lightning) {
        ItemEntity itemEntity = (ItemEntity)(Object)this;
        ItemStack itemStack = itemEntity.getItem();
        Item item = itemStack.getItem();
        boolean validItemInstance = item instanceof ArmorItem || item instanceof SwordItem || item instanceof DiggerItem || item instanceof MaceItem || item instanceof ShieldItem;
        if (validItemInstance || AscensionData.LIGHTNING_INVULNERABLE_INGREDIENT_LIST.contains(itemStack.getItem())) {
            itemEntity.setInvulnerable(true);
        }
        if (lightning.getTags().contains("can_upgrade_or_enhance")) {
            if (validItemInstance) {
                ItemAttributeModifiers.Builder newBuilder = ItemAttributeModifiers.builder();
                List<ItemAttributeModifiers.Entry> stackModifierEntryList = itemStack.getAttributeModifiers().modifiers();

                // Enhancement Operation | Only operates on the second call for whatever reason...
                if (lightning.getHitEntities().anyMatch(hitEntity -> (hitEntity instanceof ItemEntity hitItemEntity && AscensionData.LIGHTNING_INVULNERABLE_INGREDIENT_LIST.contains(hitItemEntity.getItem().getItem())))) {
                    for (Quartet<Holder<Attribute>, AttributeModifier, String, Item> quartet : AscensionData.LIGHTNING_NEW_ATTRIBUTES_LIST) {
                        for (Entity hitEntity : lightning.getHitEntities().toList()) {
                            if (hitEntity instanceof ItemEntity hitItemEntity && hitItemEntity.getItem().getItem().equals(quartet.getD())) {
                                if (itemStack.get(DataComponents.CUSTOM_DATA) != null && !itemStack.get(DataComponents.CUSTOM_DATA).contains(quartet.getC()) || itemStack.get(DataComponents.CUSTOM_DATA) == null) {
                                    hitItemEntity.getItem().shrink(1);
                                    EquipmentSlotGroup group = itemStack.getAttributeModifiers().modifiers().getFirst().slot();
                                    ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Ascension.MODID, quartet.getB().id().getPath()+item.getDescriptionId());
                                    newBuilder = newBuilder.add(quartet.getA(), new AttributeModifier(resourceLocation, quartet.getB().amount(), quartet.getB().operation()), group);
                                    ascension_1_21_1$addCompoundTag(quartet.getC(), itemStack);
                                }
                            }
                        }
                    }
                }

                // Upgrade Operation
                if (itemStack.get(DataComponents.CUSTOM_DATA) != null && !itemStack.get(DataComponents.CUSTOM_DATA).contains("is_lightning_upgraded") || itemStack.get(DataComponents.CUSTOM_DATA) == null) {
                    for (ItemAttributeModifiers.Entry modifierEntry : stackModifierEntryList) {
                        for (Map.Entry<ResourceKey<Attribute>, Float> mapEntry : AscensionData.LIGHTNING_ATTRIBUTE_TO_MODIFIER_MAP.entrySet()) {
                            if (item instanceof DiggerItem) {
                                // Add Efficiency to every DiggerItem instead of Damage/AttackSpeed
                                newBuilder = newBuilder.add(modifierEntry.attribute(), modifierEntry.modifier(), modifierEntry.slot());
                                newBuilder = newBuilder.add(Attributes.MINING_EFFICIENCY, new AttributeModifier(ResourceLocation.withDefaultNamespace("player.mining_efficiency"),12,AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
                            } else {
                                if (modifierEntry.attribute().is(mapEntry.getKey())) {
                                    newBuilder = newBuilder.add(modifierEntry.attribute(), new AttributeModifier(modifierEntry.modifier().id(), modifierEntry.modifier().amount() + mapEntry.getValue(), modifierEntry.modifier().operation()), modifierEntry.slot()
                                    );
                                } else if (!AscensionData.LIGHTNING_ATTRIBUTE_TO_MODIFIER_MAP.containsKey(modifierEntry.attribute().getKey())) {
                                    newBuilder = newBuilder.add(modifierEntry.attribute(), modifierEntry.modifier(), modifierEntry.slot());
                                }
                            }
                        }
                    }
                    if (stackModifierEntryList.isEmpty() && item instanceof ShieldItem) {
                        newBuilder = newBuilder.add(Attributes.SNEAKING_SPEED, new AttributeModifier(ResourceLocation.withDefaultNamespace("player.sneaking_speed"),1,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.HAND);
                    }

                    ascension_1_21_1$addCompoundTag("is_lightning_upgraded", itemStack);
                    // Actually set the Modifiers
                    ItemAttributeModifiers itemAttributeModifiers = newBuilder.build();
                    itemStack.set(DataComponents.ATTRIBUTE_MODIFIERS, itemAttributeModifiers);

                } else {
                    // get current Modifiers (including Enhancements) and add them again even if no Upgrade happens
                    for (ItemAttributeModifiers.Entry modifierEntry : stackModifierEntryList) {
                        newBuilder = newBuilder.add(modifierEntry.attribute(), modifierEntry.modifier(), modifierEntry.slot());
                    }
                    // Actually set the Modifiers
                    ItemAttributeModifiers itemAttributeModifiers = newBuilder.build();
                    itemStack.set(DataComponents.ATTRIBUTE_MODIFIERS, itemAttributeModifiers);
                }
            }
        }

        // Default Entity thunderHit
        itemEntity.setRemainingFireTicks(itemEntity.getRemainingFireTicks() + 1);
        if (itemEntity.getRemainingFireTicks() == 0) {
            itemEntity.igniteForSeconds(8.0F);
        }
        itemEntity.hurt(itemEntity.damageSources().lightningBolt(), lightning.getDamage());
    }

    @Unique
    private void ascension_1_21_1$addCompoundTag(String name, ItemStack stack) {
        CompoundTag compoundTag = getCurrentCustomTag(stack);
        compoundTag.putBoolean(name, true);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundTag));
    }
}
