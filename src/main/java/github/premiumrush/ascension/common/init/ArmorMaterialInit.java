package github.premiumrush.ascension.common.init;

import github.premiumrush.ascension.Ascension;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ArmorMaterialInit {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, Ascension.MODID);

    public static final Holder<ArmorMaterial> TITANIUM = ARMOR_MATERIALS.register("titanium", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 5);
                map.put(ArmorItem.Type.CHESTPLATE, 5);
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.BODY, 5);
            }),
            10,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(ItemInit.TITANIUM_INGOT::get),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "titanium"))),
            0f,
            0f
    ));
    public static final Holder<ArmorMaterial> FERROTITANIUM = ARMOR_MATERIALS.register("ferrotitanium", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.BODY, 6);
            }),
            8,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> Ingredient.of(ItemInit.FERROTITANIUM_INGOT::get),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "ferrotitanium"))),
            1.5f,
            0f
    ));
    public static final Holder<ArmorMaterial> FLEROVIUM = ARMOR_MATERIALS.register("flerovium", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 5);
                map.put(ArmorItem.Type.LEGGINGS, 8);
                map.put(ArmorItem.Type.CHESTPLATE, 10);
                map.put(ArmorItem.Type.HELMET, 5);
                map.put(ArmorItem.Type.BODY, 8);
            }),
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> Ingredient.of(ItemInit.FLEROVIUM_CRYSTAL::get),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "flerovium"))),
            3.5f,
            0.1f
    ));
    public static final Holder<ArmorMaterial> ICE = ARMOR_MATERIALS.register("ice", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.BODY, 5);
            }),
            25,
            SoundEvents.ARMOR_EQUIP_CHAIN,
            () -> Ingredient.of(ItemInit.ICE_GEM::get),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "ice"))),
            0f,
            0.12f
    ));
    public static final Holder<ArmorMaterial> GOLEM = ARMOR_MATERIALS.register("golem", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 5);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.BODY, 5);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_CHAIN,
            () -> Ingredient.of(ItemInit.GOLEM_GYRO::get),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "golem"))),
            0.0f,
            0.25f
    ));
}
