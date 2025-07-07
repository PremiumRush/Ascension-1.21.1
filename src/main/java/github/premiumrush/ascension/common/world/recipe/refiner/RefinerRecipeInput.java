package github.premiumrush.ascension.common.world.recipe.refiner;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record RefinerRecipeInput(String enchantment, ItemStack catalyst) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        ItemStack var;
        if (slot == 0) {
            var = this.catalyst;
        } else {
            throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        }
        return var;
    }

    @Override
    public int size() {
        return 1;
    }
}
