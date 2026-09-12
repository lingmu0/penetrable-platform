package net.xuwu.penetrableplatform;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.xuwu.penetrableplatform.event.PlatformEvents;
import net.xuwu.penetrableplatform.registry.PlatformContent;

@Mod(PenetrablePlatform.MOD_ID)
public final class PenetrablePlatform {
    public static final String MOD_ID = "penetrable_platform";

    public PenetrablePlatform() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        PlatformContent.register(modBus);
        MinecraftForge.EVENT_BUS.register(PlatformEvents.class);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static boolean isHoldingWand(Player player) {
        return player.getMainHandItem().is(PlatformContent.PLATFORM_WAND.get())
                || player.getOffhandItem().is(PlatformContent.PLATFORM_WAND.get());
    }
}
