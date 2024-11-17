package github.premiumrush.ascension.world.block;

import github.premiumrush.ascension.init.BlockInit;
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

    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextInt(5) == 0) {
            Direction $$4 = DIRECTIONS[randomSource.nextInt(DIRECTIONS.length)];
            BlockPos $$5 = blockPos.relative($$4);
            BlockState $$6 = serverLevel.getBlockState($$5);
            Block $$7 = null;
            if (canClusterGrowAtState($$6)) {
                $$7 = BlockInit.SMALL_VEXAL_BUD.get();
            } else if ($$6.is(BlockInit.SMALL_VEXAL_BUD.get()) && $$6.getValue(AmethystClusterBlock.FACING) == $$4) {
                $$7 = BlockInit.MEDIUM_VEXAL_BUD.get();
            } else if ($$6.is(BlockInit.MEDIUM_VEXAL_BUD.get()) && $$6.getValue(AmethystClusterBlock.FACING) == $$4) {
                $$7 = BlockInit.LARGE_VEXAL_BUD.get();
            } else if ($$6.is(BlockInit.LARGE_VEXAL_BUD.get()) && $$6.getValue(AmethystClusterBlock.FACING) == $$4) {
                $$7 = BlockInit.VEXAL_CLUSTER.get();
            }

            if ($$7 != null) {
                BlockState $$8 = (BlockState)((BlockState)$$7.defaultBlockState().setValue(AmethystClusterBlock.FACING, $$4)).setValue(AmethystClusterBlock.WATERLOGGED, $$6.getFluidState().getType() == Fluids.WATER);
                serverLevel.setBlockAndUpdate($$5, $$8);
            }
        }
    }
}
