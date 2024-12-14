package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.control.ExteriorDisplayControl;
import whocraft.tardis_refined.common.tardis.control.flight.*;
import whocraft.tardis_refined.common.tardis.control.ship.MonitorControl;
import whocraft.tardis_refined.common.tardis.control.ship.ToggleDoorControl;

public class TRControlRegistry {
    /**
     * Registry Key for the Controls registry. For addon mods, use this as the registry key
     */
    public static final ResourceKey<Registry<Control>> CONTROL_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "control"));

    /**
     * Instance of registry containing all Control entries. Addon mod entries will be included in this registry as long as they are use the same ResourceKey<Registry<ObjectType>>.
     */
    public static final Registry<Control> CONTROL_REGISTRY = RegistryBuilder.create(CONTROL_REGISTRY_KEY).build();

    /**
     * Tardis Refined instance of the Controls registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only
     */
    public static final DeferredRegister<Control> CONTROL_DEFERRED_REGISTRY = DeferredRegister.create(TardisRefined.MODID, CONTROL_REGISTRY_KEY);


    // Tardis refined controls
    public static final RegistryHolder<Control, Control> DOOR_TOGGLE = register(new ToggleDoorControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "door_toggle")));
    public static final RegistryHolder<Control, Control> X = register(new CoordinateControl(CoordinateButton.X, TardisRefined.MODID));
    public static final RegistryHolder<Control, Control> Y = register(new CoordinateControl(CoordinateButton.Y, TardisRefined.MODID));
    public static final RegistryHolder<Control, Control> Z = register(new CoordinateControl(CoordinateButton.Z, TardisRefined.MODID));
    public static final RegistryHolder<Control, Control> INCREMENT = register(new IncrementControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "increment")));
    public static final RegistryHolder<Control, Control> ROTATE = register(new RotationControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "rotate")));
    public static final RegistryHolder<Control, Control> RANDOM = register(new RandomControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "random")));
    public static final RegistryHolder<Control, Control> THROTTLE = register(new ThrottleControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "throttle")));
    public static final RegistryHolder<Control, Control> MONITOR = register(new MonitorControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "monitor")).setCanBeUsedPostCrash(true));
    public static final RegistryHolder<Control, Control> DIMENSION = register(new DimensionalControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "dimension")));
    public static final RegistryHolder<Control, Control> FAST_RETURN = register(new FastReturnControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "fast_return")));
    public static final RegistryHolder<Control, Control> READOUT = register(new ReadoutControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "read_out")));
    public static final RegistryHolder<Control, Control> GENERIC_NO_SHOW = register(new GenericControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "generic_no_show")));
    public static final RegistryHolder<Control, Control> HANDBRAKE = register(new HandbrakeControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "hand_brake")));
    public static final RegistryHolder<Control, Control> FUEL = register(new FuelToggleControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "fuel")));
    public static final RegistryHolder<Control, Control> EXTERIOR_DISPLAY = register(new ExteriorDisplayControl(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "exterior_display")));

    public static Control get(ResourceLocation id) {
        Control potentialTheme = CONTROL_REGISTRY.get(id);
        if (potentialTheme != null) {
            return potentialTheme;
        }
        return THROTTLE.get();
    }

    public static ResourceLocation getKey(Control control) {
        return CONTROL_REGISTRY.getKey(control);
    }

    /**
     * Register methods for Tardis Refined only
     *
     * @return the registered control
     */
    private static RegistryHolder<Control, Control> register(Control control) {
        return register(control, control.getId().getPath());
    }

    private static RegistryHolder<Control, Control> register(Control control, String id) {
        return CONTROL_DEFERRED_REGISTRY.register(id, () -> control);
    }
}

