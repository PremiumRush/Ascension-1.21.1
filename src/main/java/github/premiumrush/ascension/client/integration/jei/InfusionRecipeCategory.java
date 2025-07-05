package github.premiumrush.ascension.client.integration.jei;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.recipe.InfusionRecipe;
import github.premiumrush.ascension.common.init.ItemInit;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class InfusionRecipeCategory implements IRecipeCategory<InfusionRecipe> {
    public static final int OUTPUT_GRID_X = 76;
    public static final int OUTPUT_GRID_Y = 10;
    private final IDrawable slot;
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;
    private final Component oreInfusion;

    public InfusionRecipeCategory(IGuiHelper helper) {
        this.title = Component.translatable("jei.ascension.title");
        ResourceLocation backgroundImage = ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "textures/gui/jei/infusion_table.png");
        this.slot = helper.createDrawable(backgroundImage, 0, 58, 18, 18);
        this.background = helper.createDrawable(backgroundImage, 0, 0, 117, 57);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemInit.INFUSING_TABLE.get()));
        this.oreInfusion = Component.translatable("jei.ascension.ore_infusion");
    }

    @Override
    public RecipeType<InfusionRecipe> getRecipeType() {
        return AscensionPlugin.INFUSION_RECIPE;
    }

    @Override
    public Component getTitle() {
        return this.title;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public int getWidth() {
        return 117;
    }

    @Override
    public int getHeight() {
        return 57;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, InfusionRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 16, 8).addIngredients(recipe.getCatalystItem());
        if (recipe.getOreBoolean()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 16, 27).addIngredients(Ingredient.of(ItemInit.BLAZE_GEM.get()));
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, 16, 27).addIngredients(recipe.getBaseItem());
        }

        ItemStack result = recipe.getResult();

        int size = 1;
        int centerX = 10;
        int centerY = 10;

        for (int i = 0; i < size; i++) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_GRID_X + centerX, OUTPUT_GRID_Y + centerY)
                    .addItemStack(result);
        }
    }

    @Override
    public void draw(InfusionRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        int size = 1;
        int centerX = 9;
        int centerY = 9;

        if (recipe.getOreBoolean()) {
            Minecraft mc = Minecraft.getInstance();
            guiGraphics.drawString(mc.font, oreInfusion, 52, 48, 0xff7700);
        }

        for (int i = 0; i < size; i++) {
            this.slot.draw(guiGraphics, OUTPUT_GRID_X + centerX, OUTPUT_GRID_Y + centerY);
        }

        this.background.draw(guiGraphics);
    }
}
