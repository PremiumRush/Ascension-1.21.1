package github.premiumrush.ascension.client.integration.jei;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.init.ItemInit;
import github.premiumrush.ascension.common.world.recipe.refiner.RefinerRecipe;
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
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class RefinerRecipeCategory implements IRecipeCategory<RefinerRecipe> {
    final List<ItemStack> enchantableItemStacks = BuiltInRegistries.ITEM.stream()
            .filter(item -> {
                ItemStack testStack = new ItemStack(item);
                return testStack.isEnchantable();
            })
            .map(item -> {
                ItemStack stack = new ItemStack(item);
                stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
                return stack;
            })
            .collect(Collectors.toList());
    public static final int OUTPUT_GRID_X = 76;
    public static final int OUTPUT_GRID_Y = 10;
    private final IDrawable slot;
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable questionmark;

    public RefinerRecipeCategory(IGuiHelper helper) {
        this.title = Component.translatable("jei.ascension.refining.title");
        ResourceLocation backgroundImage = ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "textures/gui/jei/refiner.png");
        this.slot = helper.createDrawable(backgroundImage, 0, 58, 18, 18);
        this.background = helper.createDrawable(backgroundImage, 0, 0, 117, 57);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemInit.ENCHANTMENT_REFINER.get()));
        this.questionmark = helper.createDrawable(backgroundImage, 19, 59, 16, 16);
    }

    @Override
    public RecipeType<RefinerRecipe> getRecipeType() {
        return AscensionPlugin.REFINER_RECIPE;
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
    public void setRecipe(IRecipeLayoutBuilder builder, RefinerRecipe recipe, IFocusGroup focuses) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null) return;

        Registry<Enchantment> enchantmentRegistry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        ResourceLocation enchID = ResourceLocation.parse(recipe.getEnchantmentString());

        Holder<Enchantment> enchantmentHolder = Optional.ofNullable(enchantmentRegistry.get(enchID))
                .flatMap(enchantmentRegistry::getResourceKey)
                .flatMap(enchantmentRegistry::getHolder)
                .orElse(null);
        if (enchantmentHolder == null) return;

        List<ItemStack> enchantedStacks = createEnchantedStacks(this.enchantableItemStacks, enchantmentHolder, enchantmentHolder.value().getMaxLevel());
        List<ItemStack> enchantedStacksRefined = createEnchantedStacks(this.enchantableItemStacks, enchantmentHolder, enchantmentHolder.value().getMaxLevel() + 1);

        builder.addSlot(RecipeIngredientRole.INPUT, 16, 8)
                .addIngredients(recipe.getCatalystItem());
        builder.addSlot(RecipeIngredientRole.INPUT, 16, 27)
                .addItemStacks(enchantedStacks);

        builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_GRID_X + 10, OUTPUT_GRID_Y + 10)
                .addItemStacks(enchantedStacksRefined);
    }

    private List<ItemStack> createEnchantedStacks(List<ItemStack> originalStacks, Holder<Enchantment> holder, int level) {
        return originalStacks.stream()
                .map(originalStack -> {
                    ItemStack copy = originalStack.copy();
                    if (copy.supportsEnchantment(holder)) {
                        copy.enchant(holder, level);
                        return copy;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public void draw(RefinerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
        this.questionmark.draw(guiGraphics, 35, 42);
        this.slot.draw(guiGraphics, OUTPUT_GRID_X + 9, OUTPUT_GRID_Y + 9);

        if ((mouseX >= 37 && mouseX <= 49) && (mouseY >= 42 && mouseY <= 58)) {
            Component component = Component.translatable("jei.ascension.refining_hint");
            Component component2 = Component.translatable("jei.ascension.refining_hint2");
            int color = 0xFF8190ff;
            int borderColor = 0xFF8F39FF;
            FormattedCharSequence formattedcharsequence1 = component.getVisualOrderText();
            FormattedCharSequence formattedcharsequence2 = component2.getVisualOrderText();
            Font font = Minecraft.getInstance().font;

            renderTextBackground(guiGraphics, formattedcharsequence1, font, -12, borderColor);
            renderTextBackground(guiGraphics, formattedcharsequence2, font, 62, borderColor);

            guiGraphics.drawCenteredString(font, component, getWidth()/2, -12, color);
            guiGraphics.drawCenteredString(font, component2, getWidth()/2, 62, color);
        }
    }

    private void renderTextBackground(GuiGraphics guiGraphics, FormattedCharSequence formattedtext, Font font, int y, int borderColor) {
        TooltipRenderUtil.renderTooltipBackground(guiGraphics,
                getWidth()/2 - font.width(formattedtext) / 2, y,
                font.width(formattedtext), 8,
                0,
                0xb01c1d13, 0xb01c1d13,
                borderColor, borderColor);
    }
}

