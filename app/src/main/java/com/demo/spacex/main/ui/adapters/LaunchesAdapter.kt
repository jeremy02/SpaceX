package com.demo.spacex.main.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.demo.spacex.R
import com.demo.spacex.models.launch_info.Launches

class LaunchesAdapter(private val ctx: Context, private val items: MutableList<Launches>) : RecyclerView.Adapter<LaunchesAdapter.ViewHolder>() {

    // on click listener
    var onItemClick: ((View, Int, Launches) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_launch, parent, false)

        return ViewHolder(v)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val parentLayout: LinearLayout = v.findViewById(R.id.parent_layout)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj: Launches = items[position]

        // set the data here

        // on click launch item
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(holder.itemView,position,obj)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}