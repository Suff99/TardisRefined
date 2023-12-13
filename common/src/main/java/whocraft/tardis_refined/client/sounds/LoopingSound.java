package whocraft.tardis_refined.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LoopingSound extends AbstractTickableSoundInstance {

    public static LoopingSound ARS_HUMMING = null;


    public LoopingSound(@NotNull SoundEvent soundEvent, SoundSource soundSource) {
        super(soundEvent, soundSource, SoundInstance.createUnseededRandom());
        attenuation = Attenuation.NONE;
        looping = true;
        delay = 0;
        volume = 0.0001f;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    public void setLocation(Vec3 location) {
        x = location.x;
        y = location.y;
        z = location.z;
    }

    public void stopSound() {
        Minecraft.getInstance()
                .getSoundManager()
                .stop(this);
    }

    @Override
    public ResourceLocation getLocation() {
        return super.getLocation();
    }

    @Override
    public void tick() {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player != null) {
            Vec3 playerVec = player.position();
            double distance = playerVec.distanceTo(new Vec3(x, y, z));
            double maxDistance = 11.0;
            double fadeFactor = Math.max(1.0 - distance / maxDistance, 0.0);
            float defaultVolume = 1.0f;
            volume = (float) (fadeFactor * defaultVolume);
        }
    }


}