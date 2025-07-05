package github.premiumrush.ascension.common.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class FleroviumSmithingTemplateItem extends SmithingTemplateItem {
    private static final ChatFormatting TITLE_FORMAT;
    private static final Component FLEROVIUM_UPGRADE;
    private static final Component FLEROVIUM_UPGRADE_APPLIES_TO;
    private static final Component FLEROVIUM_UPGRADE_INGREDIENTS;
    private static final Component FLEROVIUM_UPGRADE_BASE_SLOT_DESCRIPTION;
    private static final Component FLEROVIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION;
    private static final ResourceLocation EMPTY_SLOT_HELMET;
    private static final ResourceLocation EMPTY_SLOT_CHESTPLATE;
    private static final ResourceLocation EMPTY_SLOT_LEGGINGS;
    private static final ResourceLocation EMPTY_SLOT_BOOTS;
    private static final ResourceLocation EMPTY_SLOT_HOE;
    private static final ResourceLocation EMPTY_SLOT_AXE;
    private static final ResourceLocation EMPTY_SLOT_SWORD;
    private static final ResourceLocation EMPTY_SLOT_SHOVEL;
    private static final ResourceLocation EMPTY_SLOT_PICKAXE;
    private static final ResourceLocation EMPTY_SLOT_AMETHYST_SHARD;
    private static final ResourceLocation EMPTY_SLOT_DAGGER;

    public FleroviumSmithingTemplateItem(Component pAppliesTo, Component pIngredients, Component pUpgradeDescription, Component pBaseSlotDescription, Component pAdditionalSlotDescription, List<ResourceLocation> pBaseSlotEmptyIcons, List<ResourceLocation> pAdditionalSlotEmptyIcons) {
        super(pAppliesTo,pIngredients,pUpgradeDescription,pBaseSlotDescription,pAdditionalSlotDescription,pBaseSlotEmptyIcons,pAdditionalSlotEmptyIcons);
    }

    public static FleroviumSmithingTemplateItem createFleroviumUpgradeTemplate() {
        return new FleroviumSmithingTemplateItem(FLEROVIUM_UPGRADE_APPLIES_TO, FLEROVIUM_UPGRADE_INGREDIENTS, FLEROVIUM_UPGRADE, FLEROVIUM_UPGRADE_BASE_SLOT_DESCRIPTION, FLEROVIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION, createFleroviumUpgradeIconList(), createFleroviumUpgradeMaterialList());
    }

    private static List<ResourceLocation> createFleroviumUpgradeIconList() {
        return List.of(EMPTY_SLOT_HELMET, EMPTY_SLOT_SWORD, EMPTY_SLOT_CHESTPLATE, EMPTY_SLOT_PICKAXE, EMPTY_SLOT_LEGGINGS, EMPTY_SLOT_AXE, EMPTY_SLOT_BOOTS, EMPTY_SLOT_HOE, EMPTY_SLOT_SHOVEL, EMPTY_SLOT_DAGGER);
    }

    private static List<ResourceLocation> createFleroviumUpgradeMaterialList() {
        return List.of(EMPTY_SLOT_AMETHYST_SHARD);
    }

    static {
        TITLE_FORMAT = ChatFormatting.GRAY;
        FLEROVIUM_UPGRADE = Component.translatable("tooltip.ascension.flerovium_upgrade_upgrade_description.tooltip").withStyle(TITLE_FORMAT);
        FLEROVIUM_UPGRADE_APPLIES_TO = Component.translatable("tooltip.ascension.flerovium_upgrade_applies_to.tooltip");
        FLEROVIUM_UPGRADE_INGREDIENTS = Component.translatable("tooltip.ascension.flerovium_upgrade_ingredients.tooltip");
        FLEROVIUM_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable("tooltip.ascension.flerovium_upgrade_baseslot_description.tooltip");
        FLEROVIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable("tooltip.ascension.flerovium_upgrade_additional_slot_description.tooltip");
        EMPTY_SLOT_HELMET = ResourceLocation.parse("item/empty_armor_slot_helmet");
        EMPTY_SLOT_CHESTPLATE = ResourceLocation.parse("item/empty_armor_slot_chestplate");
        EMPTY_SLOT_LEGGINGS = ResourceLocation.parse("item/empty_armor_slot_leggings");
        EMPTY_SLOT_BOOTS = ResourceLocation.parse("item/empty_armor_slot_boots");
        EMPTY_SLOT_HOE = ResourceLocation.parse("item/empty_slot_hoe");
        EMPTY_SLOT_AXE = ResourceLocation.parse("item/empty_slot_axe");
        EMPTY_SLOT_SWORD = ResourceLocation.parse("item/empty_slot_sword");
        EMPTY_SLOT_SHOVEL = ResourceLocation.parse("item/empty_slot_shovel");
        EMPTY_SLOT_PICKAXE = ResourceLocation.parse("item/empty_slot_pickaxe");
        EMPTY_SLOT_AMETHYST_SHARD = ResourceLocation.parse("item/empty_slot_amethyst_shard");
        EMPTY_SLOT_DAGGER = ResourceLocation.parse("ascension:item/empty_slot_dagger");
    }
}
