package github.premiumrush.ascension.common.event.player;

import github.premiumrush.ascension.common.init.BlockInit;
import github.premiumrush.ascension.common.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

import static net.minecraft.world.level.block.SculkShriekerBlock.CAN_SUMMON;

public class UseItemOnBlockEvents {
    @SubscribeEvent
    public void sculkShriekerConversionEvent(UseItemOnBlockEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        ItemStack stack = event.getItemStack();
        BlockState state = level.getBlockState(pos);
        if (state.is(Blocks.SCULK_SHRIEKER) && !state.getValue(CAN_SUMMON) && stack.is(Items.ECHO_SHARD)) {
            stack.consume(1, event.getPlayer());
            level.playLocalSound(pos, SoundEvents.WARDEN_STEP, SoundSource.BLOCKS, 1, 0, false);
            level.setBlock(pos, state.setValue(CAN_SUMMON, true), Block.UPDATE_ALL);
        }
    }
    @SubscribeEvent
    public void buddingBlockConversion(UseItemOnBlockEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        ItemStack stack = event.getItemStack();
        BlockState state = level.getBlockState(pos);
        if ((state.is(Blocks.AMETHYST_BLOCK) || state.is(BlockInit.VEXAL_BLOCK)) && stack.is(ItemInit.ICE_GEM.get())) {
            stack.consume(1, event.getPlayer());
            level.playLocalSound(event.getPos(), SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 1, 0, false);
            BlockState newState = state.is(Blocks.AMETHYST_BLOCK) ? Blocks.BUDDING_AMETHYST.defaultBlockState() : BlockInit.BUDDING_VEXAL_BLOCK.get().defaultBlockState();
            level.setBlock(event.getPos(), newState, Block.UPDATE_ALL);
        }
    }
}
