package github.premiumrush.ascension.common.world.entity.ai.goal;

import github.premiumrush.ascension.common.world.entity.custom.EmberGolemEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class EmberGolemAttackGoal extends MeleeAttackGoal {
    private final EmberGolemEntity entity;
    private int animationAttackDelay = 3;
    private int ticksUntilNextAttack = 20;
    private boolean shouldCountTillNextAttack = false;

    public EmberGolemAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        entity = ((EmberGolemEntity) mob);
    }

    @Override
    public void start() {
        super.start();
        animationAttackDelay = 3;
        ticksUntilNextAttack = 20;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy) {
        if (isEnemyWithinAttackDistance(enemy)) {
            shouldCountTillNextAttack = true;
            if (isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }
            if (isTimeToAttack()) {
                this.mob.getLookControl().setLookAt(enemy.getX(), enemy.getEyeY(), enemy.getZ());
                performAttack(enemy);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy) {
        float pDistToEnemySqr = Mth.sqrt(pEnemy.distanceTo(entity));
        float pAttackReachSqr = Mth.sqrt((float)entity.getAttributes().getBaseValue(Attributes.ENTITY_INTERACTION_RANGE));
        return pDistToEnemySqr <= pAttackReachSqr;
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(20);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= animationAttackDelay;
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }


    protected void performAttack(LivingEntity pEnemy) {
        this.resetAttackCooldown();
        this.mob.swing(InteractionHand.MAIN_HAND);
        this.mob.doHurtTarget(pEnemy);
    }

    @Override
    public void tick() {
        super.tick();
        if(shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }
}
