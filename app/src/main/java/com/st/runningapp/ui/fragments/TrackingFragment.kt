package com.st.runningapp.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.st.runningapp.R
import com.st.runningapp.RunningApp
import com.st.runningapp.TrackingService
import com.st.runningapp.db.LatLong
import com.st.runningapp.db.Run
import com.st.runningapp.others.Constant.ACTION_START_AND_RESUME_SERVICE
import com.st.runningapp.others.Constant.ACTION_STOP_SERVICE
import com.st.runningapp.ui.MainActivity
import com.st.runningapp.ui.vm.TrackViewModel
import com.st.runningapp.ui.vm.factory.TrackViewModelFactory
import kotlinx.android.synthetic.main.fragment_tracking.*
import java.util.*
import javax.inject.Inject


class TrackingFragment : Fragment() {

    @Inject
    lateinit var mTrackViewModelFactory: TrackViewModelFactory

    private val mTrackingViewModel: TrackViewModel by viewModels {
        mTrackViewModelFactory
    }

    private lateinit var mList: List<LatLong>
    private lateinit var mGoogleMap: GoogleMap

    override fun onAttach(context: Context) {
        super.onAttach(context)
        RunningApp.getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tracking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainViewModel = MainActivity.getMainViewModel(this)

        if (mainViewModel.isUserDataAvailable()) {
            label.text =
                "Let's go, " + mainViewModel.getName().toUpperCase(Locale.getDefault()) + " !"
            label.visibility = View.VISIBLE
        }

        run.setOnClickListener {
            TrackingService.sendCommand(
                requireContext(),
                ACTION_START_AND_RESUME_SERVICE
            )
        }
        stop.setOnClickListener {
            TrackingService.sendCommand(
                requireContext(),
                ACTION_STOP_SERVICE
            )
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync {
            mGoogleMap = it
            drawPolyLineForAllPoints()
        }

        TrackingService.mTimerLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { time ->
                timer.text = time
            })

        TrackingService.mServiceStatusLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { serviceRunning ->
                if (serviceRunning) {
                    run.visibility = View.GONE
                    stop.visibility = View.VISIBLE
                } else {
                    run.visibility = View.VISIBLE
                    stop.visibility = View.GONE
                    mTrackingViewModel.clearAllLatLong()
                    AlertDialog.Builder(requireContext())
                        .setTitle("Running App")
                        .setMessage("Would you like to save last running ?")
                        .setCancelable(false)
                        .setPositiveButton("SAVE") { _, _ ->
                            run {
                                mGoogleMap.snapshot {
                                    MainActivity.getMainViewModel(this).insertRun(
                                        Run(
                                            img = it,
                                            caloriesBurned = 100,
                                            timeInMillis = 1000,
                                            timestamp = System.currentTimeMillis(),
                                            distanceInMeters = 105
                                        )
                                    )
                                    mTrackingViewModel.clearAllLatLong()
                                }
                            }
                        }
                        .setNegativeButton("DISCARD", null)
                        .show()
                }
            })

        mTrackingViewModel.getAllLatLong().observe(viewLifecycleOwner)
        {
            mList = it
            drawPolyLine()
        }
    }

    private fun animateCamera(latLong: LatLong) =
        mGoogleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    latLong.latitude,
                    latLong.longitude
                ), 13f
            )
        )

    private fun drawPolyLine() {
        if (!::mGoogleMap.isInitialized || !::mList.isInitialized || mList.size <= 1) {
            return
        }
        val lastPosition = mList.first()
        val secondLastPosition = mList[1]
        animateCamera(lastPosition) // animate to last position
        mGoogleMap
            .addPolyline(
                PolylineOptions()
                    .add(LatLng(lastPosition.latitude, lastPosition.longitude))
                    .add(LatLng(secondLastPosition.latitude, secondLastPosition.longitude))
                    .color(Color.RED)
                    .width(8f)
            )
    }

    private fun drawPolyLineForAllPoints() {
        if (!::mGoogleMap.isInitialized || !::mList.isInitialized) {
            return
        }
        val lastPosition = mList.first()
        animateCamera(lastPosition) // animate to last position
        mGoogleMap
            .addPolyline(
                PolylineOptions()
                    .addAll(mList.map {
                        LatLng(it.latitude, it.longitude)
                    })
                    .color(Color.RED)
                    .width(8f)
            )
    }
}