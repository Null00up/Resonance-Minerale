package com.resonanceminerale.detection;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class OreDetectionService {
    private OreDetectionService() {
    }

    public static OreDetectionResult detectNearbyOre(World world, BlockPos origin, int radius, OreType oreType) {
        int radiusSquared = radius * radius;
        int nearestDistanceSquared = Integer.MAX_VALUE;
        BlockPos nearestOrePos = null;

        for (BlockPos candidate : BlockPos.iterateOutwards(origin, radius, radius, radius)) {
            int distanceSquared = (int) origin.getSquaredDistance(candidate);

            if (distanceSquared > radiusSquared) {
                continue;
            }

            if (oreType.blocks().contains(world.getBlockState(candidate).getBlock()) && distanceSquared < nearestDistanceSquared) {
                nearestDistanceSquared = distanceSquared;
                nearestOrePos = candidate.toImmutable();
            }
        }

        if (nearestOrePos != null) {
            return OreDetectionResult.found(oreType, nearestDistanceSquared, radius, nearestOrePos);
        }

        return OreDetectionResult.none(oreType, radius);
    }
}