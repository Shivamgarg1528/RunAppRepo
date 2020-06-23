package com.st.runningapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
    lateinit var mMainVMFactory: MainViewModelFactory

    val mMainViewModel by viewModels<MainViewModel> {
        mMainVMFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        RunningApp.getAppComponent().inject(this) // inject dependencies
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.runFragment, R.id.statisticsFragment, R.id.settingsFragment ->
                        bottomNavigationView.visibility = View.VISIBLE
                    else -> bottomNavigationView.visibility = View.GONE
                }
            }
    }

    companion object {
        fun getMainViewModel(fragment: Fragment): MainViewModel {
            return (fragment.requireActivity() as MainActivity).mMainViewModel
        }
    }
}
