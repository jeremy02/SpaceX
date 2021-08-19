package com.demo.spacex.main.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.spacex.R
import com.demo.spacex.databinding.FragmentLaunchesListBinding
import com.demo.spacex.main.ui.adapters.LaunchesAdapter
import com.demo.spacex.main.viewmodels.FilterViewModel
import com.demo.spacex.main.viewmodels.MainViewModel
import com.demo.spacex.models.FilterItem
import com.demo.spacex.models.company_info.CompanyInfo
import com.demo.spacex.models.launch_info.Launches
import com.demo.spacex.models.launch_info.LaunchesResponse
import com.demo.spacex.network.utils.ResponseUtil
import com.demo.spacex.network.utils.Status
import kotlinx.android.synthetic.main.error_message.*
import kotlinx.android.synthetic.main.fragment_launch_details.*
import kotlinx.android.synthetic.main.fragment_launches_list.*
import kotlinx.android.synthetic.main.fragment_launches_list.toolbar
import kotlinx.android.synthetic.main.progress_bar.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LaunchesListFragment : BaseFragment() {

    private val TAG: String = LaunchesListFragment::class.java.simpleName

    private var _binding: FragmentLaunchesListBinding? = null

    private val mainViewModel: MainViewModel by activityViewModels()

    private val filterViewModel: FilterViewModel by activityViewModels()

    private lateinit var launchesList: List<Launches>

    private lateinit var mLaunchesAdapter: LaunchesAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // make the adapter unclickable until finished with loading launches
    private var adapterClickable: Boolean = false

    // we use this to not call the company info and get launches
    // api if they had been called before
    protected var onCreateViewCalled = false

    fun hasOnCreateViewBeenCalled(): Boolean {
        return onCreateViewCalled
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLaunchesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup toolbar
        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(toolbar)

        // initialize the views
        initViews()

        // handle response from getting company info
        mainViewModel.companyInfoLiveData().observe(viewLifecycleOwner, { handleCompanyInfoResponse(it) })

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

        // had the view been created before?
        // had we experienced an error in calling the company info api and therefore its view is not displayed?
        if(!hasOnCreateViewBeenCalled() || !company_title_layout.isShown){
            onCreateViewCalled = true

            mainViewModel.getCompanyInfo(true) // get the company info

            filterViewModel.callLaunchesApiFunction(true) // call the api to get the launches by setting this variable to true
        }
    }

    // initialize the views
    private fun initViews() {
        company_title_layout.visibility = View.GONE
        progress_bar_container.visibility = View.GONE
        error_container.visibility = View.GONE
        no_launches_found.visibility = View.GONE
        launches_recycler_view.visibility = View.GONE

        // this is the button from the error container
        btn_ok.setOnClickListener {
            switchStateUI(View.GONE, View.GONE, View.GONE, false)
        }

        // refresh goals list by calling API
        swipe_to_refresh_layout.setOnRefreshListener {
            swipe_to_refresh_layout.isRefreshing = true

            initRecyclerView() // reset recyclerview

            filterViewModel.callLaunchesApiFunction(true) // get the launches by setting this value to true
        }

        initRecyclerView() // initialize the recyclerview
    }


    // handle getting company info response
    private fun handleCompanyInfoResponse(res: ResponseUtil<CompanyInfo>?) {
        res?.let {
            when (it.status) {
                Status.LOADING -> {

                }

                Status.REFRESHING -> {

                }

                Status.EMPTY -> {

                }

                Status.SUCCEED -> {
                    if(res.data?.summary != null) {
                        company_title_layout.visibility = View.VISIBLE
                        company_description_text_view.text = res.data.summary
                    }
                }

                Status.FAILED -> {

                }

                Status.NO_CONNECTION -> {

                }
            }
        }
    }

    // handle getting launches response
    private fun handleResponse(res: ResponseUtil<LaunchesResponse>?) {
        swipe_to_refresh_layout.isRefreshing = false

        // adapter not clickable
        adapterClickable = false

        res?.let {
            when (it.status) {
                Status.LOADING -> {
                    switchStateUI(View.GONE, View.VISIBLE, View.GONE, false)
                }

                Status.REFRESHING -> {
                    switchStateUI(View.GONE, View.VISIBLE, View.GONE, true)
                }

                Status.EMPTY -> {
                    switchStateUI(View.GONE, View.GONE, View.VISIBLE, true)
                }

                Status.SUCCEED -> {
                    adapterClickable = true // adapter clickable
                    if(res.data?.launchItems != null && res.data.launchItems.isNotEmpty()) {
                        refreshAdapter(res.data.launchItems) // refresh the adapter
                        switchStateUI(View.GONE, View.GONE, View.GONE, false)
                    }else{
                        switchStateUI(View.GONE, View.GONE, View.GONE,true)
                    }
                }

                Status.FAILED -> {
                    switchStateUI(View.VISIBLE, View.GONE, View.GONE, true)
                    error_message.text = getString(R.string.api_fail_error)
                    error_container.requestFocus()
                }

                Status.NO_CONNECTION -> {
                    switchStateUI(View.VISIBLE, View.GONE, View.GONE, true)
                    error_message.text = getString(R.string.api_network_error)
                    error_container.requestFocus()
                }
            }
        }
    }

    private fun initRecyclerView() {
        launchesList = ArrayList() // init data and empty the list of launches
        // reset the adapter
        val linearlayoutManager = LinearLayoutManager(requireActivity())
        launches_recycler_view.layoutManager = linearlayoutManager
        launches_recycler_view.setHasFixedSize(true)
        launches_recycler_view.isNestedScrollingEnabled = false
        mLaunchesAdapter = LaunchesAdapter(requireActivity(),launchesList.toMutableList())
        launches_recycler_view.adapter = mLaunchesAdapter

        // this will sort the issue of swipe to refresh view not working with recycler view
        // recyclerview on scroll conflict with swipe refresh layout is sorted by this code
        launches_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.getChildAt(0) != null) {
                    swipe_to_refresh_layout.isEnabled = (linearlayoutManager.findFirstVisibleItemPosition() == 0
                            && recyclerView.getChildAt(0).top == 0)
                }
            }
        })
    }

    // change ui according to error, loading states etc
    private fun switchStateUI(error: Int, loading: Int, isFirstLoad: Int, isLaunchesEmpty: Boolean) {
        progress_bar_container.visibility = loading
        error_container.visibility = error

        if(isLaunchesEmpty){ // checking for list
            if(progress_bar_container.isShown){
                launches_recycler_view.visibility = View.GONE
                no_launches_found.visibility = View.GONE
            }else {
                launches_recycler_view.visibility = View.GONE
                no_launches_found.visibility = View.VISIBLE
            }
        }else{
            if(mLaunchesAdapter.itemCount >= 1){
                launches_recycler_view.visibility = View.VISIBLE
                no_launches_found.visibility = View.GONE
            }else {
                launches_recycler_view.visibility = View.GONE
                no_launches_found.visibility = View.GONE
            }
        }
    }

    // refresh the adapter with this list of launches
    private fun refreshAdapter(launchItems: List<Launches>) {
        mLaunchesAdapter = LaunchesAdapter(requireActivity(), launchItems.toMutableList())
        launches_recycler_view.adapter = mLaunchesAdapter
        mLaunchesAdapter.notifyDataSetChanged()

        // click launches recycler view listener
        mLaunchesAdapter.onItemClick = { _view:View, _position:Int, _launchObj:Launches ->
            // show the launch item detail
            if(adapterClickable) {
                mainViewModel.selectLaunchItem(_launchObj)
                findNavController().navigate(R.id.action_LaunchesListFragment_to_LaunchDetailsFragment)
            } else {
                switchStateUI(View.VISIBLE, View.GONE, View.GONE, true)
                error_message.text = getString(R.string.error_ongoing_process)
                error_container.requestFocus()

                // hide the message after 5 seconds
                Handler().postDelayed({
                    switchStateUI(View.GONE, View.GONE, View.GONE, true)
                }, 3000)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_filter -> {
                showFilterDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // show the filter and sort dialog
    private fun showFilterDialog() {
        val manager: FragmentManager = childFragmentManager
        val filterDialog = FilterDialogFragment()
        filterDialog.show(manager, getString(R.string.filter_dialog_title))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}