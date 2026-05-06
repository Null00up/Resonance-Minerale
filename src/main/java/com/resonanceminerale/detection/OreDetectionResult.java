package com.resonanceminerale.detection;

public record OreDetectionResult(
        boolean found,
        OreType oreType,
        int nearestDistanceSquared,
        int detectionRadius
) {
    public enum SignalStrength {
        STRONG,
        MEDIUM,
        WEAK,
        NONE
    }

    public static OreDetectionResult none(OreType oreType, int detectionRadius) {
        return new OreDetectionResult(false, oreType, Integer.MAX_VALUE, detectionRadius);
    }

    public static OreDetectionResult found(OreType oreType, int nearestDistanceSquared, int detectionRadius) {
        return new OreDetectionResult(true, oreType, nearestDistanceSquared, detectionRadius);
    }

    public SignalStrength signalStrength() {
        if (!found) {
            return SignalStrength.NONE;
        }

        int strongLimit = Math.max(4, (detectionRadius * detectionRadius) / 9);
        int mediumLimit = Math.max(16, (detectionRadius * detectionRadius) / 3);

        if (nearestDistanceSquared <= strongLimit) {
            return SignalStrength.STRONG;
        }

        if (nearestDistanceSquared <= mediumLimit) {
            return SignalStrength.MEDIUM;
        }

        return SignalStrength.WEAK;
    }
}