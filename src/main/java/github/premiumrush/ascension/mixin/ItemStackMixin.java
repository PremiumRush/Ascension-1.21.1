package github.premiumrush.ascension.mixin;

import github.premiumrush.ascension.common.world.item.FerrotitaniumArmorItem;
import github.premiumrush.ascension.common.util.AscensionUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements AscensionUtil {
    @Shadow public abstract boolean isDamageableItem();

    @Inject(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
    public void hurtAndBreakHead(int damage, ServerLevel serverLevel, LivingEntity livingEntity, Consumer<Item> itemConsumer, CallbackInfo ci) {
        if (this.isDamageableItem() && livingEntity instanceof ServerPlayer serverPlayer && hasArmorSet(serverPlayer, FerrotitaniumArmorItem.class)) {
            if (serverPlayer.getRandom().nextBoolean()) {
                ci.cancel();
            }
        }
    }
}
