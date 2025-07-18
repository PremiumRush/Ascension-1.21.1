package github.premiumrush.ascension.common.init;

import com.mojang.serialization.MapCodec;
import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.loot.AddItem;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class LootModifierInit {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Ascension.MODID);

    public static final Supplier<MapCodec<AddItem>> ADD_ITEM =
            GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("add_item", () -> AddItem.CODEC);
}
