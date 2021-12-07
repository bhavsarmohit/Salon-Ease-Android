package com.caprusdigi.salon_ease

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.caprusdigi.salon_ease.dashboard_vendor.dashboard_vendor
import com.caprusdigi.salon_ease.databinding.ActivityChooseYourselfBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class chooseYourself : AppCompatActivity() {

    //    view binding
    private lateinit var binding: ActivityChooseYourselfBinding

//    lateinit var progressDialog: ProgressDialog

    lateinit var number: String


    private lateinit var auth: FirebaseAuth

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_choose_yourself)
        binding = ActivityChooseYourselfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
//        checkUser()



        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)


//        val vendor: CardView = findViewById(R.id.cv_vendor)
//        val customer: CardView = findViewById(R.id.cv_customer)



//        auth = FirebaseAuth.getInstance()
//
//        progressDialog = ProgressDialog(this)
//        progressDialog.setTitle("Please Wait...")
//        progressDialog.setCanceledOnTouchOutside(false)
//
//
//        // receives logged in user mobile
//        val fireCustomDef = FirebaseCustomDefinitions()
//        val numberFir = fireCustomDef.getLoggedInProfileMobile()
//
//        val rootRef = FirebaseDatabase.getInstance().reference
//        val userNameRef = rootRef.child("Users/$numberFir/Mobile")
////                    var registerUser: Boolean = false
//        val eventListener: ValueEventListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
////                            if (!dataSnapshot.exists()) {
//
//                if(dataSnapshot.getValue<String>() == null) {
//
//                    //create new user
//                    Log.d(ContentValues.TAG, "check return123if:"+dataSnapshot.getValue<String>()) //Don't ignore errors!
//
//                    // Write a message to the database
//                    val database = Firebase.database
//                    var addMobdb =
//                        database.getReference("Users/" + numberFir + "/Mobile")
//                    addMobdb.setValue(number)
//
//                    progressDialog.dismiss()
////                    moveUserToRegisterActivity(numberFir)
////                    SingleToast.show(this, "", Toast.LENGTH_LONG);
//
//
////                                moveUserToNextActivity(numberFir,true)
//                    Log.d(ContentValues.TAG, "registerUser:" + numberFir) //Don't ignore errors!
//                }
////                            }
//                else{
//                    Log.d(ContentValues.TAG, "check return123else:"+dataSnapshot.getValue<String>()) //Don't ignore errors!
//
//                    progressDialog.dismiss()
////                    moveUserToChooseYourselfActivity()
//                    Log.d(ContentValues.TAG, "move to dashboard:"+numberFir) //Don't ignore errors!
//
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.d(ContentValues.TAG, databaseError.message) //Don't ignore errors!
//            }
//        }
//        userNameRef.addListenerForSingleValueEvent(eventListener)




        binding.cvVendor.setOnClickListener{


            progressDialog.setMessage("Checking Vendor Profile...")
            progressDialog.show()


            // receives logged in user mobile
            val fireCustomDef = FirebaseCustomDefinitions()
            val numberFir = fireCustomDef.getLoggedInProfileMobile()

            // Write a message to the database
            val database = Firebase.database


//            ==============


//            // receives logged in user mobile
//            val fireCustomDef = FirebaseCustomDefinitions()
//            val numberFir = fireCustomDef.getLoggedInProfileMobile()

            val rootRef = FirebaseDatabase.getInstance().reference
            val userNameRef = rootRef.child("Users/$numberFir/Vendor/Profile/Salon_Name")
//                    var registerUser: Boolean = false
            val eventListener: ValueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            if (!dataSnapshot.exists()) {

                    if(dataSnapshot.getValue<String>() == null) {

                        //create new user
                        Log.d(ContentValues.TAG, "check return123if:"+dataSnapshot.getValue<String>()) //Don't ignore errors!
//
//                        // Write a message to the database
//                        val database = Firebase.database
//                        var addMobdb =
//                            database.getReference("Users/" + numberFir + "/Mobile")
//                        addMobdb.setValue(number)

                        progressDialog.dismiss()
                        moveVendorSalonToRegisterActivity()

//                                moveUserToNextActivity(numberFir,true)
                        Log.d(ContentValues.TAG, "registerUser:" + numberFir) //Don't ignore errors!
                    }
//                            }
                    else{
                        Log.d(ContentValues.TAG, "check return123else:"+dataSnapshot.getValue<String>()) //Don't ignore errors!

                        progressDialog.dismiss()
                        moveUserToVendorActivity()
                        Log.d(ContentValues.TAG, "move to dashboard:"+numberFir) //Don't ignore errors!

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(ContentValues.TAG, databaseError.message) //Don't ignore errors!
                }
            }
            userNameRef.addListenerForSingleValueEvent(eventListener)

//            ==============




            var myRef = database.getReference("Vendors/Applications/$numberFir")
            myRef.setValue("APPROVED")

//            val intent = Intent(this, dashboard_vendor::class.java)
//            startActivity(intent)
        }

        binding.cvCustomer.setOnClickListener{
//            val intent = Intent(this, dashboard_customer::class.java)
//            startActivity(intent)

            SingleToast.show(this, "Locked!", Toast.LENGTH_LONG)


        }
    }

    private fun checkUser(){
//        get current user
        val firebaseUser = auth.currentUser
        if(firebaseUser == null){
//            logged out
            startActivity(Intent(this, welcomeMobileEnter::class.java))
            finish()
        }else{
//            logged in get phone number of user
            val phone = firebaseUser.phoneNumber
//            set phone number
//            binding.mobiletv.text = phone
            SingleToast.show(this, "Logged In:"+phone, Toast.LENGTH_LONG);

        }
    }

    override fun onStart() {
        super.onStart()
//        checkUser()
    }


    private fun moveUserToVendorActivity() {
        val intent = Intent(this , dashboard_vendor::class.java)
//                    intent.putExtra("mobileNo",mobileno)
        startActivity(intent)
        finish()
    }

    private fun moveVendorSalonToRegisterActivity() {
        Log.w(ContentValues.TAG, "null condition checked...")
////                        open register user data page and upload user data to firebase
//
//                        // Write a message to the database
//        val database = Firebase.database
//        var myRef = database.getReference("Users/"+numberFir+"/Mobile")
//        myRef.setValue(number)

        val intent = Intent(this , fillDetailsVendorSalonRegister::class.java)
//                    intent.putExtra("mobileNo",mobileno)
        startActivity(intent)
        finish()
    }


}