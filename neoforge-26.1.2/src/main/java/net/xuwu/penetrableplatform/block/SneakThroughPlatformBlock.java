package net.xuwu.penetrableplatform.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.world.level.block.state.BlockBehaviour;

/** The platform variant that lets a sneaking player drop through it. */
public final class SneakThroughPlatformBlock extends PlatformBlock {
    public static final MapCodec<SneakThroughPlatformBlock> CODEC = simpleCodec(SneakThroughPlatformBlock::new);

    public SneakThroughPlatformBlock(BlockBehaviour.Properties properties) {
        super(properties, true, false);
    }

    @Override
    public MapCodec<SneakThroughPlatformBlock> codec() {
        return CODEC;
    }
}
