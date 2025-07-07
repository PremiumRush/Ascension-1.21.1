package github.premiumrush.ascension.common.init;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.recipe.infusion.InfusionRecipe;
import github.premiumrush.ascension.common.world.recipe.refiner.RefinerRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RecipeInit {
    public static final DeferredRegister<RecipeType<?>> MOD_RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Ascension.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> MOD_RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Ascension.MODID);

    public static final Supplier<RecipeType<InfusionRecipe>> INFUSION_RECIPE_TYPE =
            MOD_RECIPE_TYPES.register(
                    "infusion",
                    registryName -> new RecipeType<InfusionRecipe>() {
                        @Override
                        public String toString() {
                            return registryName.toString();
                        }
                    }
            );
    public static final Supplier<RecipeSerializer<InfusionRecipe>> INFUSION_RECIPE_SERIALIZER =
            MOD_RECIPE_SERIALIZERS.register("infusion", InfusionRecipe.Serializer::new);


    public static final Supplier<RecipeType<RefinerRecipe>> REFINER_RECIPE_TYPE =
            MOD_RECIPE_TYPES.register(
                    "enchantment_refining",
                    registryName -> new RecipeType<RefinerRecipe>() {
                        @Override
                        public String toString() {
                            return registryName.toString();
                        }
                    }
            );
    public static final Supplier<RecipeSerializer<RefinerRecipe>> REFINER_RECIPE_SERIALIZER =
            MOD_RECIPE_SERIALIZERS.register("enchantment_refining", RefinerRecipe.Serializer::new);
}
