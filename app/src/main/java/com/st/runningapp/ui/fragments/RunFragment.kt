package com.st.runningapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.st.runningapp.R
import com.st.runningapp.RunAdapter
import com.st.runningapp.RunningApp
import com.st.runningapp.others.Util
import com.st.runningapp.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_run.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class RunFragment : Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks {

    private val runAdapter by lazy { RunAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvRuns.adapter = runAdapter

        fab.setOnClickListener {
            checkPermission(false)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
        MainActivity.getMainViewModel(this).getAllRunsSortedByDate().observe(viewLifecycleOwner,
            Observer {
                runAdapter.submitList(it)
            })
    }

    private fun checkPermission(doNotAskForPermissionNow: Boolean) {
        when {
            Util.checkLocationPermission(requireContext()) -> {
                findNavController().navigate(RunFragmentDirections.actionRunFragmentToTrackingFragment())
            }
            doNotAskForPermissionNow.not() -> {
                Util.requestLocationPermission(
                    this,
                    "We need this to track your running for giving you best results !!",
                    102
                )
            }
            else -> {
                Toast.makeText(
                    requireContext(),
                    "We can't do anything without permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
            return
        }
        Toast.makeText(
            requireContext(),
            "We can't do anything without permission",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            checkPermission(true)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        checkPermission(false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}