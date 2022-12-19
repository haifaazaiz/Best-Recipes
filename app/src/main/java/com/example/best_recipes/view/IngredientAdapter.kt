package com.example.best_recipes.view

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.best_recipes.R
import com.example.best_recipes.controller.IngredientsActivity
import com.example.best_recipes.controller.MealActivity
import com.example.best_recipes.modal.Ingredient

class IngredientViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var ingredientTextView: TextView = itemView.findViewById(R.id.ingredient_textview)
    var ingredientItem: LinearLayoutCompat =itemView.findViewById(R.id.ingredient_item)
    var ingredientView: View = itemView
    var imageView : ImageView = itemView.findViewById(R.id.imageView)
}

class IngredientAdapter(val ingredients: ArrayList<Ingredient>): RecyclerView.Adapter<IngredientViewHolder>() {


    private var list = ingredients as MutableList<Ingredient>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.ingredientTextView.setText(ingredients.get(position).strIngredient)
        val urlImage = "https://www.themealdb.com/images/ingredients/"+ingredients.get(position).strIngredient+".png"
        ImageProvider.imageHolder(
            holder.imageView, urlImage
        )
        holder.ingredientItem.setOnClickListener {
            Log.d("click on", "clicked")
            val intent = Intent(holder.ingredientView.context, MealActivity::class.java)
            intent.putExtra("CategoryName", "i=" + ingredients.get(position).strIngredient)
            var context = holder.ingredientView.context
            context.startActivity(intent)
        }

    }


    override fun getItemCount(): Int {
        return ingredients.count()
    }
}