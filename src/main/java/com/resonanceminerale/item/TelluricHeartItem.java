package com.resonanceminerale.item;

import com.resonanceminerale.cooldown.PlayerCooldownManager;
import com.resonanceminerale.detection.OreDetectionResult;
import com.resonanceminerale.detection.OreDetectionService;
import com.resonanceminerale.detection.OreType;
import com.resonanceminerale.visual.OreVisualEffectService;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import com.resonanceminerale.registry.ModSounds;
import net.minecraft.sound.SoundEvent;

import java.util.List;

public class TelluricHeartItem extends Item {
    private static final int COOLDOWN_SECONDS = 20;
    private static final float TARGET_CHANGE_SOUND_VOLUME = 1.0F;
    private static final float RESONANCE_SOUND_VOLUME = 1.5F;

    private final int maxTier;

    public TelluricHeartItem(Settings settings, int maxTier) {
        super(settings);
        this.maxTier = Math.max(1, Math.min(maxTier, OreType.values().length));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (world.isClient()) {
            return TypedActionResult.success(stack, true);
        }

        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

        if (serverPlayer.isSneaking()) {
            OreType nextOre = cycleTargetOre(stack);
            serverPlayer.sendMessage(targetChangedMessage(nextOre), true);

            world.playSound(
                    null,
                    serverPlayer.getX(),
                    serverPlayer.getY(),
                    serverPlayer.getZ(),
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                    SoundCategory.PLAYERS,
                    TARGET_CHANGE_SOUND_VOLUME,
                    1.4F
            );

            return TypedActionResult.success(stack, false);
        }

        OreType targetOre = getTargetOre(stack);
        updateStackVisuals(stack, targetOre);

        if (PlayerCooldownManager.isOnCooldown(serverPlayer, this)) {
            serverPlayer.sendMessage(Text.literal("Le CÅ“ur Tellurique rÃ©cupÃ¨re son Ã©nergie..."), true);
            return TypedActionResult.pass(stack);
        }

        OreDetectionResult result = OreDetectionService.detectNearbyOre(
                world,
                serverPlayer.getBlockPos(),
                targetOre.detectionRadius(),
                targetOre
        );

        if (result.found()) {
            OreDetectionResult.SignalStrength signalStrength = result.signalStrength();

            serverPlayer.sendMessage(messageFor(signalStrength, targetOre), true);
            spawnSignalParticles(serverPlayer, signalStrength);
            OreVisualEffectService.tryStartVisibleOreEffect(serverPlayer, result);
            applyResonancePenalty(serverPlayer, targetOre);

            world.playSound(
                    null,
                    serverPlayer.getX(),
                    serverPlayer.getY(),
                    serverPlayer.getZ(),
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                    SoundCategory.PLAYERS,
                    RESONANCE_SOUND_VOLUME,
                    pitchFor(signalStrength)
            );
        } else {
            serverPlayer.sendMessage(messageFor(OreDetectionResult.SignalStrength.NONE, targetOre), true);
        }

        PlayerCooldownManager.applyCooldown(serverPlayer, this, COOLDOWN_SECONDS);
        return TypedActionResult.success(stack, false);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        OreType targetOre = getTargetOre(stack);
        OreType maxOre = maxUnlockedOre();

        tooltip.add(Text.literal("Niveau : ")
                .formatted(Formatting.GRAY)
                .append(Text.literal(maxOre.displayName()).formatted(formattingFor(maxOre))));

        tooltip.add(Text.literal("Cible actuelle : ")
                .formatted(Formatting.GRAY)
                .append(Text.literal(targetOre.displayName()).formatted(formattingFor(targetOre))));

        tooltip.add(Text.literal("Minerais d\u00E9bloqu\u00E9s : ")
                .formatted(Formatting.DARK_GRAY)
                .append(Text.literal(unlockedOresText()).formatted(Formatting.GRAY)));

        tooltip.add(Text.literal("Port\u00E9e de la cible : ")
                .formatted(Formatting.DARK_GRAY)
                .append(Text.literal(targetOre.detectionRadius() + " blocs").formatted(Formatting.AQUA)));

        tooltip.add(Text.literal("Cooldown : ")
                .formatted(Formatting.DARK_GRAY)
                .append(Text.literal(COOLDOWN_SECONDS + " secondes").formatted(Formatting.YELLOW)));

        tooltip.add(Text.literal("Clic droit : sonder les environs").formatted(Formatting.DARK_GRAY));
        tooltip.add(Text.literal("Shift + clic droit : changer de minerai").formatted(Formatting.DARK_GRAY));
    }

    private String unlockedOresText() {
        StringBuilder builder = new StringBuilder();

        OreType[] ores = OreType.values();

        for (int i = 0; i < maxTier && i < ores.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }

            builder.append(ores[i].displayName());
        }

