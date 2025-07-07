package github.premiumrush.ascension.common.world.block;

import com.mojang.serialization.MapCodec;
import github.premiumrush.ascension.common.world.blockentity.EnchantmentRefinerBlockEntity;
import github.premiumrush.ascension.common.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class EnchantmentRefinerBlock extends HorizontalDirectionalBlock implements EntityBlock {
    protected static final VoxelShape SHAPEZ;
    protected static final VoxelShape SHAPEX;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final IntegerProperty FUEL_STAGE = IntegerProperty.create("fuel_stage", 0, 3);

    public EnchantmentRefinerBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(ACTIVE, false).setValue(FUEL_STAGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, ACTIVE, FUEL_STAGE);
    }
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getClockWise());
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(EnchantmentRefinerBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new EnchantmentRefinerBlockEntity(blockPos, blockState);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(FACING).getAxis() == Direction.Axis.X ? SHAPEX : SHAPEZ;
    }

    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        EnchantmentRefinerBlockEntity blockEntity = (EnchantmentRefinerBlockEntity) level.getBlockEntity(pos);
        if (state.getBlock() != newState.getBlock()) {
            if (blockEntity != null) {
                Containers.dropContents(level, pos, blockEntity.getItems());
                if (blockEntity.getFuel() > 0) {
                    ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemInit.INFUSED_VEXAL_CRYSTAL.get(), blockEntity.getFuel()));
                    level.addFreshEntity(itemEntity);
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof EnchantmentRefinerBlockEntity blockEntity && hand == InteractionHand.MAIN_HAND) {
            ItemStack interactItem = player.getItemInHand(hand);
            boolean noEmptyHand = interactItem != ItemStack.EMPTY && !interactItem.is(Items.AIR);
            if (blockEntity.getItems().getFirst() != ItemStack.EMPTY && interactItem.is(Items.AIR) && blockEntity.getRefiningTicks() == -1) {
                Containers.dropContents(level, pos.atY(pos.getY()+1), blockEntity.getItems());
                blockEntity.clearContent();
                return ItemInteractionResult.SUCCESS;
            }
            if (interactItem.is(ItemInit.INFUSED_VEXAL_CRYSTAL.get()) && blockEntity.addFuel(1, interactItem, player, level, state, pos)) {
                this.checkFuelStage(state,level,pos);
                return ItemInteractionResult.SUCCESS;
            }
            if (blockEntity.getItems().getFirst() != ItemStack.EMPTY && blockEntity.getItems().getLast() == ItemStack.EMPTY && noEmptyHand) {
                blockEntity.insertItem(player, interactItem, 1);
            }
            if (blockEntity.getItems().getFirst() == ItemStack.EMPTY && interactItem.isEnchanted() && noEmptyHand) {
                blockEntity.insertItem(player, interactItem, 0);
            }
            if (blockEntity.getItems().getFirst() == ItemStack.EMPTY && interactItem.is(Items.ENCHANTED_BOOK) && noEmptyHand) {
                blockEntity.insertItem(player, interactItem, 0);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.isClientSide()) {
            if (state.getValue(ACTIVE) && random.nextInt(0,3) == 2) {
                Random random2 = new Random();
                double valuesX = pos.getX() + random2.nextDouble(0.71d - 0.3d) + 0.3d;
                double valuesZ = pos.getZ() + random2.nextDouble(0.71d - 0.3d) + 0.3d;
                double valuesDY = random2.nextDouble(0.06d - 0.02d) + 0.02d;
                double valuesDXandDZ = (random2.nextDouble(0.01d))-0.005;
                level.addParticle(
                        ParticleTypes.ENCHANT,
                        valuesX,
                        pos.getY() + 1.3d,
                        valuesZ,
                        valuesDXandDZ,
                        valuesDY,
                        valuesDXandDZ
                );
            }
        }
        super.animateTick(state, level, pos, random);
    }

    public void checkFuelStage(BlockState state, Level level, BlockPos pos) {
        EnchantmentRefinerBlockEntity blockEntity = (EnchantmentRefinerBlockEntity) level.getBlockEntity(pos);
        int fuel = blockEntity.getFuel();
        if(fuel == 0) {
            level.setBlock(pos, state.setValue(FUEL_STAGE,0), 1|2|4|8);
        } else if (fuel > 0 && fuel <=6) {
            level.setBlock(pos, state.setValue(FUEL_STAGE,1), 1|2|4|8);
        } else if (fuel > 6 && fuel <=12) {
            level.setBlock(pos, state.setValue(FUEL_STAGE,2), 1|2|4|8);
        } else if (fuel > 12) {
            level.setBlock(pos, state.setValue(FUEL_STAGE,3), 1|2|4|8);
        }
        blockEntity.setChanged();
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (level0,pos0,blockState0,blockEntity) -> {
            if (blockEntity instanceof EnchantmentRefinerBlockEntity be) be.tick();
        };
    }

    static {
        VoxelShape BASE = Block.box(2,0,2, 14,4,14);
        VoxelShape SMALL_BASEX = Block.box(4,4,3,12,5,13);
        VoxelShape MIDDLEX = Block.box(6,5,4,10,10,12);
        VoxelShape TOPX = Block.box(3,10,0,13,16,16);
        VoxelShape SMALL_BASEZ = Block.box(3,4,4,13,5,12);
        VoxelShape MIDDLEZ = Block.box(4,5,6,12,10,10);
        VoxelShape TOPZ = Block.box(0,10,3,16,16,13);
        SHAPEZ = Shapes.or(BASE, SMALL_BASEX, MIDDLEX, TOPX);
        SHAPEX = Shapes.or(BASE, SMALL_BASEZ, MIDDLEZ, TOPZ);
    }
}
