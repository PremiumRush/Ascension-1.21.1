package github.premiumrush.ascension.init;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.world.item.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static github.premiumrush.ascension.init.CreativeTabInit.addToTab;

public class ItemInit {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Ascension.MODID);

// ## BLOCK-ENTITIES

// ## BLOCKS
// Titanium BlockItems
    public static final Supplier<BlockItem> TITANIUM_ORE_ITEM = addToTab(ITEMS.registerSimpleBlockItem("titanium_ore", BlockInit.TITANIUM_ORE, new Item.Properties()));
    public static final Supplier<BlockItem> DEEPSLATE_TITANIUM_ORE_ITEM = addToTab(ITEMS.registerSimpleBlockItem("deepslate_titanium_ore", BlockInit.DEEPSLATE_TITANIUM_ORE, new Item.Properties()));
    public static final Supplier<BlockItem> RAW_TITANIUM_BLOCK_ITEM = addToTab(ITEMS.registerSimpleBlockItem("raw_titanium_block", BlockInit.RAW_TITANIUM_BLOCK, new Item.Properties()));
    public static final Supplier<BlockItem> TITANIUM_BLOCK_ITEM = addToTab(ITEMS.registerSimpleBlockItem("titanium_block", BlockInit.TITANIUM_BLOCK ,new Item.Properties()));
    public static final Supplier<BlockItem> ICE_LANTERN_ITEM = addToTab(ITEMS.registerSimpleBlockItem("ice_lantern", BlockInit.ICE_LANTERN, new Item.Properties()));
    public static final Supplier<BlockItem> TITANIUM_CHAIN_ITEM = addToTab(ITEMS.registerSimpleBlockItem("titanium_chain", BlockInit.TITANIUM_CHAIN, new Item.Properties()));
// Ferrotitanium BlockItems
    public static final Supplier<BlockItem> FERROTITANIUM_BLOCK_ITEM = addToTab(ITEMS.registerSimpleBlockItem("ferrotitanium_block", BlockInit.FERROTITANIUM_BLOCK,new Item.Properties()));
    public static final Supplier<BlockItem> BLAZE_LANTERN_ITEM = addToTab(ITEMS.registerSimpleBlockItem("blaze_lantern", BlockInit.BLAZE_LANTERN, new Item.Properties()));
    public static final Supplier<BlockItem> FERROTITANIUM_CHAIN_ITEM = addToTab(ITEMS.registerSimpleBlockItem("ferrotitanium_chain", BlockInit.FERROTITANIUM_CHAIN, new Item.Properties()));
// Flerovium BlockItems
    public static final Supplier<BlockItem> DEEPSLATE_FLEROVIUM_ORE_ITEM = addToTab(ITEMS.registerSimpleBlockItem("deepslate_flerovium_ore", BlockInit.DEEPSLATE_FLEROVIUM_ORE, new Item.Properties().rarity(Rarity.EPIC)));
    public static final Supplier<BlockItem> FLEROVIUM_BLOCK_ITEM = addToTab(ITEMS.registerSimpleBlockItem("flerovium_block", BlockInit.FLEROVIUM_BLOCK, new Item.Properties().rarity(Rarity.EPIC)));
// Vexal BlockItems
    public static final Supplier<BlockItem> VEXAL_BLOCK = addToTab(ITEMS.registerSimpleBlockItem("vexal_block", BlockInit.VEXAL_BLOCK, new Item.Properties()));
    public static final Supplier<BlockItem> BUDDING_VEXAL_BLOCK = addToTab(ITEMS.registerSimpleBlockItem("budding_vexal_block", BlockInit.BUDDING_VEXAL_BLOCK, new Item.Properties()));
    public static final Supplier<BlockItem> SMALL_VEXAL_BUD_ITEM = addToTab(ITEMS.registerSimpleBlockItem("small_vexal_bud", BlockInit.SMALL_VEXAL_BUD, new Item.Properties()));
    public static final Supplier<BlockItem> MEDIUM_VEXAL_BUD_ITEM = addToTab(ITEMS.registerSimpleBlockItem("medium_vexal_bud", BlockInit.MEDIUM_VEXAL_BUD, new Item.Properties()));
    public static final Supplier<BlockItem> LARGE_VEXAL_BUD_ITEM = addToTab(ITEMS.registerSimpleBlockItem("large_vexal_bud", BlockInit.LARGE_VEXAL_BUD, new Item.Properties()));
    public static final Supplier<BlockItem> VEXAL_CLUSTER_ITEM = addToTab(ITEMS.registerSimpleBlockItem("vexal_cluster", BlockInit.VEXAL_CLUSTER, new Item.Properties()));

