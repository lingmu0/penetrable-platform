package net.xuwu.penetrableplatform.registry;

import java.util.function.Supplier;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.xuwu.penetrableplatform.PenetrablePlatform;
import net.xuwu.penetrableplatform.block.PlatformBlock;
import net.xuwu.penetrableplatform.block.MobStandablePlatformBlock;
import net.xuwu.penetrableplatform.block.SneakThroughPlatformBlock;

public final class PlatformContent {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(PenetrablePlatform.MOD_ID);
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(PenetrablePlatform.MOD_ID);
    public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPES =
            DeferredRegister.create(Registries.BLOCK_TYPE, PenetrablePlatform.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PenetrablePlatform.MOD_ID);

    public static final Supplier<PlatformBlock> PIERCING_PLATFORM = BLOCKS.registerBlock(
            "piercing_platform", PlatformBlock::new, PlatformContent::platformProperties);
    public static final Supplier<SneakThroughPlatformBlock> SHIFT_PIERCING_PLATFORM = BLOCKS.registerBlock(
            "shift_piercing_platform", SneakThroughPlatformBlock::new, PlatformContent::platformProperties);
    public static final Supplier<MobStandablePlatformBlock> MOB_PIERCING_PLATFORM = BLOCKS.registerBlock(
            "mob_piercing_platform", MobStandablePlatformBlock::new, PlatformContent::platformProperties);

    static {
        BLOCK_TYPES.register("piercing_platform", () -> PlatformBlock.CODEC);
        BLOCK_TYPES.register("shift_piercing_platform", () -> SneakThroughPlatformBlock.CODEC);
        BLOCK_TYPES.register("mob_piercing_platform", () -> MobStandablePlatformBlock.CODEC);
    }

    public static final Supplier<Item> PLATFORM_WAND = ITEMS.registerSimpleItem(
            "platform_wand", properties -> properties.stacksTo(1));
    public static final Supplier<BlockItem> PIERCING_PLATFORM_ITEM = ITEMS.registerSimpleBlockItem("piercing_platform", PIERCING_PLATFORM);
    public static final Supplier<BlockItem> SHIFT_PIERCING_PLATFORM_ITEM = ITEMS.registerSimpleBlockItem("shift_piercing_platform", SHIFT_PIERCING_PLATFORM);
    public static final Supplier<BlockItem> MOB_PIERCING_PLATFORM_ITEM = ITEMS.registerSimpleBlockItem("mob_piercing_platform", MOB_PIERCING_PLATFORM);

    public static final Supplier<CreativeModeTab> TAB = TABS.register(
            "platforms",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.penetrable_platform"))
                    .icon(() -> PLATFORM_WAND.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(PLATFORM_WAND.get());
                        output.accept(PIERCING_PLATFORM_ITEM.get());
                        output.accept(SHIFT_PIERCING_PLATFORM_ITEM.get());
                        output.accept(MOB_PIERCING_PLATFORM_ITEM.get());
                    })
                    .build());

    private static BlockBehaviour.Properties platformProperties(BlockBehaviour.Properties properties) {
        return properties
                .strength(0.3F, 3_600_000.0F)
                .sound(SoundType.GLASS)
                .dynamicShape()
                .noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false)
                .pushReaction(PushReaction.BLOCK)
                .lightLevel(state -> 12);
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        BLOCK_TYPES.register(modBus);
        ITEMS.register(modBus);
        TABS.register(modBus);
    }

    private PlatformContent() {
    }
}
