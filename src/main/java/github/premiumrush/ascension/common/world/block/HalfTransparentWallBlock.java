package github.premiumrush.ascension.common.world.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;

public class HalfTransparentWallBlock extends WallBlock {
    public HalfTransparentWallBlock(Properties properties) {
        super(properties);
    }

    protected boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.is(this) | adjacentBlockState.is(Blocks.ICE) || super.skipRendering(state, adjacentBlockState, side);
    }
}
