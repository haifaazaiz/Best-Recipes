package com.example.best_recipes.view

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.best_recipes.R
import com.example.best_recipes.controller.MealActivity
import com.example.best_recipes.modal.Category
import java.util.*

class CategoryViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var categoryTextView: TextView = itemView.findViewById(R.id.category_textview)
    var imageView : ImageView = itemView.findViewById(R.id.imageView)
    var categoryItem: LinearLayoutCompat =itemView.findViewById(R.id.category_item)
    var categoryView:View = itemView
}

class CategoryAdapter( val categories: List<Category>): RecyclerView.Adapter<CategoryViewHolder>(),Filterable {


    private var list = categories as MutableList<Category>
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
            intent.putExtra("CategoryName", categories.get(position).nameCategory)
            var context = holder.categoryView.context
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return categories.count()
    }

    override fun getFilter(): Filter? {
        return Searched_Filter
    }

    private val Searched_Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList = mutableListOf<Category>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(categories )

            } else {
                val filterPattern =
                    constraint.toString().toLowerCase(Locale.ITALIAN).trim { it <= ' ' }
                for (item in categories) {
                    if (item.nameCategory?.toLowerCase(Locale.ITALIAN)?.contains(filterPattern) == true) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            list.clear()
            list.addAll(p1?.values as MutableList<Category>)
            Log.e("list",list.count().toString())
            notifyDataSetChanged()




            }
        }
}