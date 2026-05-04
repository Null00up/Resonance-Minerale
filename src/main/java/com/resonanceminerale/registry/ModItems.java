package com.resonanceminerale.registry;

import com.resonanceminerale.ResonanceMineraleMod;
import com.resonanceminerale.item.TelluricHeartItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModItems {
    public static final Item TELLURIC_HEART = Registry.register(
            Registries.ITEM,
            Identifier.of(ResonanceMineraleMod.MOD_ID, "telluric_heart"),
            new TelluricHeartItem(new Item.Settings().maxCount(1))
    );

    private ModItems() {
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(TELLURIC_HEART));
    }
}
