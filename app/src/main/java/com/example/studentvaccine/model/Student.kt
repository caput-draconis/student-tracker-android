package com.example.studentvaccine.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

data class Student(val studentId: Int? = null, val studentName: String? = null, val studentClass: Int? = null, val studentContact: Long? = null, val studentGender: String? = null, val studentDOB: Date? = null) {


}