// ## ITEMS
// Titanium Items
    public static final Supplier<Item> RAW_TITANIUM = addToTab(ITEMS.registerSimpleItem("raw_titanium"));
    public static final Supplier<Item> TITANIUM_INGOT = addToTab(ITEMS.registerSimpleItem("titanium_ingot"));
    public static final Supplier<Item> TITANIUM_NUGGET = addToTab(ITEMS.registerSimpleItem("titanium_nugget"));
// Ferrotitanium Items
    public static final Supplier<Item> FERROTITANIUM_BLEND = addToTab(ITEMS.registerSimpleItem("ferrotitanium_blend"));
    public static final Supplier<Item> FERROTITANIUM_INGOT = addToTab(ITEMS.registerSimpleItem("ferrotitanium_ingot"));
    public static final Supplier<Item> FERROTITANIUM_NUGGET = addToTab(ITEMS.registerSimpleItem("ferrotitanium_nugget"));
// Vexal Items
    public static final Supplier<VexalCrystalItem> VEXAL_CRYSTAL = addToTab(ITEMS.registerItem("vexal_crystal", VexalCrystalItem::new));
    public static final Supplier<Item> INFUSED_VEXAL_CRYSTAL = addToTab(ITEMS.registerSimpleItem("infused_vexal_crystal"));
// Flerovium Items
    public static final Supplier<Item> FLEROVIUM_SHARD = addToTab(ITEMS.registerItem("flerovium_shard", Item::new, new Item.Properties().rarity(Rarity.EPIC)));
    public static final Supplier<Item> FLEROVIUM_CRYSTAL = addToTab(ITEMS.registerItem("flerovium_crystal", Item::new, new Item.Properties().rarity(Rarity.EPIC)));
