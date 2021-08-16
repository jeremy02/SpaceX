package com.demo.spacex.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.demo.spacex.R
import com.demo.spacex.databinding.ActivityMainBinding
import com.demo.spacex.main.viewmodels.MainViewModel
import com.demo.spacex.models.company_info.CompanyInfo
import com.demo.spacex.network.utils.ResponseUtil
import com.demo.spacex.network.utils.Status

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // get the view model
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // handle response
        viewModel.companyInfoLiveData().observe(this, { this@MainActivity.handleResponse(it) })

        // get the company info
        viewModel.getCompanyInfo(true)

        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

            viewModel.getLaunches(true, "2017-08-01", "2020-08-22", null)
        }
    }

    private fun handleResponse(response: ResponseUtil<CompanyInfo>?) {
        response?.let {
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
                    Log.e(TAG, response.data.toString())
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}