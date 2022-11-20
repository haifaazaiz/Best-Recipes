package com.example.best_recipes.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.best_recipes.Model.Category
import com.example.best_recipes.R

class CategoryViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var categoryTextView: TextView = itemView.findViewById(R.id.category_textview)
}

class CategoryAdapter(val categories: List<Category>): RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryTextView.setText(categories.get(position).nameCategory)
    }

    override fun getItemCount(): Int {
        return categories.count()
    }
}