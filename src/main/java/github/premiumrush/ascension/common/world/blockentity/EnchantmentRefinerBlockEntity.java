package github.premiumrush.ascension.common.world.blockentity;

import github.premiumrush.ascension.common.world.block.EnchantmentRefinerBlock;
import github.premiumrush.ascension.common.init.BlockEntityInit;
import github.premiumrush.ascension.common.init.ItemInit;
import github.premiumrush.ascension.common.util.AscensionData;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.Random;

import static github.premiumrush.ascension.common.world.block.EnchantmentRefinerBlock.ACTIVE;

public class EnchantmentRefinerBlockEntity extends BlockEntity implements Clearable {
    public static final List<Pair<ResourceKey<Enchantment>, Item>> ENCHANTMENT_TO_ITEMSTACK_MAP = List.of(
            new Pair<>(Enchantments.SHARPNESS, ItemInit.SHARK_TOOTH.get()),
            new Pair<>(Enchantments.KNOCKBACK, ItemInit.GOLEM_GYRO.get())
    );
    public NonNullList<ItemStack> items;
    public int refiningTicks;
    public int fuel;

    public EnchantmentRefinerBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityInit.ENCHANTMENT_REFINER_BLOCK_ENTITY.get(), pos, blockState);
        this.items = NonNullList.withSize(2, ItemStack.EMPTY);
        this.refiningTicks = -1;
        this.fuel = 0;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        ContainerHelper.saveAllItems(nbt, this.items, true, registries);
        nbt.putInt("refining_ticks", this.refiningTicks);
        nbt.putInt("fuel", this.fuel);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        this.items.clear();
        ContainerHelper.loadAllItems(nbt, this.items, registries);
        this.refiningTicks = nbt.getInt("refining_ticks");
        this.fuel = nbt.getInt("fuel");
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public void clearContent() {
        this.items.clear();
        this.setChanged();
    }

    public void insertItem(LivingEntity entity, ItemStack stack, int index) {
        entity.level().playLocalSound(entity.blockPosition(), SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1, 0, false);
        this.items.set(index, stack.consumeAndReturn(1, entity));
        this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
        this.setChanged();
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }
    public ItemStack getItemStackAt(int index) {
        return this.items.get(index);
    }

    public int getFuel() {
        return this.fuel;
    }
    public boolean addFuel(int addedFuel, ItemStack interactStack, LivingEntity entity) {
        if ((this.fuel + addedFuel) <= 64) {
            interactStack.consume(1, entity);
            this.fuel += addedFuel;
            this.setChanged();
            this.checkBlockFuel();
            return true;
        } else {
            return false;
        }
    }

    public void checkBlockFuel() {
        EnchantmentRefinerBlock block =  (EnchantmentRefinerBlock) getLevel().getBlockState(getBlockPos()).getBlock();
        block.checkFuelStage(getLevel().getBlockState(getBlockPos()), getLevel(), getBlockPos());
    }

    public int getRefiningTicks() {
        return this.refiningTicks;
    }
    public void setRefiningTicks(int ticks) {
        this.refiningTicks = ticks;
        this.setChanged();
    }
    public void resetRefiningTicks() {
        this.refiningTicks = -1;
        this.setChanged();
    }

    public void tick() {
        if (this.level == null) {
            return;
        }

        if (getItemStackAt(0) != ItemStack.EMPTY && getItemStackAt(1) != ItemStack.EMPTY && getFuel() > 0) {
            ItemStack mainStack = getItemStackAt(0);
            ItemStack secondaryStack = getItemStackAt(1);
            if (mainStack.is(Items.ENCHANTED_BOOK)) {
                ItemEnchantments bookEnchantments = mainStack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
                List<Object2IntMap.Entry<Holder<Enchantment>>> enchHolderEntryList = bookEnchantments.entrySet().stream().toList();
                enchantmentRefiningOperation(enchHolderEntryList, mainStack, secondaryStack, true);
            }
            if (!mainStack.is(Items.ENCHANTED_BOOK)) {
                ItemEnchantments itemEnchantments = mainStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
                List<Object2IntMap.Entry<Holder<Enchantment>>> enchHolderEntryList = itemEnchantments.entrySet().stream().toList();
                enchantmentRefiningOperation(enchHolderEntryList, mainStack, secondaryStack, false);
            }
        }
    }

    private void enchantmentRefiningOperation(List<Object2IntMap.Entry<Holder<Enchantment>>> enchHolderEntryList, ItemStack mainStack, ItemStack secondaryStack, boolean isBook) {
        for (Object2IntMap.Entry<Holder<Enchantment>> enchHolderEntry : enchHolderEntryList) {
            if (this.refiningTicks == -1) {
                for (Pair<ResourceKey<Enchantment>, Item> listEntry : AscensionData.ENCHANTMENT_TO_ITEMSTACK_LIST) {
                    if (enchHolderEntry.getKey().is(listEntry.getA()) && secondaryStack.is(listEntry.getB())) {
                        int enchLevel;
                        if (isBook) {
                            enchLevel = enchHolderEntry.getIntValue();
                        } else {
                            enchLevel = mainStack.getEnchantmentLevel(enchHolderEntry.getKey());
                        }
                        if (enchLevel == enchHolderEntry.getKey().value().getMaxLevel()) {
                            this.getLevel().setBlock(this.getBlockPos(), this.getBlockState().setValue(ACTIVE, true), 1 | 2 | 4 | 8);
                            setRefiningTicks(200 * enchHolderEntryList.size());
                        }
                    }
                }
            }
            if (this.refiningTicks > 0) {
                this.refiningTicks--;
            } else if (this.refiningTicks == 0) {
                mainStack.enchant(enchHolderEntry.getKey(), enchHolderEntry.getKey().value().getMaxLevel() + 1);
                mainStack.set(DataComponents.RARITY, Rarity.EPIC);
                addCompletionParticleSet(getLevel(), getBlockPos());
                getLevel().playLocalSound(getBlockPos(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1,1,false);
                this.items.set(1, ItemStack.EMPTY);
                this.fuel -= 1;
                this.checkBlockFuel();
                this.getLevel().setBlock(this.getBlockPos(), this.getBlockState().setValue(ACTIVE, false), 1 | 2 | 4 | 8);
                resetRefiningTicks();
            }
        }
    }

    private void addCompletionParticleSet(Level level, BlockPos pos) {
        ParticleOptions particle = ParticleTypes.ENCHANTED_HIT;
        Random random = new Random();
        for (int i=0; i<10; i++) {
            double offset = random.nextDouble(-0.3,0.3);
            level.addParticle(particle, true, pos.getX() + 0.5 + offset, pos.getY() + 1.2, pos.getZ() + 0.5 + offset, 0, 0, 0);
        }
    }
}
