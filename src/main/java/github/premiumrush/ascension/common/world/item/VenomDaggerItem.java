package github.premiumrush.ascension.common.world.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VenomDaggerItem extends BaseDaggerItem {
    public VenomDaggerItem(Tier p_tier, Properties p_properties) {
        super(p_tier, p_properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.venom_dagger.tooltip"));
        pToolTipComponents.add(Component.translatable("tooltip.ascension.venom_dagger2.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Player player = (Player) attacker;
        if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 40, 5), attacker);
            if (target.hasEffect(MobEffects.POISON)) {
                player.getCooldowns().addCooldown(stack.getItem(), 100);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
