package com.example.best_recipes.modal
import com.google.gson.annotations.SerializedName

class MealRepository {
    var meals: List<Meal>? = null
}
class Meal {
    var idMeal: Int? = null
    @SerializedName("strMeal")
    var mealName : String? = null
    @SerializedName("strMealThumb")
    var mealImage : String? = null
}
