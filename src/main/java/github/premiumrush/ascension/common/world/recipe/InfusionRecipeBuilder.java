package github.premiumrush.ascension.common.world.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class InfusionRecipeBuilder extends BaseRecipeBuilder {
    private final Ingredient baseItem;
    private final Ingredient catalystItem;
    private final boolean oreBoolean;

    public InfusionRecipeBuilder(ItemStack result, Ingredient base, Ingredient catalyst, boolean oreBoolean) {
        super(result);
        this.baseItem = base;
        this.catalystItem = catalyst;
        this.oreBoolean = oreBoolean;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation resourceLocation) {
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
                .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        InfusionRecipe recipe = new InfusionRecipe(this.baseItem, this.catalystItem, this.result, this.oreBoolean);
        output.accept(resourceLocation, recipe, advancement.build(resourceLocation.withPrefix("recipes/")));
    }
}
