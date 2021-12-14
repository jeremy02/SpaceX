package com.demo.spacex.main.ui.fragments

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.demo.spacex.R
import com.demo.spacex.databinding.FragmentLaunchDetailsBinding
import com.demo.spacex.main.ui.adapters.LaunchGalleryAdapter
import com.demo.spacex.main.viewmodels.MainViewModel
import com.demo.spacex.models.launch_info.Launches
import kotlinx.android.synthetic.main.fragment_launch_details.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class LaunchDetailsFragment : BaseFragment() {

    private var _binding: FragmentLaunchDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    private val launchImagesAdapter by lazy {
        LaunchGalleryAdapter { position: Int, item: String ->
            launch_gallery_list.smoothScrollToPosition(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLaunchDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup toolbar
        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(toolbar)

        mainViewModel.selectedLaunchItem.observe(viewLifecycleOwner, {
            // Update the UI
            if(it != null) {
                updateUI(it)
            }else{
                findNavController().navigate(R.id.action_LaunchDetailsFragment_to_LaunchesListFragment)
            }
        })
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun updateUI(res: Launches) {
        // set the launch name
        if(res.missionName != null) {
            launch_name.text = res.missionName
        }else{
            // set the launch name
            launch_name.text = ""
        }

        // show image
        if(res.links != null) {
            var imageUrl = res.links?.missionPatchSmall

            if(imageUrl == null) {
                imageUrl = res.links?.missionPatch
            }

            launch_image_view.alpha = 1.0f

            Glide.with(requireContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_rocket)
                .error(R.drawable.ic_rocket)
                .into(launch_image_view)
        }

        // set the launch date
        if(res.launchDateUnix != null) {
            val unixSeconds: Long = res.launchDateUnix!!.toLong()
            val date = Date(unixSeconds * 1000L) // convert seconds to milliseconds

            try {
                val sdf = SimpleDateFormat("MMM dd, yyyy")
                val formattedDate: String = sdf.format(date)
                launch_date.text = formattedDate
            } catch (e: ParseException) {
                e.printStackTrace()
                launch_date.text = res.launchYear
            }

            // set the launch time
            try {
                val sdf = SimpleDateFormat("HH:mm:ss ZZZ")
                val formattedTime: String = sdf.format(date)

                launch_time_name.text = formattedTime
            } catch (e: ParseException) {
                e.printStackTrace()
                launch_time_name.text = res.launchYear
            }
        }

        // set the launch site name
        if(res.launchSite?.siteNameLong != null) {
            launch_site_name.text = res.launchSite?.siteNameLong
        }else{
            launch_site_name.text = ""
        }

        // update the upcoming chip icon
        if(res.upcoming != null){
            upcoming_chip.visibility = View.VISIBLE
            if(res.upcoming == false){
                upcoming_chip.chipIcon = ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_action_cancel
                )
            }
        }else{
            upcoming_chip.visibility = View.GONE
        }

        // update the rocket's name chip
        if(res.launchSite?.siteNameLong != null) {
            rocket_name_chip.text = res.rocket?.rocketName
        }else{
            rocket_name_chip.text = ""
        }

        // update the launch_success_chip
        if(res.launchSuccess != null){
            launch_success_chip.visibility = View.VISIBLE
            if(res.launchSuccess == false){
                launch_success_chip.chipIcon = ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_action_cancel
                )
                launch_success_chip.text = getString(R.string.launch_failed)
            }
        }else{
            launch_success_chip.visibility = View.GONE
        }

        // update the rocket landing_type_chip
        if(!res.rocket?.firstStage?.cores.isNullOrEmpty() && res.rocket?.firstStage?.cores?.get(0)?.landingType != null){
            landing_type_chip.visibility = View.VISIBLE
            landing_type_chip.text = res.rocket?.firstStage?.cores?.get(0)?.landingType
        }else{
            landing_type_chip.visibility = View.GONE
        }

        // update the rocket orbit_chip

        // update the rocket block_chip
        if(res.rocket?.secondStage?.block != null){
            block_chip.visibility = View.VISIBLE
            block_chip.text = "Block "+res.rocket?.secondStage?.block
        }

        // set the launch description
        launch_description.text = res.details

        // button click watch video
        launch_youtube_button.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(res.links?.videoLink)
                )
            )
        }

        // button click reddit
        launch_reddit_button.setOnClickListener {
            openUrl(res.links?.redditLaunch)
        }

        // button click read more
        launch_read_more.setOnClickListener {
            openUrl(res.links?.articleLink)
        }

        // show the gallery of the launch
        if(res.links?.flickrImages != null) {
            // show the images
            if(res.links?.flickrImages.isNullOrEmpty()) {
                launch_gallery_layout.visibility = View.GONE
            }else{
                launch_gallery_layout.visibility = View.VISIBLE
                launch_gallery_list.initialize(launchImagesAdapter)
                launch_gallery_list.setViewsToChangeColor(listOf(R.id.list_item_background))
                launchImagesAdapter.setItems(getListOfLaunchImageItems(res.links?.flickrImages as MutableList<String>))
            }
        }else{
            launch_gallery_layout.visibility = View.GONE
        }
    }

    private fun getListOfLaunchImageItems(flickrImages: MutableList<String>): List<String> {
        val items = mutableListOf<String>()
        (0..40).map { items.add(flickrImages.random()) }
        return items
    }

    private fun openUrl(url: String?) {
        try {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Issue experienced opening the article...", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}