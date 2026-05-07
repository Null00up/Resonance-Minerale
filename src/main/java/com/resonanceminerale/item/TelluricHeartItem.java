package com.resonanceminerale.item;

import com.resonanceminerale.cooldown.PlayerCooldownManager;
import com.resonanceminerale.detection.OreDetectionResult;
import com.resonanceminerale.detection.OreDetectionService;
import com.resonanceminerale.detection.OreType;
import com.resonanceminerale.visual.OreVisualEffectService;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
            player.sendMessage(Text.literal("Cible changée : " + newTarget.displayName()), true);

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

        if (PlayerCooldownManager.isOnCooldown(player, this)) {
            player.sendMessage(Text.literal("Le Cœur Tellurique doit se stabiliser..."), true);
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

            player.sendMessage(Text.literal(messageFor(signalStrength, targetOre)), true);
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
            player.sendMessage(Text.literal("Aucune résonance de " + targetOre.displayName() + " proche."), true);
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

    private static String messageFor(OreDetectionResult.SignalStrength signalStrength, OreType oreType) {
        return switch (signalStrength) {
            case STRONG -> "Résonance de " + oreType.displayName() + " très forte...";
            case MEDIUM -> "Résonance de " + oreType.displayName() + " détectée...";
            case WEAK -> "Faible résonance de " + oreType.displayName() + "...";
            case NONE -> "Aucune résonance de " + oreType.displayName() + " proche.";
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