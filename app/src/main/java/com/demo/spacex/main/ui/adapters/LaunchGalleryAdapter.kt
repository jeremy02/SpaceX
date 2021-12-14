package com.demo.spacex.main.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.spacex.R
import kotlinx.android.synthetic.main.item_launch_image.view.*

class LaunchGalleryAdapter(val itemClick: (position:Int,item: String) -> Unit) : RecyclerView.Adapter<ItemViewHolder>() {

    private var items: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_launch_image, parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            itemClick(position,items[position])
        }
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }
}

class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: String) {

        // display the image here
        Glide.with(view.context)
            .load(item)
            .centerCrop()
            .placeholder(R.drawable.ic_rocket)
            .error(R.drawable.ic_action_cancel)
            .into(view.list_item_icon)
    }
}