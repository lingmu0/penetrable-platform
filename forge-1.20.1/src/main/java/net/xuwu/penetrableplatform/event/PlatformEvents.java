package net.xuwu.penetrableplatform.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.xuwu.penetrableplatform.PenetrablePlatform;
import net.xuwu.penetrableplatform.block.PlatformBlock;

public final class PlatformEvents {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!event.getLevel().isClientSide()
                && event.getState().getBlock() instanceof PlatformBlock
                && !PenetrablePlatform.isHoldingWand(event.getPlayer())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getPlacedBlock().getBlock() instanceof PlatformBlock)) {
            return;
        }

        if (!(event.getEntity() instanceof Player player) || !PenetrablePlatform.isHoldingWand(player)) {
            event.setCanceled(true);
        }
    }

    private PlatformEvents() {
    }
}
