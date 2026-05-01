# Résonance Minérale

**Résonance Minérale** est un mod Minecraft centré sur un pouvoir progressif de détection des minerais.

Le joueur fabrique un objet spécial appelé **Cœur Tellurique**, qui lui permet de ressentir la présence de certains minerais autour de lui. Au départ, le joueur ne peut détecter que le charbon. Avec le temps, l’objet peut être amélioré pour détecter d’autres minerais et gagner en puissance, précision, stabilité et endurance.

L’objectif est de proposer une mécanique immersive et équilibrée, sans créer un simple système de X-Ray.

## Objectif du mod

Ajouter un système de “sens minéral” permettant au joueur de détecter progressivement les minerais proches grâce à des signaux sonores, visuels ou sensoriels.

## Version cible

- Minecraft : 1.21.x
- Mod loader recommandé : Fabric
- Langage : Java
- Build system : Gradle

## Fonctionnalités V1

La première version doit rester simple et jouable.

### V1 — Prototype initial

- Ajouter un nouvel objet : **Cœur Tellurique**
- Permettre au joueur de détecter le minerai de charbon proche
- Rayon de détection de base : 6 blocs
- Cooldown d’utilisation : 30 secondes
- Signal lorsqu’un minerai est détecté :
  - son discret
  - particules autour du joueur
  - message d’ambiance optionnel
- Ne pas révéler directement l’emplacement exact du bloc
- Préparer le code pour ajouter plus tard :
  - autres minerais
  - système de niveaux
  - améliorations de portée
  - précision du signal
  - stabilité du pouvoir

## Philosophie du gameplay

Le mod ne doit pas donner une vision directe des minerais à travers les murs. Le joueur doit recevoir des indices progressifs, par exemple :

- plus il est proche, plus le signal est fort ;
- le son peut devenir plus fréquent ;
- les particules peuvent apparaître plus souvent ;
- à haut niveau, une direction vague peut être donnée ;
- le bloc exact ne doit être révélé que très tard dans la progression, si cette option est ajoutée.

## Progression prévue

Le joueur commencera avec le charbon, puis pourra débloquer les autres minerais grâce à des crafts ou un système de niveau.

Progression proposée :

```text
Charbon → Cuivre → Fer → Or → Redstone → Lapis → Diamant → Émeraude → Débris antiques
