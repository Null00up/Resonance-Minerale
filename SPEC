# Spécifications du mod Résonance Minérale

## 1. Objectif général

Créer un mod Minecraft dans lequel le joueur peut développer un sens minéral.

Ce sens permet de détecter la présence de minerais proches grâce à un objet spécial appelé Cœur Tellurique.

Le mod doit être utile pour le minage, mais il ne doit pas devenir un simple outil de triche ou de X-Ray.

## 2. Objet principal

Nom de l’objet :

Cœur Tellurique

Description :

Un artefact minéral capable de résonner avec les minerais enfouis dans la roche.

## 3. Fonctionnement de la V1

Quand le joueur utilise le Cœur Tellurique avec un clic droit :

1. Le mod vérifie si le joueur est en cooldown.
2. Si le cooldown est actif, le joueur reçoit un message discret.
3. Si le cooldown est terminé, le mod cherche du minerai de charbon autour du joueur.
4. Si du charbon est trouvé, le joueur reçoit un signal.
5. Si aucun charbon n’est trouvé, le joueur reçoit un message discret.
6. Le cooldown est appliqué.

## 4. Valeurs de la V1

Rayon de détection :

6 blocs

Cooldown :

30 secondes

Minerais détectés :

- coal_ore
- deepslate_coal_ore

Activation :

Clic droit avec le Cœur Tellurique

## 5. Messages proposés

Quand du charbon est détecté :

Le Cœur Tellurique frétille faiblement...

Quand aucun minerai n’est détecté :

Aucune résonance minérale proche.

Quand le cooldown est actif :

Le Cœur Tellurique doit se stabiliser...

## 6. Règles importantes

Le mod ne doit pas :

- afficher les minerais à travers les murs
- donner les coordonnées exactes du minerai
- rendre le diamant détectable dès le début
- rendre le pouvoir actif en permanence en V1
- supprimer l’intérêt de l’exploration et du minage

Le mod doit :

- rester équilibré
- être immersif
- utiliser des signaux progressifs
- être évolutif
- permettre d’ajouter facilement d’autres minerais plus tard

## 7. Architecture souhaitée du code

Le code doit être organisé proprement.

Classes possibles :

- ResonanceMineraleMod.java
- ModItems.java
- TelluricHeartItem.java
- OreDetectionService.java
- OreDetectionResult.java
- OreType.java
- PlayerCooldownManager.java

## 8. Rôle des classes

### ResonanceMineraleMod.java

Classe principale du mod.

Elle initialise les objets, les registres et les éléments principaux.

### ModItems.java

Classe utilisée pour enregistrer les objets du mod.

Elle doit contenir l’enregistrement du Cœur Tellurique.

### TelluricHeartItem.java

Classe de l’objet Cœur Tellurique.

Elle gère l’utilisation de l’objet quand le joueur fait clic droit.

### OreDetectionService.java

Classe responsable de la recherche des minerais autour du joueur.

La logique de détection doit être placée ici pour éviter de tout mettre dans la classe de l’objet.

### OreDetectionResult.java

Classe qui représente le résultat d’une détection.

Elle peut contenir :

- si un minerai a été trouvé
- le type de minerai trouvé
- la distance approximative
- éventuellement la position interne du minerai, sans l’afficher directement au joueur

### OreType.java

Enum représentant les types de minerais détectables.

Exemples :

- COAL
- COPPER
- IRON
- GOLD
- REDSTONE
- LAPIS
- DIAMOND
- EMERALD
- ANCIENT_DEBRIS

### PlayerCooldownManager.java

Classe responsable de la gestion du cooldown par joueur.

## 9. Progression future

Le Cœur Tellurique pourra évoluer avec un système de niveaux.

Progression proposée :

| Niveau | Minerais détectables | Rayon |
|---:|---|---:|
| 1 | Charbon | 6 blocs |
| 2 | Charbon, Cuivre | 8 blocs |
| 3 | Charbon, Cuivre, Fer | 10 blocs |
| 4 | Or, Redstone | 12 blocs |
| 5 | Lapis | 14 blocs |
| 6 | Diamant | 16 blocs |
| 7 | Émeraude | 18 blocs |
| 8 | Débris antiques | 20 blocs |

## 10. Statistiques futures

### Puissance

Augmente la portée du pouvoir.

### Précision

Améliore la qualité du signal.

Exemples :

- niveau faible : simple message
- niveau moyen : signal plus fort si le minerai est proche
- niveau élevé : direction vague
- niveau très élevé : particules orientées

### Stabilité

Réduit les faux signaux et le cooldown.

### Endurance

Permet d’utiliser le pouvoir plus souvent ou plus longtemps.

### Affinité

Débloque de nouveaux minerais détectables.

## 11. Craft proposé

Craft possible du Cœur Tellurique :

- Charbon
- Cuivre
- Améthyste

Idée de recette :

Ligne 1 : Charbon, Cuivre, Charbon  
Ligne 2 : Cuivre, Améthyste, Cuivre  
Ligne 3 : Charbon, Cuivre, Charbon  

Résultat :

Cœur Tellurique

## 12. Ambiance du mod

Le mod doit avoir une ambiance :

- minérale
- mystérieuse
- magique légère
- vanilla-friendly
- pas trop flashy

## 13. Effets possibles selon les minerais

Charbon :

- son grave discret
- particules grises ou noires
- signal faible

Cuivre :

- tintement métallique
- particules orangées

Fer :

- vibration lourde
- particules grises

Diamant :

- son cristallin
- particules bleutées

Débris antiques :

- grondement profond
- particules sombres et rouges
