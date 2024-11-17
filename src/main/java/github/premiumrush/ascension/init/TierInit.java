package github.premiumrush.ascension.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class TierInit {
    public static final Tier TitaniumTier = new SimpleTier(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            312,
            6.5F,
            2F,
            15,
            () -> Ingredient.of(ItemInit.TITANIUM_INGOT::get)
    );
    public static final Tier FerrotitaniumTier = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            1792,
            7.5F,
            3.0F,
            10,
            () -> Ingredient.of(ItemInit.FERROTITANIUM_INGOT::get)
    );
    public static final Tier FerrotitaniumSwordTier = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            1792,
            7.5F,
            2.8F,
            10,
            () -> Ingredient.of(ItemInit.FERROTITANIUM_INGOT::get)
    );
    public static final Tier BlazeTier = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            1024,
            8.0F,
            4.0F,
            18,
            () -> Ingredient.of(ItemInit.BLAZE_GEM::get)
    );
    public static final Tier IceTier = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            896,
            8.0F,
            2.0F,
            20,
            () -> Ingredient.of(ItemInit.ICE_GEM::get)
    );
    public static final Tier WingedTier = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            256,
            5.0F,
            1.0F,
            8,
            () -> Ingredient.of(Items.AIR)
    );
    public static final Tier RazorTier = new SimpleTier(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            672,
            8.0F,
            3.0F,
            10,
            () -> Ingredient.of(Items.AIR)
    );
    public static final Tier FleroviumTier = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            2240,
            15F,
            6.0F,
            20,
            () -> Ingredient.of(ItemInit.FLEROVIUM_CRYSTAL.get())
    );
    public static final Tier VexalTier = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            334,
            6.0F,
            2F,
            25,
            () -> Ingredient.of(ItemInit.VEXAL_CRYSTAL::get)
    );
    public static final Tier InfusedVexalTier = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            712,
            7.5F,
            4F,
            25,
            () -> Ingredient.of(ItemInit.INFUSED_VEXAL_CRYSTAL::get)
    );
    public static final Tier GyroTier = new SimpleTier(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            484,
            6.5F,
            3F,
            12,
            () -> Ingredient.of(Items.AIR)
    );
    public static final Tier ShadowTier = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            345,
            7.5F,
            3F,
            15,
            () -> Ingredient.of(Items.AIR)
    );
}
