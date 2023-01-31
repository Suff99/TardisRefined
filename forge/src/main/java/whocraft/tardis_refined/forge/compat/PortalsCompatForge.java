package whocraft.tardis_refined.forge.compat;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;

public class PortalsCompatForge {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new PortalsCompatForge());
    }

    @SubscribeEvent
    public void onBreak(BlockEvent.BreakEvent breakEvent) {
        BlockEntity blockEntity = breakEvent.getLevel().getBlockEntity(breakEvent.getPos());
        ImmersivePortals.onDoorRemoved(breakEvent.getPlayer().getLevel(), breakEvent.getPlayer(), breakEvent.getPos(), breakEvent.getState(), blockEntity);
    }

}
