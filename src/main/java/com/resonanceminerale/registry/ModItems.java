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
    public static final Item TELLURIC_HEART = registerTelluricHeart("telluric_heart", 1);
    public static final Item TELLURIC_HEART_TIER_COPPER = registerTelluricHeart("telluric_heart_tier_copper", 2);
    public static final Item TELLURIC_HEART_TIER_IRON = registerTelluricHeart("telluric_heart_tier_iron", 3);
    public static final Item TELLURIC_HEART_TIER_GOLD = registerTelluricHeart("telluric_heart_tier_gold", 4);
    public static final Item TELLURIC_HEART_TIER_REDSTONE = registerTelluricHeart("telluric_heart_tier_redstone", 5);
    public static final Item TELLURIC_HEART_TIER_LAPIS = registerTelluricHeart("telluric_heart_tier_lapis", 6);
    public static final Item TELLURIC_HEART_TIER_DIAMOND = registerTelluricHeart("telluric_heart_tier_diamond", 7);
    public static final Item TELLURIC_HEART_TIER_EMERALD = registerTelluricHeart("telluric_heart_tier_emerald", 8);
    public static final Item TELLURIC_HEART_TIER_ANCIENT_DEBRIS = registerTelluricHeart("telluric_heart_tier_ancient_debris", 9);

    private ModItems() {
    }

    private static Item registerTelluricHeart(String name, int tier) {
        return Registry.register(
                Registries.ITEM,
                Identifier.of(ResonanceMineraleMod.MOD_ID, name),
                new TelluricHeartItem(
                        new Item.Settings()
                                .maxCount(1)
                                .rarity(TelluricHeartItem.rarityForTier(tier)),
                        tier
                )
        );
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(TELLURIC_HEART);
            entries.add(TELLURIC_HEART_TIER_COPPER);
            entries.add(TELLURIC_HEART_TIER_IRON);
            entries.add(TELLURIC_HEART_TIER_GOLD);
            entries.add(TELLURIC_HEART_TIER_REDSTONE);
            entries.add(TELLURIC_HEART_TIER_LAPIS);
            entries.add(TELLURIC_HEART_TIER_DIAMOND);
            entries.add(TELLURIC_HEART_TIER_EMERALD);
            entries.add(TELLURIC_HEART_TIER_ANCIENT_DEBRIS);
        });
    }
}
