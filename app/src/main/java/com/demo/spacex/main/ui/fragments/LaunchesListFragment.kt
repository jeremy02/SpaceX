package com.demo.spacex.main.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.demo.spacex.R
import com.demo.spacex.databinding.FragmentLaunchesListBinding
import com.demo.spacex.models.FilterItem
import com.demo.spacex.main.viewmodels.FilterViewModel
import com.demo.spacex.main.viewmodels.MainViewModel
import com.demo.spacex.models.company_info.CompanyInfo
import com.demo.spacex.models.launch_info.Launches
import com.demo.spacex.models.launch_info.LaunchesResponse
import com.demo.spacex.network.utils.ResponseUtil
import com.demo.spacex.network.utils.Status

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LaunchesListFragment : Fragment() {

    private val TAG: String = LaunchesListFragment::class.java.simpleName

    private var _binding: FragmentLaunchesListBinding? = null

    private val mainViewModel: MainViewModel by activityViewModels()

    private val filterViewModel: FilterViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLaunchesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_LaunchesListFragment_to_SecondFragment)
        }

        // handle response from getting launches
        mainViewModel.launchesLiveData().observe(viewLifecycleOwner, { handleResponse(it) })

        // observe callFilterSortFunctionLiveData to check if we need to call the api function
        filterViewModel.callLaunchesApiFunctionLiveData.observe(viewLifecycleOwner, {
            // By Default we are sorting by "flight_number"
            val sortBy = "flight_number"

            // if set to true call the api to get launches
            if(it != null){
                // check if we should call the api
                    if(it){
                        mainViewModel.getLaunches(true, FilterItem(
                            startDate = filterViewModel.startDate.value,
                            endDate = filterViewModel.endDate.value,
                            launchSuccess = filterViewModel.launchSuccess.value,
                            sortBy = sortBy,
                            sortOrder = filterViewModel.sortOrder.value
                        )
                        )
                    }
            }
        })

        // call the api to get the launches by setting this variable to true
        filterViewModel.callLaunchesApiFunction(true)
    }

    private fun handleResponse(res: ResponseUtil<LaunchesResponse>?) {
        res?.let {
            when (it.status) {
                Status.LOADING -> {
                    if (it.isFirst) {
                        Log.e(TAG, "it.isFirst")
                    } else {
                        Log.e(TAG, " no it.isFirst")
                    }
                }

                Status.REFRESHING -> {
                    Log.e(TAG, "REFRESHING")
                }

                Status.EMPTY -> {
                    Log.e(TAG, "EMPTY")
                }

                Status.SUCCEED -> {
                    Log.e(TAG, "SUCCEED")
                    Log.e(TAG, res.data.toString())
                }

                Status.FAILED -> {
                    Log.e(TAG, "FAILED")
                }

                Status.NO_CONNECTION -> {
                    Log.e(TAG, "NO_CONNECTION")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}