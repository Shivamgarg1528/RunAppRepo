package com.st.runningapp.ui.fragments

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
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.st.runningapp.R
import com.st.runningapp.RunningApp
import com.st.runningapp.db.LatLong
import com.st.runningapp.ui.MainActivity
import com.st.runningapp.ui.vm.TrackViewModel
import com.st.runningapp.ui.vm.factory.TrackViewModelFactory
import kotlinx.android.synthetic.main.fragment_tracking.*
import timber.log.Timber
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
        mTrackingViewModel.clearAllLatLong() // clearing all record from db before inserting new records
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

        }
        stop.setOnClickListener {

        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(callback)

        mTrackingViewModel.mLocationLiveData.observe(viewLifecycleOwner) {
            mainViewModel.insertLatLong(it)
        }

        mTrackingViewModel.getAllLatLong().observe(viewLifecycleOwner) {
            mList = it
            drawLine()
        }
    }

    private val callback = OnMapReadyCallback { googleMap: GoogleMap ->
        mGoogleMap = googleMap
    }

    private fun drawLine() {
        if (!::mGoogleMap.isInitialized || mList.isEmpty()) {
            return
        }
        val startPosition = mList[0]
        val startLatLong = LatLng(startPosition.latitude, startPosition.longitude)
        mGoogleMap.clear()
        mGoogleMap.addMarker(MarkerOptions().position(startLatLong).title("Start position"))
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(startLatLong))
        mGoogleMap.addPolyline(PolylineOptions().addAll(mList.map {
            LatLng(it.latitude, it.longitude)
        }).color(Color.RED))
        Timber.i("drawLine called")
    }
}