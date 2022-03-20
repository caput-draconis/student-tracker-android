package com.example.studentvaccine.ui.managevaccine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studentvaccine.R
import com.example.studentvaccine.firebasedb.Datastore
import com.google.firebase.database.DatabaseReference

class ManageVaccine : Fragment() {

    companion object {
        fun newInstance() = ManageVaccine()
    }

    private lateinit var viewModel: ManageVaccineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.manage_vaccine_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManageVaccineViewModel::class.java)
        // TODO: Use the ViewModel
    }

}