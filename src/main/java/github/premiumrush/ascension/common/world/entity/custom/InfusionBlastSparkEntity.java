package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.common.init.EntityInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class InfusionBlastSparkEntity extends AbstractSparkEntity {
    public UUID owner_uuid;

    public InfusionBlastSparkEntity(EntityType<?> entityType, Level level) {
        super(entityType, level, 120);
    }
    public InfusionBlastSparkEntity(Level level, @Nullable UUID owner_uuid) {
        super(EntityInit.INFUSION_BLAST_SPARK.get(), level, 120);
        this.owner_uuid = owner_uuid;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putUUID("owner", this.owner_uuid);
    }
    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.owner_uuid = compoundTag.getUUID("owner");
    }

    @Override
    public void tick() {
        super.tick();
        List<Entity> list = this.level().getEntities(this, new AABB(getX()-2,getY()-2,getZ()-2,getX()+2,getY()+2,getZ()+2));
        if (!list.isEmpty()) {
            for (Entity element : list) {
                if (element instanceof LivingEntity target && !target.getUUID().equals(this.owner_uuid)) {
                    switch (this.getLifetime()) {
                        case 115, 110, 90, 85, 65, 60, 40, 35, 15, 10 -> {
                            target.hurt(damageSources().magic(), 1);
                            target.invulnerableTime = 0;
                        }
                    }
                }
            }
        }
        switch (this.getLifetime()) {
            case 115, 110, 90, 85, 65, 60, 40, 35, 15, 10 -> this.spawnParticleSet(this.level(), -2.4,2.4, 24);
        }
    }

    private void spawnParticleSet(Level level, double origin, double bound, int amount) {
        if (level.isClientSide()) {
            for (int i = 0; i < amount; i++) {
                this.addParticle(level, origin, bound);
            }
        }
    }
    private void addParticle(Level level, double origin, double bound) {
        Random random = new Random();
        double xRandom = random.nextDouble(origin, bound);
        double yRandom = random.nextDouble(0, 0.2);
        double zRandom = random.nextDouble(origin, bound);
        level.addParticle(ParticleTypes.ENCHANTED_HIT, getX()+0.5+xRandom, getY()+0.5+yRandom, getZ()+0.5+zRandom, 0,0,0);
    }
}
