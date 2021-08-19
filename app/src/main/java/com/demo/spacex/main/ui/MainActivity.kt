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

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // handle response
        // mainViewModel.companyInfoLiveData().observe(this, { this@MainActivity.handleResponse(it) })

        // get the company info
        mainViewModel.getCompanyInfo(true)

        // call the api to get the launches by setting this variable to true
        // this can also be called in the fragment from which we observe the launches live data
        // filterViewModel.callLaunchesApiFunction(true)
    }

    // show the filter and sort dialog
    private fun showFilterDialog() {
        val manager: FragmentManager = supportFragmentManager
        val filterDialog = FilterDialogFragment()
        filterDialog.show(manager, getString(R.string.filter_dialog_title))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}