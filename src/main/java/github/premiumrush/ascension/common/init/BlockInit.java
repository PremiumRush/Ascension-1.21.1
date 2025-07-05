package github.premiumrush.ascension.common.init;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.block.*;
import github.premiumrush.ascension.common.world.block.item_pedestals.*;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.ToIntFunction;

public class BlockInit {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Ascension.MODID);

// Ice Blocks
    public static final DeferredBlock<HalfTransparentBlock> SOLID_ICE = BLOCKS.register("solid_ice",
            () -> new HalfTransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ICE).noOcclusion()
            ));
    public static final DeferredBlock<HalfTransparentStairBlock> ICE_STAIRS = BLOCKS.register("ice_stairs",
            () -> new HalfTransparentStairBlock(Blocks.ICE.defaultBlockState() ,BlockBehaviour.Properties.ofFullCopy(Blocks.ICE).noOcclusion()
            ));
    public static final DeferredBlock<HalfTransparentSlabBlock> ICE_SLAB = BLOCKS.register("ice_slab",
            () -> new HalfTransparentSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ICE).noOcclusion()
            ));
    public static final DeferredBlock<HalfTransparentWallBlock> ICE_WALL = BLOCKS.register("ice_wall",
            () -> new HalfTransparentWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ICE).forceSolidOn().noOcclusion()
            ));
    public static final DeferredBlock<StairBlock> PACKED_ICE_STAIRS = BLOCKS.register("packed_ice_stairs",
            () -> new StairBlock(Blocks.PACKED_ICE.defaultBlockState() ,BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_ICE)
            ));
    public static final DeferredBlock<SlabBlock> PACKED_ICE_SLAB = BLOCKS.register("packed_ice_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_ICE)
            ));
    public static final DeferredBlock<WallBlock> PACKED_ICE_WALL = BLOCKS.register("packed_ice_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_ICE).forceSolidOn()
            ));
    public static final DeferredBlock<StairBlock> BLUE_ICE_STAIRS = BLOCKS.register("blue_ice_stairs",
            () -> new StairBlock(Blocks.BLUE_ICE.defaultBlockState() ,BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_ICE)
            ));
    public static final DeferredBlock<SlabBlock> BLUE_ICE_SLAB = BLOCKS.register("blue_ice_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_ICE)
            ));
    public static final DeferredBlock<WallBlock> BLUE_ICE_WALL = BLOCKS.register("blue_ice_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_ICE).forceSolidOn()
            ));

// Titanium Blocks
    public static final DeferredBlock<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)
                    .strength(3.0f,7.0f)
            ));
    public static final DeferredBlock<Block> DEEPSLATE_TITANIUM_ORE = BLOCKS.register("deepslate_titanium_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)
                    .strength(4.5f,7.0f)
            ));
    public static final DeferredBlock<Block> RAW_TITANIUM_BLOCK = BLOCKS.register("raw_titanium_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK)
                    .strength(3.0f,7.0f)
                    .mapColor(MapColor.TERRACOTTA_WHITE)
            ));
    public static final DeferredBlock<Block> TITANIUM_BLOCK = BLOCKS.register("titanium_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .strength(3.0f,7.0f)
                    .mapColor(MapColor.TERRACOTTA_WHITE)
            ));

// Ferrotitanium Blocks
    public static final DeferredBlock<Block> FERROTITANIUM_BLOCK = BLOCKS.register("ferrotitanium_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK)
                    .mapColor(MapColor.METAL)
            ));

// Vexal Blocks
    public static final DeferredBlock<AmethystClusterBlock> VEXAL_CLUSTER = BLOCKS.register("vexal_cluster", () -> new AmethystClusterBlock(7,3,
            BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_CLUSTER)
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .lightLevel((p_152680_) -> {
                        return 8;})));
    public static final DeferredBlock<AmethystClusterBlock> LARGE_VEXAL_BUD = BLOCKS.register("large_vexal_bud", () -> new AmethystClusterBlock(5,3,
            BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_AMETHYST_BUD)
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .lightLevel((p_152680_) -> {
                        return 7;})));
    public static final DeferredBlock<AmethystClusterBlock> MEDIUM_VEXAL_BUD = BLOCKS.register("medium_vexal_bud", () -> new AmethystClusterBlock(4,3,
            BlockBehaviour.Properties.ofFullCopy(Blocks.MEDIUM_AMETHYST_BUD)
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .lightLevel((p_152680_) -> {
                        return 6;})));
    public static final DeferredBlock<AmethystClusterBlock> SMALL_VEXAL_BUD = BLOCKS.register("small_vexal_bud", () -> new AmethystClusterBlock(3,4,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SMALL_AMETHYST_BUD)
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .lightLevel((p_152680_) -> {
                        return 5;})));
    public static final DeferredBlock<AmethystBlock> VEXAL_BLOCK = BLOCKS.register("vexal_block",
            () -> new AmethystBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK)
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final DeferredBlock<BuddingVexalBlock> BUDDING_VEXAL_BLOCK = BLOCKS.register("budding_vexal_block",
            () -> new BuddingVexalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BUDDING_AMETHYST)
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)));

