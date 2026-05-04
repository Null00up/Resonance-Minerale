package com.resonanceminerale.cooldown;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public final class PlayerCooldownManager {
    private PlayerCooldownManager() {
    }

    public static boolean isOnCooldown(PlayerEntity player, Item item) {
        return player.getItemCooldownManager().isCoolingDown(item);
    }

    public static void applyCooldown(PlayerEntity player, Item item, int cooldownSeconds) {
        player.getItemCooldownManager().set(item, cooldownSeconds * 20);
    }
}
