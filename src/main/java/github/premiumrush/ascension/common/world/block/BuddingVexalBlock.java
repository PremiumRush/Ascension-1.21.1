package github.premiumrush.ascension.common.world.block;

import github.premiumrush.ascension.common.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class BuddingVexalBlock extends BuddingAmethystBlock {
    public BuddingVexalBlock(Properties properties) {
        super(properties);
    }

    private static final Direction[] DIRECTIONS = Direction.values();

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        if (randomSource.nextInt(6) == 0) {
            Direction randomDirection = DIRECTIONS[randomSource.nextInt(DIRECTIONS.length)];
            BlockPos targetPos = pos.relative(randomDirection);
            BlockState targetState = level.getBlockState(targetPos);
            // Determine what to place
            Block block = null;
            if (canClusterGrowAtState(targetState)) {
                block = BlockInit.SMALL_VEXAL_BUD.get();
            } else if (targetState.is(BlockInit.SMALL_VEXAL_BUD.get()) && targetState.getValue(AmethystClusterBlock.FACING) == randomDirection) {
                block = BlockInit.MEDIUM_VEXAL_BUD.get();
            } else if (targetState.is(BlockInit.MEDIUM_VEXAL_BUD.get()) && targetState.getValue(AmethystClusterBlock.FACING) == randomDirection) {
                block = BlockInit.LARGE_VEXAL_BUD.get();
            } else if (targetState.is(BlockInit.LARGE_VEXAL_BUD.get()) && targetState.getValue(AmethystClusterBlock.FACING) == randomDirection) {
                block = BlockInit.VEXAL_CLUSTER.get();
            }

            // Place block
            if (block != null) {
                BlockState newState = block.defaultBlockState().setValue(AmethystClusterBlock.FACING, randomDirection).setValue(AmethystClusterBlock.WATERLOGGED, targetState.getFluidState().getType() == Fluids.WATER);
                level.setBlockAndUpdate(targetPos, newState);
            }
        }
    }
}
