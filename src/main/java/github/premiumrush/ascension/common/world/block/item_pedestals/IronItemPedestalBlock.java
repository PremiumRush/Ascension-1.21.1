package github.premiumrush.ascension.common.world.block.item_pedestals;

import github.premiumrush.ascension.common.world.blockentity.item_pedestals.variants.IronItemPedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class IronItemPedestalBlock extends BasePedestalBlock implements EntityBlock {
    public IronItemPedestalBlock(Properties properties) {
        super(properties);
    }
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new IronItemPedestalBlockEntity(blockPos, blockState);
    }
}
