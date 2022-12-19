package com.example.best_recipes.controller

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.best_recipes.R
import com.example.best_recipes.modal.Area
import com.example.best_recipes.modal.AreaRepository
import com.example.best_recipes.view.AreaAdapter
import com.example.best_recipes.view.BottomNav
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class AreaActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var areaAdapter: AreaAdapter
    private lateinit var circularProgressIndicator: CircularProgressIndicator
    private lateinit var tempList : ArrayList<Area>
    private lateinit var list : ArrayList<Area>
    private lateinit var bottomNav: BottomNavigationView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tempList = ArrayList()
        list = ArrayList()
        supportActionBar?.setTitle("Areas")
        setContentView(R.layout.activity_area)
        //categoroItem= findViewById(R.id.category_item)
        recyclerView = findViewById(R.id.area_recycler_view)
        var itemDecoration = DividerItemDecoration(this@AreaActivity, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
        recyclerView.addItemDecoration(itemDecoration)
        circularProgressIndicator= findViewById(R.id.progress_circulair)
        circularProgressIndicator.visibility= View.VISIBLE
        bottomNav = findViewById(R.id.navigationView)
        bottomNav.selectedItemId=R.id.area_item

        BottomNav.getBottom(bottomNav,this@AreaActivity)
        val url = URL("https://www.themealdb.com/api/json/v1/1/list.php?a=list")


        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()


        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP failure", e.localizedMessage)
                runOnUiThread {
                    circularProgressIndicator.visibility = View.GONE
                    MaterialAlertDialogBuilder(this@AreaActivity)
                        .setTitle("Pas de réponse")
                        .setMessage("Vérifier votre connexion internet")
                        .setNeutralButton("OK") { dialog, which ->
                            val intent = Intent(this@AreaActivity, AreaActivity::class.java)
                            startActivity(intent)
                        }
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {

                response.body?.string()?.let {
                    val gson = Gson()
                    val areas = gson.fromJson(it, AreaRepository::class.java)
                    areas.areas?.let { it1 -> tempList.addAll(it1) }
                    areas.areas?.let { it1 -> list.addAll(it1) }
                    areas.areas?.let { it1 ->
                        runOnUiThread {
                            val intent = Intent(applicationContext, MealActivity::class.java)
                            areaAdapter = AreaAdapter(it1)
                            recyclerView.adapter = areaAdapter
                            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                            circularProgressIndicator.visibility = View.GONE

                        }
                    }

                    Log.d("OKHTTP", "Got " + areas.areas?.count() + " results")
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
                        if (item.nameArea?.toLowerCase(Locale.ITALIAN)?.contains(filterPattern) == true) {
                            tempList.add(item)
                        }
                    }
                }
                tempList?.let { it1 ->
                    runOnUiThread {
                        areaAdapter = AreaAdapter(it1)
                        recyclerView.adapter = areaAdapter
                        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

                    }
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}