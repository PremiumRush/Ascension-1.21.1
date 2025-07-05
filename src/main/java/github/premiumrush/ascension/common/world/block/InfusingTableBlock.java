package github.premiumrush.ascension.common.world.block;

import com.mojang.serialization.MapCodec;
import github.premiumrush.ascension.common.world.blockentity.InfusingTableBlockEntity;
import github.premiumrush.ascension.common.world.recipe.InfusionRecipe;
import github.premiumrush.ascension.common.world.recipe.InfusionRecipeInput;
import github.premiumrush.ascension.common.init.ItemInit;
import github.premiumrush.ascension.common.init.ParticleInit;
import github.premiumrush.ascension.common.init.RecipeInit;
import github.premiumrush.ascension.common.util.AscensionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class InfusingTableBlock extends HorizontalDirectionalBlock implements EntityBlock, AscensionUtil {
    protected static final VoxelShape SHAPE_COLLISION = Block.box(0,0,0,16,15,16);
    protected static final VoxelShape SHAPE = Block.box(1,0,1,15,15,15);
    private int bubbleTicks;

// ## BlockStates
    public static final BooleanProperty HAS_CATALYST = BooleanProperty.create("has_catalyst");
    public static final BooleanProperty ORE_INFUSION = BooleanProperty.create("ore_infusion");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HAS_CATALYST, ORE_INFUSION);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()); // Flip rotation
    }

// Constructor
    public InfusingTableBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HAS_CATALYST, false).setValue(ORE_INFUSION, false));
    }

// Imported Methods
    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(InfusingTableBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InfusingTableBlockEntity(pos, state);
    }

