package com.example.best_recipes.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.best_recipes.R
import com.example.best_recipes.modal.*
import com.example.best_recipes.view.BottomNav
import com.example.best_recipes.view.IngredientAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class IngredientsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var circularProgressIndicator: CircularProgressIndicator
    private lateinit var tempList : ArrayList<Ingredient>
    private lateinit var list : ArrayList<Ingredient>
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        tempList = ArrayList()
        list = ArrayList()
        setContentView(R.layout.activity_ingredient)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Ingrédients")
        recyclerView = findViewById(R.id.ingredient_recycler_view)
        var itemDecoration = DividerItemDecoration(this@IngredientsActivity, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
        recyclerView.addItemDecoration(itemDecoration)
        circularProgressIndicator= findViewById(R.id.progress_circulair)
        circularProgressIndicator.visibility= View.VISIBLE
        bottomNav = findViewById(R.id.navigationView)
        bottomNav.selectedItemId=R.id.ingredient_item
        BottomNav.getBottom(bottomNav,this@IngredientsActivity)

        val url = URL("https://www.themealdb.com/api/json/v1/1/list.php?i=list")

        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP", e.localizedMessage)
                runOnUiThread {
                    circularProgressIndicator.visibility = View.GONE
                    MaterialAlertDialogBuilder(this@IngredientsActivity)
                        .setTitle("Pas de réponse")
                        .setMessage("Vérifier votre connexion internet")
                        .setNeutralButton("OK") { dialog, which ->
                            val newIntent = Intent(this@IngredientsActivity, IngredientsActivity::class.java)
                            newIntent.putExtra("IngredientName",intent.getStringExtra("strIngredient"))

                            startActivity(newIntent)
                        }
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val gson = Gson()
                    val ingredientsResponse = gson.fromJson(it, IngredientJson::class.java)
                    ingredientsResponse.ingredients?.let { it ->
                        runOnUiThread {
                            var ingredientJson = ingredientsResponse.ingredients?.get(0)
                            //var ingredient = gson.fromJson(ingredientJson, Ingredient::class.java)
                            ingredientAdapter = IngredientAdapter(it as ArrayList<Ingredient>)
                            recyclerView.adapter = ingredientAdapter
                            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                            circularProgressIndicator.visibility = View.GONE
                            //Log.d("OKHTTP", "Got " + ingredient.idIngredient)
                        }
                    }
                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        val search : MenuItem? = menu?.findItem(R.id.nav_search)
        val searchView : SearchView = search?.actionView as SearchView
        searchView.queryHint ="Search somthing!"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?):Boolean{
                return false
            }
            override fun onQueryTextChange(query: String):Boolean{
                tempList.clear()
                if (query == null || query.isEmpty()) {
                    tempList.addAll(list )

                } else {
                    val filterPattern =
                        query.toString().toLowerCase(Locale.ITALIAN).trim { it <= ' ' }
                    for (item in list) {
                        if (item.strIngredient?.toLowerCase(Locale.ITALIAN)?.contains(filterPattern) == true) {
                            tempList.add(item)
                        }
                    }
                }
                tempList?.let { it1 ->
                    runOnUiThread {
                        ingredientAdapter = IngredientAdapter(it1)
                        recyclerView.adapter = ingredientAdapter
                        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

                    }
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}
