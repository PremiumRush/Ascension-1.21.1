package github.premiumrush.ascension.common.util;

import github.premiumrush.ascension.common.world.blockentity.InfusingTableBlockEntity;
import github.premiumrush.ascension.common.world.blockentity.item_pedestals.variants.BasePedestalBlockEntity;
import github.premiumrush.ascension.common.world.recipe.InfusionRecipe;
import github.premiumrush.ascension.common.init.RecipeInit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static github.premiumrush.ascension.common.world.block.InfusingTableBlock.ORE_INFUSION;

public interface AscensionUtil {
    // Client
    default void sendClientSideMessage(Level level, Player player, String message) {
        if (level.isClientSide()) {
            player.sendSystemMessage(Component.literal(message));
        }
    }
    default void sendClientSideSoundPacket(Level level, BlockPos pos, Player player, SoundEvent soundEvent, int volume, int pitch) {
        if (level.isClientSide()) {
            level.playSound(player, pos, soundEvent, SoundSource.BLOCKS, volume, pitch);
        }
    }

    // Packets
    default void sendUpdatePacket(BlockEntity blockEntity) {
        if (blockEntity.getLevel() != null) {
            List<? extends Player> playerList = blockEntity.getLevel().players();
            for (Player player : playerList) {
                if (player instanceof ServerPlayer serverPlayer && blockEntity.getUpdatePacket() != null) {
                    serverPlayer.connection.send(blockEntity.getUpdatePacket());
                }
            }
        }
    }

    // Blocks
    default void displayMessageForInfusionRecipe(Level level, InfusingTableBlockEntity blockEntity, Player player, @Nullable BlockState state, @Nullable List<RecipeHolder<InfusionRecipe>> availableRecipes) {
        List<RecipeHolder<InfusionRecipe>> allInfusionRecipes = level.getRecipeManager().getAllRecipesFor(RecipeInit.INFUSION_RECIPE_TYPE.get());
        for (RecipeHolder<InfusionRecipe> recipe : allInfusionRecipes) {
            if (availableRecipes != null && state != null) {
                if (availableRecipes.isEmpty() && recipe.value().getBaseItem().test(blockEntity.getBaseItemStack()) && !blockEntity.getBaseItemStack().is(Items.AIR)) {
                    if (recipe.value().getOreBoolean() == state.getValue(ORE_INFUSION) && level.isClientSide()) {
                        player.displayClientMessage(Component.literal("Required Catalyst: " + Arrays.stream(recipe.value().getCatalystItem().getItems()).toList().getFirst().getDisplayName().getString()).withStyle(ChatFormatting.YELLOW), true);
                    }
                } return;
            } else {
                if (recipe.value().getBaseItem().test(player.getItemInHand(InteractionHand.MAIN_HAND)) && !player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.AIR) || recipe.value().getBaseItem().isEmpty() && recipe.value().getCatalystItem().test(player.getItemInHand(InteractionHand.MAIN_HAND))) {
                    if (recipe.value().getOreBoolean() == blockEntity.getBlockState().getValue(ORE_INFUSION)) {
                        player.displayClientMessage(Component.literal("Recipe Available").withStyle(ChatFormatting.DARK_GREEN), true);
                    } else {
                        player.displayClientMessage(Component.literal("No Recipe Available").withStyle(ChatFormatting.DARK_RED), true);
                    }
                }
            }
        }
    }

    // Maybe useful
    default <T extends BlockEntity> T getBlockEntity(Level level, BlockPos pos, Class<T> clazz) {
        BlockEntity be = level.getBlockEntity(pos);
        if (clazz.isInstance(be)) {
            return clazz.cast(be);
        } else {
            throw new ClassCastException("Expected BlockEntity of type " + clazz.getSimpleName() + " at " + pos + ", but found " + (be == null ? "null" : be.getClass().getSimpleName()));
        }
    }

    // Items
    default boolean hasArmorSet(Player player, Class<?> clazz) {
        return player.getInventory().getArmor(0).getItem().getClass() == clazz
                && player.getInventory().getArmor(1).getItem().getClass() == clazz
                && player.getInventory().getArmor(2).getItem().getClass() == clazz
                && player.getInventory().getArmor(3).getItem().getClass() == clazz;
    }
    default CompoundTag getCurrentCustomTag(ItemStack stack) {
        return stack.get(DataComponents.CUSTOM_DATA) != null ? stack.get(DataComponents.CUSTOM_DATA).copyTag() : new CompoundTag();
    }

    // Pedestals
    default void setRenderTypeSwitch(Level level, Player player, BlockEntity blockEntity) {
        if (blockEntity instanceof BasePedestalBlockEntity pedestalBlockEntity) {
            switch (pedestalBlockEntity.getRenderType()) {
                case 1 -> {
                    sendClientSideMessage(level, player, "2xDefault");
                    pedestalBlockEntity.setRenderType(2);
                }
                case 2 -> {
                    sendClientSideMessage(level, player, "Inverted");
                    pedestalBlockEntity.setRenderType(3);
                }
                case 3 -> {
                    sendClientSideMessage(level, player, "2x Inverted");
                    pedestalBlockEntity.setRenderType(4);
                }
                case 4 -> {
                    sendClientSideMessage(level, player, "Static Default");
                    pedestalBlockEntity.setRenderType(5);
                }
                case 5 -> {
                    sendClientSideMessage(level, player, "Static Straight");
                    pedestalBlockEntity.setRenderType(6);
                }
                case 6 -> {
                    sendClientSideMessage(level, player, "Static Straight Inverted");
                    pedestalBlockEntity.setRenderType(7);
                }
                case 7 -> {
                    sendClientSideMessage(level, player, "Static Flat");
                    pedestalBlockEntity.setRenderType(0);
                }
                case 0 -> {
                    sendClientSideMessage(level, player, "Default");
                    pedestalBlockEntity.setRenderType(1);
                }
            }
        }
    }
}
