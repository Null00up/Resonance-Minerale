# TODO - Résonance Minérale

## Phase 1 - Initialisation du projet

- [ ] Créer un projet Fabric 1.21.x
- [ ] Configurer Gradle
- [ ] Définir le modid
- [ ] Ajouter les métadonnées du mod
- [ ] Vérifier que Minecraft se lance avec le mod vide

## Phase 2 - Objet principal

- [ ] Créer l’objet Cœur Tellurique
- [ ] Enregistrer l’objet dans le registre Fabric
- [ ] Ajouter le nom français de l’objet
- [ ] Ajouter une texture temporaire
- [ ] Ajouter l’objet dans un groupe créatif

## Phase 3 - Détection du charbon

- [ ] Créer un service de détection des minerais
- [ ] Chercher les blocs autour du joueur dans un rayon de 6 blocs
- [ ] Détecter coal_ore
- [ ] Détecter deepslate_coal_ore
- [ ] Retourner un résultat simple : trouvé ou non trouvé

## Phase 4 - Utilisation de l’objet

- [ ] Déclencher la détection au clic droit
- [ ] Ajouter un cooldown de 30 secondes
- [ ] Empêcher le spam d’utilisation
- [ ] Afficher un message si du charbon est détecté
- [ ] Afficher un message discret si rien n’est détecté

## Phase 5 - Feedback joueur

- [ ] Ajouter un son quand un minerai est détecté
- [ ] Ajouter des particules autour du joueur
- [ ] Faire varier le signal selon la distance si possible
- [ ] Éviter les effets trop intrusifs

## Phase 6 - Recette

- [ ] Ajouter une recette de craft pour le Cœur Tellurique
- [ ] Utiliser charbon, cuivre et améthyste
- [ ] Tester le craft en survie

## Phase 7 - Nettoyage du code

- [ ] Organiser les packages Java
- [ ] Commenter les classes importantes
- [ ] Vérifier les logs
- [ ] Corriger les warnings simples
- [ ] Préparer le code pour les futures améliorations

## Phase 8 - Évolutions futures

- [ ] Ajouter le cuivre comme minerai détectable
- [ ] Ajouter le fer comme minerai détectable
- [ ] Ajouter l’or comme minerai détectable
- [ ] Ajouter la redstone comme minerai détectable
- [ ] Ajouter le lapis comme minerai détectable
- [ ] Ajouter le diamant comme minerai détectable
- [ ] Ajouter l’émeraude comme minerai détectable
- [ ] Ajouter les débris antiques comme minerai détectable
- [ ] Ajouter un système de niveaux
- [ ] Ajouter la statistique Puissance
- [ ] Ajouter la statistique Précision
- [ ] Ajouter la statistique Stabilité
- [ ] Ajouter la statistique Endurance
- [ ] Ajouter la statistique Affinité
- [ ] Ajouter une interface d’amélioration
- [ ] Ajouter une configuration serveur
- [ ] Ajouter des options d’équilibrage
