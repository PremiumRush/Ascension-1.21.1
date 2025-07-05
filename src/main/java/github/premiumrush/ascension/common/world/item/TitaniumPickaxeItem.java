package github.premiumrush.ascension.common.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TitaniumPickaxeItem extends PickaxeItem {
    public TitaniumPickaxeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.titanium_item.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        state.getBlock().popExperience((ServerLevel) level, pos, state.getBlock().getExpDrop(state, level, pos, null, miningEntity, stack));
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.isDeadOrDying()) {
            int reward = net.neoforged.neoforge.event.EventHooks.getExperienceDrop(target, (Player) attacker, target.getExperienceReward((ServerLevel) target.level(), attacker));
            ExperienceOrb.award((ServerLevel) target.level(), target.position(), reward);
        }
        super.postHurtEnemy(stack, target, attacker);
    }
}
