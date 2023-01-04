package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

import java.util.Optional;

public class GlobalDoorBlockEntity extends AbstractEntityBlockDoor {

    public GlobalDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.GLOBAL_DOOR_BLOCK.get(), blockPos, blockState);
    }


    public void onRightClick(BlockState blockState, ITardisInternalDoor door, Player player) {
        if (getLevel() instanceof ServerLevel serverLevel) {

            // we know that in this instance the serverlevel has a capability.
            TardisLevelOperator.get(serverLevel).ifPresent(cap -> {
                if (cap.getInternalDoor() != door) {
                    cap.setInternalDoor(door);
                }
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                ResourceKey<Level> dimension = level.dimension();

                boolean validKey = KeyItem.keychainContains(stack, dimension);
                if(validKey) {
                    cap.getExteriorManager().setLocked(!door.locked());
                    door.setLocked(!door.locked());
                    cap.setDoorClosed(true);
                    return;
                }
                if (!cap.getControlManager().isInFlight() && !door.locked()) {
                    cap.setDoorClosed(blockState.getValue(GlobalDoorBlock.OPEN));
                }
            });
        }
    }

    public void onAttemptEnter(Level level, Player player) {
        if (!level.isClientSide()) {
            Optional<TardisLevelOperator> operator = TardisLevelOperator.get((ServerLevel) level);
            operator.ifPresent(x -> {
                x.setInternalDoor(this);
                x.exitTardis(player);
            });
        }
    }
}