// Flerovium Blocks
    public static final DeferredBlock<FleroviumOreBlock> DEEPSLATE_FLEROVIUM_ORE = BLOCKS.register("deepslate_flerovium_ore",
            () -> new FleroviumOreBlock(UniformInt.of(9, 12), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE)
                    .strength(5.5f,15.0f)
                    .lightLevel((p_152607_) -> {
                        return 4;
                    })
                    .forceSolidOn()
            ));
    public static final DeferredBlock<Block> FLEROVIUM_BLOCK = BLOCKS.register("flerovium_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK)
                    .strength(6.0f,20.0f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .lightLevel((p_152607_) -> {
                        return 10;
                    })
            ));
// Misc Blocks
    public static final DeferredBlock<IrradiatedFarmlandBlock> IRRADIATED_FARMLAND = BLOCKS.register("irradiated_farmland",
            () -> new IrradiatedFarmlandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND)));
    public static final DeferredBlock<LanternBlock> BLAZE_LANTERN = BLOCKS.register("blaze_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)));
    public static final DeferredBlock<ChainBlock> FERROTITANIUM_CHAIN = BLOCKS.register("ferrotitanium_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN)));
    public static final DeferredBlock<LanternBlock> ICE_LANTERN = BLOCKS.register("ice_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)));
    public static final DeferredBlock<ChainBlock> TITANIUM_CHAIN = BLOCKS.register("titanium_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN)));

// Block Entity Blocks
    public static final DeferredBlock<InfusingTableBlock> INFUSING_TABLE = BLOCKS.register("infusing_table",
            () -> new InfusingTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BREWING_STAND)
                    .mapColor(MapColor.WOOD)
                    .strength(2.5F)
                    .sound(SoundType.WOOD)
            ));
    public static final DeferredBlock<EnchantmentRefinerBlock> ENCHANTMENT_REFINER = BLOCKS.register("enchantment_refiner",
            () -> new EnchantmentRefinerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL)
                    .mapColor(MapColor.COLOR_BLACK)
                    .sound(SoundType.NETHER_BRICKS)
            ));
    public static final DeferredBlock<GoldItemPedestalBlock> GOLD_ITEM_PEDESTAL = BLOCKS.register("item_pedestal",
            () -> new GoldItemPedestalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).lightLevel(litBlockEmission(10))));
    public static final DeferredBlock<IronItemPedestalBlock> IRON_ITEM_PEDESTAL = BLOCKS.register("iron_item_pedestal",
            () -> new IronItemPedestalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).lightLevel(litBlockEmission(10))));
    public static final DeferredBlock<NetheriteItemPedestalBlock> NETHERITE_ITEM_PEDESTAL = BLOCKS.register("netherite_item_pedestal",
            () -> new NetheriteItemPedestalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).lightLevel(litBlockEmission(10))));
    public static final DeferredBlock<CopperItemPedestalBlock> COPPER_ITEM_PEDESTAL = BLOCKS.register("copper_item_pedestal",
            () -> new CopperItemPedestalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).lightLevel(litBlockEmission(10))));
    public static final DeferredBlock<TitaniumItemPedestalBlock> TITANIUM_ITEM_PEDESTAL = BLOCKS.register("titanium_item_pedestal",
            () -> new TitaniumItemPedestalBlock(BlockBehaviour.Properties.ofFullCopy(BlockInit.TITANIUM_BLOCK.get()).lightLevel(litBlockEmission(10))));
    public static final DeferredBlock<FerrotitaniumItemPedestalBlock> FERROTITANIUM_ITEM_PEDESTAL = BLOCKS.register("ferrotitanium_item_pedestal",
            () -> new FerrotitaniumItemPedestalBlock(BlockBehaviour.Properties.ofFullCopy(BlockInit.FERROTITANIUM_BLOCK.get()).lightLevel(litBlockEmission(10))));

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return (blockState) -> (Boolean)blockState.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }
}
