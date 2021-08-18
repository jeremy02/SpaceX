package com.demo.spacex.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.demo.spacex.R
import com.demo.spacex.databinding.FragmentFirstBinding
import com.demo.spacex.main.viewmodels.FilterViewModel
import com.demo.spacex.main.viewmodels.MainViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val TAG: String = FirstFragment::class.java.simpleName

    private var _binding: FragmentFirstBinding? = null

    // Using the activityViewModels() Kotlin property delegate from the
    // fragment-ktx artifact to retrieve the ViewModel in the activity scope

    // viewModel.getLaunches(true, "2017-08-01", "2020-08-22", null)

    private val launchesViewModel: MainViewModel by activityViewModels()

    private val filterViewModel: FilterViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        // observe changes in start date
        filterViewModel.startDate.observe(viewLifecycleOwner, { it ->
            Log.e(TAG, "2FirstFragment")
            Log.e(TAG, "2FirstFragment $it")
        })

        // observe changes in start date
        filterViewModel.endDate.observe(viewLifecycleOwner, { it ->
            Log.e(TAG, "21FirstFragment")
            Log.e(TAG, "21FirstFragment $it")
        })

        // observe changes in launch success filter
        filterViewModel.launchSuccess.observe(viewLifecycleOwner, { it ->
            Log.e(TAG, "2FirstFragment")
            Log.e(TAG, "2FirstFragment $it")
        })

        // observe sort order
        filterViewModel.sortOrderLiveData.observe(viewLifecycleOwner, {
            if(it != null){
                Log.e(TAG, "sortOrderLiveData $it")
            }
        })

        // observe to call the api function
        filterViewModel.callFilterSortFunctionLiveData.observe(viewLifecycleOwner, {
            if(it != null){
                Log.e(TAG, "callFilterSortFunctionLiveData $it")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}