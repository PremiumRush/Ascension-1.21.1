package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.common.init.ParticleInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Random;

public class InertiaSparkEntity extends AbstractSparkEntity {
    public InertiaSparkEntity(EntityType<?> entityType, Level level) {
        super(entityType, level, 600);
    }

    @Override
    public void tick() {
        super.tick();
        List<Entity> list = this.level().getEntities(this, new AABB(getX()-3.5,getY()-2,getZ()-3.5,getX()+3.5,getY()+3.5,getZ()+3.5));
        if (!list.isEmpty()) {
            for (Entity element : list) {
                if (element instanceof LivingEntity livingEntity /*&& !(element instanceof Player)*/) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30));
                }
            }
        }

        if (level().isClientSide()) {
            switch (getLifetime()) {
                case 600 -> spawnParticleSet(level(), -1.0, 1.0);
                case 599 -> spawnParticleSet(level(), -1.5, 1.5);
                case 598 -> spawnParticleSet(level(), -2.0, 2.0);
                case 597 -> spawnParticleSet(level(), -2.5, 2.5);
                case 596 -> spawnParticleSet(level(), -3.0, 3.0);
                case 595 -> spawnParticleSet(level(), -3.5, 3.5);
            }
        }
    }

    private void spawnParticleSet(Level level, double origin, double bound) {
        for (int i = 0; i<45; i++) {
            addParticles(level, origin, bound);
        }
    }
    private void addParticles(Level level, double origin, double bound) {
        Random random = new Random();
        double xRandom = random.nextDouble(origin, bound);
        double yRandom = 0;
        switch ((int)bound) {
            case 1 -> yRandom = random.nextDouble(origin, bound)+2;
            case 2 -> yRandom = random.nextDouble(origin, bound)+0.5;
            case 3 -> yRandom = random.nextDouble(origin, bound)-1.0;
            case 4 -> yRandom = random.nextDouble(origin, bound)-1.5;
        }
        double zRandom = random.nextDouble(origin, bound);
        level.addParticle(ParticleInit.DARKNESS_PARTICLE.get(), getX()+0.5+xRandom, getY()+0.5+yRandom, getZ()+0.5+zRandom, 0,0,0);
    }
}
