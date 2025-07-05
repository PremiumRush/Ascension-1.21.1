package github.premiumrush.ascension.common.event.player;

import github.premiumrush.ascension.common.init.ItemInit;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class PlayerInteractEvents {
    @SubscribeEvent
    public void useMaceEvent(PlayerInteractEvent.RightClickItem event) {
        if (!event.getEntity().level().isClientSide()) {
            ItemStack stack = event.getItemStack();
            if (stack.is(Items.MACE) || stack.is(ItemInit.THUNDER_MACE.get())) {
                if (stack.get(DataComponents.CUSTOM_DATA) != null
                        && stack.get(DataComponents.CUSTOM_DATA).contains("can_summon_lightning")
                        && stack.get(DataComponents.CUSTOM_DATA).copyTag().getInt("can_summon_lightning") > 0
                        && event.getEntity() instanceof Player player
                        && !player.getCooldowns().isOnCooldown(stack.getItem()))
                {
                    Level level = player.level();
                    Vec3 eyePosition = player.getEyePosition();
                    Vec3 targetPosition = eyePosition.add(player.getLookAngle().scale(20));
                    LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
                    BlockHitResult clipHitResult = level.clip(new ClipContext(eyePosition, targetPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.of(lightning)));
                    if (level.getBlockState(clipHitResult.getBlockPos()).is(Blocks.AIR)) {
                        lightning.discard();
                        player.displayClientMessage(Component.literal("Too Far!"), true);
                        player.playSound(SoundEvents.FIRE_EXTINGUISH, 0.5f, 0);
                    } else {
                        setCompoundTag("can_summon_lightning", stack.get(DataComponents.CUSTOM_DATA).copyTag().getInt("can_summon_lightning") - 1, stack);
                        if (stack.is(Items.MACE)) {
                            setLoreComponents(stack);
                        }
                        if (stack.get(DataComponents.CUSTOM_DATA).copyTag().getInt("can_summon_lightning") == 0 && !stack.isEnchanted()) {
                            stack.remove(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
                        }
                        player.displayClientMessage(Component.literal("Lightning Strikes Left: " + stack.get(DataComponents.CUSTOM_DATA).copyTag().getInt("can_summon_lightning")), true);
                        player.getCooldowns().addCooldown(stack.getItem(), 140);
                        lightning.moveTo(clipHitResult.getBlockPos().above(), lightning.getYRot(), lightning.getXRot());
                        lightning.addTag("can_upgrade_or_enhance");
                        level.addFreshEntity(lightning);
                    }
                }
            }
        }
    }

    private void setLoreComponents(ItemStack stack) {
        ItemLore lore = stack.get(DataComponents.LORE);
        if (lore != null) {
            if (lore.lines().contains(Component.literal("Lightning Strikes: "+(stack.get(DataComponents.CUSTOM_DATA).copyTag().getInt("can_summon_lightning")+1)))) {
                lore.lines().remove(Component.literal("Lightning Strikes: "+(stack.get(DataComponents.CUSTOM_DATA).copyTag().getInt("can_summon_lightning")+1)));
                lore.lines().add(Component.literal("Lightning Strikes: "+(stack.get(DataComponents.CUSTOM_DATA).copyTag().getInt("can_summon_lightning"))));
            } else {
                lore = lore.withLineAdded(Component.literal("Lightning Strikes: "+(stack.get(DataComponents.CUSTOM_DATA).copyTag().getInt("can_summon_lightning"))));
            }
            if (lore.lines().contains(Component.literal("Lightning Strikes: "+0))) {
                lore.lines().remove(Component.literal("Lightning Strikes: "+0));
            }
        }
        stack.set(DataComponents.LORE, lore);
    }

    private void setCompoundTag(String name, int value, ItemStack stack) {
        CompoundTag compoundTag = new CompoundTag();
        if (stack.get(DataComponents.CUSTOM_DATA) != null) {
            compoundTag = stack.get(DataComponents.CUSTOM_DATA).copyTag();
        }
        compoundTag.putInt(name, value);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundTag));
    }
}
