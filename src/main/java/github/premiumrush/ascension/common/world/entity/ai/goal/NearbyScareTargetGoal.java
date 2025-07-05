package github.premiumrush.ascension.common.world.entity.ai.goal;

import github.premiumrush.ascension.common.world.entity.custom.TamedGhostEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

public class NearbyScareTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    protected TargetingConditions animalTargetConditions;
    protected Class<T> targetType;

    public NearbyScareTargetGoal(Mob tamedGhostEntity, Class<T> targetType, boolean mustSee, boolean mustReach) {
        this(tamedGhostEntity, targetType, mustSee, mustReach, null);
    }

    public NearbyScareTargetGoal(Mob mob, Class<T> targetType, boolean mustSee, boolean mustReach, Predicate<LivingEntity> targetPredicate) {
        super(mob, targetType, mustSee, mustReach);
        this.targetConditions = TargetingConditions.forNonCombat().range(this.getFollowDistance()).selector(targetPredicate);
        this.animalTargetConditions = TargetingConditions.forNonCombat().range(this.getFollowDistance()).selector((entity) -> entity instanceof TamableAnimal tamableAnimal && tamableAnimal.isTame());
    }

    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        }
        if (((TamedGhostEntity)this.mob).getScareTimeout() != 0) {
            return false;
        } else {
            this.findTarget();
            return this.target != null;
        }
    }

    @Override
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
}
