package whocraft.tardis_refined.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import whocraft.tardis_refined.TardisRefined;

public class TRSoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(TardisRefined.MODID, Registries.SOUND_EVENT);

    public static final RegistryHolder<SoundEvent, SoundEvent> TARDIS_TAKEOFF = setUpSound("tardis_takeoff");
    public static final RegistryHolder<SoundEvent, SoundEvent> TARDIS_LAND = setUpSound("tardis_land");
    public static final RegistryHolder<SoundEvent, SoundEvent> TARDIS_SINGLE_FLY = setUpSound("tardis_single_fly");
    public static final RegistryHolder<SoundEvent, SoundEvent> TIME_BLAST = setUpSound("time_blast");
    public static final RegistryHolder<SoundEvent, SoundEvent> TARDIS_CRASH_LAND = setUpSound("tardis_crash_land");
    public static final RegistryHolder<SoundEvent, SoundEvent> PATTERN_MANIPULATOR = setUpSound("pattern_manipulator");
    public static final RegistryHolder<SoundEvent, SoundEvent> TARDIS_MISC_SPARKLE = setUpSound("tardis_misc_sparkle");
    public static final RegistryHolder<SoundEvent, SoundEvent> STATIC = setUpSound("static");
    public static final RegistryHolder<SoundEvent, SoundEvent> DESTINATION_DING = setUpSound("destination_ding");
    public static final RegistryHolder<SoundEvent, SoundEvent> ARS_HUM = setUpSound("ars_hum");
    public static final RegistryHolder<SoundEvent, SoundEvent> BULKHEAD_LOCKED = setUpSound("bulkhead_locked");

    public static final RegistryHolder<SoundEvent, SoundEvent> INTERIOR_CREAKS = setUpSound("interior_creaks");
    public static final RegistryHolder<SoundEvent, SoundEvent> FLIGHT_FAIL_START = setUpSound("flight_fail_start");
    public static final RegistryHolder<SoundEvent, SoundEvent> CONSOLE_POWER_ON = setUpSound("console_power_on");
    public static final RegistryHolder<SoundEvent, SoundEvent> ALARM = setUpSound("alarm");
    public static final RegistryHolder<SoundEvent, SoundEvent> INTERIOR_VOICE = setUpSound("interior_voice");
    public static final RegistryHolder<SoundEvent, SoundEvent> LOW_FUEL = setUpSound("low_fuel");
    public static final RegistryHolder<SoundEvent, SoundEvent> ARTRON_PILLAR_ACTIVE = setUpSound("artron_pillar_active");

    public static final RegistryHolder<SoundEvent, SoundEvent> CORRIDOR_TELEPORTER = setUpSound("corridor_teleporter");
    public static final RegistryHolder<SoundEvent, SoundEvent> CORRIDOR_TELEPORTER_SUCCESS = setUpSound("corridor_teleporter_success");
    public static final RegistryHolder<SoundEvent, SoundEvent> CLOISTER_BELL = setUpSound("cloister_bell");
    public static final RegistryHolder<SoundEvent, SoundEvent> MALLET = setUpSound("mallet");
    public static final RegistryHolder<SoundEvent, SoundEvent> VORTEX = setUpSound("vortex");

    // Hums

    public static final RegistryHolder<SoundEvent, SoundEvent> HUM_CORAL = setUpSound("hum_coral");
    public static final RegistryHolder<SoundEvent, SoundEvent> HUM_CAVE = setUpSound("hum_cave");
    public static final RegistryHolder<SoundEvent, SoundEvent> HUM_TOYOTA = setUpSound("hum_toyota");
    public static final RegistryHolder<SoundEvent, SoundEvent> HUM_CLASSIC = setUpSound("hum_classic");
    public static final RegistryHolder<SoundEvent, SoundEvent> HUM_VICTORIAN = setUpSound("hum_victorian");
    public static final RegistryHolder<SoundEvent, SoundEvent> HUM_AVIATRAX = setUpSound("hum_aviatrax");


    // Screwdriver
    public static final RegistryHolder<SoundEvent, SoundEvent> SCREWDRIVER_SHORT = setUpSound("screwdriver_short");
    public static final RegistryHolder<SoundEvent, SoundEvent> SCREWDRIVER_CONNECT = setUpSound("screwdriver_connect");
    public static final RegistryHolder<SoundEvent, SoundEvent> SCREWDRIVER_DISCARD = setUpSound("screwdriver_discard");
    public static final RegistryHolder<SoundEvent, SoundEvent> GRAVITY_TUNNEL = setUpSound("gravity_tunnel");

    private static RegistryHolder<SoundEvent, SoundEvent> setUpSound(String soundName) {
        SoundEvent sound = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, soundName));
        return SOUNDS.register(soundName, () -> sound);
    }
}
