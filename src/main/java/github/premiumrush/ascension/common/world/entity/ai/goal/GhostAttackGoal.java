package github.premiumrush.ascension.common.world.entity.ai.goal;

import github.premiumrush.ascension.common.world.entity.custom.AbstractGhostEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class GhostAttackGoal extends MeleeAttackGoal {
    private final AbstractGhostEntity ghost;

    public GhostAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        this.ghost = (AbstractGhostEntity) mob;
    }

    @Override
    public void stop() {
        ghost.setAttacking(false);
        super.stop();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if (this.canPerformAttack(target) && ghost.attackAnimationTimeout == 0) {
            ghost.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
            this.resetAttackCooldown();
            this.ghost.lastAttackFallTimer = 60;
            this.ghost.addDeltaMovement((ghost.position().vectorTo(target.position())).scale(0.4).add(0,0.5,0));
            this.ghost.swing(InteractionHand.MAIN_HAND);
            this.ghost.doHurtTarget(target);
            ghost.setAttacking(true);
        } else if (ghost.attackAnimationTimeout == 0) {
            ghost.setAttacking(false);
        }
    }

    @Override
    public boolean canUse() {
        return super.canUse() && ghost.attackAnimationTimeout == 0;
    }

    @Override
    protected boolean canPerformAttack(LivingEntity entity) {
        return this.isTimeToAttack() && this.isWithinMeleeAttackRange(this.ghost, entity) && this.mob.getSensing().hasLineOfSight(entity);
    }

    public boolean isWithinMeleeAttackRange(LivingEntity attacker, LivingEntity target) {
        return attacker.getBoundingBox().inflate(1.15).intersects(target.getHitbox());
    }
}
