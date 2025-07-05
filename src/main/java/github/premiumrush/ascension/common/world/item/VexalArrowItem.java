package github.premiumrush.ascension.common.world.item;

import github.premiumrush.ascension.common.world.entity.custom.VexalArrow;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class VexalArrowItem extends ArrowItem implements ProjectileItem {
    public VexalArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Projectile asProjectile(@NotNull Level level, Position pos, ItemStack stack, @NotNull Direction direction) {
        VexalArrow vexal_arrow = new VexalArrow(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), null);
        vexal_arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return vexal_arrow;
    }

    @Override
    public @NotNull AbstractArrow createArrow(@NotNull Level level, ItemStack ammo, @NotNull LivingEntity shooter, @Nullable ItemStack weapon) {
        return new VexalArrow(level, shooter, ammo.copyWithCount(1), weapon);
    }

    @Override
    public boolean isInfinite(@NotNull ItemStack ammo, @NotNull ItemStack bow, @NotNull LivingEntity livingEntity) {
        return false;
    }
}
