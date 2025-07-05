package github.premiumrush.ascension.common.event.block;

import github.premiumrush.ascension.common.world.block.IrradiatedFarmlandBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.minecraft.world.level.block.CropBlock.AGE;

public class BlockDropsEvents {
    private static List<Item> cachedCropList = null;
    private static final Map<Item, Block> ITEM_TO_BLOCK = new HashMap<>();
    static {
        Item.BY_BLOCK.forEach((block, item) -> ITEM_TO_BLOCK.put(item, block));
    }
    private static List<Item> getCachedCropListOrCreate(Level level) {
        if (cachedCropList == null) {
            cachedCropList = getItemsFromItemTag(level, TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "crops")));
        }
        return cachedCropList;
    }

    @SubscribeEvent
    public void handleCropDropsEvent(BlockDropsEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        RandomSource random = level.getRandom();
        if (level.getBlockState(pos.below()).getBlock() instanceof IrradiatedFarmlandBlock && state.getBlock() instanceof CropBlock && state.getValue(AGE) == ((CropBlock) state.getBlock()).getMaxAge()) {
            if (random.nextBoolean()) {
                List<Item> cropList = getCachedCropListOrCreate(level);
                if (!cropList.isEmpty()) {
                    event.getDrops().clear();
                    event.setCanceled(true);
                    Vec3 spawnPos = Vec3.atCenterOf(pos);
                    Item item = cropList.get(random.nextInt(cropList.size()));
                    ItemStack seedStack = getSeedStack(level, pos, state, item);
                    if (!seedStack.isEmpty()) {
                        ItemEntity seedItemEntity = new ItemEntity(level, spawnPos.x(), spawnPos.y(), spawnPos.z(), seedStack);
                        level.addFreshEntity(seedItemEntity);
                    }
                    ItemStack newStack = new ItemStack(item, random.nextInt(1,3));
                    ItemEntity cropItemEntity = new ItemEntity(level, spawnPos.x(), spawnPos.y(), spawnPos.z(), newStack);
                    level.addFreshEntity(cropItemEntity);
                }
            }
        }
    }

    private static List<Item> getItemsFromItemTag(Level level, TagKey<Item> itemTag) {
        return level.registryAccess()
                .registryOrThrow(Registries.ITEM)
                .getTag(itemTag)
                .map(tag -> tag.stream()
                        .map(Holder::value)
                        .filter(item -> item != Items.AIR)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    private static ItemStack getSeedStack(Level level, BlockPos pos, BlockState cropState, Item item) {
        Block block = ITEM_TO_BLOCK.get(item);
        if (block instanceof CropBlock crop) {
            return crop.getCloneItemStack(level, pos, cropState);
        }
        if (block instanceof StemBlock stem) {
            return stem.getCloneItemStack(level, pos, cropState);
        }
        // Special cases: pumpkins, melons
        if (item == Items.PUMPKIN) {
            BlockState stemState = Blocks.PUMPKIN_STEM.defaultBlockState();
            if (stemState.getBlock() instanceof StemBlock stem) {
                return stem.getCloneItemStack(level, pos, stemState);
            }
        }
        if (item == Items.MELON) {
            BlockState stemState = Blocks.MELON_STEM.defaultBlockState();
            if (stemState.getBlock() instanceof StemBlock stem) {
                return stem.getCloneItemStack(level, pos, stemState);
            }
        }
        // Fallback
        return ItemStack.EMPTY;
    }
}
