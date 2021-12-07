package com.example.salon_ease

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseCustomDefinitions {


    private lateinit var auth: FirebaseAuth

    lateinit var number: String

    lateinit var value: String

    // Write a message to the database
    val database = Firebase.database



    ////////////////////////////////////////////////////

     fun getLoggedInProfileMobile(): String {

         number = ""

        auth=FirebaseAuth.getInstance()

//        get current user
        val firebaseUser = auth.currentUser
        if(firebaseUser == null){
//            logged out

        }else{
//            logged in get phone number and return
            number = firebaseUser.phoneNumber.toString()

        }
        return number
    }

    ////////////////////////////////////////////////////


//    fun getReceiveUserCustomDataFirebase(path: String): String {
//        value = ""
//
//        var myRef = database.getReference("Users/"+path)
//        // Read from the database
//        myRef.addValueEventListener(object: ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                value = snapshot.getValue<String>().toString()
//                Log.d(TAG, "Value is: " + value)
//
//
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "Failed to read value.", error.toException())
////                SingleToast.show(this, "Please enter OTP!", Toast.LENGTH_LONG);
//
////                value = null.toString()
//            }
//
//
//        })
//        return value
//
//    }

}