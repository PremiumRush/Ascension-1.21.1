package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.common.init.EntityInit;
import github.premiumrush.ascension.common.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import oshi.util.tuples.Pair;

import java.util.List;

public class FerrotitaniumGolemEntity extends AbstractGolem {
    private int attackAnimationTick;

    public FerrotitaniumGolemEntity(EntityType<? extends AbstractGolem> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.6F));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        List<Pair<String, EntityType<?>>> tagToEntityList = List.of(
                new Pair<>("armadillo", EntityType.ARMADILLO),
                new Pair<>("bee", EntityType.BEE),
                new Pair<>("breeze", EntityType.BREEZE),
                new Pair<>("blaze", EntityType.BLAZE),
                new Pair<>("chicken", EntityType.CHICKEN),
                new Pair<>("creeper", EntityType.CREEPER),
                new Pair<>("cod", EntityType.COD),
                new Pair<>("cow", EntityType.COW),
                new Pair<>("ender_dragon", EntityType.ENDER_DRAGON),
                new Pair<>("enderman", EntityType.ENDERMAN),
                new Pair<>("endermite", EntityType.ENDERMITE),
                new Pair<>("fox", EntityType.FOX),
                new Pair<>("frog", EntityType.FROG),
                new Pair<>("ghast", EntityType.GHAST),
                new Pair<>("goat", EntityType.GOAT),
                new Pair<>("horse", EntityType.HORSE),
                new Pair<>("iron_golem", EntityType.IRON_GOLEM),
                new Pair<>("magma_slime", EntityType.MAGMA_CUBE),
                new Pair<>("mushroom_cow", EntityType.MOOSHROOM),
                new Pair<>("phantom", EntityType.PHANTOM),
                new Pair<>("pig", EntityType.PIG),
                new Pair<>("piglin", EntityType.PIGLIN),
                new Pair<>("polar_bear", EntityType.POLAR_BEAR),
                new Pair<>("pufferfish", EntityType.PUFFERFISH),
                new Pair<>("rabbit", EntityType.RABBIT),
                new Pair<>("salmon", EntityType.SALMON),
                new Pair<>("sheep", EntityType.SHEEP),
                new Pair<>("shulker", EntityType.SHULKER),
                new Pair<>("silverfish", EntityType.SILVERFISH),
                new Pair<>("slime", EntityType.SLIME),
                new Pair<>("snow_golem", EntityType.SNOW_GOLEM),
                new Pair<>("tropical_fish", EntityType.TROPICAL_FISH),
                new Pair<>("turtle", EntityType.TURTLE),
                new Pair<>("vex", EntityType.VEX),
                new Pair<>("warden", EntityType.WARDEN),
                new Pair<>("witch", EntityType.WITCH),
                new Pair<>("wither", EntityType.WITHER),
                new Pair<>("wolf", EntityType.WOLF),
                new Pair<>("zombified_piglin", EntityType.ZOMBIFIED_PIGLIN),
                new Pair<>("ice_blaze", EntityInit.ICE_BLAZE.get()),
                new Pair<>("ember_golem", EntityInit.EMBER_GOLEM.get())
        );
        for (Pair<String, EntityType<?>> listEntry : tagToEntityList) {
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false,
                    (potentialTarget) -> this.getTags().contains(listEntry.getA()) && potentialTarget.getType() == listEntry.getB()));
        }
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false,
                (potentialTarget) -> this.getTags().contains("cats") && potentialTarget instanceof Cat | potentialTarget instanceof Ocelot));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false,
                (potentialTarget) -> this.getTags().contains("spiders") && potentialTarget instanceof Spider | potentialTarget instanceof CaveSpider));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false,
                (potentialTarget) -> this.getTags().contains("guardians") && potentialTarget instanceof Guardian | potentialTarget instanceof ElderGuardian));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false,
                (potentialTarget) -> this.getTags().contains("hoglins") && potentialTarget instanceof Hoglin | potentialTarget instanceof Zoglin));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false,
                (potentialTarget) -> this.getTags().contains("illager") && potentialTarget instanceof Raider));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false,
                (potentialTarget) -> this.getTags().contains("skeletons") && potentialTarget instanceof Skeleton | potentialTarget instanceof WitherSkeleton | potentialTarget instanceof Bogged));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false,
                (potentialTarget) -> this.getTags().contains("squids") && potentialTarget instanceof Squid | potentialTarget instanceof GlowSquid));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false,
                (potentialTarget) -> this.getTags().contains("villagers") && potentialTarget instanceof AbstractVillager));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false,
                (potentialTarget) -> this.getTags().contains("zombies") && potentialTarget instanceof Zombie | potentialTarget instanceof Drowned | potentialTarget instanceof Giant | potentialTarget instanceof Husk | potentialTarget instanceof ZombieVillager));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractGolem.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 100)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.KNOCKBACK_RESISTANCE,1)
                .add(Attributes.ATTACK_DAMAGE, 15)
                .add(Attributes.STEP_HEIGHT, 1)
                .add(Attributes.FOLLOW_RANGE,32);
    }

    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(ItemInit.FERROTITANIUM_INGOT.get())) {
            float health = this.getHealth();
            this.heal(25.0F);
            if (this.getHealth() == health) {
                return InteractionResult.PASS;
            } else {
                float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, f1);
                itemstack.consume(1, player);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
        List<Pair<Item, String>> itemToTagList = List.of(
                new Pair<>(Items.ARMADILLO_SCUTE, "armadillo"),
                new Pair<>(Items.HONEYCOMB, "bee"),
                new Pair<>(Items.BREEZE_ROD, "breeze"),
                new Pair<>(Items.BLAZE_ROD, "blaze"),
                new Pair<>(Items.STRING, "cats"),
                new Pair<>(Items.SPIDER_EYE, "spiders"),
                new Pair<>(Items.CHICKEN, "chicken"),
                new Pair<>(Items.GUNPOWDER, "creeper"),
                new Pair<>(Items.COD, "cod"),
                new Pair<>(Items.BEEF, "cow"),
                new Pair<>(Items.DRAGON_BREATH, "ender_dragon"),
                new Pair<>(Items.ENDER_PEARL, "enderman"),
                new Pair<>(Items.ENDER_EYE, "endermite"),
                new Pair<>(Items.SWEET_BERRIES, "fox"),
                new Pair<>(Items.FROGSPAWN, "frog"),
                new Pair<>(Items.GHAST_TEAR, "ghast"),
                new Pair<>(Items.GOAT_HORN, "goat"),
                new Pair<>(Items.PRISMARINE_SHARD, "guardians"),
                new Pair<>(Items.COOKED_PORKCHOP, "hoglins"),
                new Pair<>(Items.LEATHER, "horse"),
                new Pair<>(Items.OMINOUS_BOTTLE, "illager"),
                new Pair<>(Items.IRON_INGOT, "iron_golem"),
                new Pair<>(Items.MAGMA_CREAM, "magma_slime"),
                new Pair<>(Items.MUSHROOM_STEW, "mushroom_cow"),
                new Pair<>(Items.PHANTOM_MEMBRANE, "phantom"),
                new Pair<>(Items.PORKCHOP, "pig"),
                new Pair<>(Items.PIGLIN_BANNER_PATTERN, "piglin"),
                new Pair<>(Items.SNOWBALL, "polar_bear"),
                new Pair<>(Items.PUFFERFISH, "pufferfish"),
                new Pair<>(Items.RABBIT, "rabbit"),
                new Pair<>(Items.SALMON, "salmon"),
                new Pair<>(Items.MUTTON, "sheep"),
                new Pair<>(Items.SHULKER_SHELL, "shulker"),
                new Pair<>(Items.STONE, "silverfish"),
                new Pair<>(Items.SLIME_BALL, "slime"),
                new Pair<>(Items.BONE, "skeletons"),
                new Pair<>(Items.SNOW_BLOCK, "snow_golem"),
                new Pair<>(Items.INK_SAC, "squids"),
                new Pair<>(Items.TROPICAL_FISH, "tropical_fish"),
                new Pair<>(Items.TURTLE_SCUTE, "turtle"),
                new Pair<>(Items.IRON_SWORD, "vex"),
                new Pair<>(Items.EMERALD, "villagers"),
                new Pair<>(Items.SCULK, "warden"),
                new Pair<>(Items.POTION, "witch"),
                new Pair<>(Items.WITHER_SKELETON_SKULL, "wither"),
                new Pair<>(Items.WOLF_ARMOR, "wolf"),
                new Pair<>(Items.ROTTEN_FLESH, "zombies"),
                new Pair<>(Items.GOLD_NUGGET, "zombified_piglin"),
                new Pair<>(ItemInit.COLD_BLAZE_ROD.get(), "ice_blaze"),
                new Pair<>(ItemInit.GOLEM_GYRO.get(), "ember_golem")
        );
        for (Pair<Item, String> listEntry : itemToTagList) {
            Item item = listEntry.getA();
            String tag = listEntry.getB();
            if (itemstack.is(item) && !this.getTags().contains(tag)) {
                this.addTag(tag);
                this.playSound(SoundEvents.ITEM_FRAME_ADD_ITEM);
                if (!itemstack.is(Items.WOLF_ARMOR) && !itemstack.is(Items.GOAT_HORN)) {
                    itemstack.consume(1, player);
                }
            }
        }

        if (itemstack.is(Items.AIR) && player.isCrouching()) {
            resetTags(this);
            this.playSound(SoundEvents.ANVIL_USE);
            this.setTarget(null);
        }

        return InteractionResult.PASS;
    }

    private void resetTags(LivingEntity entity) {
        List<String> tagList = List.of("armadillo", "bee", "breeze", "blaze", "cats", "spiders", "chicken", "creeper", "cod", "cow", "ender_dragon", "enderman", "endermite", "fox", "frog", "ghast", "goat", "guardians", "hoglins", "horse", "illager", "iron_golem", "magma_slime", "mushroom_cow", "phantom", "pig", "piglin", "polar_bear", "pufferfish", "rabbit", "salmon", "sheep", "shulker", "silverfish", "slime", "skeletons", "snow_golem", "squids", "tropical_fish", "turtle", "vex", "villagers", "warden", "witch", "wither", "wolf", "zombies", "zombified_piglin", "ice_blaze", "ember_golem");
        for (String listEntry : tagList) {
            entity.getTags().remove(listEntry);
        }
    }

    protected void doPush(Entity entity) {
        if (entity instanceof Animal && this.getRandom().nextInt(20) == 0) {
            this.setTarget((LivingEntity)entity);
        }
        super.doPush(entity);
    }

    public void aiStep() {
        super.aiStep();
        if (this.attackAnimationTick > 0) {
            --this.attackAnimationTick;
        }
    }

    public int getAttackAnimationTick() {
        return this.attackAnimationTick;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    public boolean canSpawnSprintParticle() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 2.500000277905201E-7 && this.random.nextInt(5) == 0;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    public boolean doHurtTarget(Entity entity) {
        this.attackAnimationTick = 10;
        this.level().broadcastEntityEvent(this, (byte)4);
        float attackDamage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float randomDamage = (int)attackDamage > 0 ? attackDamage / 2.0F + (float)this.random.nextInt((int)attackDamage) : attackDamage;
        DamageSource damagesource = this.damageSources().mobAttack(this);
        boolean flag = entity.hurt(damagesource, randomDamage);
        if (flag) {
            double var10000;
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                var10000 = livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            } else {
                var10000 = 0.0;
            }

            double d0 = var10000;
            double d1 = Math.max(0.0, 1.0 - d0);
            entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, 0.4000000059604645 * d1, 0.0));
            Level var11 = this.level();
            if (var11 instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel)var11;
                EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
            }
        }

        this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    public boolean hurt(DamageSource source, float amount) {
        if(source.is(DamageTypes.FALL)) {
            return false;
        } else {
            Crackiness.Level crackinessLevel = this.getCrackiness();
            boolean superHurt = super.hurt(source, amount);
            if (superHurt && this.getCrackiness() != crackinessLevel) {
                this.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
            }
            return superHurt;
        }
    }

    public Crackiness.Level getCrackiness() {
        return Crackiness.GOLEM.byFraction(this.getHealth() / this.getMaxHealth());
    }

    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.attackAnimationTick = 10;
            this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        } else {
            super.handleEntityEvent(id);
        }
    }

    protected int decreaseAirSupply(int air) {
        return air;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }
    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 1.0F, 1.0F);
    }
}