        return builder.toString();
    }

    private OreType getTargetOre(ItemStack stack) {
        CustomModelDataComponent modelData = stack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
        int modelValue = modelData == null ? 1 : modelData.value();
        int index = modelValue - 1;

        OreType[] ores = OreType.values();

        if (index < 0 || index >= ores.length) {
            return OreType.COAL;
        }

        OreType ore = ores[index];
        return isUnlocked(ore) ? ore : OreType.COAL;
    }

    private OreType cycleTargetOre(ItemStack stack) {
        OreType currentOre = getTargetOre(stack);
        int nextIndex = currentOre.ordinal() + 1;

        if (nextIndex >= maxTier) {
            nextIndex = 0;
        }

        OreType nextOre = OreType.values()[nextIndex];
        updateStackVisuals(stack, nextOre);
        return nextOre;
    }

    private boolean isUnlocked(OreType oreType) {
        return oreType.ordinal() < maxTier;
    }

    private OreType maxUnlockedOre() {
        return OreType.values()[maxTier - 1];
    }

    private void updateStackVisuals(ItemStack stack, OreType oreType) {
        updateStackRarity(stack, maxUnlockedOre());
        updateItemSkin(stack, oreType);
    }

    private static void updateStackRarity(ItemStack stack, OreType maxUnlockedOre) {
        stack.set(DataComponentTypes.RARITY, rarityFor(maxUnlockedOre));
    }

    public static Rarity rarityForTier(int tier) {
        OreType[] ores = OreType.values();
        int safeTier = Math.max(1, Math.min(tier, ores.length));
        return rarityFor(ores[safeTier - 1]);
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
            case STRONG -> "R\u00E9sonance de " + oreType.displayName() + " tr\u00E8s forte...";
            case MEDIUM -> "R\u00E9sonance de " + oreType.displayName() + " d\u00E9tect\u00E9e...";
            case WEAK -> "Faible r\u00E9sonance de " + oreType.displayName() + "...";
            case NONE -> "Aucune r\u00E9sonance de " + oreType.displayName() + " proche.";
        };

        return Text.empty()
                .append(Text.literal("⟦ ").formatted(Formatting.DARK_GRAY))
                .append(Text.literal(message).formatted(oreColor))
                .append(Text.literal(" ⟧").formatted(Formatting.DARK_GRAY));
    }

    private static Text targetChangedMessage(OreType oreType) {
        Formatting oreColor = formattingFor(oreType);

        return Text.empty()
                .append(Text.literal("âŸ¦ ").formatted(Formatting.GRAY))
                .append(Text.literal("Cible changÃ©e : ").formatted(Formatting.GRAY))
                .append(Text.literal(oreType.displayName()).formatted(oreColor))
                .append(Text.literal(" âŸ§").formatted(Formatting.GRAY));
    }

    private static Formatting formattingFor(OreType oreType) {
        return switch (oreType) {
            case COAL, COPPER, IRON -> Formatting.GRAY;
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
                ParticleTypes.ENCHANT,
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

    private static void applyResonancePenalty(ServerPlayerEntity player, OreType oreType) {
        switch (oreType) {
            case COAL, COPPER, IRON -> {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 60, 0));
                player.getHungerManager().addExhaustion(0.5F);
            }
            case GOLD, REDSTONE, LAPIS -> {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 100, 0));
                player.getHungerManager().addExhaustion(1.0F);
            }
            case DIAMOND, EMERALD -> {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 140, 0));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0));
                player.getHungerManager().addExhaustion(1.5F);
            }
            case ANCIENT_DEBRIS -> {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 200, 1));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 0));
                player.getHungerManager().addExhaustion(2.0F);
            }
        }
    }

        private static void updateItemSkin(ItemStack stack, OreType oreType) {
        int customModelData = switch (oreType) {
            case COAL -> 1;
            case COPPER -> 2;
            case IRON -> 3;
            case GOLD -> 4;
            case REDSTONE -> 5;
            case LAPIS -> 6;
            case DIAMOND -> 7;
            case EMERALD -> 8;
            case ANCIENT_DEBRIS -> 9;
        };

        stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(customModelData));
    }

    private static SoundEvent soundFor(OreType oreType) {
        return switch (oreType) {
            case COAL -> ModSounds.RESONANCE_COAL;
            case COPPER -> ModSounds.RESONANCE_COPPER;
            case IRON -> ModSounds.RESONANCE_IRON;
            case GOLD -> ModSounds.RESONANCE_GOLD;
            case REDSTONE -> ModSounds.RESONANCE_REDSTONE;
            case LAPIS -> ModSounds.RESONANCE_LAPIS;
            case DIAMOND -> ModSounds.RESONANCE_DIAMOND;
            case EMERALD -> ModSounds.RESONANCE_EMERALD;
            case ANCIENT_DEBRIS -> ModSounds.RESONANCE_ANCIENT_DEBRIS;
        };
    }
}

