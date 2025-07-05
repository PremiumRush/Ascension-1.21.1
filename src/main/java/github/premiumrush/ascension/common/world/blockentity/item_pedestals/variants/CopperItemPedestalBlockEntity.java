package github.premiumrush.ascension.common.world.blockentity.item_pedestals.variants;

import github.premiumrush.ascension.common.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CopperItemPedestalBlockEntity extends BasePedestalBlockEntity {
    public CopperItemPedestalBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityInit.COPPER_ITEM_PEDESTAL_BLOCK_ENTITY.get(), pos, blockState);
    }
}
