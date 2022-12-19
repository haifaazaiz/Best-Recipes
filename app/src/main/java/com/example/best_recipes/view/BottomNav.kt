package com.example.best_recipes.view

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.example.best_recipes.R
import com.example.best_recipes.controller.AreaActivity
import com.example.best_recipes.controller.CategoryActivity
import com.example.best_recipes.controller.RandomRecipeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNav {
    companion object {
        fun getBottom(bottomNav: BottomNavigationView, context: Context) {
            bottomNav.setOnNavigationItemSelectedListener {

                when (it.itemId) {
                    R.id.category_item -> {
                        val intent = Intent(context, CategoryActivity::class.java)
                        context.startActivity(intent)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.ingredient_item -> {
                        Log.d("item", "random item selected")
                        val intent = Intent(context, CategoryActivity::class.java)
                        context.startActivity(intent)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.area_item -> {
                        Log.d("item", "random item selected")
                        val intent = Intent(context, AreaActivity::class.java)
                        context.startActivity(intent)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.random_recipe_item -> {
                        Log.d("item", "random item selected")
                        val intent = Intent(context, RandomRecipeActivity::class.java)
                        context.startActivity(intent)
                        return@setOnNavigationItemSelectedListener true
                    }


                }
                false

            }
        }
    }
}