package net.xuwu.penetrableplatform.registry;

import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.xuwu.penetrableplatform.PenetrablePlatform;
import net.xuwu.penetrableplatform.block.PlatformBlock;
import net.xuwu.penetrableplatform.block.SneakThroughPlatformBlock;

public final class PlatformContent {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, PenetrablePlatform.MOD_ID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, PenetrablePlatform.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PenetrablePlatform.MOD_ID);

    public static final Supplier<PlatformBlock> PIERCING_PLATFORM = BLOCKS.register(
            "piercing_platform",
            () -> new PlatformBlock(platformProperties(), false));
    public static final Supplier<SneakThroughPlatformBlock> SHIFT_PIERCING_PLATFORM = BLOCKS.register(
            "shift_piercing_platform",
            () -> new SneakThroughPlatformBlock(platformProperties()));

    public static final Supplier<Item> PLATFORM_WAND = ITEMS.register(
            "platform_wand",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PIERCING_PLATFORM_ITEM = ITEMS.register(
            "piercing_platform",
            () -> new BlockItem(PIERCING_PLATFORM.get(), new Item.Properties()));
    public static final Supplier<Item> SHIFT_PIERCING_PLATFORM_ITEM = ITEMS.register(
            "shift_piercing_platform",
            () -> new BlockItem(SHIFT_PIERCING_PLATFORM.get(), new Item.Properties()));

    public static final Supplier<CreativeModeTab> TAB = TABS.register(
            "platforms",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.penetrable_platform"))
                    .icon(() -> PLATFORM_WAND.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(PLATFORM_WAND.get());
                        output.accept(PIERCING_PLATFORM_ITEM.get());
                        output.accept(SHIFT_PIERCING_PLATFORM_ITEM.get());
                    })
                    .build());

    private static BlockBehaviour.Properties platformProperties() {
        return BlockBehaviour.Properties.of()
                .strength(0.3F)
                .sound(SoundType.GLASS)
                .dynamicShape()
                .noOcclusion()
                .lightLevel(state -> 12);
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        TABS.register(modBus);
    }

    private PlatformContent() {
    }
}
