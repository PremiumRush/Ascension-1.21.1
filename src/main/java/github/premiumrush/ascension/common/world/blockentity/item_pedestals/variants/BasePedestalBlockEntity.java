package github.premiumrush.ascension.common.world.blockentity.item_pedestals.variants;

import github.premiumrush.ascension.Ascension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class BasePedestalBlockEntity extends BlockEntity implements Clearable {
    public NonNullList<ItemStack> item;
    public int renderType;

    public BasePedestalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        this.item = NonNullList.withSize(1, ItemStack.EMPTY);
        this.renderType = 1;
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        this.item.clear();
        ContainerHelper.loadAllItems(nbt, this.item, registries);
        CompoundTag item_pedestal_data = nbt.getCompound(Ascension.MODID);
        this.renderType = item_pedestal_data.getInt("rotation_speed");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        ContainerHelper.saveAllItems(nbt, this.item, true, registries);
        var item_pedestal_data = new CompoundTag();
        item_pedestal_data.putInt("rotation_speed", this.renderType);
        nbt.put(Ascension.MODID, item_pedestal_data);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public void clearContent() {
        this.item.clear();
        setChanged();
    }

    public NonNullList<ItemStack> getItem() {
        return this.item;
    }
    public ItemStack getCurrentItemStack() {
        return this.item.getFirst();
    }
    public void insertItem(LivingEntity entity, ItemStack stack) {
        entity.level().playLocalSound(entity.blockPosition(), SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1, 0, false);
        this.item.set(0, stack.consumeAndReturn(1, entity));
        this.setChanged();
        this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
    }

    public void setRenderType(int renderType) {
        this.renderType = renderType;
    }
    public int getRenderType() {
        return renderType;
    }
}