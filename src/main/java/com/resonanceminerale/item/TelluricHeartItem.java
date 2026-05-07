package com.resonanceminerale.item;

import com.resonanceminerale.cooldown.PlayerCooldownManager;
import com.resonanceminerale.detection.OreDetectionResult;
import com.resonanceminerale.detection.OreDetectionService;
import com.resonanceminerale.detection.OreType;
import com.resonanceminerale.visual.OreVisualEffectService;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.util.Formatting;

public class TelluricHeartItem extends Item {
    public static final int COOLDOWN_SECONDS = 3;

    private static final Map<UUID, OreType> PLAYER_TARGETS = new HashMap<>();

    public TelluricHeartItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient()) {
            return TypedActionResult.success(stack, true);
        }

        ServerPlayerEntity player = (ServerPlayerEntity) user;

        if (player.isSneaking()) {
            OreType newTarget = cycleTargetOre(player);
            updateStackRarity(stack, newTarget);

            player.sendMessage(targetChangedMessage(newTarget), true);

            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                    SoundCategory.PLAYERS,
                    0.6F,
                    1.4F
            );

            return TypedActionResult.success(stack, false);
        }

        OreType targetOre = getTargetOre(player);
        updateStackRarity(stack, targetOre);

        if (PlayerCooldownManager.isOnCooldown(player, this)) {
            player.sendMessage(Text.literal("Le Cœur Tellurique récupère son énergie..."), true);
            return TypedActionResult.consume(stack);
        }

        OreDetectionResult result = OreDetectionService.detectNearbyOre(
                world,
                player.getBlockPos(),
                targetOre.detectionRadius(),
                targetOre
        );

        if (result.found()) {
            OreDetectionResult.SignalStrength signalStrength = result.signalStrength();

            player.sendMessage(messageFor(signalStrength, targetOre), true);
            spawnSignalParticles(player, signalStrength);
            OreVisualEffectService.tryStartVisibleOreEffect(player, result);

            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                    SoundCategory.PLAYERS,
                    0.7F,
                    pitchFor(signalStrength)
            );
        } else {
            player.sendMessage(messageFor(OreDetectionResult.SignalStrength.NONE, targetOre), true);
        }

        PlayerCooldownManager.applyCooldown(player, this, COOLDOWN_SECONDS);
        return TypedActionResult.success(stack, false);
    }

    private static OreType getTargetOre(ServerPlayerEntity player) {
        return PLAYER_TARGETS.getOrDefault(player.getUuid(), OreType.COAL);
    }

    private static OreType cycleTargetOre(ServerPlayerEntity player) {
        OreType currentTarget = getTargetOre(player);
        OreType[] availableTargets = OreType.values();

        int nextIndex = (currentTarget.ordinal() + 1) % availableTargets.length;
        OreType nextTarget = availableTargets[nextIndex];

        PLAYER_TARGETS.put(player.getUuid(), nextTarget);
        return nextTarget;
    }

    private static void updateStackRarity(ItemStack stack, OreType oreType) {
        stack.set(DataComponentTypes.RARITY, rarityFor(oreType));
    }

    private static Rarity rarityFor(OreType oreType) {
        return switch (oreType) {
            case COAL, COPPER, IRON -> Rarity.COMMON;
            case GOLD, REDSTONE, LAPIS -> Rarity.UNCOMMON;
            case DIAMOND, EMERALD -> Rarity.RARE;
            case ANCIENT_DEBRIS -> Rarity.EPIC;
        };
    }

    private static Text messageFor(OreDetectionResult.SignalStrength signalStrength, OreType oreType) {
        Formatting oreColor = formattingFor(oreType);

        String message = switch (signalStrength) {
            case STRONG -> "Résonance de " + oreType.displayName() + " très forte...";
            case MEDIUM -> "Résonance de " + oreType.displayName() + " détectée...";
            case WEAK -> "Faible résonance de " + oreType.displayName() + "...";
            case NONE -> "Aucune résonance de " + oreType.displayName() + " proche.";
        };

        return Text.empty()
                .append(Text.literal("⟦ ").formatted(Formatting.DARK_GRAY))
                .append(Text.literal(message).formatted(oreColor))
                .append(Text.literal(" ⟧").formatted(Formatting.DARK_GRAY));
    }

    private static Text targetChangedMessage(OreType oreType) {
        Formatting oreColor = formattingFor(oreType);

        return Text.empty()
                .append(Text.literal("⟦ ").formatted(Formatting.WHITE))
                .append(Text.literal("Cible changée : ").formatted(Formatting.WHITE))
                .append(Text.literal(oreType.displayName()).formatted(oreColor))
                .append(Text.literal(" ⟧").formatted(Formatting.WHITE));
    }

    private static Formatting formattingFor(OreType oreType) {
        return switch (oreType) {
            case COAL, COPPER, IRON -> Formatting.WHITE;
            case GOLD, REDSTONE, LAPIS -> Formatting.YELLOW;
            case DIAMOND, EMERALD -> Formatting.AQUA;
            case ANCIENT_DEBRIS -> Formatting.LIGHT_PURPLE;
        };
    }

    private static float pitchFor(OreDetectionResult.SignalStrength signalStrength) {
        return switch (signalStrength) {
            case STRONG -> 1.2F;
            case MEDIUM -> 0.9F;
            case WEAK -> 0.6F;
            case NONE -> 0.5F;
        };
    }

    private static void spawnSignalParticles(ServerPlayerEntity player, OreDetectionResult.SignalStrength signalStrength) {
        int count = switch (signalStrength) {
            case STRONG -> 40;
            case MEDIUM -> 22;
            case WEAK -> 10;
            case NONE -> 0;
        };

        if (count <= 0) {
            return;
        }

        player.getServerWorld().spawnParticles(
                ParticleTypes.END_ROD,
                player.getX(),
                player.getY() + 1.0D,
                player.getZ(),
                count,
                0.9D,
                0.7D,
                0.9D,
                0.02D
        );
    }
}