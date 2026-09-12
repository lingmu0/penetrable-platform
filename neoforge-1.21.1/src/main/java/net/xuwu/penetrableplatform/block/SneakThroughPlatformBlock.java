package net.xuwu.penetrableplatform.block;

import net.minecraft.world.level.block.state.BlockBehaviour;

/** The platform variant that lets a sneaking player drop through it. */
public final class SneakThroughPlatformBlock extends PlatformBlock {
    public SneakThroughPlatformBlock(BlockBehaviour.Properties properties) {
        super(properties, true);
    }
}
