package com.demo.spacex.main.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.demo.spacex.R
import com.demo.spacex.databinding.ActivityMainBinding
import com.demo.spacex.main.ui.fragments.FilterDialogFragment
import com.demo.spacex.main.viewmodels.FilterViewModel
import com.demo.spacex.main.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    private val filterViewModel: FilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // handle response
        // mainViewModel.companyInfoLiveData().observe(this, { this@MainActivity.handleResponse(it) })

        /*
            Please note you can call the bewlo commented functions from the main activity
            This ensures that we call the functions only once instead of every time the fragments are created
         */
        // get the company info
        // mainViewModel.getCompanyInfo(true)

        // call the api to get the launches by setting this variable to true
        // this can also be called in the fragment from which we observe the launches live data
        // filterViewModel.callLaunchesApiFunction(true)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}