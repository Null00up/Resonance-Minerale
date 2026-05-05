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
    public static final int DETECTION_RADIUS = 6;
    public static final int COOLDOWN_SECONDS = 30;

    public TelluricHeartItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient()) {
            return TypedActionResult.success(stack);
        }

        ServerPlayerEntity player = (ServerPlayerEntity) user;

        if (PlayerCooldownManager.isOnCooldown(player, this)) {
            player.sendMessage(Text.literal("Le Cœur Tellurique doit se stabiliser..."), true);
            return TypedActionResult.consume(stack);
        }

        OreDetectionResult result = OreDetectionService.detectNearbyOre(world, player.getBlockPos(), DETECTION_RADIUS, OreType.COAL);

        if (result.found()) {
            player.sendMessage(Text.literal(messageFor(result.signalStrength())), true);
            spawnSignalParticles(player, result.signalStrength());
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.PLAYERS, 0.5F, 0.7F);
        } else {
            player.sendMessage(Text.literal("Aucune résonance minérale proche."), true);
        }

        PlayerCooldownManager.applyCooldown(player, this, COOLDOWN_SECONDS);
        return TypedActionResult.success(stack);
    }

    private static String messageFor(OreDetectionResult.SignalStrength signalStrength) {
        return switch (signalStrength) {
            case STRONG -> "Le Cœur Tellurique vibre fortement...";
            case MEDIUM -> "Le Cœur Tellurique frétille...";
            case WEAK -> "Une faible résonance minérale se fait sentir...";
            case NONE -> "Aucune résonance minérale proche.";
        };
    }

    private static void spawnSignalParticles(ServerPlayerEntity player, OreDetectionResult.SignalStrength signalStrength) {
        int count = switch (signalStrength) {
            case STRONG -> 24;
            case MEDIUM -> 12;
            case WEAK -> 5;
            case NONE -> 0;
        };

        if (count <= 0) {
            return;
        }

        player.getServerWorld().spawnParticles(
                ParticleTypes.END_ROD,
                player.getX(),
                player.getBodyY(0.5D),
                player.getZ(),
                count,
                0.8D,
                0.5D,
                0.8D,
                0.01D
        );
    }
}
