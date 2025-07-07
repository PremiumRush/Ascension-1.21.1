package github.premiumrush.ascension.datagen.recipe;

import github.premiumrush.ascension.common.world.recipe.infusion.InfusionRecipeBuilder;
import github.premiumrush.ascension.common.init.ItemInit;
import github.premiumrush.ascension.common.world.recipe.refiner.RefinerRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        addInfusionRecipe(ItemInit.VEXAL_CRYSTAL.get(), ItemInit.FLEROVIUM_SHARD.get(), ItemInit.INFUSED_VEXAL_CRYSTAL.get(), 1, output);
        addInfusionRecipe(ItemInit.FLEROVIUM_UPGRADE_SMITHING_TEMPLATE_INACTIVE.get(), ItemInit.FLEROVIUM_CRYSTAL.get(), ItemInit.FLEROVIUM_UPGRADE_SMITHING_TEMPLATE.get(), 1, output);
        addInfusionRecipe(Items.AIR, Items.STRING, Items.COBWEB, 1, output);
        addInfusionRecipe(Items.AIR, Items.BLAZE_ROD, Items.BLAZE_POWDER, 4, output);
        addInfusionRecipe(Items.POISONOUS_POTATO, ItemInit.FLEROVIUM_SHARD.get(), ItemInit.CURATIVE_POTATO.get(), 8, output);
        addInfusionRecipe(Items.AIR, ItemInit.COLD_BLAZE_ROD.get(), ItemInit.ICE_BLAZE_POWDER.get(), 4, output);
        addInfusionRecipe(Items.AIR ,Items.BREEZE_ROD, Items.WIND_CHARGE, 8, output);
        addInfusionRecipe(Items.GOLDEN_APPLE, ItemInit.FLEROVIUM_SHARD.get(), Items.ENCHANTED_GOLDEN_APPLE, 1, output);
        addInfusionRecipe(Items.SKELETON_SKULL, Items.WITHER_ROSE, Items.WITHER_SKELETON_SKULL, 1, output);
        addInfusionRecipe(Items.DRAGON_BREATH, ItemInit.VEXAL_CRYSTAL.get(), Items.EXPERIENCE_BOTTLE, 2, output);
        addInfusionRecipe(Items.INK_SAC, Items.GLOWSTONE_DUST, Items.GLOW_INK_SAC, 1, output);
        addInfusionRecipe(Items.ENDER_EYE, Items.GHAST_TEAR, Items.END_CRYSTAL, 1, output);
        addInfusionRecipe(Items.OBSIDIAN, Items.AMETHYST_SHARD, Items.CRYING_OBSIDIAN, 1, output);
        addInfusionRecipe(Items.DIRT, Items.BONE_MEAL, Items.GRASS_BLOCK, 1, output);
        addInfusionRecipe(Items.DIRT, Items.WHEAT_SEEDS, Items.ROOTED_DIRT, 1, output);
        addInfusionRecipe(Items.NETHERRACK, Items.WARPED_ROOTS, Items.WARPED_NYLIUM, 2, output);
        addInfusionRecipe(Items.NETHERRACK, Items.CRIMSON_ROOTS, Items.CRIMSON_NYLIUM, 2, output);
        addInfusionRecipe(Items.DIRT, Items.BROWN_MUSHROOM, Items.MYCELIUM, 2, output);
        addInfusionRecipe(Items.BLACKSTONE, Items.GOLD_INGOT, Items.GILDED_BLACKSTONE, 2, output);
        addInfusionRecipe(Items.MACE, ItemInit.GOLEM_GYRO.get(), Items.MACE, 1, output);

        // Ore Conversions
        addOreInfusionRecipe(Items.AIR, Items.AMETHYST_BLOCK, Items.AMETHYST_SHARD, 4, output, "");
        addOreInfusionRecipe(Items.AIR, ItemInit.VEXAL_BLOCK.get(), ItemInit.VEXAL_CRYSTAL.get(), 4, output, "");
        addOreInfusionRecipe(Items.ANCIENT_DEBRIS, Items.GOLD_BLOCK, Items.NETHERITE_SCRAP, 2, output,"");
        addOreInfusionRecipe(Items.AIR, Items.RAW_IRON, Items.IRON_INGOT, 2, output, "_raw");
        addOreInfusionRecipe(Items.AIR, Items.IRON_ORE, Items.IRON_INGOT, 4, output, "_ore");
        addOreInfusionRecipe(Items.AIR, Items.DEEPSLATE_IRON_ORE, Items.IRON_INGOT, 4, output, "_deep_ore");
        addOreInfusionRecipe(Items.AIR, Items.RAW_GOLD, Items.GOLD_INGOT, 2, output, "_raw");
        addOreInfusionRecipe(Items.AIR, Items.GOLD_ORE, Items.GOLD_INGOT, 4, output, "_ore");
        addOreInfusionRecipe(Items.AIR, Items.DEEPSLATE_GOLD_ORE, Items.GOLD_INGOT, 4, output, "_deep_ore");
        addOreInfusionRecipe(Items.AIR, Items.NETHER_GOLD_ORE, Items.GOLD_INGOT, 2, output, "_nether_ore");
        addOreInfusionRecipe(Items.AIR, Items.COAL_ORE, Items.COAL, 6, output, "_ore");
        addOreInfusionRecipe(Items.AIR, Items.DEEPSLATE_COAL_ORE, Items.COAL, 6, output,"_deep_ore");
        addOreInfusionRecipe(Items.AIR, Items.RAW_COPPER, Items.COPPER_INGOT, 2, output, "_raw");
        addOreInfusionRecipe(Items.AIR, Items.COPPER_ORE, Items.COPPER_INGOT, 8, output, "_ore");
        addOreInfusionRecipe(Items.AIR, Items.DEEPSLATE_COPPER_ORE, Items.COPPER_INGOT, 8, output,"_deep_ore");
        addOreInfusionRecipe(Items.AIR, Items.REDSTONE_ORE, Items.REDSTONE, 16, output, "_ore");
        addOreInfusionRecipe(Items.AIR, Items.DEEPSLATE_REDSTONE_ORE, Items.REDSTONE, 16, output,"_deep_ore");
        addOreInfusionRecipe(Items.AIR, Items.EMERALD_ORE, Items.EMERALD, 4, output, "_ore");
        addOreInfusionRecipe(Items.AIR, Items.DEEPSLATE_EMERALD_ORE, Items.EMERALD, 4, output,"_deep_ore");
        addOreInfusionRecipe(Items.AIR, Items.LAPIS_ORE, Items.LAPIS_LAZULI, 24, output, "_ore");
        addOreInfusionRecipe(Items.AIR, Items.DEEPSLATE_LAPIS_ORE, Items.LAPIS_LAZULI, 24, output,"_deep_ore");
        addOreInfusionRecipe(Items.AIR, ItemInit.RAW_TITANIUM.get(), ItemInit.TITANIUM_INGOT.get(), 2, output, "_raw");
        addOreInfusionRecipe(Items.AIR, ItemInit.TITANIUM_ORE_ITEM.get(), ItemInit.TITANIUM_INGOT.get(), 4, output, "_ore");
        addOreInfusionRecipe(Items.AIR, ItemInit.DEEPSLATE_TITANIUM_ORE_ITEM.get(), ItemInit.TITANIUM_INGOT.get(), 4, output,"_deep_ore");
        addOreInfusionRecipe(Items.AIR, Items.DIAMOND_ORE, Items.DIAMOND, 4, output, "_ore");
        addOreInfusionRecipe(Items.AIR, Items.DEEPSLATE_DIAMOND_ORE, Items.DIAMOND, 4, output,"_deep_ore");
        addOreInfusionRecipe(Items.AIR, Items.NETHER_QUARTZ_ORE, Items.QUARTZ, 6, output,"");
        addOreInfusionRecipe(Items.AIR, ItemInit.DEEPSLATE_FLEROVIUM_ORE_ITEM.get(), ItemInit.FLEROVIUM_SHARD.get(), 3, output,"");



        addRefiningRecipe("minecraft:protection", Items.SHULKER_SHELL, output, "protection");
        addRefiningRecipe("minecraft:fire_protection", Items.FIRE_CHARGE, output, "fire_protection");
        addRefiningRecipe("minecraft:feather_falling", Items.FEATHER, output, "feather_falling");
        addRefiningRecipe("minecraft:blast_protection", Items.TNT, output, "blast_protection");
        addRefiningRecipe("minecraft:projectile_protection", Items.ARROW, output, "projectile_protection");
        addRefiningRecipe("minecraft:respiration", Items.TURTLE_HELMET, output, "respiration");
        addRefiningRecipe("minecraft:thorns", Items.CACTUS, output, "thorns");
        addRefiningRecipe("minecraft:depth_strider", Items.TURTLE_SCUTE, output, "depth_strider");
        addRefiningRecipe("minecraft:frost_walker", ItemInit.ICE_BLAZE_POWDER.get(), output, "frost_walker");
        addRefiningRecipe("minecraft:soul_speed", Items.RABBIT_FOOT, output, "soul_speed");
        addRefiningRecipe("minecraft:swift_sneak", Items.ARMADILLO_SCUTE, output, "swift_sneak");
        addRefiningRecipe("minecraft:sharpness", Items.AMETHYST_SHARD, output, "sharpness");
        addRefiningRecipe("minecraft:smite", Items.GOLDEN_APPLE, output, "smite");
        addRefiningRecipe("minecraft:bane_of_arthropods", Items.COBWEB, output, "bane_of_arthropods");
        addRefiningRecipe("minecraft:knockback", ItemInit.GOLEM_GYRO.get(), output, "knockback");
        addRefiningRecipe("minecraft:fire_aspect", Items.BLAZE_POWDER, output, "fire_aspect");
        addRefiningRecipe("minecraft:looting", Items.GLOWSTONE_DUST, output, "looting");
        addRefiningRecipe("minecraft:sweeping_edge", ItemInit.OBSIDIAN_ROD.get(), output, "sweeping_edge");
        addRefiningRecipe("minecraft:efficiency", Items.DRAGON_BREATH, output, "efficiency");
        addRefiningRecipe("minecraft:unbreaking", Items.NETHERITE_INGOT, output, "unbreaking");
        addRefiningRecipe("minecraft:fortune", Items.EMERALD_BLOCK, output, "fortune");
        addRefiningRecipe("minecraft:power", Items.NETHER_STAR, output, "power");
        addRefiningRecipe("minecraft:punch", ItemInit.GOLEM_GYRO.get(), output, "punch");
        addRefiningRecipe("minecraft:luck_of_the_sea", Items.HEART_OF_THE_SEA, output, "luck_of_the_sea");
        addRefiningRecipe("minecraft:lure", Items.TROPICAL_FISH, output, "lure");
        addRefiningRecipe("minecraft:loyalty", Items.ENDER_PEARL, output, "loyalty");
        addRefiningRecipe("minecraft:impaling", Items.PRISMARINE_SHARD, output, "impaling");
        addRefiningRecipe("minecraft:riptide", Items.PRISMARINE_CRYSTALS, output, "riptide");
        addRefiningRecipe("minecraft:quick_charge", Items.CLOCK, output, "quick_charge");
        addRefiningRecipe("minecraft:piercing", ItemInit.SHARK_TOOTH.get(), output, "piercing");
        addRefiningRecipe("minecraft:density", Items.IRON_BLOCK, output, "density");
        addRefiningRecipe("minecraft:breach", Items.FLINT, output, "breach");
        addRefiningRecipe("minecraft:wind_burst", Items.WIND_CHARGE, output, "wind_burst");
    }

    private void addInfusionRecipe(ItemLike base, ItemLike catalyst, ItemLike result, int count, RecipeOutput output) {
        new InfusionRecipeBuilder(
                new ItemStack(result, count),
                Ingredient.of(base),
                Ingredient.of(catalyst),
                false
        ).unlockedBy("has_infusion_table", has(ItemInit.INFUSING_TABLE.get()))
                .save(output, result.toString()+"_infusion");
    }

    private void addOreInfusionRecipe(ItemLike base, ItemLike catalyst, ItemLike result, int count, RecipeOutput output, String nameaddtion) {
        new InfusionRecipeBuilder(
                new ItemStack(result, count),
                Ingredient.of(base),
                Ingredient.of(catalyst),
                true
        ).unlockedBy("has_infusion_table", has(ItemInit.INFUSING_TABLE.get()))
                .save(output, result.toString()+nameaddtion+"_infusion");
    }

    private void addRefiningRecipe(String enchantment, ItemLike catalyst, RecipeOutput output, String name) {
        new RefinerRecipeBuilder(
            enchantment,
            Ingredient.of(catalyst)
        ).unlockedBy("has_enchantment_refiner", has(ItemInit.ENCHANTMENT_REFINER.get()))
                .save(output, name+"_refining");
    }
}
