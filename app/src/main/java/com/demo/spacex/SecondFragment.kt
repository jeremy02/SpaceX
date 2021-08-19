package com.demo.spacex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.demo.spacex.databinding.FragmentSecondBinding
import com.demo.spacex.main.viewmodels.MainViewModel
import com.demo.spacex.models.FilterItem
import com.demo.spacex.models.launch_info.Launches
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.selectedLaunchItem.observe(viewLifecycleOwner, {
            // Update the UI
            if(it != null) {
                updateUI(it)
            }else{
                findNavController().navigate(R.id.action_SecondFragment_to_LaunchesListFragment)
            }
        })
    }

    private fun updateUI(res: Launches) {
        // update the upcoming chip icon
        if(res.upcoming != null){
            upcoming_chip.visibility = View.VISIBLE
            if(res.upcoming == false){
                upcoming_chip.chipIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_action_cancel)
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
                launch_success_chip.chipIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_action_cancel)
                launch_success_chip.text = getString(R.string.launch_failed)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}