package com.example.best_recipes.modal

import com.google.gson.annotations.SerializedName

class Area {

    @SerializedName("strArea")
    var nameArea: String? = null


}

class AreaRepository {
    @SerializedName("meals")
    var areas: List<Area>? = null
}
