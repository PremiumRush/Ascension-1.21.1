package github.premiumrush.ascension.common.world.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class BansheeEntity extends AbstractGhostEntity {
    public BansheeEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
}
