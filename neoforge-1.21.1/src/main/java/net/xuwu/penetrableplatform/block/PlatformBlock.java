package net.xuwu.penetrableplatform.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
 */
public class PlatformBlock extends Block {
    private final boolean passableWhileSneaking;

    public PlatformBlock(Properties properties, boolean passableWhileSneaking) {
        super(properties);
        this.passableWhileSneaking = passableWhileSneaking;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
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
        if (!(context instanceof EntityCollisionContext entityContext)
                || !(entityContext.getEntity() instanceof Player player)
                || (passableWhileSneaking && player.isShiftKeyDown())
                || player.getDeltaMovement().y > 0
                || !context.isAbove(Shapes.block(), pos, false)) {
            return Shapes.empty();
        }
        return Shapes.block();
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float distance) {
        if (entity instanceof Player player) {
            player.fallDistance = 0.0F;
        } else {
            super.fallOn(level, state, pos, entity, distance);
        }
    }
}
