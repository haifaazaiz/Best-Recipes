package com.example.best_recipes.controller
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.best_recipes.R
import com.example.best_recipes.modal.MealRepository
import com.example.best_recipes.view.MealsAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

class MealActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView

    private lateinit var mealsAdapter: MealsAdapter
    private lateinit var circularProgressIndicator: CircularProgressIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(intent.getStringExtra("CategoryName"))
        recyclerView = findViewById(R.id.recycler_view)
        circularProgressIndicator= findViewById(R.id.progress_circulair)
        circularProgressIndicator.visibility= View.VISIBLE

        val url = URL("https://www.themealdb.com/api/json/v1/1/filter.php?c="+intent.getStringExtra("CategoryName"))

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
                            val intent = Intent(this@MealActivity, CategoryActivity::class.java)
                            startActivity(intent)
                        }
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val gson = Gson()
                    val mealsResponse = gson.fromJson(it, MealRepository::class.java)
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
}