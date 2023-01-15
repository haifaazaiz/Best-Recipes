# Best-Recipes
# Les participants :
  Haifa AZAIZ et Sana MASMOUDI
# Gestion github 
On a fait une branche develop et à chaque ajout d'une fonctionalité on utilise les commandes :
  * git flow feature start nom_feature 
  * git flow feature finish nom_feature
# Architecture du projet 
On a décomposé l'application suivant MVC:
* Model :  pour la gestion des données.
* View :  pour la gestion de la disposition et l'affichage ( Les adaptateurs ).
* Controller : contenant les activités de l'application.
# Les Fonctionnalités développées
L'application contient 6 activities : 
* CategoryActivity
  - Affichage de la liste des catégories de recette obtenu par appel de l'api.
* MealActivity 
  - En cliquant sur une catégorie, afficher la liste de ses plats. 
* RecipeActivity
  - Affichage des détails de chaque plat en cliquant sur une recette.
* RandomRecipeActivity
  - Afficher une recette aléatoire.
* IngredientsActivity 
  - Affichage de la liste des ingrédients obtenu par appel de l'api.
  - Faire le filtrage des plats moyennant les ingrédients.
* AreaActivity 
  - Affichage de la liste des Area obtenu par appel de l'api.
  - Faire le filtrage des plats moyennant l'origine(l'area) de chaque recette.

On a développé aussi les fonctionalités suivantes:
  - La barre de recherche pour faciliter la recherche d'une catégorie, d'un plat, d'une recette, d'un ingrédient et d'une area.
  - Gestion des recettes en favori en utilisant Shared Preferences.
