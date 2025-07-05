package github.premiumrush.ascension.common.init;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.blockentity.EnchantmentRefinerBlockEntity;
import github.premiumrush.ascension.common.world.blockentity.FleroviumOreBlockEntity;
import github.premiumrush.ascension.common.world.blockentity.InfusingTableBlockEntity;
import github.premiumrush.ascension.common.world.blockentity.item_pedestals.variants.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Ascension.MODID);

    public static final Supplier<BlockEntityType<InfusingTableBlockEntity>> INFUSING_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("infusing_table_block_entity",
                    () -> BlockEntityType.Builder.of(InfusingTableBlockEntity::new, new Block[]{BlockInit.INFUSING_TABLE.get()}).build(null));
    public static final Supplier<BlockEntityType<EnchantmentRefinerBlockEntity>> ENCHANTMENT_REFINER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("enchantment_refiner_block_entity",
                    () -> BlockEntityType.Builder.of(EnchantmentRefinerBlockEntity::new, new Block[]{BlockInit.ENCHANTMENT_REFINER.get()}).build(null));
    public static final Supplier<BlockEntityType<GoldItemPedestalBlockEntity>> GOLD_ITEM_PEDESTAL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("item_pedestal_block_entity",
                    () -> BlockEntityType.Builder.of(GoldItemPedestalBlockEntity::new, new Block[]{BlockInit.GOLD_ITEM_PEDESTAL.get()}).build(null));
    public static final Supplier<BlockEntityType<IronItemPedestalBlockEntity>> IRON_ITEM_PEDESTAL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("iron_item_pedestal_block_entity",
                    () -> BlockEntityType.Builder.of(IronItemPedestalBlockEntity::new, new Block[]{BlockInit.IRON_ITEM_PEDESTAL.get()}).build(null));
    public static final Supplier<BlockEntityType<NetheriteItemPedestalBlockEntity>> NETHERITE_ITEM_PEDESTAL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("netherite_item_pedestal_block_entity",
                    () -> BlockEntityType.Builder.of(NetheriteItemPedestalBlockEntity::new, new Block[]{BlockInit.NETHERITE_ITEM_PEDESTAL.get()}).build(null));
    public static final Supplier<BlockEntityType<CopperItemPedestalBlockEntity>> COPPER_ITEM_PEDESTAL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("copper_item_pedestal_block_entity",
                    () -> BlockEntityType.Builder.of(CopperItemPedestalBlockEntity::new, new Block[]{BlockInit.COPPER_ITEM_PEDESTAL.get()}).build(null));
    public static final Supplier<BlockEntityType<TitaniumItemPedestalBlockEntity>> TITANIUM_ITEM_PEDESTAL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("titanium_item_pedestal_block_entity",
                    () -> BlockEntityType.Builder.of(TitaniumItemPedestalBlockEntity::new, new Block[]{BlockInit.TITANIUM_ITEM_PEDESTAL.get()}).build(null));
    public static final Supplier<BlockEntityType<FerrotitaniumItemPedestalBlockEntity>> FERROTITANIUM_ITEM_PEDESTAL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("ferrotitanium_item_pedestal_block_entity",
                    () -> BlockEntityType.Builder.of(FerrotitaniumItemPedestalBlockEntity::new, new Block[]{BlockInit.FERROTITANIUM_ITEM_PEDESTAL.get()}).build(null));
    public static final Supplier<BlockEntityType<FleroviumOreBlockEntity>> FLEROVIUM_ORE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("flerovium_ore_block_entity",
                    () -> BlockEntityType.Builder.of(FleroviumOreBlockEntity::new, new Block[]{BlockInit.DEEPSLATE_FLEROVIUM_ORE.get()}).build(null));

}
