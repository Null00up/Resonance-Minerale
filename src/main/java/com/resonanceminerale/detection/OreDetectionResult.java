package com.resonanceminerale.detection;

public record OreDetectionResult(boolean found, OreType oreType, int nearestDistanceSquared) {
    public static OreDetectionResult none(OreType oreType) {
        return new OreDetectionResult(false, oreType, Integer.MAX_VALUE);
    }

    public static OreDetectionResult found(OreType oreType, int nearestDistanceSquared) {
        return new OreDetectionResult(true, oreType, nearestDistanceSquared);
    }
}
