package com.example.best_recipes.controller

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.best_recipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {

    //private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("loaded","fragment.toString()")

        bottomNav = findViewById(R.id.navigationView)


        bottomNav.setOnNavigationItemSelectedListener {
            Log.d("itemclick",it.itemId.toString())
             when (it.itemId) {
                R.id.category_item -> {

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.ingredient_item -> {

                    return@setOnNavigationItemSelectedListener true
                }
                 R.id.area_item -> {
                     Log.d("item","random item selected")

                     return@setOnNavigationItemSelectedListener true
                 }
                R.id.random_recipe_item -> {
                    Log.d("item","random item selected")

                    return@setOnNavigationItemSelectedListener true
                }


             }
            false

        }


    }
    private  fun loadFragment(fragment: Fragment){
        Log.d("load",fragment.toString())
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        Log.d("load",fragment.javaClass.toString())
        transaction.commit()
    }
    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }*/
}