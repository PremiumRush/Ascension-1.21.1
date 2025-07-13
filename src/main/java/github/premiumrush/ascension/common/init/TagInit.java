package github.premiumrush.ascension.common.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class TagInit {
    public static final TagKey<EntityType<?>> INVULNERABLE_TO_BLEEDING = tag("bleeding_invulnerable");

    private static TagKey<EntityType<?>> tag(String name) {
        return TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), ResourceLocation.fromNamespaceAndPath("ascension", name));
    }
}
