package net.xuwu.penetrableplatform.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.xuwu.penetrableplatform.PenetrablePlatform;

/**
 * A player-only one-way platform. Without the platform wand it is invisible
 * to block selection and ray casts, so attacks can pass through it naturally.
 * The empty collision context is kept non-empty for Minecraft's block-hit
 * particle code, which asks for the shape without an entity context and then
 * reads its bounds unconditionally.
 */
public class PlatformBlock extends Block {
    public static final MapCodec<PlatformBlock> CODEC = simpleCodec(PlatformBlock::new);
    private final boolean passableWhileSneaking;
    private final boolean livingEntitiesCanStand;

    public PlatformBlock(Properties properties) {
        this(properties, false, false);
    }

    protected PlatformBlock(Properties properties, boolean passableWhileSneaking, boolean livingEntitiesCanStand) {
        super(properties);
        this.passableWhileSneaking = passableWhileSneaking;
        this.livingEntitiesCanStand = livingEntitiesCanStand;
    }

    @Override
    public MapCodec<? extends PlatformBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction side) {
        return adjacentState.getBlock() instanceof PlatformBlock
                || super.skipRendering(state, adjacentState, side);
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return false;
    }

    @Override
    public void onBlockExploded(BlockState state, ServerLevel level, BlockPos pos, Explosion explosion) {
        // Keep the platform intact even if an unusually strong explosion reaches this callback.
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context == CollisionContext.empty()) {
            return Shapes.block();
        }
        if (context instanceof EntityCollisionContext entityContext
                && entityContext.getEntity() instanceof Player player
                && PenetrablePlatform.isHoldingWand(player)) {
            return Shapes.block();
        }
        return Shapes.empty();
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (!(context instanceof EntityCollisionContext entityContext)) {
            return Shapes.empty();
        }

        Entity entity = entityContext.getEntity();
        if (!(entity instanceof Player)
                && !(livingEntitiesCanStand && entity instanceof LivingEntity)) {
            return Shapes.empty();
        }

        if ((passableWhileSneaking && entity instanceof Player player && player.isShiftKeyDown())
                || entity.getDeltaMovement().y > 0
                || !context.isAbove(Shapes.block(), pos, false)) {
            return Shapes.empty();
        }
        return Shapes.block();
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double distance) {
        if (entity instanceof Player player) {
            player.resetFallDistance();
        } else {
            super.fallOn(level, state, pos, entity, distance);
        }
    }
}
