package github.premiumrush.ascension.common.world.block;

import github.premiumrush.ascension.common.init.BlockInit;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class HalfTransparentStairBlock extends StairBlock {
    public HalfTransparentStairBlock(BlockState baseState, Properties properties) {
        super(baseState, properties);
    }

    protected boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return (adjacentBlockState.is(BlockInit.ICE_STAIRS) && adjacentBlockState == state) | adjacentBlockState.is(Blocks.ICE) || super.skipRendering(state, adjacentBlockState, side);
    }
}
