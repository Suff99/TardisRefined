package whocraft.tardis_refined.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.items.DrillItem;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ZeitonIngotItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemRegistry {
    public static List<RegistrySupplier<Item>> TAB_ITEMS = new ArrayList<>();
    public static final DeferredRegistry<CreativeModeTab> TABS = DeferredRegistry.create(TardisRefined.MODID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> MAIN_TAB = TABS.register("main_tab", ItemRegistry::getCreativeTab);
    public static final RegistrySupplier<CreativeModeTab> DYED_TAB = TABS.register("dyed_tab", ItemRegistry::getDyedCreativeTab);


    public static final DeferredRegistry<Item> ITEMS = DeferredRegistry.create(TardisRefined.MODID, Registries.ITEM);

    public static final RegistrySupplier<Item> KEY = register("tardis_key", () -> new KeyItem(new Item.Properties().stacksTo(1)), true);
    public static final RegistrySupplier<Item> SCREWDRIVER = register("amethyst_screwdriver", () -> new ScrewdriverItem(new Item.Properties().stacksTo(1)), true);
    public static final RegistrySupplier<Item> PATTERN_MANIPULATOR = register("pattern_manipulator", () -> new Item(new Item.Properties().stacksTo(1)), true);
    public static final RegistrySupplier<Item> DRILL = register("drill", () -> new DrillItem(new Item.Properties().stacksTo(1)), true);

    public static final RegistrySupplier<Item> RAW_ZEITON = register("raw_zeiton", () -> new Item(new Item.Properties()), true);
    public static final RegistrySupplier<ZeitonIngotItem> ZEITON_INGOT = register("zeiton_ingot", () -> new ZeitonIngotItem(new Item.Properties()), true);



    private static <T extends Item> RegistrySupplier<T> register(String id, Supplier<T> itemSupplier, boolean addToTab) {
        RegistrySupplier<T> item = ITEMS.register(id, itemSupplier);
        if(addToTab) {
            TAB_ITEMS.add((RegistrySupplier<Item>) item);
        }
        return item;
    }

    @ExpectPlatform
    public static CreativeModeTab getCreativeTab() {
        throw new RuntimeException(TardisRefined.PLATFORM_ERROR);
    }

    @ExpectPlatform
    public static CreativeModeTab getDyedCreativeTab() {
        throw new RuntimeException(TardisRefined.PLATFORM_ERROR);
    }


}
