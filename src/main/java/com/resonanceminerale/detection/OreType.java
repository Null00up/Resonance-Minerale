package com.resonanceminerale.detection;

import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum OreType {
    COAL(Set.of(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE));

    private final Set<Block> blocks;

    OreType(Set<Block> blocks) {
        this.blocks = blocks;
    }

    public Set<Block> blocks() {
        return blocks;
    }
}
