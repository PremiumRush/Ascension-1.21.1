package github.premiumrush.ascension.common.world.entity.ai.goal;

import github.premiumrush.ascension.common.world.entity.custom.EmberGolemEntity;
import github.premiumrush.ascension.common.world.item.GolemArmorItem;
import github.premiumrush.ascension.common.util.AscensionUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class EmberGolemWaveAtPlayerGoal extends Goal implements AscensionUtil {
    protected final EmberGolemEntity entity;
    protected final Class<? extends LivingEntity> waveAtType;
    protected final float waveDistance;
    protected final TargetingConditions waveAtContext;
    private int lookAtWaveAtCountdown = 30;
    private Entity waveAt;

    public EmberGolemWaveAtPlayerGoal(Mob mob, Class<? extends LivingEntity> waveAtType, float waveDistance) {
        this.entity = (EmberGolemEntity) mob;
        this.waveAtType = waveAtType;
        this.waveDistance = waveDistance;
        this.waveAtContext = TargetingConditions.forNonCombat().range(waveDistance);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        this.waveAt = null;
        entity.setWaving(false);
    }

    @Override
    public boolean canUse() {
        if (this.entity.getTarget() != null) {
            this.waveAt = this.entity.getTarget();
        }

        if (this.waveAtType == Player.class) {
            this.waveAt = this.entity.level().getNearestPlayer(this.waveAtContext, this.entity, this.entity.getX(), this.entity.getEyeY(), this.entity.getZ());
        } else {
            return false;
        }

        return this.waveAt != null;
    }

    @Override
    public void tick() {
        super.tick();
        if (entity.wavingAnimationTimeout <= 0 && hasArmorSet((Player) this.waveAt, GolemArmorItem.class) && entity.getTarget() == null) {
            if (entity.distanceTo(waveAt) <= 5) {
                entity.setWaving(true);
                setLookAtWaveAtCountdown(30);
            } else if (entity.distanceTo(waveAt) > 5) {
                entity.setWaving(false);
            }
        } else {
            entity.setWaving(false);
        }

        if (lookAtWaveAtCountdown > 0) {
            this.entity.getLookControl().setLookAt(this.waveAt.getX(), this.waveAt.getEyeY(), this.waveAt.getZ());
            this.lookAtWaveAtCountdown--;
        }
    }

    public void setLookAtWaveAtCountdown(int lookAtWaveAtCountdown) {
        this.lookAtWaveAtCountdown = lookAtWaveAtCountdown;
    }
}
