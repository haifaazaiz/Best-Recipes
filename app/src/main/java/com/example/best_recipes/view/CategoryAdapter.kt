package com.example.best_recipes.view

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.best_recipes.R
import com.example.best_recipes.controller.MealActivity
import com.example.best_recipes.modal.Category


class CategoryViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var categoryTextView: TextView = itemView.findViewById(R.id.category_textview)
    var imageView : ImageView = itemView.findViewById(R.id.imageView)
    var categoryItem: LinearLayoutCompat =itemView.findViewById(R.id.category_item)
    var categoryView:View = itemView
    var cbHeart: CheckBox = itemView.findViewById(R.id.cbHeart)

}

class CategoryAdapter(context: Context, val categories: List<Category>): RecyclerView.Adapter<CategoryViewHolder>(){


    private var list = categories as MutableList<Category>
    private val sharedPreferences = context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        categories.get(position).urlPictureCategory?.let {
            ImageProvider.imageHolder(
                holder.imageView,
                it
            )
        }
        holder.categoryTextView.setText(categories.get(position).nameCategory)
        holder.categoryItem.setOnClickListener {
            Log.d("click on", "clicked")
            val intent = Intent(holder.categoryView.context, MealActivity::class.java)
            intent.putExtra("CategoryName", "c="+categories.get(position).nameCategory)
            var context = holder.categoryView.context
            context.startActivity(intent)
        }
        val isChecked = sharedPreferences.getBoolean(position.toString(), false)
        holder.cbHeart.isChecked= isChecked
        holder.cbHeart.setOnClickListener{
            sharedPreferences.edit().apply{
                putBoolean(position.toString(),holder.cbHeart.isChecked)
                apply()
            }
        }
        }


    override fun getItemCount(): Int {
        return categories.count()
    }

}