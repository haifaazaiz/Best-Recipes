package com.example.best_recipes.controller
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.best_recipes.R
import com.example.best_recipes.modal.Recipe
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import okhttp3.*
import java.io.IOException
import java.net.URL
data class C(
    var idMeal: Int?= null,
   //@SerializedName("strMeal")
   var strMeal: String? = null,
//@SerializedName("strDrinkAlternate")
var strDrinkAlternate: String? = null,
//@SerializedName("strCategory")
var strCategory: String? = null,
//@SerializedName("strArea")
var strArea: String? = null,
//@SerializedName("strInstructions")
var strInstructions: String? = null,
   //@SerializedName("strMealThumb")
   var strMealThumb: String? = null,
   //@SerializedName("strYoutube")
   var strYoutube: String? = null,
   //@SerializedName("strTags")
   var strTags: String? = null,
   var strIngredient1: String? = null,
   var strIngredient2: String? = null,
   var strIngredient3: String? = null,
   var strIngredient4: String? = null,
   var strIngredient5: String? = null,
   var strIngredient6: String? = null,
   var strIngredient7: String? = null,
   var strIngredient8: String? = null,
   var strIngredient9: String? = null,
   var strIngredient10: String? = null,
   var strIngredient11: String? = null,
   var strIngredient12: String? = null,
   var strIngredient13: String? = null,
   var strIngredient14: String? = null,
   var strIngredient15: String? = null,
   var strIngredient16: String? = null,
   var strIngredient17: String? = null,
   var strIngredient18: String? = null,
   var strIngredient19: String? = null,
   var strIngredient20: String? = null,
   var strMeasure1: String? = null,
   var strMeasure2: String? = null,
   var strMeasure3: String? = null,
   var strMeasure4: String? = null,
   var strMeasure5: String? = null,
   var strMeasure6: String? = null,
   var strMeasure7: String? = null,
   var strMeasure8: String? = null,
   var strMeasure9: String? = null,
   var strMeasure10: String? = null,
   var strMeasure11: String? = null,
   var strMeasure12: String? = null,
   var strMeasure13: String? = null,
   var strMeasure14: String? = null,
   var strMeasure15: String? = null,
   var strMeasure16: String? = null,
   var strMeasure17: String? = null,
   var strMeasure18: String? = null,
   var strMeasure19: String? = null,
   var strMeasure20: String? = null,

  var strSource: String? = null,
  var strImageSource: String? = null,
  var strCreativeCommonsConfirmed: String? = null,
  var dateModified: String? = null
)
class RecipeActivity : AppCompatActivity(){
    private lateinit var ingredient_textview : TextView
    private lateinit var tags_textview : TextView
    private lateinit var  instruction_textview : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        ingredient_textview = findViewById(R.id.ingredient_textview)
        tags_textview = findViewById(R.id.tags_textview)
        instruction_textview = findViewById(R.id.instruction_textview)
        val url = URL("https://www.themealdb.com/api/json/v1/1/lookup.php?i=52819")

        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP", e.localizedMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val gson = Gson()
                    val recipeResponse = gson.fromJson(it, Recipe::class.java)
                    runOnUiThread {
                        var recipeJson = recipeResponse.meals?.get(0)
                        //val mapper = jacksonObjectMapper()
                        //val c:C = mapper.readValue(recipe,object: TypeReference<C>)
                        var recipe = gson.fromJson(recipeJson, C::class.java)
                        ingredient_textview.text = recipe.strIngredient1 + recipe.strIngredient2
                        tags_textview.text = recipe.strTags
                        instruction_textview.text = recipe.strInstructions
                        Log.d("OKHTTP", "Got " + recipe.idMeal)
                    }
                }
            }
        })
    }
}