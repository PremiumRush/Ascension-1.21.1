package github.premiumrush.ascension.world.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WingedSwordItem extends SwordItem {
    public WingedSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.winged_sword_item.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        pTarget.addEffect(new MobEffectInstance(MobEffects.LEVITATION,20,2),pAttacker);
        return super.hurtEnemy(pStack,pTarget, pAttacker);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pTarget, InteractionHand pHand) {
        if(!pPlayer.getCooldowns().isOnCooldown(pStack.getItem())) {

            double Radians = Math.toRadians(pPlayer.getYRot());
            Vec3 vec3 = new Vec3(-Math.sin(Radians)*1.5, 0.7, Math.cos(Radians)*1.5);

            playParticles(pPlayer.level(), pPlayer);
            pTarget.setDeltaMovement(vec3);
            pStack.hurtAndBreak(1,pPlayer,EquipmentSlot.MAINHAND);
            pPlayer.getCooldowns().addCooldown(pStack.getItem(), 65);
        }
        return super.interactLivingEntity(pStack,pPlayer,pTarget,pHand);
    }

    public void playParticles(Level level, LivingEntity entity) {
        double Radians1 = Math.toRadians(entity.getYRot());
        double Radians2 = Math.toRadians(entity.getYRot()+7);
        double Radians4 = Math.toRadians(entity.getYRot()+14);
        double Radians3 = Math.toRadians(entity.getYRot()-7);
        double Radians5 = Math.toRadians(entity.getYRot()-14);

        Vec3 vec3_1 = new Vec3(-Math.sin(Radians1)*0.4, 0.2, Math.cos(Radians1)*0.4);
        Vec3 vec3_2 = new Vec3(-Math.sin(Radians2)*0.4, 0.2, Math.cos(Radians2)*0.4);
        Vec3 vec3_3 = new Vec3(-Math.sin(Radians3)*0.4, 0.2, Math.cos(Radians3)*0.4);
        Vec3 vec3_4 = new Vec3(-Math.sin(Radians4)*0.4, 0.2, Math.cos(Radians4)*0.4);
        Vec3 vec3_5 = new Vec3(-Math.sin(Radians5)*0.4, 0.2, Math.cos(Radians5)*0.4);

        level.addParticle(ParticleTypes.FIREWORK, entity.position().x(), entity.position().y(), entity.position().z(), vec3_1.x(), vec3_1.y(), vec3_1.z());
        level.addParticle(ParticleTypes.FIREWORK, entity.position().x(), entity.position().y(), entity.position().z(), vec3_2.x(), vec3_2.y(), vec3_2.z());
        level.addParticle(ParticleTypes.FIREWORK, entity.position().x(), entity.position().y(), entity.position().z(), vec3_3.x(), vec3_3.y(), vec3_3.z());
        level.addParticle(ParticleTypes.FIREWORK, entity.position().x(), entity.position().y(), entity.position().z(), vec3_4.x(), vec3_4.y(), vec3_4.z());
        level.addParticle(ParticleTypes.FIREWORK, entity.position().x(), entity.position().y(), entity.position().z(), vec3_5.x(), vec3_4.y(), vec3_5.z());
    }
}
