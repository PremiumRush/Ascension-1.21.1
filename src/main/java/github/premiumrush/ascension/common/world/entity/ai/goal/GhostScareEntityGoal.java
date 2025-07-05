package github.premiumrush.ascension.common.world.entity.ai.goal;

import github.premiumrush.ascension.common.world.entity.custom.TamedGhostEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class GhostScareEntityGoal extends MeleeAttackGoal {
    private final TamedGhostEntity tamedGhost;

    public GhostScareEntityGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        this.tamedGhost = (TamedGhostEntity) mob;
    }

    @Override
    public void stop() {
        tamedGhost.setAttacking(false);
        super.stop();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if (this.canPerformAttack(target) && tamedGhost.attackAnimationTimeout == 0) {
            tamedGhost.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
            this.resetAttackCooldown();
            this.tamedGhost.lastAttackFallTimer = 60;
            this.tamedGhost.addDeltaMovement((tamedGhost.position().vectorTo(target.position())).scale(0.1).add(0,0.3,0));
            tamedGhost.setAttacking(true);
            tamedGhost.setTarget(null);
            tamedGhost.setScareTimeout(600 + tamedGhost.getRandom().nextInt(20, 300));
        } else if (tamedGhost.attackAnimationTimeout == 0) {
            tamedGhost.setAttacking(false);
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (tamedGhost.getTarget() == null) {
            return false;
        }
        return super.canContinueToUse();
    }

    @Override
    public boolean canUse() {
        return super.canUse() && tamedGhost.attackAnimationTimeout == 0;
    }

    @Override
    protected boolean canPerformAttack(LivingEntity entity) {
        return this.isTimeToAttack() && this.isWithinMeleeAttackRange(this.tamedGhost, entity) && this.mob.getSensing().hasLineOfSight(entity);
    }

    public boolean isWithinMeleeAttackRange(LivingEntity attacker, LivingEntity target) {
        return attacker.getBoundingBox().inflate(1.15).intersects(target.getHitbox());
    }
}
