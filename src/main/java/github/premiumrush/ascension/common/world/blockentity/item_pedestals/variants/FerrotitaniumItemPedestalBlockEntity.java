package github.premiumrush.ascension.common.world.blockentity.item_pedestals.variants;

import github.premiumrush.ascension.common.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class FerrotitaniumItemPedestalBlockEntity extends BasePedestalBlockEntity {
    public FerrotitaniumItemPedestalBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityInit.FERROTITANIUM_ITEM_PEDESTAL_BLOCK_ENTITY.get(), pos, blockState);
    }
}
