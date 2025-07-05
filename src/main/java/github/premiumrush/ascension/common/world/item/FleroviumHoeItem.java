package github.premiumrush.ascension.common.world.item;

import com.mojang.datafixers.util.Pair;
import github.premiumrush.ascension.common.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FleroviumHoeItem extends HoeItem {
    public FleroviumHoeItem(Tier p_40521_, Properties p_40524_) {
        super(p_40521_, p_40524_);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.flerovium_tool.tooltip"));
        pToolTipComponents.add(Component.translatable("tooltip.ascension.irradiated_farmland.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState toolModifiedState = this.getToolModifiedState(context, level.getBlockState(pos), false);
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of((ctx) -> {
            return true;
        }, changeIntoState(toolModifiedState));
        if (pair == null) {
            return InteractionResult.PASS;
        } else {
            Predicate<UseOnContext> predicate = pair.getFirst();
            Consumer<UseOnContext> consumer = pair.getSecond();
            if (predicate.test(context)) {
                Player player = context.getPlayer();
                level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide()) {
                    consumer.accept(context);
                    if (player != null) {
                        context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide());
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    private BlockState getToolModifiedState(UseOnContext context, BlockState state, boolean simulate) {
        Block block = state.getBlock();
        if (block == Blocks.ROOTED_DIRT) {
            if (!simulate && !context.getLevel().isClientSide()) {
                Block.popResourceFromFace(context.getLevel(), context.getClickedPos(), context.getClickedFace(), new ItemStack(Items.HANGING_ROOTS));
            }
            return Blocks.DIRT.defaultBlockState();
        } else if ((block == Blocks.GRASS_BLOCK || block == Blocks.DIRT_PATH || block == Blocks.DIRT || block == Blocks.COARSE_DIRT) &&
                context.getLevel().getBlockState(context.getClickedPos().above()).isAir()) {
            return block == Blocks.COARSE_DIRT ? Blocks.DIRT.defaultBlockState() : (context.getLevel().getRandom().nextFloat() < 0.75f ? BlockInit.IRRADIATED_FARMLAND.get().defaultBlockState() : Blocks.FARMLAND.defaultBlockState());
        }
        return null;
    }
}
