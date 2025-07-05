package github.premiumrush.ascension.common.world.block.item_pedestals;

import com.mojang.serialization.MapCodec;
import github.premiumrush.ascension.common.world.blockentity.item_pedestals.variants.BasePedestalBlockEntity;
import github.premiumrush.ascension.common.util.AscensionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BasePedestalBlock extends HorizontalDirectionalBlock implements AscensionUtil {
    protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(4.0, 0.0, 4.0, 12.0, 2.0, 12.0),
            Block.box(6.0, 0.0, 6.0, 10.0, 13.0, 10.0),
            Block.box(5,12,5,11,13,11),
            Block.box(4,13,4,12,14,12));
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    protected BasePedestalBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(LIT);
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(LIT, false);
    }
    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(BasePedestalBlock::new);
    }
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (state.getBlock() != newState.getBlock()) {
            if (blockentity instanceof BasePedestalBlockEntity) {
                Containers.dropContents(level, pos, ((BasePedestalBlockEntity) blockentity).getItem());
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof BasePedestalBlockEntity blockEntity && hand == InteractionHand.MAIN_HAND) {
            ItemStack interactItem = player.getItemInHand(hand);
            if (player.isCrouching() && interactItem.is(Items.AIR)) {
                sendClientSideSoundPacket(level, pos, player, SoundEvents.EXPERIENCE_ORB_PICKUP,1,0);
                setRenderTypeSwitch(level, player, blockEntity);
            } else if (!blockEntity.getBlockState().getValue(LIT) && interactItem.is(Items.FLINT_AND_STEEL)) {
                sendClientSideSoundPacket(level, pos, player, SoundEvents.FLINTANDSTEEL_USE,1,0);
                level.setBlock(pos, level.getBlockState(pos).setValue(LIT, true), 1|2|4|8);
            } else if (blockEntity.getBlockState().getValue(LIT) && interactItem.is(ItemTags.SHOVELS)) {
                sendClientSideSoundPacket(level, pos, player, SoundEvents.FLINTANDSTEEL_USE,1,0);
                level.setBlock(pos, level.getBlockState(pos).setValue(LIT, false), 1|2|4|8);
            } else if (blockEntity.getItem().getFirst() == ItemStack.EMPTY && !interactItem.is(Items.AIR)) {
                blockEntity.insertItem(player, interactItem);
            } else if (!(blockEntity.getItem().getFirst() == ItemStack.EMPTY)) {
                Containers.dropContents(level, new BlockPos(pos.getX(), pos.getY()+1, pos.getZ()), blockEntity.getItem());
                level.playLocalSound(pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1, 0, false);
                blockEntity.clearContent();
            }
            level.getChunkSource().getLightEngine().checkBlock(pos);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}