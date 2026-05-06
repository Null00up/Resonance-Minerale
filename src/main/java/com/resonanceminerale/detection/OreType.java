package com.resonanceminerale.detection;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.List;

public enum OreType {
    COAL("charbon", 12, Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE),
    COPPER("cuivre", 10, Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE),
    IRON("fer", 8, Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE),
    GOLD("or", 7, Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.NETHER_GOLD_ORE),
    REDSTONE("redstone", 6, Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE),
    LAPIS("lapis", 6, Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE),
    DIAMOND("diamant", 5, Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE),
    EMERALD("émeraude", 5, Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE),
    ANCIENT_DEBRIS("débris antiques", 4, Blocks.ANCIENT_DEBRIS);

    private final String displayName;
    private final int detectionRadius;
    private final List<Block> blocks;

    OreType(String displayName, int detectionRadius, Block... blocks) {
        this.displayName = displayName;
        this.detectionRadius = detectionRadius;
        this.blocks = List.of(blocks);
    }

    public String displayName() {
        return displayName;
    }

    public int detectionRadius() {
        return detectionRadius;
    }

    public List<Block> blocks() {
        return blocks;
    }
}