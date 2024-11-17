package github.premiumrush.ascension.world.item;

import github.premiumrush.ascension.init.ParticleInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShadowDaggerItem extends DaggerItem {
    public ShadowDaggerItem(Tier p_tier, Properties p_properties) {
        super(p_tier, p_properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.shadow_dagger.tooltip"));
        pToolTipComponents.add(Component.translatable("tooltip.ascension.shadow_dagger2.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(!player.getCooldowns().isOnCooldown(player.getUseItem().getItem()) && player.onGround()) {
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 110,0,false,false,false));
            playParticles(level, player);
            player.playSound(SoundEvents.WOOL_FALL);
            doMove();
            player.causeFoodExhaustion(1.5F);
            player.getCooldowns().addCooldown(stack.getItem(), 75);
        } else if (!player.getCooldowns().isOnCooldown(player.getUseItem().getItem()) && !player.onGround()) {
            player.push(0.0,-1.0,0.0);
            player.causeFoodExhaustion(1.0F);
            player.getCooldowns().addCooldown(stack.getItem(), 25);
        }
        return super.use(level, player, hand);
    }

    private void playParticles(Level level, Player player) {
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX(),player.getY(),player.getZ(),0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX(),player.getY()+0.4,player.getZ(),0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX(),player.getY()+0.8,player.getZ(),0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX(),player.getY()+1.2,player.getZ(),0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX(),player.getY()+1.6,player.getZ(),0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX(),player.getY()+2.0,player.getZ(),0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()+0.3,player.getY(),player.getZ()+0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()-0.3,player.getY(),player.getZ()+0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()+0.3,player.getY(),player.getZ()-0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()-0.3,player.getY(),player.getZ()-0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()+0.3,player.getY()+0.4,player.getZ()+0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()-0.3,player.getY()+0.4,player.getZ()+0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()+0.3,player.getY()+0.4,player.getZ()-0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()-0.3,player.getY()+0.4,player.getZ()-0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()+0.3,player.getY()+0.8,player.getZ()+0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()-0.3,player.getY()+0.8,player.getZ()+0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()+0.3,player.getY()+0.8,player.getZ()-0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()-0.3,player.getY()+0.8,player.getZ()-0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()+0.3,player.getY()+1.2,player.getZ()+0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()-0.3,player.getY()+1.2,player.getZ()+0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()+0.3,player.getY()+1.2,player.getZ()-0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()-0.3,player.getY()+1.2,player.getZ()-0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()+0.3,player.getY()+1.6,player.getZ()+0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()-0.3,player.getY()+1.6,player.getZ()+0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()+0.3,player.getY()+1.6,player.getZ()-0.3,0,0,0);
        level.addParticle(ParticleInit.SHADOW_DODGE_PARTICLE.get(), player.getX()-0.3,player.getY()+1.6,player.getZ()-0.3,0,0,0);
        level.addParticle(ParticleTypes.FIREWORK, player.getX()+0.3,player.getY()+1.4,player.getZ()+0.3,0,0,0);
        level.addParticle(ParticleTypes.FIREWORK, player.getX()-0.3,player.getY()+1.4,player.getZ()+0.3,0,0,0);
        level.addParticle(ParticleTypes.FIREWORK, player.getX()+0.3,player.getY()+1.4,player.getZ()-0.3,0,0,0);
        level.addParticle(ParticleTypes.FIREWORK, player.getX()-0.3,player.getY()+1.4,player.getZ()-0.3,0,0,0);
    }

/*
The dodge functionality used in this Mod is shortened and adapted from the OpenSource Project "ElenaiDodge2.0" by ElenaiDev. Thanks for being OpenSource!
GitHub link: https://github.com/ElenaiDev/ElenaiDodge2.0
*/

    private void inputDirectionAddDeltaMovement(InputDirection direction) {
        Minecraft instance = Minecraft.getInstance();

        if(instance.player != null) {
            Vec3 facingDirection = instance.player.getLookAngle().multiply(1.16, 0.0, 1.16).normalize();

            Vec3 front = new Vec3(facingDirection.x,0.2,facingDirection.z);
            Vec3 back = new Vec3(-facingDirection.x,0.2,-facingDirection.z);
            Vec3 left = new Vec3(facingDirection.z, 0.2, -facingDirection.x);
            Vec3 right = new Vec3(-facingDirection.z, 0.2, facingDirection.x);
            Vec3 frontLeft = front.add(left).scale(0.5);
            Vec3 frontRight = front.add(right).scale(0.5);
            Vec3 backRight = back.add(right).scale(0.5);
            Vec3 backLeft = back.add(left).scale(0.5);

            switch (direction) {
                case FRONT:
                    instance.player.addDeltaMovement(front);
                    break;
                case BACK:
                    instance.player.addDeltaMovement(back);
                    break;
                case LEFT:
                    instance.player.addDeltaMovement(left);
                    break;
                case RIGHT:
                    instance.player.addDeltaMovement(right);
                    break;
                case FRONT_LEFT:
                    instance.player.addDeltaMovement(frontLeft);
                    break;
                case FRONT_RIGHT:
                    instance.player.addDeltaMovement(frontRight);
                    break;
                case BACK_LEFT:
                    instance.player.addDeltaMovement(backLeft);
                    break;
                case BACK_RIGHT:
                    instance.player.addDeltaMovement(backRight);
                    break;
            }
        }
    }

    private enum InputDirection {
        FRONT,BACK,LEFT,RIGHT,FRONT_LEFT,FRONT_RIGHT,BACK_RIGHT,BACK_LEFT;
    }

    private void doMove() {
        Minecraft instance = Minecraft.getInstance();
        assert instance.player != null;
        if (instance.player.input.leftImpulse > 0 && instance.player.input.forwardImpulse > 0) {
            inputDirectionAddDeltaMovement(InputDirection.FRONT_LEFT);
        } else if(instance.player.input.leftImpulse < 0 && instance.player.input.forwardImpulse > 0) {
            inputDirectionAddDeltaMovement(InputDirection.FRONT_RIGHT);
        } else if(instance.player.input.leftImpulse > 0 && instance.player.input.forwardImpulse < 0) {
            inputDirectionAddDeltaMovement(InputDirection.BACK_LEFT);
        } else if(instance.player.input.leftImpulse < 0 && instance.player.input.forwardImpulse < 0) {
            inputDirectionAddDeltaMovement(InputDirection.BACK_RIGHT);
        }else if (instance.player.input.leftImpulse > 0) {
            inputDirectionAddDeltaMovement(InputDirection.LEFT);
        } else if (instance.player.input.leftImpulse < 0) {
            inputDirectionAddDeltaMovement(InputDirection.RIGHT);
        } else if (instance.player.input.forwardImpulse > 0) {
            inputDirectionAddDeltaMovement(InputDirection.FRONT);
        } else {
            inputDirectionAddDeltaMovement(InputDirection.BACK);
        }
    }
}
