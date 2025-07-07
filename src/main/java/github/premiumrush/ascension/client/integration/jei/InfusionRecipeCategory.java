package github.premiumrush.ascension.client.integration.jei;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.recipe.infusion.InfusionRecipe;
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
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class InfusionRecipeCategory implements IRecipeCategory<InfusionRecipe> {
    public static final int OUTPUT_GRID_X = 76;
    public static final int OUTPUT_GRID_Y = 10;
    private final IDrawable slot;
    private final IDrawable oreInfusionActive;
    private final IDrawable oreInfusionInactive;
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable sketch;
    private final Component oreInfusionOn;
    private final Component oreInfusionOff;

    public InfusionRecipeCategory(IGuiHelper helper) {
        this.title = Component.translatable("jei.ascension.infusion.title");
        ResourceLocation backgroundImage = ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "textures/gui/jei/infusion_table.png");
        this.slot = helper.createDrawable(backgroundImage, 0, 58, 18, 18);
        this.sketch = helper.createDrawable(backgroundImage, 18, 58, 18, 18);
        this.oreInfusionActive = helper.createDrawable(backgroundImage, 37, 59, 16, 16);
        this.oreInfusionInactive = helper.createDrawable(backgroundImage, 55, 59, 16, 16);
        this.background = helper.createDrawable(backgroundImage, 0, 0, 117, 57);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemInit.INFUSING_TABLE.get()));
        this.oreInfusionOn = Component.translatable("jei.ascension.ore_infusion_on");
        this.oreInfusionOff = Component.translatable("jei.ascension.ore_infusion_off");
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
        builder.addSlot(RecipeIngredientRole.INPUT, 16, 8)
                .addIngredients(recipe.getCatalystItem());
        builder.addSlot(RecipeIngredientRole.INPUT, 16, 27)
                .addIngredients(recipe.getBaseItem());

        if (recipe.getResult().is(Items.MACE)) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_GRID_X + 10, OUTPUT_GRID_Y + 10)
                    .addItemStack(ItemInit.THUNDER_MACE.get().getDefaultInstance());
        } else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_GRID_X + 10, OUTPUT_GRID_Y + 10)
                    .addItemStack(recipe.getResult());
        }
    }

    @Override
    public void draw(InfusionRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
        boolean oreBoolean = recipe.getOreBoolean();
        drawOreBooleans(guiGraphics, oreBoolean);

        if ((mouseX >= 100 && mouseX <= 116) && (mouseY >= 0 && mouseY <= 16)) { // Hovering over Gem
            Component component = oreBoolean ? this.oreInfusionOn : this.oreInfusionOff;
            int color = oreBoolean ? 0xff7700 : 0xA0A0A0;
            int borderColor = oreBoolean ? 0x99FF7700 : 0xFFA0A0A0;
            FormattedCharSequence formattedcharsequence = component.getVisualOrderText();
            Font font = Minecraft.getInstance().font;

            TooltipRenderUtil.renderTooltipBackground(guiGraphics,
                    getWidth()/2 - font.width(formattedcharsequence) / 2, -12,
                    font.width(formattedcharsequence), 8,
                    0,
                    0xb01c1d13, 0xb01c1d13,
                    borderColor, borderColor);

            guiGraphics.drawCenteredString(font, component, getWidth()/2, -12, color);

            if (oreBoolean) {
                this.sketch.draw(guiGraphics, 100, 40);
            }
        }

        this.slot.draw(guiGraphics, OUTPUT_GRID_X + 9, OUTPUT_GRID_Y + 9);
    }

    private void drawOreBooleans(GuiGraphics guiGraphics, boolean oreBoolean) {
        if (oreBoolean) {
            this.oreInfusionActive.draw(guiGraphics, 100, 0);
        } else {
            this.oreInfusionInactive.draw(guiGraphics, 100, 0);
        }
    }
}
