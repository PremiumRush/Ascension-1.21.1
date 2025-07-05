package github.premiumrush.ascension.common.world.recipe;

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

public class InfusionRecipe implements Recipe<InfusionRecipeInput> {
    private final Ingredient baseItem;
    private final Ingredient catalystItem;
    private final ItemStack result;
    private final boolean oreBoolean;

    public InfusionRecipe(Ingredient baseItem, Ingredient catalystItem, ItemStack result, boolean oreBoolean) {
        this.baseItem = baseItem;
        this.catalystItem = catalystItem;
        this.result = result;
        this.oreBoolean = oreBoolean;
    }

    @Override
    public boolean matches(InfusionRecipeInput input, Level level) {
        return this.baseItem.test(input.base()) && this.catalystItem.test(input.catalyst());
    }

    @Override
    public ItemStack assemble(InfusionRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }
    public ItemStack getResult() {
        return result;
    }

    public boolean getOreBoolean() {
        return this.oreBoolean;
    }
    public Ingredient getBaseItem() {
        return this.baseItem;
    }
    public Ingredient getCatalystItem() {
        return this.catalystItem;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeInit.INFUSION_RECIPE_TYPE.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeInit.INFUSION_RECIPE_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<InfusionRecipe> {
        public static final MapCodec<InfusionRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("base").forGetter((base) -> {
                    return base.baseItem;
                }),
                Ingredient.CODEC.fieldOf("catalyst").forGetter((catalyst) -> {
                    return catalyst.catalystItem;
                }),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter((result) -> {
                    return result.result;
                }),
                Codec.BOOL.fieldOf("ore_processing").forGetter((ore_processing) -> {
                    return ore_processing.oreBoolean;
                })
        ).apply(inst, InfusionRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, InfusionRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        private static InfusionRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient base = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient catalyst = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack result = (ItemStack)ItemStack.STREAM_CODEC.decode(buffer);
            boolean oreBoolean = (boolean)buffer.readBoolean();
            return new InfusionRecipe(base, catalyst, result, oreBoolean);
        }
        private static void toNetwork(RegistryFriendlyByteBuf buffer, InfusionRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.baseItem);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.catalystItem);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
            buffer.writeBoolean(recipe.oreBoolean);
        }

        @Override
        public MapCodec<InfusionRecipe> codec() {
            return CODEC;
        }
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, InfusionRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
