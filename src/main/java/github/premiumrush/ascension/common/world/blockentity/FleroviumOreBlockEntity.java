package github.premiumrush.ascension.common.world.blockentity;

import github.premiumrush.ascension.common.init.BlockEntityInit;
import github.premiumrush.ascension.common.init.ItemInit;
import github.premiumrush.ascension.common.init.MobEffectInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class FleroviumOreBlockEntity extends BlockEntity {
    private AABB cachedAABB = null;
    private BlockPos cachedPos = null;

    public FleroviumOreBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityInit.FLEROVIUM_ORE_BLOCK_ENTITY.get(), pos, blockState);
    }

    private void ensureAABBInitialized() {
        if (cachedAABB == null) {
            cachedAABB = new AABB(getBlockPos()).inflate(4);
        }
    }

    private void confirmCachedPosIsBlockPos() {
        if (cachedPos != getBlockPos()) {
            cachedPos = getBlockPos();
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        cachedPos = getBlockPos();
        if (getLevel() != null) {
            ensureAABBInitialized();
        }
    }

    private boolean wearsProtection(Player player) {
        ItemStack chestplateStack = player.getInventory().getArmor(2);
        return chestplateStack.is(ItemInit.FLEROVIUM_CHESTPLATE.get()) || chestplateStack.is(ItemInit.FERROTITANIUM_CHESTPLATE.get());
    }

    public void tick() {
        Level level = getLevel();
        if (level == null || level.isClientSide()) return;

        confirmCachedPosIsBlockPos();

        AABB aabb = cachedAABB != null ? cachedAABB : new AABB(getBlockPos()).inflate(4);
        List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(LivingEntity.class, aabb);
        if (nearbyEntities.isEmpty()) return;

        MobEffectInstance radiation = new MobEffectInstance(MobEffectInit.RADIATION, 60);
        for (LivingEntity entity : nearbyEntities) {
            if (entity instanceof Player player && wearsProtection(player)) {
                if (level.getGameTime() % 50 == 0) {
                    player.displayClientMessage(Component.translatable("message.ascension.radiation_resistance"), true);
                }
                continue;
            }
            if (!entity.hasEffect(MobEffectInit.RADIATION) || entity.getEffect(MobEffectInit.RADIATION).getDuration() < 4) {
                entity.addEffect(radiation);
            }
        }
    }
}
