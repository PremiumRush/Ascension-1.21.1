package github.premiumrush.ascension.common.world.item;

import github.premiumrush.ascension.common.world.entity.custom.InertiaProjectile;
import github.premiumrush.ascension.common.init.EntityInit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ShadowStaffItem extends TieredItem {
    public ShadowStaffItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!player.getCooldowns().isOnCooldown(this)) {
            Vec3 movement = player.getLookAngle();
            InertiaProjectile entity = new InertiaProjectile(EntityInit.INERTIA_PROJECTILE.get(), level);
            entity.setOwner(player);
            entity.addDeltaMovement(movement);
            entity.moveTo(player.getX(), player.getY() + 1, player.getZ(), player.getYRot(), player.getXRot());
            level.addFreshEntity(entity);
            player.getItemInHand(usedHand).hurtAndBreak(1, player, player.getItemInHand(usedHand).getEquipmentSlot());
            player.getCooldowns().addCooldown(this, 600);
        }
        return super.use(level, player, usedHand);
    }
}
