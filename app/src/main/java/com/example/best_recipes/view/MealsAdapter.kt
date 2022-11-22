package com.example.best_recipes.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.best_recipes.R
import com.example.best_recipes.modal.Meal

class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameTextView: TextView = itemView.findViewById(R.id.textview_name)
    var imageView : ImageView = itemView.findViewById(R.id.imageView)
}

class MealsAdapter(val meals: List<Meal>) : RecyclerView.Adapter<MealViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        meals.get(position).mealImage?.let { ImageProvider.imageHolder(holder,null, it) }
        holder.nameTextView.setText(meals.get(position).mealName)
    }

    override fun getItemCount(): Int {
        return meals.count()
    }
}