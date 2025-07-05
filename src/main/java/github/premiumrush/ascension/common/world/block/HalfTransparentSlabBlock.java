package github.premiumrush.ascension.common.world.block;

import github.premiumrush.ascension.common.init.BlockInit;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

public class HalfTransparentSlabBlock extends SlabBlock {
    public HalfTransparentSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return (adjacentBlockState.is(BlockInit.ICE_SLAB) && adjacentBlockState == state) | adjacentBlockState.is(Blocks.ICE) || super.skipRendering(state, adjacentBlockState, side);
    }
}
