package com.resonanceminerale.detection;

public record OreDetectionResult(boolean found, OreType oreType, int nearestDistanceSquared) {
    public enum SignalStrength {
        STRONG,
        MEDIUM,
        WEAK,
        NONE
    }

    private static final int STRONG_DISTANCE_SQUARED = 4; // <= 2 blocs
    private static final int MEDIUM_DISTANCE_SQUARED = 16; // <= 4 blocs

    public static OreDetectionResult none(OreType oreType) {
        return new OreDetectionResult(false, oreType, Integer.MAX_VALUE);
    }

    public static OreDetectionResult found(OreType oreType, int nearestDistanceSquared) {
        return new OreDetectionResult(true, oreType, nearestDistanceSquared);
    }

    public SignalStrength signalStrength() {
        if (!found) {
            return SignalStrength.NONE;
        }

        if (nearestDistanceSquared <= STRONG_DISTANCE_SQUARED) {
            return SignalStrength.STRONG;
        }

        if (nearestDistanceSquared <= MEDIUM_DISTANCE_SQUARED) {
            return SignalStrength.MEDIUM;
        }

        return SignalStrength.WEAK;
    }
}
