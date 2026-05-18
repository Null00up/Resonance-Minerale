package com.resonanceminerale.registry;

import com.resonanceminerale.ResonanceMineraleMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class ModSounds {
    public static final SoundEvent RESONANCE_COAL = register("telluric.resonance.coal");
    public static final SoundEvent RESONANCE_COPPER = register("telluric.resonance.copper");
    public static final SoundEvent RESONANCE_IRON = register("telluric.resonance.iron");
    public static final SoundEvent RESONANCE_GOLD = register("telluric.resonance.gold");
    public static final SoundEvent RESONANCE_REDSTONE = register("telluric.resonance.redstone");
    public static final SoundEvent RESONANCE_LAPIS = register("telluric.resonance.lapis");
    public static final SoundEvent RESONANCE_DIAMOND = register("telluric.resonance.diamond");
    public static final SoundEvent RESONANCE_EMERALD = register("telluric.resonance.emerald");
    public static final SoundEvent RESONANCE_ANCIENT_DEBRIS = register("telluric.resonance.ancient_debris");

    private ModSounds() {
    }

    public static void register() {
        ResonanceMineraleMod.LOGGER.info("Registering sounds for {}", ResonanceMineraleMod.MOD_ID);
    }

    private static SoundEvent register(String name) {
        Identifier id = Identifier.of(ResonanceMineraleMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}