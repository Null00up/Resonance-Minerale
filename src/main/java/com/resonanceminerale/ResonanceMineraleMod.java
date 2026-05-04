package com.resonanceminerale;

import com.resonanceminerale.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResonanceMineraleMod implements ModInitializer {
    public static final String MOD_ID = "resonance_minerale";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.register();
        LOGGER.info("Résonance Minérale initialisé.");
    }
}
