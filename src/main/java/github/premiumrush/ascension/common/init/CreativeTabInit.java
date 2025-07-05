package github.premiumrush.ascension.common.init;

import github.premiumrush.ascension.Ascension;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = Ascension.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CreativeTabInit {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Ascension.MODID);

    public static final List<Supplier<? extends ItemLike>> ASCENSION_TAB_ITEMS = new ArrayList<>();

    public static final Supplier<CreativeModeTab> ASCENSION_TAB = TABS.register("ascension_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.ascension_tab"))
                    .icon(ItemInit.GOLEM_GYRO.get()::getDefaultInstance)
                    .displayItems((displayParams, output) -> {
                        ASCENSION_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get()));
                    })
                    .build()
    );
    public static <T extends Item> Supplier<T> addToTab(Supplier<T> itemLike) {
        ASCENSION_TAB_ITEMS.add(itemLike);
        return itemLike;
    }

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.insertAfter(Items.NETHERITE_SWORD.getDefaultInstance(), ItemInit.IRON_DAGGER.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ItemInit.IRON_DAGGER.get().getDefaultInstance(), ItemInit.GOLD_DAGGER.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ItemInit.GOLD_DAGGER.get().getDefaultInstance(), ItemInit.DIAMOND_DAGGER.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ItemInit.DIAMOND_DAGGER.get().getDefaultInstance(), ItemInit.NETHERITE_DAGGER.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
