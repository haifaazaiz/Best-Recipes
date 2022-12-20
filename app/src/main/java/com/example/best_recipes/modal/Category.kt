package com.example.best_recipes.modal
import com.google.gson.annotations.SerializedName
class Category {

    var idCategory: Int? = null
    @SerializedName("strCategory")
    var nameCategory: String? = null
    @SerializedName("strCategoryThumb")
    var urlPictureCategory: String? = null
    @SerializedName("strCategoryDescription")
    var descriptionCategory: String? = null
    var checkBox: Boolean?=null

}

class CategoryRepository {

    var categories: List<Category>? = null
}