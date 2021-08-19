package com.demo.spacex.main.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.spacex.R
import com.demo.spacex.databinding.FragmentLaunchesListBinding
import com.demo.spacex.main.ui.adapters.LaunchesAdapter
import com.demo.spacex.models.FilterItem
import com.demo.spacex.models.launch_info.Launches
import com.demo.spacex.models.launch_info.LaunchesResponse
import com.demo.spacex.network.utils.ResponseUtil
import com.demo.spacex.network.utils.Status
import kotlinx.android.synthetic.main.error_message.*
import kotlinx.android.synthetic.main.fragment_launches_list.*
import kotlinx.android.synthetic.main.progress_bar.*

open class BaseFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        view.findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, appBarConfiguration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}