# Instructions pour Codex - Résonance Minérale

## Contexte du projet

Tu travailles sur un mod Minecraft appelé Résonance Minérale.

Le mod ajoute un système de détection progressive des minerais grâce à un objet appelé Cœur Tellurique.

Le but est de créer une mécanique immersive, équilibrée et évolutive.

Le mod ne doit pas devenir un système de X-Ray.

## Priorité actuelle

Créer une première version jouable très simple.

La V1 doit contenir :

- un mod Minecraft Fabric 1.21.x
- un objet nommé Cœur Tellurique
- une activation au clic droit
- une détection du charbon autour du joueur
- un rayon de détection de 6 blocs
- un cooldown de 30 secondes
- un message si du charbon est détecté
- un message discret si aucun minerai n’est détecté
- une architecture de code propre

## Contraintes importantes

### Ne pas faire

- Ne pas afficher directement les minerais à travers les murs.
- Ne pas donner les coordonnées exactes du minerai au joueur.
- Ne pas rendre le pouvoir actif en permanence.
- Ne pas rendre le diamant détectable dès la V1.
- Ne pas créer tout le système final d’un coup si la V1 n’est pas stable.

### Faire

- Garder le code modulaire.
- Séparer la logique de détection de la classe de l’objet.
- Prévoir une structure pour ajouter d’autres minerais plus tard.
- Utiliser des noms de classes clairs.
- Ajouter des commentaires utiles sans surcharger le code.
- Garder une ambiance proche de Minecraft vanilla.
- Préférer une base simple, stable et testable.

## Architecture souhaitée

Créer une structure proche de celle-ci :

src/main/java/.../ResonanceMineraleMod.java  
src/main/java/.../registry/ModItems.java  
src/main/java/.../item/TelluricHeartItem.java  
src/main/java/.../detection/OreDetectionService.java  
src/main/java/.../detection/OreDetectionResult.java  
src/main/java/.../detection/OreType.java  
src/main/java/.../cooldown/PlayerCooldownManager.java  

## Comportement attendu du Cœur Tellurique

Quand le joueur fait clic droit avec l’objet :

1. Vérifier le cooldown.
2. Si le cooldown est actif, informer discrètement le joueur.
3. Si le cooldown est terminé, chercher du charbon dans un rayon de 6 blocs.
4. Si du charbon est trouvé, afficher un message d’ambiance.
5. Si aucun charbon n’est trouvé, afficher un message discret.
6. Appliquer le cooldown.

## Valeurs de la V1

Rayon de détection :

6 blocs

Cooldown :

30 secondes

Minerais détectés :

- coal_ore
- deepslate_coal_ore

Activation :

Clic droit avec le Cœur Tellurique

## Messages proposés

Minerai détecté :

Le Cœur Tellurique frétille faiblement...

Aucun minerai :

Aucune résonance minérale proche.

Cooldown :

Le Cœur Tellurique doit se stabiliser...

## Style de code

- Code Java clair et lisible.
- Éviter les classes trop grandes.
- Utiliser des constantes pour les valeurs importantes.
- Éviter la duplication.
- Séparer les responsabilités.
- Préparer le projet pour des évolutions futures.

## Évolutions à prévoir plus tard

Le code doit pouvoir accueillir plus tard :

- un système de niveaux
- le déblocage progressif des minerais
- les statistiques Puissance, Précision, Stabilité, Endurance et Affinité
- différents signaux selon le minerai
- des sons et particules personnalisés
- une configuration serveur
- une compatibilité avec d’autres mods de minerais
