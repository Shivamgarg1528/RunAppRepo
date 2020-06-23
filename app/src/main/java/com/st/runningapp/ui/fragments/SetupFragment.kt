package com.st.runningapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.st.runningapp.R
import com.st.runningapp.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_setup.*

class SetupFragment : Fragment(R.layout.fragment_setup) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainViewModel = MainActivity.getMainViewModel(this)
        tvContinue.setOnClickListener {
            if (mainViewModel.isUserDataAvailable()) {
                findNavController().navigate(SetupFragmentDirections.actionSetupFragmentToRunFragment())
                return@setOnClickListener
            }
            if (etName.text.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Please Enter Valid Name",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }
            if (etWeight.text.isNullOrBlank() || etWeight.text.toString().toInt() < 0) {
                Toast.makeText(
                    requireContext(),
                    "Please Enter Valid Weight Figure",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }
            mainViewModel.saveUserInfo(etName.text.toString(), etWeight.text.toString().toInt())
            Toast.makeText(requireContext(), "User Info Saved Successfully", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(SetupFragmentDirections.actionSetupFragmentToRunFragment())
        }
    }
}