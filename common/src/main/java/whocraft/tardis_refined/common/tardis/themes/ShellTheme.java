package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import whocraft.tardis_refined.ModMessages;
import whocraft.tardis_refined.TardisRefined;

public enum ShellTheme implements StringRepresentable, Theme {

    FACTORY("factory", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/factory_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/factory_shell_door.png")),
    POLICE_BOX("police_box", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/tdis_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/tdis_shell_door.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/tdis_shell_emissive.png")),
    PHONE_BOOTH("phone_booth", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/phone_booth_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/phone_booth_shell_door.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/phone_booth_shell_emissive.png")),
    MYSTIC("mystic", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/mystic_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/mystic_shell_door.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/mystic_shell_emmissive.png"));

    private final String id;
    private final ResourceLocation externalShellTexture, internalDoorTexture;
    private ResourceLocation emmissiveExternal;

    ShellTheme(String id, ResourceLocation externalShellTexture, ResourceLocation internalDoorTexture) {
        this.id = id;
        this.externalShellTexture = externalShellTexture;
        this.internalDoorTexture = internalDoorTexture;
        this.emmissiveExternal = null;
    }

    ShellTheme(String id, ResourceLocation externalShellTexture, ResourceLocation internalDoorTexture, ResourceLocation emmissiveExternalShellTexture) {
        this.id = id;
        this.externalShellTexture = externalShellTexture;
        this.internalDoorTexture = internalDoorTexture;
        this.emmissiveExternal = emmissiveExternalShellTexture;
    }

    public ResourceLocation getExternalShellTexture() {
        return this.externalShellTexture;
    }

    public ResourceLocation getInternalDoorTexture() {
        return this.internalDoorTexture;
    }

    public ResourceLocation emmissiveExternal() {
        return emmissiveExternal;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }

    public String getTranslationKey() {
        return ModMessages.shell(id);
    }

    public Component getDisplayName() {
        return Component.translatable(getTranslationKey());
    }

    public static ShellTheme findOr(String id, ShellTheme shellTheme) {
        for (ShellTheme value : ShellTheme.values()) {
            if (value.name().toLowerCase().matches(id)) {
                return value;
            }
        }
        return shellTheme;
    }
}
