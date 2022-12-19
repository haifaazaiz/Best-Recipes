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
import com.example.best_recipes.controller.MealActivity
import com.example.best_recipes.modal.Area

class AreaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var areaTextView: TextView = itemView.findViewById(R.id.area_textview)
    var areaItem: LinearLayoutCompat =itemView.findViewById(R.id.area_item)
    var areaView: View = itemView
}

class AreaAdapter(private val areas: List<Area>): RecyclerView.Adapter<AreaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_area, parent, false)
        return AreaViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {

        holder.areaTextView.setText(areas.get(position).nameArea)
        holder.areaItem.setOnClickListener {
            Log.d("click on", "clicked")
            val intent = Intent(holder.areaView.context, MealActivity::class.java)
            intent.putExtra("CategoryName", "a="+areas.get(position).nameArea)
            var context = holder.areaView.context
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return areas.count()
    }


}