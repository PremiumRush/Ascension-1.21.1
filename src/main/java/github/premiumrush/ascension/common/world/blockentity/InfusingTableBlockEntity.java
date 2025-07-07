package github.premiumrush.ascension.common.world.blockentity;

import github.premiumrush.ascension.common.world.block.InfusingTableBlock;
import github.premiumrush.ascension.common.world.recipe.infusion.InfusionRecipe;
import github.premiumrush.ascension.common.world.recipe.infusion.InfusionRecipeInput;
import github.premiumrush.ascension.common.init.BlockEntityInit;
import github.premiumrush.ascension.common.init.RecipeInit;
import github.premiumrush.ascension.common.util.AscensionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

import static github.premiumrush.ascension.common.world.block.InfusingTableBlock.HAS_CATALYST;
import static github.premiumrush.ascension.common.world.block.InfusingTableBlock.ORE_INFUSION;

public class InfusingTableBlockEntity extends BlockEntity implements Clearable, WorldlyContainer, AscensionUtil {
    // Define & set custom data
    protected int infusing_ticks, particle_breaks;
    protected boolean render_catalyst;
    public NonNullList<ItemStack> items;

    public InfusingTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityInit.INFUSING_TABLE_BLOCK_ENTITY.get(), pos, blockState);
        this.items = NonNullList.withSize(3, ItemStack.EMPTY);
        this.infusing_ticks = -1;
        this.particle_breaks = -1;
        this.render_catalyst = false;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("infusing_ticks", this.infusing_ticks);
        tag.putInt("particle_breaks", this.particle_breaks);
        tag.putBoolean("render_catalyst", this.render_catalyst);
        ContainerHelper.saveAllItems(tag, this.items, true, registries);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items.clear();
        ContainerHelper.loadAllItems(tag, this.items, registries);
        this.infusing_ticks = tag.getInt("infusing_ticks");
        this.particle_breaks = tag.getInt("particle_breaks");
        this.render_catalyst = tag.getBoolean("render_catalyst");
    }

    // Server - Client Logic
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // Getters & Setters
    public int getInfusingTicks() {
        return this.infusing_ticks;
    }
    public void setInfusingTicks(int ticks) {
        this.infusing_ticks = ticks;
        setChanged();
    }
    public void resetInfusingTicks() {
        this.infusing_ticks = -1;
        setChanged();
    }

    public void setParticleBreaks(int ticks) {
        this.particle_breaks = ticks;
        setChanged();
    }
    public void resetParticleBreaks() {
        this.particle_breaks = -1;
        setChanged();
    }

    public boolean shouldRenderCatalyst() {
        return this.render_catalyst;
    }
    public void setRenderCatalyst(boolean flag) {
        this.render_catalyst = flag;
        setChanged();
    }

    // Base Item Logic (still get and set but more) --> No Sided Capabilities (WordlyContainer interface)
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }
    public ItemStack getBaseItemStack() {
        return this.items.getFirst();
    }
    public ItemStack getCatalystItemStack() {
        return this.items.get(1);
    }
    public void setCatalystItemStack(ItemStack stack) {
        this.items.set(1, stack);
        setChanged();
    }
    public void insertBaseItem(ItemStack stack) {
        this.items.set(0, stack);
        setChanged();
    }
    public void playerInsertBaseItem(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
        this.items.set(0, stack.consumeAndReturn(1, entity));
        setChanged();
        level.playSound(null, pos, SoundEvents.CHISELED_BOOKSHELF_INSERT, SoundSource.BLOCKS, 1, 0);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, state));
    }
    public void insertCatalyst(ItemStack handStack, ItemStack newItemCatalyst, Player player, int infusing_ticks) {
        this.setCatalystItemStack(newItemCatalyst);
        if (player != null) {
            handStack.consume(1, player);
        }
        setInfusingTicks(infusing_ticks);
        if (getBaseItemStack() == ItemStack.EMPTY || getBaseItemStack().is(Items.AIR)) {
            setRenderCatalyst(true);
        }
        setChanged();
    }
    @Override
    public void clearContent() {
        this.items.clear();
        setChanged();
    }

    // Ticking the Block
    public void tick() {
        Level level = getLevel();
        if (level == null) {
            return;
        }

        if (this.infusing_ticks > 0) {
            --this.infusing_ticks;
            Random random = new Random();
            if(random.nextInt(0,20) == 5) {
                level.playLocalSound(this.getBlockPos(), SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 1,0,false);
            }
            setChanged();
        } else if (this.infusing_ticks == 0) {
            List<RecipeHolder<InfusionRecipe>> recipeList = level.getRecipeManager().getRecipesFor(RecipeInit.INFUSION_RECIPE_TYPE.get(), new InfusionRecipeInput(this.getItems().getFirst(), getCatalystItemStack()), level);
            if (!recipeList.isEmpty()) {
                RecipeHolder<InfusionRecipe> recipeholder = recipeList.getFirst();
                ItemStack resultStack = (recipeholder.value()).assemble(new InfusionRecipeInput(this.getItems().getFirst(), getCatalystItemStack()), level.registryAccess());
                if (resultStack.is(Items.MACE)) {
                    addThunderMaceTag("can_summon_lightning", 10, this.items.getFirst());
                    addThunderMaceLore(this.items.getFirst());
                    this.items.getFirst().set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
                    ItemStack mace = this.items.getFirst();
                    this.items.set(2, mace);
                } else {
                    this.items.set(2, resultStack);
                }
                this.items.set(0, ItemStack.EMPTY);
                this.items.set(1, ItemStack.EMPTY);
            }
            if (level.getBlockState(this.getBlockPos()).getValue(HAS_CATALYST)) {
                sendUpdatePacket(this);
                setParticleBreaks(12);
                if (level.isClientSide()) {
                    InfusingTableBlock block = (InfusingTableBlock) level.getBlockState(this.getBlockPos()).getBlock();
                    level.playLocalSound(this.getBlockPos(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 1, false);
                    block.addInfuseParticleSet(level, this.getBlockPos(), this.getBlockState());
                }
            }
            resetInfusingTicks();
            setRenderCatalyst(false);
            level.setBlock(this.getBlockPos(), this.getBlockState().setValue(HAS_CATALYST, false), 1 | 2 | 4 | 8);
            setChanged();
        }

        if (this.particle_breaks > 0) {
            --this.particle_breaks;
        } else if (this.particle_breaks == 0) {
            resetParticleBreaks();
            if (level.isClientSide()) {
                InfusingTableBlock block = (InfusingTableBlock) level.getBlockState(this.getBlockPos()).getBlock();
                block.addSplashParticleSet(level, this.getBlockPos(), this.getBlockState());
            }
        }
    }

    private void addThunderMaceTag(String name, int value, ItemStack stack) {
        CompoundTag compoundTag = getCurrentCustomTag(stack);
        compoundTag.putInt(name, value);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundTag));
    }
    private void addThunderMaceLore(ItemStack stack) {
        ItemLore lore = stack.get(DataComponents.LORE);
        if (lore != null) {
            lore = lore.withLineAdded(Component.literal("Lightning Strikes: "+(stack.get(DataComponents.CUSTOM_DATA).copyTag().getInt("can_summon_lightning"))));
        }
        stack.set(DataComponents.LORE, lore);
    }

    // WordlyContainer Methods --> Sided Capabilities
    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return new int[]{2};
        } else {
            return direction == Direction.UP ? new int[]{0} : new int[]{1};
        }
    }
    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        if (index == 2) {
            return false;
        } else if (index == 0 && this.items.getFirst().is(ItemStack.EMPTY.getItem()) && this.items.get(2).is(ItemStack.EMPTY.getItem())) {
            return true;
        } else {
            List<RecipeHolder<InfusionRecipe>> availableRecipes = level.getRecipeManager().getRecipesFor(RecipeInit.INFUSION_RECIPE_TYPE.get(), new InfusionRecipeInput(this.getItems().getFirst(), itemStack), level);
            if ((this.items.get(1).is(ItemStack.EMPTY.getItem()) && this.items.get(2).is(ItemStack.EMPTY.getItem()) && this.getInfusingTicks() == -1 && !availableRecipes.isEmpty() && availableRecipes.getFirst().value().getOreBoolean() && getBlockState().getValue(ORE_INFUSION)) || (this.items.get(1).is(ItemStack.EMPTY.getItem()) && this.items.get(2).is(ItemStack.EMPTY.getItem()) && this.getInfusingTicks() == -1 && !availableRecipes.isEmpty() && !availableRecipes.getFirst().value().getOreBoolean() && !getBlockState().getValue(ORE_INFUSION))) {
                return true;
            } else {
                return false;
            }
        }
    }
    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack itemStack, Direction direction) {
        return index == 2 && direction == Direction.DOWN && this.particle_breaks < 11;
    }
    @Override
    public int getContainerSize() {
        return this.items.size();
    }
    @Override
    public boolean isEmpty() {
        return this.items.stream().allMatch(ItemStack::isEmpty);
    }
    @Override
    public ItemStack getItem(int i) {
        return this.items.get(i);
    }
    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack itemstack = ContainerHelper.removeItem(this.getItems(), slot, amount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }
        this.sendUpdatePacket(this);
        return itemstack;
    }
    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        this.sendUpdatePacket(this);
        return ContainerHelper.takeItem(this.getItems(), slot);
    }
    @Override
    public void setItem(int index, ItemStack stack) {
        if (index == 1) {
            this.insertCatalyst(stack, stack, null, 200);
            level.setBlock(getBlockPos(), level.getBlockState(getBlockPos()).setValue(HAS_CATALYST, true), 1|2|4|8);
        } else if (index == 0) {
            this.insertBaseItem(stack);
        }
    }
    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }
}
