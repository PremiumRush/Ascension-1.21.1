package github.premiumrush.ascension.mixin;

import github.premiumrush.ascension.common.world.item.IceArmorItem;
import github.premiumrush.ascension.common.util.AscensionUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin implements AscensionUtil {
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player)(Object)this;
        if (source.is(DamageTypes.FREEZE) && hasArmorSet(player, IceArmorItem.class)) {
            cir.setReturnValue(false);
        }
    }
}
