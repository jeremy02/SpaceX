package com.demo.spacex.main.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.spacex.R
import com.demo.spacex.models.launch_info.Launches
import kotlinx.android.synthetic.main.item_launch.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class LaunchesAdapter(private val mContext: Context, private val items: MutableList<Launches>) : RecyclerView.Adapter<LaunchesAdapter.ViewHolder>() {

    // on click listener
    var onItemClick: ((View, Int, Launches) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_launch, parent, false)

        return ViewHolder(v)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val parentLayout: RelativeLayout = v.findViewById(R.id.parent_layout)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj: Launches = items[position]
        // set the launch name
        if(obj.missionName != null) {
            holder.itemView.launch_time_name.text = obj.missionName
        }else{
            holder.itemView.launch_time_name.text = ""
        }


        // show image
        if(obj.links != null) {
            var imageUrl = obj.links?.missionPatchSmall

            if(imageUrl == null) {
                imageUrl = obj.links?.missionPatch
            }

            Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_rocket)
                .error(R.drawable.ic_rocket)
                .into(holder.itemView.launch_image_view)

            holder.itemView.launch_image_view.alpha = 1.0f
        }

        // set the rocket name
        if(obj.rocket != null) {
            if(obj.rocket?.rocketName != null) {
                holder.itemView.launch_rocket_name.text = obj.rocket?.rocketName
            }else{
                holder.itemView.launch_rocket_name.text = obj.rocket?.rocketType
            }
        }else{
            holder.itemView.launch_rocket_name.text = " - "
        }


        // set the launch date
        if(obj.launchDateUnix != null) {
            val unixSeconds: Long = obj.launchDateUnix!!.toLong()
            val launchDate = Date(unixSeconds * 1000L) // convert seconds to milliseconds

            try {
                // date
                val sdf = SimpleDateFormat("MMM dd, yyyy")
                val formattedDate: String = sdf.format(launchDate)
                // time
                val sdfTime = SimpleDateFormat("HH:mm a")
                val formattedTime: String = sdfTime.format(launchDate)

                holder.itemView.launch_date_time.text =  "$formattedDate at $formattedTime"
            } catch (e: ParseException) {
                e.printStackTrace()
                holder.itemView.launch_date_time.text = obj.launchDateUtc
            }

            val dateToday = Date() // Calendar.getInstance().timeInMillis
            val diffDays = getDaysDifference(launchDate, dateToday)

            if(launchDate < dateToday) {
                holder.itemView.launch_days_time.text = "$diffDays days ago"
            }else{
                holder.itemView.launch_days_time.text = "$diffDays days to go"
            }
        }else{
            holder.itemView.launch_days_time.text = " - days"
        }

        // icon for launc success
        if(obj.launchSuccess != null) {
            holder.itemView.launch_icon_view.visibility = View.VISIBLE
            if(!obj.launchSuccess!! || obj.launchSuccess == false){
                holder.itemView.launch_icon_view.setImageResource(R.drawable.ic_action_cancel)
            }else{
                holder.itemView.launch_icon_view.setImageResource(R.drawable.ic_action_check)
            }
        }else{
            holder.itemView.launch_icon_view.visibility = View.GONE
        }

        // on click launch item
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(holder.itemView,position,obj)
        }
    }


    fun getDaysDifference(fromDate: Date?, toDate: Date?): Int {
        return if (fromDate == null || toDate == null) 0 else ((toDate.time - fromDate.time) / (1000 * 60 * 60 * 24)).toInt()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}