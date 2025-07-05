package github.premiumrush.ascension.common.init;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.entity.custom.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.minecraft.world.entity.SpawnPlacementTypes.ON_GROUND;

@EventBusSubscriber(modid = Ascension.MODID, bus = EventBusSubscriber.Bus.MOD)
public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Ascension.MODID);

    private static String modId(String type) {
        return Ascension.MODID + "." + type;
    }

    public static final Supplier<EntityType<EmberGolemEntity>> EMBER_GOLEM =
            ENTITY_TYPES.register("ember_golem", () -> EntityType.Builder.of(EmberGolemEntity::new, MobCategory.MONSTER)
                    .sized(1.25F, 2.7F)
                    .clientTrackingRange(10)
                    .build(modId("ember_golem"))
            );

    public static final Supplier<EntityType<IceBlazeEntity>> ICE_BLAZE =
            ENTITY_TYPES.register("ice_blaze", () -> EntityType.Builder.of(IceBlazeEntity::new, MobCategory.MONSTER)
                    .sized(0.6F,1.8F)
                    .clientTrackingRange(8)
                    .build(modId("ice_blaze"))
            );
    public static final Supplier<EntityType<SmallSnowBall>> SMALL_SNOWBALL =
            ENTITY_TYPES.register("small_snowball", () -> EntityType.Builder.of(SmallSnowBall::new, MobCategory.MISC)
                    .sized(0.3125F, 0.3125F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(modId("small_snowball"))
            );
    public static final Supplier<EntityType<VexalArrow>> VEXAL_ARROW =
            ENTITY_TYPES.register("vexal_arrow", () -> EntityType.Builder.<VexalArrow>of(VexalArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(modId("vexal_arrow"))
            );
    public static final Supplier<EntityType<InfusedVexalArrow>> INFUSED_VEXAL_ARROW =
            ENTITY_TYPES.register("infused_vexal_arrow", () -> EntityType.Builder.<InfusedVexalArrow>of(InfusedVexalArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(modId("infused_vexal_arrow"))
            );
    public static final Supplier<EntityType<TitaniumGolemEntity>> TITANIUM_GOLEM =
            ENTITY_TYPES.register("titanium_golem", () -> EntityType.Builder.of(TitaniumGolemEntity::new, MobCategory.MISC)
                    .sized(1.4F, 2.7F)
                    .clientTrackingRange(10)
                    .build(modId("titanium_golem"))
            );
    public static final Supplier<EntityType<FerrotitaniumGolemEntity>> FERROTITANIUM_GOLEM =
            ENTITY_TYPES.register("ferrotitanium_golem", () -> EntityType.Builder.of(FerrotitaniumGolemEntity::new, MobCategory.MISC)
                    .sized(1.4F, 2.7F)
                    .clientTrackingRange(10)
                    .build(modId("ferrotitanium_golem"))
            );
    public static final Supplier<EntityType<InertiaSparkEntity>> SPARK_OF_INERTIA =
            ENTITY_TYPES.register("spark_of_inertia", () -> EntityType.Builder.of(InertiaSparkEntity::new, MobCategory.MISC)
                    .sized(0.3f,0.3f)
                    .build(modId("spark_of_inertia"))
            );
    public static final Supplier<EntityType<InfusionBlastSparkEntity>> INFUSION_BLAST_SPARK =
            ENTITY_TYPES.register("infusion_blast_spark", () -> EntityType.Builder.<InfusionBlastSparkEntity>of(InfusionBlastSparkEntity::new, MobCategory.MISC)
                    .sized(0.3f,0.3f)
                    .build(modId("infusion_blast_spark"))
            );
    public static final Supplier<EntityType<InertiaProjectile>> INERTIA_PROJECTILE =
            ENTITY_TYPES.register("inertia_projectile", () -> EntityType.Builder.of(InertiaProjectile::new, MobCategory.MISC)
                    .sized(0.3125F, 0.3125F)
                    .build(modId("inertia_projectile"))
            );
    public static final Supplier<EntityType<SharkEntity>> SHARK =
            ENTITY_TYPES.register("shark", () -> EntityType.Builder.of(SharkEntity::new, MobCategory.MONSTER)
                    .sized(1.2f, 0.75f)
                    .eyeHeight(0.35f)
                    .clientTrackingRange(8)
                    .build(modId("shark"))
            );
    public static final Supplier<EntityType<FlockSharkEntity>> FLOCK_SHARK =
            ENTITY_TYPES.register("flock_shark", () -> EntityType.Builder.of(FlockSharkEntity::new, MobCategory.MONSTER)
                    .sized(1.0f, 0.63f)
                    .eyeHeight(0.315f)
                    .clientTrackingRange(8)
                    .build(modId("flock_shark"))
            );
    public static final Supplier<EntityType<GhostEntity>> GHOST =
            ENTITY_TYPES.register("ghost", () -> EntityType.Builder.of(GhostEntity::new, MobCategory.MONSTER)
                    .sized(0.5f,1.6f)
                    .eyeHeight(1.3f)
                    .clientTrackingRange(10)
                    .build(modId("ghost"))
            );
    public static final Supplier<EntityType<TamedGhostEntity>> TAMED_GHOST =
            ENTITY_TYPES.register("tamed_ghost", () -> EntityType.Builder.of(TamedGhostEntity::new, MobCategory.MONSTER)
                    .sized(0.5f,1.6f)
                    .eyeHeight(1.3f)
                    .clientTrackingRange(10)
                    .build(modId("tamed_ghost"))
            );
    public static final Supplier<EntityType<BansheeEntity>> BANSHEE =
            ENTITY_TYPES.register("banshee", () -> EntityType.Builder.of(BansheeEntity::new, MobCategory.MONSTER)
                    .sized(0.5f,1.6f)
                    .eyeHeight(1.3f)
                    .clientTrackingRange(10)
                    .build(modId("banshee"))
            );

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(EMBER_GOLEM.get(), ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EmberGolemEntity::spawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(ICE_BLAZE.get(), ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(TITANIUM_GOLEM.get(), ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IronGolem::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(FERROTITANIUM_GOLEM.get(), ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IronGolem::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(GHOST.get(), ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractGhostEntity::spawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
    }
}
