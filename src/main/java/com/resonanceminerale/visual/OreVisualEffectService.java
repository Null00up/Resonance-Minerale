package com.resonanceminerale.visual;

import com.resonanceminerale.detection.OreDetectionResult;
import com.resonanceminerale.detection.OreType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.joml.Vector3f;

import java.util.*;

public final class OreVisualEffectService {
    private static final int EFFECT_DURATION_TICKS = 4 * 20;
    private static final int EFFECT_COOLDOWN_TICKS = 4 * 20;
    private static final int PARTICLES_PER_COLOR_PER_PULSE = 3;
    private static final int PULSE_INTERVAL_TICKS = 4;

    private static final Map<UUID, Long> NEXT_ALLOWED_EFFECT_TICK_BY_PLAYER = new HashMap<>();
    private static final List<VisibleOreEffect> ACTIVE_EFFECTS = new ArrayList<>();

    private OreVisualEffectService() {
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> tickEffects());
    }

    public static void tryStartVisibleOreEffect(ServerPlayerEntity player, OreDetectionResult result) {
        Optional<BlockPos> nearestOrePos = result.nearestOrePos();

        if (nearestOrePos.isEmpty()) {
            return;
        }

        if (!canStartEffect(player)) {
            return;
        }

        BlockPos orePos = nearestOrePos.get();

        if (!canSeeBlock(player, orePos)) {
            return;
        }

        ServerWorld world = player.getServerWorld();
        long currentTick = world.getTime();

        NEXT_ALLOWED_EFFECT_TICK_BY_PLAYER.put(player.getUuid(), currentTick + EFFECT_COOLDOWN_TICKS);
        ACTIVE_EFFECTS.add(new VisibleOreEffect(world, player.getUuid(), orePos, result.oreType(), EFFECT_DURATION_TICKS, 0));
    }

    private static boolean canStartEffect(ServerPlayerEntity player) {
        long nextAllowedTick = NEXT_ALLOWED_EFFECT_TICK_BY_PLAYER.getOrDefault(player.getUuid(), 0L);
        return player.getServerWorld().getTime() >= nextAllowedTick;
    }

    private static boolean canSeeBlock(ServerPlayerEntity player, BlockPos blockPos) {
        Vec3d start = player.getEyePos();
        Vec3d end = Vec3d.ofCenter(blockPos);

        HitResult hitResult = player.getWorld().raycast(new RaycastContext(
                start,
                end,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                player
        ));

        return hitResult.getType() == HitResult.Type.BLOCK
                && ((BlockHitResult) hitResult).getBlockPos().equals(blockPos);
    }

    private static void tickEffects() {
        Iterator<VisibleOreEffect> iterator = ACTIVE_EFFECTS.iterator();

        while (iterator.hasNext()) {
            VisibleOreEffect effect = iterator.next();
            ServerPlayerEntity player = effect.world.getServer().getPlayerManager().getPlayer(effect.playerUuid);

            if (effect.remainingTicks <= 0
                    || player == null
                    || player.getServerWorld() != effect.world
                    || !effect.oreType.blocks().contains(effect.world.getBlockState(effect.blockPos).getBlock())
                    || !canSeeBlock(player, effect.blockPos)) {
                iterator.remove();
                continue;
            }

            if (effect.ticksUntilNextPulse <= 0) {
                spawnOreParticles(player, effect.world, effect.blockPos, effect.oreType);
                effect.ticksUntilNextPulse = PULSE_INTERVAL_TICKS;
            }

            effect.remainingTicks--;
            effect.ticksUntilNextPulse--;
        }
    }

    private static void spawnOreParticles(ServerPlayerEntity player, ServerWorld world, BlockPos blockPos, OreType oreType) {
        for (DustParticleEffect particleEffect : particleEffectsFor(oreType)) {
            world.spawnParticles(
                    player,
                    particleEffect,
                    false,
                    blockPos.getX() + 0.5D,
                    blockPos.getY() + 0.5D,
                    blockPos.getZ() + 0.5D,
                    PARTICLES_PER_COLOR_PER_PULSE,
                    0.55D,
                    0.55D,
                    0.55D,
                    0.02D
            );
        }
    }

    private static List<DustParticleEffect> particleEffectsFor(OreType oreType) {
        return switch (oreType) {
            case COAL -> List.of(
                    dust(0.08F, 0.08F, 0.08F, 0.95F),
                    dust(0.85F, 0.85F, 0.80F, 0.70F)
            );
            case COPPER -> List.of(
                    dust(0.95F, 0.45F, 0.15F, 0.90F),
                    dust(1.00F, 0.75F, 0.35F, 0.65F)
            );
            case IRON -> List.of(
                    dust(0.65F, 0.65F, 0.65F, 0.90F),
                    dust(0.95F, 0.95F, 0.90F, 0.65F)
            );
            case GOLD -> List.of(
                    dust(1.00F, 0.78F, 0.10F, 0.95F),
                    dust(1.00F, 0.95F, 0.45F, 0.65F)
            );
            case REDSTONE -> List.of(
                    dust(1.00F, 0.05F, 0.03F, 0.95F),
                    dust(1.00F, 0.35F, 0.25F, 0.65F)
            );
            case LAPIS -> List.of(
                    dust(0.05F, 0.15F, 0.95F, 0.95F),
                    dust(0.35F, 0.55F, 1.00F, 0.65F)
            );
            case DIAMOND -> List.of(
                    dust(0.20F, 0.95F, 1.00F, 0.95F),
                    dust(0.85F, 1.00F, 1.00F, 0.65F)
            );
            case EMERALD -> List.of(
                    dust(0.05F, 1.00F, 0.25F, 0.95F),
                    dust(0.65F, 1.00F, 0.70F, 0.65F)
            );
            case ANCIENT_DEBRIS -> List.of(
                    dust(0.45F, 0.16F, 0.08F, 0.95F),
                    dust(0.95F, 0.38F, 0.14F, 0.65F)
            );
        };
    }

    private static DustParticleEffect dust(float red, float green, float blue, float scale) {
        return new DustParticleEffect(new Vector3f(red, green, blue), scale);
    }

    private static final class VisibleOreEffect {
        private final ServerWorld world;
        private final UUID playerUuid;
        private final BlockPos blockPos;
        private final OreType oreType;
        private int remainingTicks;
        private int ticksUntilNextPulse;

        private VisibleOreEffect(ServerWorld world, UUID playerUuid, BlockPos blockPos, OreType oreType, int remainingTicks, int ticksUntilNextPulse) {
            this.world = world;
            this.playerUuid = playerUuid;
            this.blockPos = blockPos;
            this.oreType = oreType;
            this.remainingTicks = remainingTicks;
            this.ticksUntilNextPulse = ticksUntilNextPulse;
        }
    }
}