package github.premiumrush.ascension.common.world.recipe.refiner;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import github.premiumrush.ascension.common.init.RecipeInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class RefinerRecipe implements Recipe<RefinerRecipeInput> {
    private final String enchantmentString;
    private final Ingredient catalystItem;

    public RefinerRecipe(String enchantment, Ingredient catalyst) {
        this.enchantmentString = enchantment;
        this.catalystItem = catalyst;
    }

    @Override
    public boolean matches(RefinerRecipeInput input, Level level) {
        return this.enchantmentString.equals(input.enchantment()) && this.catalystItem.test(input.catalyst());
    }

    @Override
    public ItemStack assemble(RefinerRecipeInput refinerRecipeInput, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    // Custom Getters
    public String getEnchantmentString() {
        return enchantmentString;
    }
    public Ingredient getCatalystItem() {
        return catalystItem;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeInit.REFINER_RECIPE_TYPE.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeInit.REFINER_RECIPE_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<RefinerRecipe> {
        public static final MapCodec<RefinerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.STRING.fieldOf("enchantment").forGetter((enchantment) -> {
                    return enchantment.enchantmentString;
                }),
                Ingredient.CODEC.fieldOf("catalyst").forGetter((catalyst) -> {
                    return catalyst.catalystItem;
                })
        ).apply(inst, RefinerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, RefinerRecipe> STREAM_CODEC =
                StreamCodec.of(RefinerRecipe.Serializer::toNetwork, RefinerRecipe.Serializer::fromNetwork);

        private static RefinerRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String enchantment = (String)buffer.readUtf();
            Ingredient catalyst = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            return new RefinerRecipe(enchantment, catalyst);
        }
        private static void toNetwork(RegistryFriendlyByteBuf buffer, RefinerRecipe recipe) {
            buffer.writeUtf(recipe.enchantmentString);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.catalystItem);
        }

        @Override
        public MapCodec<RefinerRecipe> codec() {
            return CODEC;
        }
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RefinerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
