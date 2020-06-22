package com.st.runningapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.st.runningapp.R
import com.st.runningapp.RunningApp
import com.st.runningapp.ui.vm.MainViewModel
import com.st.runningapp.ui.vm.factory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mMainVRepository: MainViewModelFactory

    private val mMainViewModel by viewModels<MainViewModel> {
        mMainVRepository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        RunningApp.getAppComponent().inject(this) // inject dependencies
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when(destination.id) {
                    R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment ->
                        bottomNavigationView.visibility = View.VISIBLE
                    else -> bottomNavigationView.visibility = View.GONE
                }
            }
    }
}
