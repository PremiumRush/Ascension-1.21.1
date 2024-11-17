package github.premiumrush.ascension.world.item;

import github.premiumrush.ascension.init.MobEffectInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RazorSwordItem extends SwordItem {
    public RazorSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.razor_sword_item.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if(
                !(pTarget instanceof Skeleton) &&
                        !(pTarget instanceof SkeletonHorse) &&
                        !(pTarget instanceof WitherSkeleton) &&
                        !(pTarget instanceof WitherBoss) &&
                        !(pTarget instanceof Stray) &&
                        !(pTarget instanceof IronGolem) &&
                        !(pTarget instanceof SnowGolem) &&
                        !(pTarget instanceof Shulker) &&
                        !(pTarget instanceof Endermite) &&
                        !(pTarget instanceof EnderMan))
        {
            pTarget.addEffect(new MobEffectInstance(MobEffectInit.BLEEDING, 120, 1), pAttacker);
        }
        return super.hurtEnemy(pStack,pTarget,pAttacker);
    }
}
