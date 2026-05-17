# Patch V1 — Résonance Minérale

Ce patch prépare une V1 plus propre du Cœur Tellurique.

## Ce que ça corrige

- Cooldown remis à 30 secondes.
- Progression en 9 niveaux.
- Shift + clic droit limité aux minerais débloqués par le niveau du Cœur.
- Recettes d'amélioration ajoutées.
- Tooltip ajoutée sur l'item.
- Modèles JSON nettoyés en UTF-8 sans BOM.
- Nouveaux items internes pour empêcher de sauter directement au diamant ou aux débris antiques.

## Progression

| Niveau | Item interne | Minerais disponibles |
|---:|---|---|
| 1 | telluric_heart | Charbon |
| 2 | telluric_heart_tier_copper | Charbon, Cuivre |
| 3 | telluric_heart_tier_iron | Charbon, Cuivre, Fer |
| 4 | telluric_heart_tier_gold | jusqu'à Or |
| 5 | telluric_heart_tier_redstone | jusqu'à Redstone |
| 6 | telluric_heart_tier_lapis | jusqu'à Lapis |
| 7 | telluric_heart_tier_diamond | jusqu'à Diamant |
| 8 | telluric_heart_tier_emerald | jusqu'à Émeraude |
| 9 | telluric_heart_tier_ancient_debris | jusqu'aux Débris antiques |

## À copier dans ton projet

Copie le contenu de ce dossier dans la racine de ton projet Fabric, en remplaçant les fichiers existants quand Windows te le demande.

Ensuite lance :

```bash
./gradlew runClient
```

ou sous Windows :

```bat
gradlew runClient
```

## Test rapide en jeu

1. Crée le Cœur Tellurique de base.
2. Vérifie qu'il ne cible que le charbon.
3. Craft l'amélioration cuivre.
4. Vérifie que Shift + clic droit alterne seulement entre charbon et cuivre.
5. Continue avec fer, or, redstone, lapis, diamant, émeraude, débris antiques.
