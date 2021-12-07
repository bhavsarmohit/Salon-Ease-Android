package com.caprusdigi.salon_ease

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import com.caprusdigi.salon_ease.databinding.ActivityFillDetailsVendorSalonRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class fillDetailsVendorSalonRegister : AppCompatActivity() {


    //    view binding
    private lateinit var binding: ActivityFillDetailsVendorSalonRegisterBinding


    lateinit var number: String

    private lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

//    defining and linking components global
//    var etName: TextInputEditText? = null
//    var etMobile: TextInputEditText? = null
//    var etAddress: TextInputEditText? = null
//    var etCity: TextInputEditText? = null


    lateinit var mobileno: String

    lateinit var name: String
    lateinit var mobile: String
    lateinit var address: String
    lateinit var city: String

    lateinit var progressDialog: ProgressDialog


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_details_vendor_salon_register)


        //        ==========/////
//        binding = ActivitySignUpBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_fill_details_user_register)
        binding = ActivityFillDetailsVendorSalonRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        supportActionBar?.hide()


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()

//        update ui data from firebase to EditText Mobile
//        binding.etMobile.setText(numberFir)
//        binding.etMobile.isEnabled = false

//        etName = findViewById(R.id.et_name)
//        etMobile = findViewById(R.id.et_mobile)
//        etAddress = findViewById(R.id.et_address)
//        etCity = findViewById(R.id.et_city)


//        val btnSubmit: Button = findViewById(R.id.btn_submit)
//
//        val imgbglogo: ImageView = findViewById(R.id.img_bg1)


//        mobileno = intent.getStringExtra("mobileNo").toString()
//        val tvmobile: TextView = findViewById(R.id.tv_mobile)
//        etMobile.setText(mobileno)

//        name = etName!!.text.toString()
//        mobile = etMobile!!.text.toString()
//        address = etAddress!!.text.toString()
//        city = etCity!!.text.toString()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)




//        Retrieve Data from firebase and put on Form===========
        var myRefName = database!!.getReference("Users/$numberFir/Vendor/Profile/Salon_Name")
        // Read from the database
        myRefName.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                binding.etSalonName.setText(snapshot.getValue<String>())
                Log.d(ContentValues.TAG, "name is: " + snapshot.getValue<String>())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })
        var myRefMobile = database!!.getReference("Users/$numberFir/Vendor/Profile/Mobile")
        // Read from the database
        myRefMobile.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                binding.etMobile.setText(snapshot.getValue<String>())
                Log.d(ContentValues.TAG, "Mobile is: " + snapshot.getValue<String>())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })
        var myRefAddress = database!!.getReference("Users/$numberFir/Vendor/Profile/Address")
        // Read from the database
        myRefAddress.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                binding.etAddress.setText(snapshot.getValue<String>())
                Log.d(ContentValues.TAG, "Address is: " + snapshot.getValue<String>())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })
        var myRefCity = database!!.getReference("Users/$numberFir/Vendor/Profile/City")
        // Read from the database
        myRefCity.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                binding.etCity.setText(snapshot.getValue<String>())
                Log.d(ContentValues.TAG, "City is: " + snapshot.getValue<String>())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })


//        ===========

//
//        progressDialog = ProgressDialog(this)
//        progressDialog.setTitle("Please Wait")
//        progressDialog.setCanceledOnTouchOutside(false)

//        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){













        binding.btnRegister.setOnClickListener{

            name = binding.etSalonName!!.text.toString()
            mobile = binding.etMobile!!.text.toString()
            address = binding.etAddress!!.text.toString()
            city = binding.etCity!!.text.toString()

            if(name.isNotEmpty() && mobile.isNotEmpty() && address.isNotEmpty() && city.isNotEmpty()){
                progressDialog.setMessage("Creating Account...")
                progressDialog.show()

                // Write a message to the database
                val database = Firebase.database

//        create user structure
//        var myRef = database.getReference("Users/")
//        myRef.setValue(mobile)

                // receives logged in user mobile
                val fireCustomDef = FirebaseCustomDefinitions()
                val numberFir = fireCustomDef.getLoggedInProfileMobile()


//            Toast.makeText(this,"mobile:"+mobile+"name:"+name, Toast.LENGTH_SHORT).show()

                var myRef = database.getReference("Users/$numberFir/Vendor/Profile/Salon_Name")
                myRef.setValue(name)

                myRef = database.getReference("Users/$numberFir/Vendor/Profile/Mobile")
                myRef.setValue(mobile)

                myRef = database.getReference("Users/$numberFir/Vendor/Profile/Address")
                myRef.setValue(address)

                myRef = database.getReference("Users/$numberFir/Vendor/Profile/City")
                myRef.setValue(city)

                progressDialog.dismiss()

//                ===
                myRef = database.getReference("Users/$numberFir/Vendor/Salon_At_Home/Status")
                myRef.setValue("DISABLE")

                myRef = database.getReference("Users/$numberFir/Vendor/Spa_Station/Status")
                myRef.setValue("DISABLE")

                myRef = database.getReference("Users/$numberFir/Vendor/Salon_Station/Status")
                myRef.setValue("DISABLE")

//                ===
                
                

//        if successful write data to firebase
                val intent = Intent(this, chooseYourself::class.java)
                startActivity(intent)
            }else{

            }


        }


//        animations
        val animationFadeIn = AnimationUtils.loadAnimation(this, R.animator.anim_fade_in)
        binding.imgBgTop.startAnimation(animationFadeIn)
        val animationMoveDownIn = AnimationUtils.loadAnimation(this, R.animator.anim_move_down_in)
        binding.imgBgTop.startAnimation(animationMoveDownIn)

        val animationMoveLeftIn = AnimationUtils.loadAnimation(this,R.animator.anim_move_left_in)
//        etName.startAnimation(animationMoveLeftIn)
//        etEmail.startAnimation(animationMoveLeftIn)
//        etMobile.startAnimation(animationMoveLeftIn)
//        etPass.startAnimation(animationMoveLeftIn)
        binding.btnRegister.startAnimation(animationMoveLeftIn)



    }


    override fun onStart() {
//        overridePendingTransition(0, 0)

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        super.onStart()
    }

    override fun onPause() {
        if (isFinishing) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        super.onPause()
    }



}