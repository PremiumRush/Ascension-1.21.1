package github.premiumrush.ascension.init;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.world.block.BuddingVexalBlock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockInit {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Ascension.MODID);

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
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_FLEROVIUM_ORE = BLOCKS.register("deepslate_flerovium_ore",
            () -> new DropExperienceBlock(UniformInt.of(9, 12), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE)
                    .strength(5.5f,15.0f)
                    .lightLevel((p_152607_) -> {
                        return 4;
                    })
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
    public static final DeferredBlock<LanternBlock> BLAZE_LANTERN = BLOCKS.register("blaze_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)));
    public static final DeferredBlock<ChainBlock> FERROTITANIUM_CHAIN = BLOCKS.register("ferrotitanium_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN)));
    public static final DeferredBlock<LanternBlock> ICE_LANTERN = BLOCKS.register("ice_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)));
    public static final DeferredBlock<ChainBlock> TITANIUM_CHAIN = BLOCKS.register("titanium_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN)));

// Block Entity Blocks
    /*
    public static final DeferredBlock<InfusingTableBlock> INFUSING_TABLE = BLOCKS.register("infusing_table",
            () -> new InfusingTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BREWING_STAND)
                    .mapColor(MapColor.WOOD)
                    .strength(2.5F)
                    .sound(SoundType.WOOD)
            ));

    */
}
