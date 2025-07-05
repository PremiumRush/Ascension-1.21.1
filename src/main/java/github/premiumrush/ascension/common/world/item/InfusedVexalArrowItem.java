package github.premiumrush.ascension.common.world.item;

import github.premiumrush.ascension.common.world.entity.custom.InfusedVexalArrow;
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

public class InfusedVexalArrowItem extends ArrowItem implements ProjectileItem {
    public InfusedVexalArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Projectile asProjectile(@NotNull Level level, Position pos, ItemStack stack, @NotNull Direction direction) {
        InfusedVexalArrow infusedVexalArrow = new InfusedVexalArrow(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), null);
        infusedVexalArrow.pickup = AbstractArrow.Pickup.DISALLOWED;
        return infusedVexalArrow;
    }

    @Override
    public @NotNull AbstractArrow createArrow(@NotNull Level level, ItemStack ammo, @NotNull LivingEntity shooter, @Nullable ItemStack weapon) {
        return new InfusedVexalArrow(level, shooter, ammo.copyWithCount(1), weapon);
    }

    @Override
    public boolean isInfinite(@NotNull ItemStack ammo, @NotNull ItemStack bow, @NotNull LivingEntity livingEntity) {
        return false;
    }
}
