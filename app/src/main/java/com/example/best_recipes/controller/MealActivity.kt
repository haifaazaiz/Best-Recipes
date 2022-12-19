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
import com.example.best_recipes.modal.Category
import com.example.best_recipes.modal.Meal
import com.example.best_recipes.modal.MealRepository
import com.example.best_recipes.view.BottomNav
import com.example.best_recipes.view.CategoryAdapter
import com.example.best_recipes.view.MealsAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class MealActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var mealsAdapter: MealsAdapter
    private lateinit var circularProgressIndicator: CircularProgressIndicator
    private lateinit var tempList : ArrayList<Meal>
    private lateinit var list : ArrayList<Meal>
    private lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tempList = ArrayList()
        list = ArrayList()
        setContentView(R.layout.activity_meal)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(intent.getStringExtra("CategoryName")?.substring(2)  )
        recyclerView = findViewById(R.id.recycler_view)
        var itemDecoration = DividerItemDecoration(this@MealActivity, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
        recyclerView.addItemDecoration(itemDecoration)
        circularProgressIndicator= findViewById(R.id.progress_circulair)
        circularProgressIndicator.visibility= View.VISIBLE
        bottomNav = findViewById(R.id.navigationView)
        BottomNav.getBottom(bottomNav,this@MealActivity)
        val url = URL("https://www.themealdb.com/api/json/v1/1/filter.php?"+intent.getStringExtra("CategoryName"))

        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP", e.localizedMessage)
                runOnUiThread {
                    circularProgressIndicator.visibility = View.GONE
                    MaterialAlertDialogBuilder(this@MealActivity)
                        .setTitle("Pas de réponse")
                        .setMessage("Vérifier votre connexion internet")
                        .setNeutralButton("OK") { dialog, which ->
                            val newIntent = Intent(this@MealActivity, MealActivity::class.java)
                            newIntent.putExtra("CategoryName",intent.getStringExtra("CategoryName"))

                            startActivity(newIntent)
                        }
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val gson = Gson()
                    val mealsResponse = gson.fromJson(it, MealRepository::class.java)
                    mealsResponse.meals?.let { it1 -> tempList.addAll(it1) }
                    mealsResponse.meals?.let { it1 -> list.addAll(it1) }
                    mealsResponse.meals?.let { it1 ->
                        runOnUiThread {
                            mealsAdapter = MealsAdapter(it1)
                            recyclerView.adapter = mealsAdapter
                            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                            circularProgressIndicator.visibility = View.GONE
                        }

                    }
                    Log.d("OKHTTP", "Got " + mealsResponse.meals?.count() + " results")
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
                        if (item.mealName?.toLowerCase(Locale.ITALIAN)?.contains(filterPattern) == true) {
                            tempList.add(item)
                        }
                    }
                }
                tempList?.let { it1 ->
                    runOnUiThread {
                        mealsAdapter = MealsAdapter(it1)
                        recyclerView.adapter = mealsAdapter
                        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

                    }
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}