package com.resonanceminerale.item;

import com.resonanceminerale.cooldown.PlayerCooldownManager;
import com.resonanceminerale.detection.OreDetectionResult;
import com.resonanceminerale.detection.OreDetectionService;
import com.resonanceminerale.detection.OreType;
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

public class TelluricHeartItem extends Item {
    public static final int COOLDOWN_SECONDS = 3;

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
        OreType targetOre = OreType.COAL;

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
            player.sendMessage(Text.literal("Aucune résonance minérale proche."), true);
        }

        PlayerCooldownManager.applyCooldown(player, this, COOLDOWN_SECONDS);
        return TypedActionResult.success(stack, false);
    }

    private static String messageFor(OreDetectionResult.SignalStrength signalStrength, OreType oreType) {
        return switch (signalStrength) {
            case STRONG -> "Résonance de " + oreType.displayName() + " très forte...";
            case MEDIUM -> "Résonance de " + oreType.displayName() + " détectée...";
            case WEAK -> "Faible résonance de " + oreType.displayName() + "...";
            case NONE -> "Aucune résonance minérale proche.";
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