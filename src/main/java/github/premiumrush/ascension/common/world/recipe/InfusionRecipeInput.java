package github.premiumrush.ascension.common.world.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

// Taken from SmithingRecipeInput
public record InfusionRecipeInput(ItemStack base, ItemStack catalyst) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        ItemStack var;
        switch (slot) {
            case 0 -> var = this.base;
            case 1 -> var = this.catalyst;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        }
        return var;
    }

    @Override
    public int size() {
        return 2;
    }
}
