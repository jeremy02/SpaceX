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
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.demo.spacex.R
import com.demo.spacex.databinding.FragmentSecondBinding
import com.demo.spacex.main.viewmodels.MainViewModel
import com.demo.spacex.models.launch_info.Launches
import kotlinx.android.synthetic.main.fragment_second.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(binding.toolbar)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        view.findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, appBarConfiguration)

        mainViewModel.selectedLaunchItem.observe(viewLifecycleOwner, {
            // Update the UI
            if(it != null) {
                updateUI(it)
            }else{
                findNavController().navigate(R.id.action_SecondFragment_to_LaunchesListFragment)
            }
        })
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun updateUI(res: Launches) {
        // set the launch name
        launch_name.text = res.missionName

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
                launch_date.text = String.format(requireContext().getString(R.string.launch_date), res.launchYear)
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
        launch_site_name.text = res.launchSite?.siteNameLong

        // update the upcoming chip icon
        if(res.upcoming != null){
            upcoming_chip.visibility = View.VISIBLE
            if(res.upcoming == false){
                upcoming_chip.chipIcon = ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_action_cancel
                )
                // upcoming_chip.chipIconTint = ContextCompat.getColorStateList(requireContext(), R.color.color_red)
                // upcoming_chip.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.color_red))
            }
        }

        // update the rocket's name chip
        rocket_name_chip.text = res.rocket?.rocketName

        // update the launch_success_chip
        if(res.launchSuccess != null){
            launch_success_chip.visibility = View.VISIBLE
            if(res.upcoming == false){
                launch_success_chip.chipIcon = ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_action_cancel
                )
                launch_success_chip.text = getString(R.string.launch_failed)
            }
        }


        // update the rocket landing_type_chip
        if(!res.rocket?.firstStage?.cores.isNullOrEmpty() && res.rocket?.firstStage?.cores?.get(0)?.landingType != null){
            landing_type_chip.visibility = View.VISIBLE
            launch_success_chip.text = res.rocket?.firstStage?.cores?.get(0)?.landingType
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