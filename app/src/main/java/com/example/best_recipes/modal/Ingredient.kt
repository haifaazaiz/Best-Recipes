package com.example.best_recipes.modal

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

class IngredientJson {

    @SerializedName("meals")
    var ingredients: List<Ingredient>?= null

}
data class Ingredient (
    var idIngredient: Int? = null,
    var strIngredient :String? = null,
    var strDescription :String? = null,
    var strType :String? = null
)