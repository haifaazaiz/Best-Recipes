package com.example.best_recipes.controller

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.best_recipes.R
import com.example.best_recipes.modal.Recipe
import com.example.best_recipes.modal.RecipeJson
import com.example.best_recipes.view.BottomNav
import com.example.best_recipes.view.ImageProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

class RandomRecipeActivity : AppCompatActivity(){
    private lateinit var ingredient_textview : TextView
    private lateinit var tags_textview : TextView
    private lateinit var  instruction_textview : TextView
    private lateinit var  lienYoutube_textview : TextView
    private lateinit var  image_view : ImageView
    private lateinit var circularProgressIndicator: CircularProgressIndicator
    private lateinit var bottomNav: BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ingredient_textview = findViewById(R.id.ingredient_textview)
        tags_textview = findViewById(R.id.tags_textview)
        instruction_textview = findViewById(R.id.instruction_textview)
        lienYoutube_textview = findViewById(R.id.lienYoutube_textview)
        image_view = findViewById(R.id.imagerecipe)
        circularProgressIndicator= findViewById(R.id.progress_circulair)
        circularProgressIndicator.visibility= View.VISIBLE
        bottomNav = findViewById(R.id.navigationView)


        BottomNav.getBottom(bottomNav,this@RandomRecipeActivity)
        val url = URL("https://www.themealdb.com/api/json/v1/1/random.php")

        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()
        Log.d("url", url.toString())
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP random recipe", e.localizedMessage)
                runOnUiThread {
                    circularProgressIndicator.visibility = View.GONE
                    MaterialAlertDialogBuilder(this@RandomRecipeActivity)
                        .setTitle("Pas de réponse")
                        .setMessage("Vérifier votre connexion internet")
                        .setNeutralButton("OK") { dialog, which ->
                            val newIntent = Intent(this@RandomRecipeActivity, RandomRecipeActivity::class.java)
                            startActivity(newIntent)                        }
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val gson = Gson()
                    var ingredients = ""
                    val recipeResponse = gson.fromJson(it, RecipeJson::class.java)
                    runOnUiThread {
                        circularProgressIndicator.visibility = View.GONE
                        var recipeJson = recipeResponse.meals?.get(0)
                        var recipe = gson.fromJson(recipeJson, Recipe::class.java)
                        supportActionBar?.setTitle(recipe.strMeal)
                        if(recipe.strIngredient1!="")
                            ingredients = ingredients + recipe.strIngredient1 + "  :  " + recipe.strMeasure1 +"\n"
                        if(recipe.strIngredient2!="")
                            ingredients = ingredients + recipe.strIngredient2 + "  :  " + recipe.strMeasure2 +"\n"
                        if(recipe.strIngredient3!="")
                            ingredients = ingredients + recipe.strIngredient3 + "  :  " + recipe.strMeasure3 +"\n"
                        if(recipe.strIngredient4!="")
                            ingredients = ingredients + recipe.strIngredient4 + "  :  " + recipe.strMeasure4 +"\n"
                        if(recipe.strIngredient5!="")
                            ingredients = ingredients + recipe.strIngredient5 + ":" + recipe.strMeasure5 +"\n"
                        if(recipe.strIngredient6!="")
                            ingredients = ingredients + recipe.strIngredient6 + "  :  " + recipe.strMeasure6 +"\n"
                        if(recipe.strIngredient7!="")
                            ingredients = ingredients + recipe.strIngredient7 + "  :  " + recipe.strMeasure7 +"\n"
                        if(recipe.strIngredient8!="")
                            ingredients = ingredients + recipe.strIngredient8 + "  :  " + recipe.strMeasure8 +"\n"
                        if(recipe.strIngredient9!="")
                            ingredients = ingredients + recipe.strIngredient9 + "  :  " + recipe.strMeasure9 +"\n"
                        if(recipe.strIngredient10!="")
                            ingredients = ingredients + recipe.strIngredient10 + ":" + recipe.strMeasure10 +"\n"
                        if(recipe.strIngredient11!="")
                            ingredients = ingredients + recipe.strIngredient11 + "  :  " + recipe.strMeasure11 +"\n"
                        if(recipe.strIngredient12!="")
                            ingredients = ingredients + recipe.strIngredient12 + "  :  " + recipe.strMeasure12 +"\n"
                        if(recipe.strIngredient13!="")
                            ingredients = ingredients + recipe.strIngredient13 + "  :  " + recipe.strMeasure13 +"\n"
                        if(recipe.strIngredient14!="")
                            ingredients = ingredients + recipe.strIngredient14 + "  :  " + recipe.strMeasure14 +"\n"
                        if(recipe.strIngredient15!="")
                            ingredients = ingredients + recipe.strIngredient15 + ":" + recipe.strMeasure15 +"\n"
                        if(recipe.strIngredient16!="")
                            ingredients = ingredients + recipe.strIngredient16 + ":" + recipe.strMeasure16 +"\n"
                        if(recipe.strIngredient17!="")
                            ingredients = ingredients + recipe.strIngredient17 + "  :  " + recipe.strMeasure17 +"\n"
                        if(recipe.strIngredient18!="")
                            ingredients = ingredients + recipe.strIngredient18 + "  :  " + recipe.strMeasure18 +"\n"
                        if(recipe.strIngredient19!="")
                            ingredients = ingredients + recipe.strIngredient19 + "  :  " + recipe.strMeasure19 +"\n"
                        if(recipe.strIngredient20!="")
                            ingredients = ingredients + recipe.strIngredient20 + "  :  " + recipe.strMeasure20 +"\n"
                        ingredient_textview.text = ingredients
                        tags_textview.text = recipe.strTags + "\n"
                        instruction_textview.text = recipe.strInstructions + "\n"
                        if(recipe.strYoutube!="")
                            lienYoutube_textview.text = recipe.strYoutube
                        recipe. strMealThumb?.let { ImageProvider.imageHolder(image_view, it) }
                        Log.d("OKHTTP", "Got " + recipe.idMeal)
                    }
                }
            }
        })
    }
}