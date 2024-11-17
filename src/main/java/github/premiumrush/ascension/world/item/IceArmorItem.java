package github.premiumrush.ascension.world.item;

import github.premiumrush.ascension.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

public class IceArmorItem extends ArmorItem {
    public IceArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.ice_armor2.tooltip"));
        if(itemStack.is(ItemInit.ICE_BOOTS.get())) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.ice_armor3.tooltip"));
        }
        pToolTipComponents.add(Component.translatable("tooltip.ascension.ice_armor1.tooltip"));
        super.appendHoverText(itemStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && entity instanceof Player player && hasIceArmorSetOn(player)) {
            onEntityMoved(player, level, player.blockPosition(), 4);
        }
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        if(hasIceBootsOn((Player) wearer)) {
            return true;
        }
        return super.canWalkOnPowderedSnow(stack, wearer);
    }

    private boolean hasIceBootsOn(Player player) {
        return player.getInventory().getArmor(0).is(ItemInit.ICE_BOOTS.get());
    }

    private boolean hasIceArmorSetOn(Player player) {
        return player.getInventory().getArmor(0).is(ItemInit.ICE_BOOTS.get()) &&
                player.getInventory().getArmor(1).is(ItemInit.ICE_LEGGINGS.get()) &&
                player.getInventory().getArmor(2).is(ItemInit.ICE_CHESTPLATE.get()) &&
                player.getInventory().getArmor(3).is(ItemInit.ICE_HELMET.get());
    }

    public static void onEntityMoved(LivingEntity livingEntity, Level level, BlockPos pos, int range) {
        if (livingEntity.onGround()) {
            BlockState blockstate = Blocks.FROSTED_ICE.defaultBlockState();
            int i = Math.min(16, 2 + range);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            Iterator iterator = BlockPos.betweenClosed(pos.offset(-i, -1, -i), pos.offset(i, -1, i)).iterator();

            while(iterator.hasNext()) {
                BlockPos blockpos = (BlockPos)iterator.next();
                if (blockpos.closerToCenterThan(livingEntity.position(), (double)i)) {
                    blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState blockstate1 = level.getBlockState(blockpos$mutableblockpos);
                    if (blockstate1.isAir()) {
                        BlockState blockstate2 = level.getBlockState(blockpos);
                        if (blockstate2 == FrostedIceBlock.meltsInto() && blockstate.canSurvive(level, blockpos) && level.isUnobstructed(blockstate, blockpos, CollisionContext.empty()) && !EventHooks.onBlockPlace(livingEntity, BlockSnapshot.create(level.dimension(), level, blockpos), Direction.UP)) {
                            level.setBlockAndUpdate(blockpos, blockstate);
                            level.scheduleTick(blockpos, Blocks.FROSTED_ICE, Mth.nextInt(livingEntity.getRandom(), 60, 120));
                        }
                    }
                }
            }
        }
    }
}
