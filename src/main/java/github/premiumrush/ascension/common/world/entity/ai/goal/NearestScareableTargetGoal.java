package github.premiumrush.ascension.common.world.entity.ai.goal;

import github.premiumrush.ascension.common.world.entity.custom.TamedGhostEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class NearestScareableTargetGoal<T extends LivingEntity> extends TargetGoal {
    protected Class<T> targetType;
    protected final int randomInterval;
    @Nullable
    protected LivingEntity target;
    protected TargetingConditions targetConditions;
    protected TargetingConditions animalTargetConditions;

    public NearestScareableTargetGoal(TamedGhostEntity tamedGhostEntity, boolean mustSee, boolean mustReach) {
        this(tamedGhostEntity, 10, mustSee, mustReach, null);
    }

    public NearestScareableTargetGoal(TamedGhostEntity tamedGhostEntity, int randomInterval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(tamedGhostEntity, mustSee, mustReach);
        this.randomInterval = reducedTickDelay(randomInterval);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.targetConditions = TargetingConditions.forNonCombat().range(this.getFollowDistance()).selector(targetPredicate);
        this.animalTargetConditions = TargetingConditions.forNonCombat().range(this.getFollowDistance()).selector((entity) -> entity instanceof TamableAnimal tamableAnimal && tamableAnimal.isTame());
    }

    public boolean canUse() {
        if (((TamedGhostEntity)this.mob).getScareTimeout() != 0) {
            return false;
        } else {
            this.findTarget();
            return this.target != null;
        }
    }

    protected AABB getTargetSearchArea(double targetDistance) {
        return this.mob.getBoundingBox().inflate(targetDistance, 4.0, targetDistance);
    }

    protected void findTarget() {
        if (this.mob.getTarget() != null) {
            this.target = this.mob.getTarget();
        } else {
            System.out.println("Trying to find Target");
            if (this.mob instanceof TamedGhostEntity ghost && ghost.getScareTimeout() == 0) {
                int rand = ghost.getRandom().nextInt(0, 3);
                switch (0) { // TESTING 0 --> rand
                    case 0 -> this.targetType = (Class<T>) Player.class;
                    case 1 -> this.targetType = (Class<T>) Cat.class;
                    case 2 -> this.targetType = (Class<T>) Wolf.class;
                }
                if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
                    this.target = ghost.level().getNearestEntity(ghost.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (p_148152_) -> {
                        return true;
                    }), this.animalTargetConditions, ghost, ghost.getX(), ghost.getEyeY(), ghost.getZ());
                    System.out.println("Ghost Found Scare Target: " + this.target);
                } else {
                    this.target = ghost.level().getNearestPlayer(this.targetConditions, ghost, ghost.getX(), ghost.getEyeY(), ghost.getZ());
                    System.out.println("Ghost Found Scare Target: " + this.target);
                }
            }
        }
    }



    @Override
    public void start() {
        if (this.target != null) {
            this.mob.setTarget(this.target);
            System.out.println("Target Set for GhostEntity: "+this.target);
        }
        super.start();
    }

    @Override
    public void stop() {
        //this.targetMob = null;
        this.mob.setTarget(this.target);
    }
}
