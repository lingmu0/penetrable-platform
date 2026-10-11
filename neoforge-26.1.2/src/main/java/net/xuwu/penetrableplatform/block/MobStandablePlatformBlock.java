package net.xuwu.penetrableplatform.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.world.level.block.state.BlockBehaviour;

/** A one-way platform that supports players and all living entities. */
public final class MobStandablePlatformBlock extends PlatformBlock {
    public static final MapCodec<MobStandablePlatformBlock> CODEC = simpleCodec(MobStandablePlatformBlock::new);
    public MobStandablePlatformBlock(BlockBehaviour.Properties properties) {
        super(properties, false, true);
    }

    @Override
    public MapCodec<MobStandablePlatformBlock> codec() {
        return CODEC;
    }
}