// Misc
    public static final Supplier<Item> BLAZE_GEM = addToTab(ITEMS.registerSimpleItem("blaze_gem"));
    public static final Supplier<Item> ICE_GEM = addToTab(ITEMS.registerSimpleItem("ice_gem"));
    public static final Supplier<Item> COLD_BLAZE_ROD = addToTab(ITEMS.registerSimpleItem("cold_blaze_rod"));
    public static final Supplier<Item> OBSIDIAN_ROD = addToTab(ITEMS.registerSimpleItem("obsidian_rod"));
    public static final Supplier<Item> SHARK_TOOTH = addToTab(ITEMS.registerSimpleItem("shark_tooth"));
    public static final Supplier<Item> GOLEM_GYRO = addToTab(ITEMS.registerItem("golem_gyro", Item::new, new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
    public static final Supplier<InactiveUpgradeItem> FLEROVIUM_UPGRADE_SMITHING_TEMPLATE_INACTIVE = addToTab(ITEMS.registerItem("flerovium_upgrade_smithing_template_inactive",
            InactiveUpgradeItem::new, new Item.Properties()));
    public static final Supplier<Item> FLEROVIUM_UPGRADE_SMITHING_TEMPLATE = addToTab(ITEMS.register("flerovium_upgrade_smithing_template",
            FleroviumSmithingTemplateItem::createFleroviumUpgradeTemplate));
// Uncertain Additions
    public static final Supplier<Item> OMEGA = addToTab(ITEMS.registerItem("omega", Item::new, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

// ## Tools & Weapons (Shovel - Pickaxe - Axe - Hoe - Sword)
// Titanium Tools & Weapons
    public static final Supplier<TitaniumShovelItem> TITANIUM_SHOVEL = addToTab(ITEMS.register("titanium_shovel",
            () -> new TitaniumShovelItem(
                    TierInit.TitaniumTier,
                    new Item.Properties().attributes(TitaniumShovelItem.createAttributes(TierInit.TitaniumTier, 1.5f, -3.0f))
            )));
    public static final Supplier<TitaniumPickaxeItem> TITANIUM_PICKAXE = addToTab(ITEMS.register("titanium_pickaxe",
            () -> new TitaniumPickaxeItem(
                    TierInit.TitaniumTier,
                    new Item.Properties().attributes(TitaniumPickaxeItem.createAttributes(TierInit.TitaniumTier, 1, -2.8f))
            )));
    public static final Supplier<TitaniumAxeItem> TITANIUM_AXE = addToTab(ITEMS.register("titanium_axe",
            () -> new TitaniumAxeItem(
                    TierInit.TitaniumTier,
                    new Item.Properties().attributes(TitaniumAxeItem.createAttributes(TierInit.TitaniumTier, 6, -3.1f))
            )));
    public static final Supplier<TitaniumHoeItem> TITANIUM_HOE = addToTab(ITEMS.register("titanium_hoe",
            () -> new TitaniumHoeItem(
                    TierInit.TitaniumTier,
                    new Item.Properties().attributes(TitaniumHoeItem.createAttributes(TierInit.TitaniumTier, -2, -1.0f))
            )));
    public static final Supplier<TitaniumSwordItem> TITANIUM_SWORD = addToTab(ITEMS.register("titanium_sword",
            () -> new TitaniumSwordItem(
                    TierInit.TitaniumTier,
                    new Item.Properties().attributes(TitaniumSwordItem.createAttributes(TierInit.TitaniumTier, 3.2f, -2.4f))
            )));
    public static final Supplier<TitaniumDaggerItem> TITANIUM_DAGGER = addToTab(ITEMS.register("titanium_dagger",
            () -> new TitaniumDaggerItem(
                    TierInit.TitaniumTier,
                    new Item.Properties().attributes(TitaniumDaggerItem.createAttributes(TierInit.TitaniumTier, 0.2f, -1.0f))
            )));
// Ferrotitanium Tools & Weapons
    public static final Supplier<FerrotitaniumShovelItem> FERROTITANIUM_SHOVEL = addToTab(ITEMS.register("ferrotitanium_shovel",
            () -> new FerrotitaniumShovelItem(
                    TierInit.FerrotitaniumTier,
                    new Item.Properties().attributes(FerrotitaniumShovelItem.createAttributes(TierInit.FerrotitaniumTier, 1.5f, -3.0f))
            )));
    public static final Supplier<FerrotitaniumPickaxeItem> FERROTITANIUM_PICKAXE = addToTab(ITEMS.register("ferrotitanium_pickaxe",
            () -> new FerrotitaniumPickaxeItem(
                    TierInit.FerrotitaniumTier,
                    new Item.Properties().attributes(FerrotitaniumPickaxeItem.createAttributes(TierInit.FerrotitaniumTier, 1, -2.8f))
            )));
    public static final Supplier<FerrotitaniumAxeItem> FERROTITANIUM_AXE = addToTab(ITEMS.register("ferrotitanium_axe",
            () -> new FerrotitaniumAxeItem(
                    TierInit.FerrotitaniumTier,
                    new Item.Properties().attributes(FerrotitaniumAxeItem.createAttributes(TierInit.FerrotitaniumTier, 5, -3.0f))
            )));
    public static final Supplier<FerrotitaniumHoeItem> FERROTITANIUM_HOE = addToTab(ITEMS.register("ferrotitanium_hoe",
            () -> new FerrotitaniumHoeItem(
                    TierInit.FerrotitaniumTier,
                    new Item.Properties().attributes(FerrotitaniumHoeItem.createAttributes(TierInit.FerrotitaniumTier, -3, 0.0f))
            )));
    public static final Supplier<FerrotitaniumSwordItem> FERROTITANIUM_SWORD = addToTab(ITEMS.register("ferrotitanium_sword",
            () -> new FerrotitaniumSwordItem(
                    TierInit.FerrotitaniumSwordTier,
                    new Item.Properties().attributes(FerrotitaniumSwordItem.createAttributes(TierInit.FerrotitaniumSwordTier, 3, -2.4f))
            )));
    public static final Supplier<FerrotitaniumDaggerItem> FERROTITANIUM_DAGGER = addToTab(ITEMS.register("ferrotitanium_dagger",
            () -> new FerrotitaniumDaggerItem(
                    TierInit.FerrotitaniumSwordTier,
                    new Item.Properties().attributes(FerrotitaniumDaggerItem.createAttributes(TierInit.FerrotitaniumSwordTier, 0, -1.0f))
            )));
// Flerovium Tools & Weapons
    public static final Supplier<FleroviumShovelItem> FLEROVIUM_SHOVEL = addToTab(ITEMS.register("flerovium_shovel",
            () -> new FleroviumShovelItem(
                    TierInit.FleroviumTier,
                    new Item.Properties().rarity(Rarity.EPIC).attributes(FleroviumShovelItem.createAttributes(TierInit.FleroviumTier, 0.5f, -3.0f))
            )));
    public static final Supplier<FleroviumPickaxeItem> FLEROVIUM_PICKAXE = addToTab(ITEMS.register("flerovium_pickaxe",
            () -> new FleroviumPickaxeItem(
                    TierInit.FleroviumTier,
                    new Item.Properties().rarity(Rarity.EPIC).attributes(FleroviumPickaxeItem.createAttributes(TierInit.FleroviumTier, 0.0f, -2.8f))
            )));
    public static final Supplier<FleroviumAxeItem> FLEROVIUM_AXE = addToTab(ITEMS.register("flerovium_axe",
            () -> new FleroviumAxeItem(
                    TierInit.FleroviumTier,
                    new Item.Properties().rarity(Rarity.EPIC).attributes(FleroviumAxeItem.createAttributes(TierInit.FleroviumTier, 4.0f, -3.0f))
            )));
    public static final Supplier<FleroviumHoeItem> FLEROVIUM_HOE = addToTab(ITEMS.register("flerovium_hoe",
            () -> new FleroviumHoeItem(
                    TierInit.FleroviumTier,
                    new Item.Properties().rarity(Rarity.EPIC).attributes(FleroviumHoeItem.createAttributes(TierInit.FleroviumTier, -5.0f, 0.0f))
            )));
    public static final Supplier<FleroviumSwordItem> FLEROVIUM_SWORD = addToTab(ITEMS.register("flerovium_sword",
            () -> new FleroviumSwordItem(
                    TierInit.FleroviumTier,
                    new Item.Properties().rarity(Rarity.EPIC).attributes(FleroviumSwordItem.createAttributes(TierInit.FleroviumTier, 3, -2.4f))
            )));
    public static final Supplier<FleroviumDaggerItem> FLEROVIUM_DAGGER = addToTab(ITEMS.register("flerovium_dagger",
            () -> new FleroviumDaggerItem(
                    TierInit.FleroviumTier,
                    new Item.Properties().rarity(Rarity.EPIC).attributes(FleroviumDaggerItem.createAttributes(TierInit.FleroviumTier, 0, -1.0f))
            )));

// Vanilla Daggers
    public static final Supplier<VanillaDaggerItem> IRON_DAGGER = addToTab(ITEMS.register("iron_dagger",
            () -> new VanillaDaggerItem(
                    Tiers.IRON,
                    new Item.Properties().attributes(VanillaDaggerItem.createAttributes(Tiers.IRON, 0, -1.0f))
            )));
    public static final Supplier<VanillaDaggerItem> GOLD_DAGGER = addToTab(ITEMS.register("gold_dagger",
            () -> new VanillaDaggerItem(
                    Tiers.GOLD,
                    new Item.Properties().attributes(VanillaDaggerItem.createAttributes(Tiers.GOLD, 0, -1.0f))
            )));
    public static final Supplier<VanillaDaggerItem> DIAMOND_DAGGER = addToTab(ITEMS.register("diamond_dagger",
            () -> new VanillaDaggerItem(
                    Tiers.DIAMOND,
                    new Item.Properties().attributes(VanillaDaggerItem.createAttributes(Tiers.DIAMOND, 0, -1.0f))
            )));
    public static final Supplier<VanillaDaggerItem> NETHERITE_DAGGER = addToTab(ITEMS.register("netherite_dagger",
            () -> new VanillaDaggerItem(
                    Tiers.NETHERITE,
                    new Item.Properties().attributes(VanillaDaggerItem.createAttributes(Tiers.NETHERITE, 1, -1.0f))
            )));
// Misc Tools & Weapons
    public static final Supplier<VexalSwordItem> VEXAL_SWORD = addToTab(ITEMS.register("vexal_sword",
            () -> new VexalSwordItem(
                    TierInit.VexalTier,
                    new Item.Properties().attributes(VexalSwordItem.createAttributes(TierInit.VexalTier, 3, -2.4f))
            )));
    public static final Supplier<VexalSwordInfusedItem> INFUSED_VEXAL_SWORD = addToTab(ITEMS.register("infused_vexal_sword",
            () -> new VexalSwordInfusedItem(
                    TierInit.InfusedVexalTier,
                    new Item.Properties().rarity(Rarity.RARE).attributes(VexalSwordInfusedItem.createAttributes(TierInit.InfusedVexalTier, 3, -2.4f))
            )));
    public static final Supplier<BlazeSwordItem> BLAZE_SWORD = addToTab(ITEMS.register("blaze_sword",
            () -> new BlazeSwordItem(
                    TierInit.BlazeTier,
                    new Item.Properties().rarity(Rarity.UNCOMMON).attributes(BlazeSwordItem.createAttributes(TierInit.BlazeTier, 3, -2.4f))
            )));
    public static final Supplier<IceSwordItem> ICE_SWORD = addToTab(ITEMS.register("ice_sword",
            () -> new IceSwordItem(
                    TierInit.IceTier,
                    new Item.Properties().rarity(Rarity.RARE).attributes(IceSwordItem.createAttributes(TierInit.IceTier, 3, -2.4f))
            )));
    public static final Supplier<WingedSwordItem> WINGED_BLADE = addToTab(ITEMS.register("winged_blade",
            () -> new WingedSwordItem(
                    TierInit.WingedTier,
                    new Item.Properties().rarity(Rarity.UNCOMMON).attributes(WingedSwordItem.createAttributes(TierInit.WingedTier, 4, -2.4f))
            )));
    public static final Supplier<RazorSwordItem> RAZOR_SWORD = addToTab(ITEMS.register("razor_sword",
            () -> new RazorSwordItem(
                    TierInit.RazorTier,
                    new Item.Properties().rarity(Rarity.UNCOMMON).attributes(RazorSwordItem.createAttributes(TierInit.RazorTier, 3, -2.4f))
            )));
    public static final Supplier<GyroMaceItem> GYRO_MACE = addToTab(ITEMS.register("gyro_mace",
            () -> new GyroMaceItem(
                    TierInit.GyroTier,
                    new Item.Properties().rarity(Rarity.UNCOMMON).attributes(GyroMaceItem.createAttributes(TierInit.GyroTier, 3, -2.8f))
            )));
    public static final Supplier<ShadowDaggerItem> SHADOW_DAGGER = addToTab(ITEMS.register("shadow_dagger",
            () -> new ShadowDaggerItem(
                    TierInit.ShadowTier,
                    new Item.Properties().rarity(Rarity.EPIC).attributes(ShadowDaggerItem.createAttributes(TierInit.ShadowTier, 1, -1.0f))
            )));

// ## Armors (Helmet - Chestplate - Leggings - Boots)
// Titanium Armor
    public static final Supplier<TitaniumArmorItem> TITANIUM_HELMET = addToTab(ITEMS.register("titanium_helmet",
            () -> new TitaniumArmorItem(
                    ArmorMaterialInit.TITANIUM,
                    TitaniumArmorItem.Type.HELMET,
                    new Item.Properties().durability(210)
            )));
    public static final Supplier<TitaniumArmorItem> TITANIUM_CHESTPLATE = addToTab(ITEMS.register("titanium_chestplate",
            () -> new TitaniumArmorItem(
                    ArmorMaterialInit.TITANIUM,
                    TitaniumArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(285)
            )));
    public static final Supplier<TitaniumArmorItem> TITANIUM_LEGGINGS = addToTab(ITEMS.register("titanium_leggings",
            () -> new TitaniumArmorItem(
                    ArmorMaterialInit.TITANIUM,
                    TitaniumArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(270)
            )));
    public static final Supplier<TitaniumArmorItem> TITANIUM_BOOTS = addToTab(ITEMS.register("titanium_boots",
            () -> new TitaniumArmorItem(
                    ArmorMaterialInit.TITANIUM,
                    TitaniumArmorItem.Type.BOOTS,
                    new Item.Properties().durability(240)
            )));
// Ferrotitanium Armor
    public static final Supplier<FerrotitaniumArmorItem> FERROTITANIUM_HELMET = addToTab(ITEMS.register("ferrotitanium_helmet",
            () -> new FerrotitaniumArmorItem(
                    ArmorMaterialInit.FERROTITANIUM,
                    FerrotitaniumArmorItem.Type.HELMET,
                    new Item.Properties().durability(495)
            )));
    public static final Supplier<FerrotitaniumArmorItem> FERROTITANIUM_CHESTPLATE = addToTab(ITEMS.register("ferrotitanium_chestplate",
            () -> new FerrotitaniumArmorItem(
                    ArmorMaterialInit.FERROTITANIUM,
                    FerrotitaniumArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(660)
            )));
    public static final Supplier<FerrotitaniumArmorItem> FERROTITANIUM_LEGGINGS = addToTab(ITEMS.register("ferrotitanium_leggings",
            () -> new FerrotitaniumArmorItem(
                    ArmorMaterialInit.FERROTITANIUM,
                    FerrotitaniumArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(627)
            )));
    public static final Supplier<FerrotitaniumArmorItem> FERROTITANIUM_BOOTS = addToTab(ITEMS.register("ferrotitanium_boots",
            () -> new FerrotitaniumArmorItem(
                    ArmorMaterialInit.FERROTITANIUM,
                    FerrotitaniumArmorItem.Type.BOOTS,
                    new Item.Properties().durability(561)
            )));
// Flerovium Armor
    public static final Supplier<FleroviumArmorItem> FLEROVIUM_HELMET = addToTab(ITEMS.register("flerovium_helmet",
            () -> new FleroviumArmorItem(
                    ArmorMaterialInit.FLEROVIUM,
                    FleroviumArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.EPIC).durability(660)
            )));
    public static final Supplier<FleroviumArmorItem> FLEROVIUM_CHESTPLATE = addToTab(ITEMS.register("flerovium_chestplate",
            () -> new FleroviumArmorItem(
                    ArmorMaterialInit.FLEROVIUM,
                    FleroviumArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(Rarity.EPIC).durability(960)
            )));
    public static final Supplier<FleroviumArmorItem> FLEROVIUM_LEGGINGS = addToTab(ITEMS.register("flerovium_leggings",
            () -> new FleroviumArmorItem(
                    ArmorMaterialInit.FLEROVIUM,
                    FleroviumArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(Rarity.EPIC).durability(900)
            )));
    public static final Supplier<FleroviumArmorItem> FLEROVIUM_BOOTS = addToTab(ITEMS.register("flerovium_boots",
            () -> new FleroviumArmorItem(
                    ArmorMaterialInit.FLEROVIUM,
                    FleroviumArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(Rarity.EPIC).durability(780)
            )));
// Ice Armor
    public static final Supplier<IceArmorItem> ICE_HELMET = addToTab(ITEMS.register("ice_helmet",
            () -> new IceArmorItem(
                    ArmorMaterialInit.ICE,
                    IceArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.RARE).durability(330)
            )));
    public static final Supplier<IceArmorItem> ICE_CHESTPLATE = addToTab(ITEMS.register("ice_chestplate",
            () -> new IceArmorItem(
                    ArmorMaterialInit.ICE,
                    IceArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(Rarity.RARE).durability(480)
            )));
    public static final Supplier<IceArmorItem> ICE_LEGGINGS = addToTab(ITEMS.register("ice_leggings",
            () -> new IceArmorItem(
                    ArmorMaterialInit.ICE,
                    IceArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(Rarity.RARE).durability(450)
            )));
    public static final Supplier<IceArmorItem> ICE_BOOTS = addToTab(ITEMS.register("ice_boots",
            () -> new IceArmorItem(
                    ArmorMaterialInit.ICE,
                    IceArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(Rarity.RARE).durability(390)
            )));
}
