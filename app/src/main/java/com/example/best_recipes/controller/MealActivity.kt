package com.example.best_recipes.controller

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.best_recipes.R
import com.example.best_recipes.model.MealRepository
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

class MealActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView

    private lateinit var mealsAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        recyclerView = findViewById(R.id.recycler_view)

        val url = URL("https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood")

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
                    val mealsResponse = gson.fromJson(it, MealRepository::class.java)
                    mealsResponse.meals?.let { it1 ->
                        runOnUiThread {
                            mealsAdapter = MealsAdapter(it1)
                            recyclerView.adapter = mealsAdapter
                            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        }

                    }
                    Log.d("OKHTTP", "Got " + mealsResponse.meals?.count() + " results")
                }
            }
        })

    }
}