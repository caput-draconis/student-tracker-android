package com.example.studentvaccine.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studentvaccine.R
import com.example.studentvaccine.databinding.FragmentHomeBinding
import com.example.studentvaccine.firebasedb.Datastore
import com.google.firebase.database.DatabaseReference
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var database: DatabaseReference = Datastore.VACCINE.get()

        database.get().addOnSuccessListener {
            it.children.forEach { x ->
                val cardContainer: LinearLayout? = binding.cardContainer

                val cardItem: View =
                    LayoutInflater.from(requireContext()).inflate(R.layout.home_card_item, null, false)

                val studentClassView: TextView = cardItem.findViewById<View>(R.id.studentClass) as TextView
                val vaccineDescriptionView: TextView = cardItem.findViewById<View>(R.id.description) as TextView

                studentClassView.text ="Class: " + x.key.toString()
                var total: Double = (x.child("total").value as Long).toDouble()
                var vaccinated: Double = (x.child("vaccinated").value as Long).toDouble()

                var percent: Double = (vaccinated / total) * 100.00;

                vaccineDescriptionView.text = "Vaccinated: ${percent.roundToInt().toString()}%"

                val circularProgressBar = cardItem.findViewById<CircularProgressBar>(R.id.circularProgressBar)
                circularProgressBar.apply {

                    setProgressWithAnimation(percent.toFloat(), 1000) // =1s

                    progressMax = 100f

                    progressBarWidth = 15f // in DP
                    backgroundProgressBarWidth = 25f // in DP

                    roundBorder = true
                    startAngle = 0f
                    progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
                }

                cardContainer!!.addView(cardItem)
            }



        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}