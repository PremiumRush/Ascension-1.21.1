package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.common.world.entity.ai.goal.GhostScareEntityGoal;
import github.premiumrush.ascension.common.world.entity.ai.goal.NearbyScareTargetGoal;
import github.premiumrush.ascension.common.world.entity.ai.goal.NearestScareableTargetGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class TamedGhostEntity extends GhostEntity {
    public static final EntityDataAccessor<Integer> SCARE_TIMEOUT = SynchedEntityData.defineId(TamedGhostEntity.class, EntityDataSerializers.INT);

    public TamedGhostEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setScareTimeout(this.getScareTimeout());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GhostScareEntityGoal(this, 1.4, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.9));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestScareableTargetGoal<>(this, true, false));
        this.targetSelector.addGoal(2, new NearbyScareTargetGoal<>(this, Player.class, true, false));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SCARE_TIMEOUT, 1200);
    }
    public void setScareTimeout(int timeout) {
        this.entityData.set(SCARE_TIMEOUT, timeout);
    }
    public int getScareTimeout() {
        return this.entityData.get(SCARE_TIMEOUT);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            if (this.getTarget() != null) {
                System.out.println("Current Target:" + this.getTarget());
            } else {
                System.out.println("Current Target: null");
            }

            if (this.getScareTimeout() > 0) {
                this.setScareTimeout(this.getScareTimeout() - 1);
                System.out.println("ScareTimeout: " + this.getScareTimeout());
            } else {
                System.out.println("Scare Timeout has reached 0 - Can Scare Entity!");
            }
        }
    }
}