// Workspace
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_COLLISION;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (state.getBlock() != newState.getBlock()) {
            if (blockentity instanceof InfusingTableBlockEntity be) {
                Containers.dropContents(level, pos, be.getItems());
            }
            if (state.getValue(ORE_INFUSION)) {
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemInit.BLAZE_GEM.get(), 1));
                level.addFreshEntity(itemEntity);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof InfusingTableBlockEntity blockEntity && hand == InteractionHand.MAIN_HAND) {
            ItemStack interactItem = player.getItemInHand(hand);
            ItemStack newItem = new ItemStack(player.getItemInHand(hand).getItem(), 1);
            List<RecipeHolder<InfusionRecipe>> availableRecipes = level.getRecipeManager().getRecipesFor(RecipeInit.INFUSION_RECIPE_TYPE.get(), new InfusionRecipeInput(blockEntity.getItems().getFirst(), interactItem), level);
            // Infusion Recipe Catalyst Input
            if ((!availableRecipes.isEmpty() && blockEntity.getInfusingTicks() == -1 && availableRecipes.getFirst().value().getOreBoolean() && state.getValue(ORE_INFUSION)) || (!availableRecipes.isEmpty() && blockEntity.getInfusingTicks() == -1 && !availableRecipes.getFirst().value().getOreBoolean() && !state.getValue(ORE_INFUSION))) {
                blockEntity.insertCatalyst(stack, newItem, player, 200);
                level.setBlock(pos, level.getBlockState(pos).setValue(HAS_CATALYST, true), 1|2|4|8);
            }
            // Drop Contents with Empty Hand
            else if (blockEntity.getInfusingTicks() == -1 && interactItem.is(ItemStack.EMPTY.getItem())) {
                Containers.dropContents(level, new BlockPos(pos.getX(), pos.getY()+1, pos.getZ()), blockEntity.getItems());
                blockEntity.clearContent();
                level.setBlock(pos, level.getBlockState(pos).setValue(HAS_CATALYST, false), 1|2|4|8);
            }
            // Activate Ore Infusion with Gem
            else if (interactItem.is(ItemInit.BLAZE_GEM.get()) && !state.getValue(ORE_INFUSION)) {
                stack.consume(1, player);
                player.displayClientMessage(Component.literal("Ore Infusion Activated"), true);
                level.setBlock(pos, level.getBlockState(pos).setValue(ORE_INFUSION, true), 1|2|4|8);
            }
            // Insert Base Item into Block
            else if (blockEntity.getBaseItemStack() == ItemStack.EMPTY && !blockEntity.shouldRenderCatalyst() && blockEntity.getItems().get(2) == ItemStack.EMPTY) {
                if (!interactItem.is(Items.AIR) && interactItem != ItemStack.EMPTY) {
                    blockEntity.playerInsertBaseItem(level, pos, state, player, interactItem);
                }
            }
            // Display Message for Recipe
            displayMessageForInfusionRecipe(level, blockEntity, player, state, availableRecipes);

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
        return super.useItemOn(stack,state,level,pos,player,hand,hitResult);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
        if(level.isClientSide() && state.getValue(HAS_CATALYST)) {
            if (this.bubbleTicks++ % 2 == 0) {
                Random random = new Random();
                double valuesX = pos.getX() + random.nextDouble(0.71d - 0.3d) + 0.3d;
                double valuesZ = pos.getZ() + random.nextDouble(0.71d - 0.3d) + 0.3d;
                double valuesDY = random.nextDouble(0.06d - 0.02d) + 0.02d;
                double valuesDXandDZ = (random.nextDouble(0.01d))-0.005;
                ParticleOptions particle;
                if (!state.getValue(ORE_INFUSION)) {
                    particle = ParticleInit.BUBBLE_PARTICLE.get();
                } else {
                    particle = ParticleTypes.FLAME;
                }
                level.addParticle(particle, valuesX, pos.getY() + 1.2d, valuesZ, valuesDXandDZ, valuesDY, valuesDXandDZ);
            }
        }
        super.animateTick(state, level, pos, randomSource);
    }

    public void addInfuseParticleSet(Level level, BlockPos pos, BlockState state) {
        ParticleOptions particle = ParticleInit.INFUSION_PARTICLE.get();
        if (state.getValue(ORE_INFUSION)) {
            particle = ParticleTypes.FLAME;
        }
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.8, pos.getZ()+0.5, 0.005, -0.05, 0);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.8, pos.getZ()+0.5, -0.005, -0.05, 0);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.8, pos.getZ()+0.5, 0, -0.05, 0.005);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.8, pos.getZ()+0.5, 0, -0.05, -0.005);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.8, pos.getZ()+0.5, 0.005, -0.05, 0.005);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.8, pos.getZ()+0.5, 0.005, -0.05, -0.005);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.8, pos.getZ()+0.5, -0.005, -0.05, 0.005);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.8, pos.getZ()+0.5, -0.005, -0.05, -0.005);
    }
    public void addSplashParticleSet(Level level, BlockPos pos, BlockState state) {
        ParticleOptions particle = ParticleInit.INFUSION_PARTICLE.get();
        if (state.getValue(ORE_INFUSION)) {
            particle = ParticleTypes.FLAME;
        }
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.05, pos.getZ()+0.5, 0.5*0.075, 0, 0.5*0.075);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.05, pos.getZ()+0.5, 0.5*0.075, 0, 0.5*-0.075);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.05, pos.getZ()+0.5, 0.5*-0.075, 0, 0.5*0.075);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.05, pos.getZ()+0.5, 0.5*-0.075, 0, 0.5*-0.075);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.05, pos.getZ()+0.5, 0, 0, 0.5*0.1);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.05, pos.getZ()+0.5, 0, 0, 0.5*-0.1);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.05, pos.getZ()+0.5, 0.5*0.1, 0, 0);
        level.addParticle(particle, true, pos.getX()+0.5, pos.getY()+1.05, pos.getZ()+0.5, 0.5*-0.1, 0, 0);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return (level0,pos0,blockState0,blockEntity) -> {
            if (blockEntity instanceof InfusingTableBlockEntity be) be.tick();
        };
    }
}
