package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.Ascension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AbstractSparkEntity extends Entity {
    private static final EntityDataAccessor<Integer> LIFETIME = SynchedEntityData.defineId(AbstractSparkEntity.class, EntityDataSerializers.INT);
    private final int lifetime;

    public AbstractSparkEntity(EntityType<?> entityType, Level level, int lifetime) {
        super(entityType, level);
        this.lifetime = lifetime;
        this.setLifetime(lifetime);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(LIFETIME, this.lifetime);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        var spark_data = new CompoundTag();
        spark_data.putInt("lifetime", this.getLifetime());
        compoundTag.put(Ascension.MODID, spark_data);
    }
    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        CompoundTag spark_data = compoundTag.getCompound(Ascension.MODID);
        this.setLifetime(spark_data.getInt("lifetime"));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getLifetime() > 0) {
            this.setLifetime((getLifetime()-1));
        } else {
            this.discard();
        }
    }

    public int getLifetime() {
        return this.entityData.get(LIFETIME);
    }
    public void setLifetime(int lifetime) {
        this.entityData.set(LIFETIME, lifetime);
    }
}
