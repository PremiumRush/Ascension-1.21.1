package github.premiumrush.ascension.datagen.tags;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.init.EntityInit;
import github.premiumrush.ascension.common.init.TagInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class AscensionEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public AscensionEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Ascension.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(TagInit.INVULNERABLE_TO_BLEEDING).add(
                EntityType.SKELETON_HORSE,
                EntityType.SKELETON,
                EntityType.WITHER,
                EntityType.WITHER_SKELETON,
                EntityType.BLAZE,
                EntityType.STRAY,
                EntityType.IRON_GOLEM,
                EntityType.SNOW_GOLEM,
                EntityType.SHULKER,
                EntityType.ENDERMITE,
                EntityType.ENDERMAN,
                EntityType.ENDER_DRAGON,
                EntityInit.EMBER_GOLEM.get(),
                EntityInit.TITANIUM_GOLEM.get(),
                EntityInit.FERROTITANIUM_GOLEM.get(),
                EntityInit.ICE_BLAZE.get()
        );
    }
}
