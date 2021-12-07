package com.example.salon_ease

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.salon_ease.dashboard_customer.dashboard_customer
import com.example.salon_ease.databinding.ActivityOtpVerificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class otpVerification : AppCompatActivity() {

    //    view binding
    private lateinit var binding: ActivityOtpVerificationBinding


    private lateinit var auth: FirebaseAuth

    var database : FirebaseDatabase? = null


    lateinit var progressDialog: ProgressDialog

    lateinit var number: String
    var checkUserRegisteredStringMobile: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_otp_verification)
        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth=FirebaseAuth.getInstance()


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        number = intent.getStringExtra("mobileNo").toString()
//        val tvmobile: TextView = findViewById(R.id.tv_mobileno)

//        set mobile to textview
        binding.tvMobileno.setText(number)
//        disable textview
        binding.tvMobileno.setFocusable(false)

//        tvmobile.setText(mobileno)



        // get storedVerificationId from the intent
        val storedVerificationId= intent.getStringExtra("storedVerificationId")

        // fill otp and call the on click on button
        binding.btnSubmit.setOnClickListener {

            progressDialog.setMessage("Verifying OTP...")
            progressDialog.show()

            var otp = binding.etOTP!!.text.toString()
            if(otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                SingleToast.show(this, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        }

    }





    // verifies if the code matches sent by firebase
    // if success start the new activity in our case it is main Activity
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {



        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // receives logged in user mobile
                    val fireCustomDef = FirebaseCustomDefinitions()
                    val numberFir = fireCustomDef.getLoggedInProfileMobile()

                    val rootRef = FirebaseDatabase.getInstance().reference
                    val userNameRef = rootRef.child("Users/$numberFir/Mobile")
//                    var registerUser: Boolean = false
                    val eventListener: ValueEventListener = object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            if (!dataSnapshot.exists()) {

                                if(dataSnapshot.getValue<String>() == null) {

                                    //create new user
                                    Log.d(TAG, "check return123if:"+dataSnapshot.getValue<String>()) //Don't ignore errors!

                                    // Write a message to the database
                                    val database = Firebase.database
                                    var addMobdb =
                                        database.getReference("Users/" + numberFir + "/Mobile")
                                    addMobdb.setValue(number)

                                    progressDialog.dismiss()
                                    moveUserToRegisterActivity(numberFir)

//                                moveUserToNextActivity(numberFir,true)
                                    Log.d(TAG, "registerUser:" + numberFir) //Don't ignore errors!
                                }
//                            }
                            else{
                                    Log.d(TAG, "check return123else:"+dataSnapshot.getValue<String>()) //Don't ignore errors!

                                    progressDialog.dismiss()
                                    moveUserToCustomerActivity()
                                Log.d(TAG, "move to dashboard:"+numberFir) //Don't ignore errors!

                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.d(TAG, databaseError.message) //Don't ignore errors!
                        }
                    }
                    userNameRef.addListenerForSingleValueEvent(eventListener)


//                    set Name to Nav Header===========
                    Log.d(ContentValues.TAG, "Mobile is 21: " + numberFir)

//                    var myRef = database!!.getReference("Users/"+numberFir+"/Mobile")
//                    // Read from the database
//                    myRef.addValueEventListener(object: ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            // This method is called once with the initial value and again
//                            // whenever data at this location is updated.
////                            val navHeader_name: TextView = binding.navigationView.getHeaderView(0).findViewById(R.id.tv_name)
////                            navHeader_name.setText(snapshot.getValue<String>())
////                            checkUserRegisteredStringMobile = snapshot.getValue<String>().toString()
//                            if(snapshot.getValue<String>() == null){
//                                progressDialog.dismiss()
//                                moveUserToRegisterActivity(numberFir)
//                            }else if(snapshot.getValue<String>() == numberFir){
//                                progressDialog.dismiss()
//                                moveUserToDashboardActivity()
//                            }
//                            Log.d(ContentValues.TAG, "Value is: " + checkUserRegisteredStringMobile)
//                        }
//                        override fun onCancelled(error: DatabaseError) {
//                            Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
////                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
//                        }
//                    })
////        ===========
////                    val result = checkUserRegisteredStringMobile.contains("")
//                    SingleToast.show(this, "value:$checkUserRegisteredStringMobile", Toast.LENGTH_LONG);


//                    Log.d(TAG, "registerUser outer:"+registerUser) //Don't ignore errors!
//
//                    if(registerUser){
//                        Log.w(ContentValues.TAG, "null condition checked...")
//////                        open register user data page and upload user data to firebase
////
////                        // Write a message to the database
//                        val database = Firebase.database
//                        var myRef = database.getReference("Users/"+numberFir+"/Mobile")
//                        myRef.setValue(number)
//
//                        val intent = Intent(this , fillDetailsUserRegister::class.java)
////                    intent.putExtra("mobileNo",mobileno)
//                        startActivity(intent)
//                        finish()
//                    }else{
////                        open dashboard
//                        val intent = Intent(this , dashboard_vendor::class.java)
////                    intent.putExtra("mobileNo",mobileno)
//                        startActivity(intent)
//                        finish()
//                    }

                    // Write a message to the database
//                    val database = Firebase.database
//                    var myRef = database.getReference("Users/"+numberFir+"/Mobile")
//                    myRef.setValue(number)





//                    val intent = Intent(this , fillDetailsUserRegister::class.java)
////                    intent.putExtra("mobileNo",mobileno)
//                    startActivity(intent)
//                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                }
            }
//        progressDialog.dismiss()
    }

    private fun moveUserToCustomerActivity() {
        val intent = Intent(this , dashboard_customer::class.java)
//                    intent.putExtra("mobileNo",mobileno)
        startActivity(intent)
        finish()
    }

    private fun moveUserToRegisterActivity(numberFir: String) {
        Log.w(ContentValues.TAG, "null condition checked...")
////                        open register user data page and upload user data to firebase
//
//                        // Write a message to the database
        val database = Firebase.database
        var myRef = database.getReference("Users/"+numberFir+"/Mobile")
        myRef.setValue(number)

        val intent = Intent(this , fillDetailsUserRegister::class.java)
//                    intent.putExtra("mobileNo",mobileno)
        startActivity(intent)
        finish()
    }


}