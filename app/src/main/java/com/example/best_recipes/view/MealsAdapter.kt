package com.example.best_recipes.view

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.best_recipes.R
import com.example.best_recipes.controller.MealActivity
import com.example.best_recipes.controller.RecipeActivity
import com.example.best_recipes.modal.Meal

class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameTextView: TextView = itemView.findViewById(R.id.textview_name)
    var imageView : ImageView = itemView.findViewById(R.id.imageView)
    var mealItem : LinearLayoutCompat = itemView.findViewById(R.id.mealItem)
}

class MealsAdapter(val meals: List<Meal>) : RecyclerView.Adapter<MealViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        meals.get(position).mealImage?.let { ImageProvider.imageHolder(holder.imageView, it) }
        holder.nameTextView.setText(meals.get(position).mealName)
        holder.mealItem.setOnClickListener{
            Log.d("click on", "clicked")
            val intent = Intent(holder.mealItem.context, RecipeActivity::class.java)
            intent.putExtra("mealId", meals.get(position).idMeal)
            Log.d("idmeal", meals.get(position).idMeal.toString())
            var context = holder.mealItem.context
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return meals.count()
    }
}