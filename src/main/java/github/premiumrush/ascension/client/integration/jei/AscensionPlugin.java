package github.premiumrush.ascension.client.integration.jei;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.recipe.infusion.InfusionRecipe;
import github.premiumrush.ascension.common.init.ItemInit;
import github.premiumrush.ascension.common.init.RecipeInit;
import github.premiumrush.ascension.common.world.recipe.refiner.RefinerRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@JeiPlugin
@OnlyIn(Dist.CLIENT)
public class AscensionPlugin implements IModPlugin {
    static final RecipeType<InfusionRecipe> INFUSION_RECIPE = RecipeType.create(Ascension.MODID,
            "infusion",
            InfusionRecipe.class
    );
    static final RecipeType<RefinerRecipe> REFINER_RECIPE = RecipeType.create(Ascension.MODID,
            "enchantment_refining",
            RefinerRecipe.class
    );

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "main");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new InfusionRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new RefinerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Minecraft mc = Minecraft.getInstance();
        registration.addRecipes(INFUSION_RECIPE,
                mc.level.getRecipeManager().getAllRecipesFor(RecipeInit.INFUSION_RECIPE_TYPE.get())
                        .stream()
                        .map(RecipeHolder::value)
                        .toList()
        );
        registration.addRecipes(REFINER_RECIPE,
                mc.level.getRecipeManager().getAllRecipesFor(RecipeInit.REFINER_RECIPE_TYPE.get())
                        .stream()
                        .map(RecipeHolder::value)
                        .toList()
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ItemInit.INFUSING_TABLE.get()), INFUSION_RECIPE);
        registration.addRecipeCatalyst(new ItemStack(ItemInit.ENCHANTMENT_REFINER.get()), REFINER_RECIPE);
        registration.addRecipeCatalyst(new ItemStack(ItemInit.INFUSED_VEXAL_CRYSTAL.get()), REFINER_RECIPE);
    }
}
