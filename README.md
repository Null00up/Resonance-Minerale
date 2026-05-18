# Résonance Minérale

**Résonance Minérale** is a Minecraft mod created by **Trys**.

**Résonance Minérale** est un mod Minecraft centré sur un pouvoir progressif de détection des minerais.

Le joueur fabrique un objet spécial appelé **Cœur Tellurique**, qui lui permet de ressentir la présence de certains minerais autour de lui. Au départ, le joueur ne peut détecter que le charbon. Avec le temps, l’objet peut être amélioré pour détecter d’autres minerais et gagner en puissance, précision, stabilité et endurance.

L’objectif est de proposer une mécanique immersive et équilibrée, sans créer un simple système de X-Ray.

---

## Objectif du mod

Ajouter un système de **sens minéral** permettant au joueur de détecter progressivement les minerais proches grâce à des signaux sonores, visuels et sensoriels.

Le mod doit donner au joueur des indices sur la présence d’un minerai, sans lui révéler directement tous les blocs cachés autour de lui.

---

## Version cible

- Minecraft : **1.21.1**
- Mod loader recommandé : **Fabric**
- Langage : **Java**
- Version Java recommandée : **Java 21**
- Build system : **Gradle**
- Fabric Loom : **1.7.4**

---

## Fonctionnalités V1

La V1 transforme le **Cœur Tellurique** en un objet progressif, avec plusieurs niveaux d’amélioration.

### V1 — Progression du Cœur Tellurique

- Ajout du **Cœur Tellurique**.
- Détection progressive des minerais.
- Le joueur commence avec le charbon uniquement.
- Les autres minerais se débloquent via des crafts d’amélioration.
- Le changement de cible se fait avec **Shift + clic droit**.
- Le clic droit normal lance la détection du minerai sélectionné.
- Le cooldown d’utilisation est de **20 secondes**.
- L’objet applique des malus légers pour éviter qu’il soit trop puissant.
- Les textures changent selon le minerai sélectionné.
- Les sons vanilla et les particules renforcent l’effet de résonance.
- Le système est pensé pour éviter un comportement de type X-Ray.
- Les sons custom sont mis de côté pour la V1 afin de garder une version simple et stable.
- Les messages français sont sécurisés pour éviter les problèmes d’encodage.
- Les tooltips affichent le niveau, la cible, les minerais débloqués, la portée, le cooldown et les contrôles.

---

## État actuel — V1 stabilisée

La V1 actuelle est fonctionnelle et validée en jeu :

- crafts V1 testés et fonctionnels ;
- progression par niveaux fonctionnelle ;
- changement de cible limité selon le niveau du Cœur ;
- cooldown réglé à **20 secondes** ;
- messages français corrigés et sécurisés ;
- tooltips améliorés ;
- sons vanilla conservés ;
- sons custom mis de côté pour une future version ;
- malus conservés tels quels ;
- build Gradle fonctionnel ;
- test en jeu fonctionnel.

## Contrôles

| Action | Effet |
|---|---|
| Clic droit | Lance une détection du minerai sélectionné |
| Shift + clic droit | Change le minerai ciblé parmi ceux débloqués |
| Changement de niveau | Débloque un nouveau minerai ciblable |

---

## Progression des minerais

Le joueur commence avec le charbon, puis améliore le Cœur Tellurique étape par étape.

```text
Charbon → Cuivre → Fer → Or → Redstone → Lapis → Diamant → Émeraude → Débris antiques
```

| Niveau | Item interne | Minerais disponibles |
|---:|---|---|
| 1 | `telluric_heart` | Charbon |
| 2 | `telluric_heart_tier_copper` | Charbon, Cuivre |
| 3 | `telluric_heart_tier_iron` | Charbon, Cuivre, Fer |
| 4 | `telluric_heart_tier_gold` | Charbon, Cuivre, Fer, Or |
| 5 | `telluric_heart_tier_redstone` | Charbon, Cuivre, Fer, Or, Redstone |
| 6 | `telluric_heart_tier_lapis` | Charbon, Cuivre, Fer, Or, Redstone, Lapis |
| 7 | `telluric_heart_tier_diamond` | Charbon, Cuivre, Fer, Or, Redstone, Lapis, Diamant |
| 8 | `telluric_heart_tier_emerald` | Charbon, Cuivre, Fer, Or, Redstone, Lapis, Diamant, Émeraude |
| 9 | `telluric_heart_tier_ancient_debris` | Tous les minerais, y compris les débris antiques |

---

## Rayons de détection

| Minerai | Distance maximale |
|---|---:|
| Charbon | 12 blocs |
| Cuivre | 10 blocs |
| Fer | 8 blocs |
| Or | 7 blocs |
| Redstone | 6 blocs |
| Lapis | 6 blocs |
| Diamant | 5 blocs |
| Émeraude | 5 blocs |
| Débris antiques | 4 blocs |

---

## Recette de base

### Cœur Tellurique — Charbon

```text
Charbon                 Bloc de spéléothème      Charbon
Bloc de spéléothème     Éclat d’améthyste        Bloc de spéléothème
Charbon                 Bloc de spéléothème      Charbon
```

Résultat :

```text
resonance_minerale:telluric_heart
```

---

## Recettes d’amélioration

Toutes les améliorations suivent le même modèle :

```text
Minerai/Lingot          Bloc de spéléothème      Minerai/Lingot
Bloc de spéléothème     Cœur précédent           Bloc de spéléothème
Minerai/Lingot          Bloc de spéléothème      Minerai/Lingot
```

### Amélioration Cuivre

