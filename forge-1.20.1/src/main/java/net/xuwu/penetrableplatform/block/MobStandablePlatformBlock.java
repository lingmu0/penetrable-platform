package net.xuwu.penetrableplatform.block;

import net.minecraft.world.level.block.state.BlockBehaviour;

/** A one-way platform that supports players and all living entities. */
public final class MobStandablePlatformBlock extends PlatformBlock {
    public MobStandablePlatformBlock(BlockBehaviour.Properties properties) {
        super(properties, false, true);
    }
}
