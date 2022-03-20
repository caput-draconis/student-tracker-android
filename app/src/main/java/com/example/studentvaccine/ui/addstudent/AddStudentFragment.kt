package com.example.studentvaccine.ui.addstudent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studentvaccine.databinding.FragmentAddStudentBinding
import com.example.studentvaccine.firebasedb.Datastore
import com.example.studentvaccine.model.Student
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.DatabaseReference
import java.text.SimpleDateFormat
import java.util.*

class AddStudentFragment : Fragment() {

    private var _binding: FragmentAddStudentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(AddStudentViewModel::class.java)

        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val studentName: EditText = binding.studentName
        val studentClass: EditText = binding.studentClass
        val studentContact: EditText = binding.studentContact
        val studentGender: RadioGroup = binding.studentGender
        val studentDOB: EditText = binding.studentDOB

        binding.studentDOB.setOnClickListener{
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Date of birth")
                    .build()
            Log.i("Tag","Clicked DOB")
            datePicker.addOnPositiveButtonClickListener {
                var dateOfBirth: Date = Date(datePicker.selection!!)
                studentDOB.setText(SimpleDateFormat("dd/MM/yyyy").format(dateOfBirth))
            }
            datePicker.show(requireParentFragment().parentFragmentManager, "tag")

        }

        studentName.doOnTextChanged { text, start, before, count ->
            Log.i("Text tag", text.toString())
            val pattern = Regex("^[\\p{L} .'-]+$")
            Log.i("Input check tag ", pattern.containsMatchIn(text.toString()).toString())
            if(!pattern.containsMatchIn(text.toString())){
                // error
                binding.studentNameField.error = "Only alphabets and spaces are allowed"
            }
            else{
                binding.studentNameField.error = null
            }
        }

        studentClass.doOnTextChanged{ text, start, before, count ->
            val pattern = Regex("^[1-9]$|[1][0-2]{1,2}$")
            if(!pattern.containsMatchIn(text.toString())){
                // error
                binding.studentClassField.error = "Please enter input from 1 through 12"
            }
            else{
                binding.studentClassField.error = null
            }
        }

        studentContact.doOnTextChanged{ text, start, before, count ->
            val pattern = Regex("^[0-9]{10}$")
            if(!pattern.containsMatchIn(text.toString())){
                // error
                binding.studentContactField.error = "Please enter 10 digits"
            }
            else{
                binding.studentContactField.error = null
            }
        }
        binding.button.setOnClickListener {

            if(binding.studentContactField.error != null || binding.studentClassField.error != null ||
                binding.studentNameField.error != null ||
                studentGender.checkedRadioButtonId == -1 ||
                studentDOB.text.toString().equals("")
            ){
                Toast.makeText(requireContext(),"Please provide valid Inputs",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var radioButton: RadioButton = requireParentFragment().requireActivity().findViewById(studentGender.checkedRadioButtonId)

             var database: DatabaseReference = Datastore.STUDENT.get()

            database.orderByKey().limitToLast(1).get().addOnSuccessListener {

                var studentLast: Student? = it.children.iterator().next().getValue(Student::class.java)
                Log.i("Data last ID","Got value ${studentLast!!.studentId} ")
                var newId: Int = studentLast.studentId!!.plus(1)

                val student: Student = Student(newId,studentName.text.toString(),
                    Integer.parseInt(studentClass.text.toString()),
                    studentContact.text.toString().toLong(),
                    radioButton.text.toString(),
                    SimpleDateFormat("dd/MM/yyyy").parse(studentDOB.text.toString())
                )
                database.child(newId.toString()).setValue(student)

                Toast.makeText(requireContext(),"Student Created",Toast.LENGTH_SHORT).show()

            }

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}