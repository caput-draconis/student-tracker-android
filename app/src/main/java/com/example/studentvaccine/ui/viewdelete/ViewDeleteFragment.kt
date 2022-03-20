package com.example.studentvaccine.ui.viewdelete

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.studentvaccine.R
import com.example.studentvaccine.databinding.FragmentViewDeleteBinding
import com.example.studentvaccine.firebasedb.Datastore
import com.example.studentvaccine.model.Student
import com.google.firebase.database.DatabaseReference
import java.text.SimpleDateFormat


class ViewDeleteFragment : Fragment() {

    private var _binding: FragmentViewDeleteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ViewDeleteViewModel::class.java)

        _binding = FragmentViewDeleteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var database: DatabaseReference = Datastore.STUDENT.get()

        database.get().addOnSuccessListener {
            it.children.forEach { x->
                var student: Student? = x.getValue(Student::class.java)
                Log.i("Tag Student", student.toString())

                val cardContainer: LinearLayout? = binding.cardContainer

                val cardItem: View =
                    LayoutInflater.from(requireContext()).inflate(R.layout.student_card_item, null, false)

                val studentIdView: TextView = cardItem.findViewById<View>(R.id.studentId) as TextView
                val studentNameView: TextView = cardItem.findViewById<View>(R.id.studentName) as TextView
                val studentClassView: TextView = cardItem.findViewById<View>(R.id.studentClass) as TextView
                val studentContactView: TextView = cardItem.findViewById<View>(R.id.studentContact) as TextView
                val studentGenderView: TextView = cardItem.findViewById<View>(R.id.studentGender) as TextView
                val studentDOBView: TextView = cardItem.findViewById<View>(R.id.studentDOB) as TextView

                studentIdView.text = student!!.studentId.toString()
                studentNameView.text = student!!.studentName.toString()
                studentClassView.text = "Class: " + student!!.studentClass.toString()
                studentContactView.text = "Contact: " + student!!.studentContact.toString()
                studentGenderView.text = "Gender: " + student!!.studentGender.toString()
                studentDOBView.text = "Date of Birth: " + SimpleDateFormat("dd/MM/yyyy").format(student!!.studentDOB)

                val expandButton: ImageView = cardItem.findViewById<View>(R.id.expand) as ImageView
                val toggleLayout: LinearLayout = cardItem.findViewById<View>(R.id.toggleLayout) as LinearLayout
                val deleteButtonView: Button = cardItem.findViewById<View>(R.id.delete) as Button

                expandButton.setOnClickListener{
                    if (View.VISIBLE == toggleLayout.visibility) {
                        TransitionManager.beginDelayedTransition(toggleLayout, AutoTransition())
                        toggleLayout.visibility = View.GONE
                        expandButton.setImageResource(android.R.drawable.arrow_down_float)
                    } else {
                        TransitionManager.beginDelayedTransition(toggleLayout, AutoTransition())
                        toggleLayout.visibility = View.VISIBLE
                        expandButton.setImageResource(android.R.drawable.arrow_up_float)
                    }
                }

                deleteButtonView.setOnClickListener{
                    database.child(student.studentId.toString()).removeValue()
                    cardContainer!!.removeView(cardItem)
                    Toast.makeText(requireContext(),"Student ${student.studentName} Deleted", Toast.LENGTH_SHORT).show()
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