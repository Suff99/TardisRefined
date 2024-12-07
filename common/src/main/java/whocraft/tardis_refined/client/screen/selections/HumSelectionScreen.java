package whocraft.tardis_refined.client.screen.selections;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.StringReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.client.screen.components.SelectionListEntry;
import whocraft.tardis_refined.client.screen.main.MonitorOS;
import whocraft.tardis_refined.common.hum.HumEntry;
import whocraft.tardis_refined.common.hum.TardisHums;
import whocraft.tardis_refined.common.network.messages.hums.C2SChangeHum;
import whocraft.tardis_refined.common.util.MiscHelper;

import java.util.Collection;
import java.util.Comparator;

public class HumSelectionScreen extends MonitorOS {

    private HumEntry currentHumEntry;

    public HumSelectionScreen() {
        super(Component.translatable(""));
    }

    public static void selectHum(HumEntry theme) {
        assert Minecraft.getInstance().player != null;
        new C2SChangeHum(Minecraft.getInstance().player.level().dimension(), theme).send();
        Minecraft.getInstance().setScreen(null);
    }

    @Override
    protected void init() {
        this.setEvents(() -> {
            HumSelectionScreen.selectHum(currentHumEntry);
        }, () -> {
            Minecraft.getInstance().setScreen(null);
        });
        this.currentHumEntry = grabHum();

        super.init();

        addSubmitButton(width / 2 + 85, (height) / 2 + 35);
        addCancelButton(width / 2 - 105, (height) / 2 + 35);
    }


    private HumEntry grabHum() {
        for (HumEntry humEntry : TardisHums.getRegistry().values()) {
            return humEntry;
        }
        return null;
    }

    @Override
    public ObjectSelectionList<SelectionListEntry> createSelectionList() {
        int vPos = (height - monitorHeight) / 2;
        int leftPos = this.width / 2 - 75;
        GenericMonitorSelectionList<SelectionListEntry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, 150, 80, leftPos, vPos + 30, vPos + monitorHeight - 60, 12);
        selectionList.setRenderBackground(false);

        Collection<HumEntry> knownHums = TardisHums.getRegistry().values();
        knownHums = knownHums.stream().sorted(Comparator.comparing(HumEntry::getNameComponent)).toList();

        for (HumEntry humEntry : knownHums) {
            Component name = Component.literal(MiscHelper.getCleanName(humEntry.getIdentifier().getPath()));

            // Check for if the tellraw name is incomplete, or fails to pass.
            try {
                var json = Component.Serializer.fromJson(new StringReader(humEntry.getNameComponent()));
                name = json;
            } catch (Exception ex) {
                TardisRefined.LOGGER.error("Could not process Name for hum " + humEntry.getIdentifier().toString());
            }

            selectionList.children().add(new SelectionListEntry(name, (entry) -> {
                // previousImage = humEntry.getPreviewTexture();
                this.currentHumEntry = humEntry;

                for (Object child : selectionList.children()) {
                    if (child instanceof SelectionListEntry current) {
                        current.setChecked(false);
                    }
                }
                entry.setChecked(true);
            }, leftPos));
        }

        return selectionList;
    }

}