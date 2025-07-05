package github.premiumrush.ascension.mixin;

import github.premiumrush.ascension.common.world.blockentity.InfusingTableBlockEntity;
import github.premiumrush.ascension.common.util.AscensionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(Player.class)
public abstract class ClientPlayerMixin implements AscensionUtil {
    // Add Hover Messages to Infusing Table
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (((Player) (Object) this).level().isClientSide()) {
            Player player = (Player) (Object) this;
            if (player.tickCount % 5 == 0) {
                HitResult hitResult = Minecraft.getInstance().hitResult;
                if (hitResult.getType().equals(HitResult.Type.BLOCK)) {
                    BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                    if (player.level().getBlockEntity(blockHitResult.getBlockPos()) instanceof InfusingTableBlockEntity blockEntity && blockEntity.getBaseItemStack().is(Items.AIR)) {
                        displayMessageForInfusionRecipe(player.level(), blockEntity, player, null, null);
                    }
                }
            }
        }
    }
}