```text
Lingot de cuivre        Bloc de spéléothème      Lingot de cuivre
Bloc de spéléothème     Cœur Tellurique Charbon  Bloc de spéléothème
Lingot de cuivre        Bloc de spéléothème      Lingot de cuivre
```

Résultat : `resonance_minerale:telluric_heart_tier_copper`

### Amélioration Fer

```text
Lingot de fer           Bloc de spéléothème      Lingot de fer
Bloc de spéléothème     Cœur Tellurique Cuivre   Bloc de spéléothème
Lingot de fer           Bloc de spéléothème      Lingot de fer
```

Résultat : `resonance_minerale:telluric_heart_tier_iron`

### Amélioration Or

```text
Lingot d’or             Bloc de spéléothème      Lingot d’or
Bloc de spéléothème     Cœur Tellurique Fer      Bloc de spéléothème
Lingot d’or             Bloc de spéléothème      Lingot d’or
```

Résultat : `resonance_minerale:telluric_heart_tier_gold`

### Amélioration Redstone

```text
Redstone                Bloc de spéléothème      Redstone
Bloc de spéléothème     Cœur Tellurique Or       Bloc de spéléothème
Redstone                Bloc de spéléothème      Redstone
```

Résultat : `resonance_minerale:telluric_heart_tier_redstone`

### Amélioration Lapis

```text
Lapis-lazuli            Bloc de spéléothème      Lapis-lazuli
Bloc de spéléothème     Cœur Tellurique Redstone Bloc de spéléothème
Lapis-lazuli            Bloc de spéléothème      Lapis-lazuli
```

Résultat : `resonance_minerale:telluric_heart_tier_lapis`

### Amélioration Diamant

```text
Diamant                 Bloc de spéléothème      Diamant
Bloc de spéléothème     Cœur Tellurique Lapis    Bloc de spéléothème
Diamant                 Bloc de spéléothème      Diamant
```

Résultat : `resonance_minerale:telluric_heart_tier_diamond`

### Amélioration Émeraude

```text
Émeraude                Bloc de spéléothème      Émeraude
Bloc de spéléothème     Cœur Tellurique Diamant  Bloc de spéléothème
Émeraude                Bloc de spéléothème      Émeraude
```

Résultat : `resonance_minerale:telluric_heart_tier_emerald`

### Amélioration Débris antiques

```text
Débris antiques         Bloc de spéléothème      Débris antiques
Bloc de spéléothème     Cœur Tellurique Émeraude Bloc de spéléothème
Débris antiques         Bloc de spéléothème      Débris antiques
```

Résultat : `resonance_minerale:telluric_heart_tier_ancient_debris`

---

## Philosophie du gameplay

Le mod ne doit pas donner une vision directe des minerais à travers les murs. Le joueur doit recevoir des indices progressifs, par exemple :

- plus il est proche, plus le signal est fort ;
- le son peut devenir plus présent ;
- les particules peuvent apparaître autour du joueur ou autour d’un minerai visible ;
- les messages doivent rester immersifs ;
- le bloc exact ne doit pas être révélé gratuitement à travers les murs.

L’idée principale est de créer une sensation de **résonance minérale**, pas un outil de triche.

---

## Équilibrage

Pour éviter que le Cœur Tellurique soit trop puissant :

- l’objet possède un cooldown de **20 secondes** ;
- les minerais rares ont une portée plus courte ;
- des malus sont appliqués après utilisation ;
- les minerais puissants nécessitent plusieurs améliorations successives ;
- le joueur ne peut pas sélectionner un minerai non débloqué.

---

## Commandes de test

```mcfunction
/give @p resonance_minerale:telluric_heart
/give @p resonance_minerale:telluric_heart_tier_copper
/give @p resonance_minerale:telluric_heart_tier_iron
/give @p resonance_minerale:telluric_heart_tier_gold
/give @p resonance_minerale:telluric_heart_tier_redstone
/give @p resonance_minerale:telluric_heart_tier_lapis
/give @p resonance_minerale:telluric_heart_tier_diamond
/give @p resonance_minerale:telluric_heart_tier_emerald
/give @p resonance_minerale:telluric_heart_tier_ancient_debris
```

---

## Installation en développement

Lancer le client de test :

```bash
./gradlew runClient
```

Sous Windows :

```bat
gradlew runClient
```

Construire le fichier `.jar` :

```bash
./gradlew build
```

Sous Windows :

```bat
gradlew build
```

Le fichier `.jar` final se trouve ensuite dans :

```text
build/libs/
```

Pour une installation classique dans Minecraft, utiliser le fichier `.jar` sans `-dev`.

---

## Installation en jeu

Pour tester le mod en dehors de l’environnement de développement :

- installer **Minecraft 1.21.1** ;
- installer **Fabric Loader** ;
- installer **Fabric API** compatible Minecraft 1.21.1 ;
- placer le fichier `.jar` du mod dans le dossier `mods`.

Exemple :

```text
.minecraft/mods/
├── fabric-api-...-1.21.1.jar
└── resonance-minerale-0.1.0.jar
```

---

## Author

Created by **Trys**.

---

## Copyright

© 2026 Trys. All rights reserved.

This mod, including its code, textures, assets, concepts, and associated files, is the property of its original author, published under the pseudonym Trys.

You are allowed to download and use this mod for personal gameplay only.

You are not allowed to redistribute, modify, publish, reuse, or claim this mod or any part of it as your own without permission.

---

## Disclaimer

This project is not an official Minecraft product.  
It is not approved by or associated with Mojang, Microsoft, or Fabric.
