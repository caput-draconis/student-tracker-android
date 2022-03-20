package com.example.studentvaccine.firebasedb

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

enum class Datastore(var nodeName:String) {
    STUDENT("student"),
    VACCINE("vaccine_status");

    fun get(): DatabaseReference {
        if(this.databaseReference == null){
            val database = Firebase.database("https://vaccinetracker-725-default-rtdb.asia-southeast1.firebasedatabase.app/")
            this.databaseReference = database.reference.child(nodeName)
        }
        return this.databaseReference!!
    }

    private var databaseReference:DatabaseReference? = null;
}