package github.premiumrush.ascension.datagen.loot;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.loot.AddItem;
import github.premiumrush.ascension.common.init.ItemInit;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Ascension.MODID);
    }

    @Override
    protected void start() {
        add("blaze_gem_from_blaze", new AddItem(new LootItemCondition[] {

                new LootTableIdCondition.Builder(ResourceLocation.parse("entities/blaze")).build(),

                LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.ATTACKER,
                        EntityPredicate.Builder.entity().equipment(
                                EntityEquipmentPredicate.Builder.equipment().mainhand(
                                        ItemPredicate.Builder.item().of(ItemInit.INFUSED_VEXAL_SWORD.get())
                                    )
                                )
                ).build(),

                LootItemRandomChanceCondition.randomChance(0.5f).build()

        }, "blaze_gem_from_blaze", 1 , ItemInit.BLAZE_GEM.get()));

        add("dormant_template_from_end_city", new AddItem(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.parse("chests/end_city_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.3f).build(),
        }, "dormant_template_from_end_city", 1, ItemInit.FLEROVIUM_UPGRADE_SMITHING_TEMPLATE_INACTIVE.get()));
    }
}
