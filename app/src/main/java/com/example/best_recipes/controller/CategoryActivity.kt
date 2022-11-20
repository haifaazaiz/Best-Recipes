package com.example.best_recipes.controller

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.best_recipes.model.CategoryRepository
import com.example.best_recipes.R
import com.example.best_recipes.view.CategoryAdapter
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
/** hello it's me
 * 
 */
class CategoryActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var circularProgressIndicator: CircularProgressIndicator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        recyclerView = findViewById(R.id.category_recycler_view)
        circularProgressIndicator= findViewById(R.id.progress_circulair)
        circularProgressIndicator.visibility= View.VISIBLE
        val url = URL("https://www.themealdb.com/api/json/v1/1/categories.php")

        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                circularProgressIndicator.visibility = View.GONE
                Log.e("OKHTTP failure", e.localizedMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val gson = Gson()
                    val categories = gson.fromJson(it, CategoryRepository::class.java)
                    categories.categories?.let { it1 ->
                        runOnUiThread {
                            categoryAdapter = CategoryAdapter(it1)
                            recyclerView.adapter = categoryAdapter
                            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        }

                    }

                    Log.d("OKHTTP", "Got " + categories.categories?.count() + " results")
                }
            }
        })
        circularProgressIndicator.visibility = View.GONE


    }

}