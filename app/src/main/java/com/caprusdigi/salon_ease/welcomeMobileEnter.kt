package com.caprusdigi.salon_ease

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.caprusdigi.salon_ease.databinding.ActivityWelcomeMobileEnterBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class welcomeMobileEnter : AppCompatActivity() {

    //    view binding
    private lateinit var binding: ActivityWelcomeMobileEnterBinding

//    private lateinit var auth: FirebaseAuth

    private lateinit var auth: FirebaseAuth

    private lateinit var database: DatabaseReference

    lateinit var progressDialog: ProgressDialog

    // we will use this to match the sent otp from firebase
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    //    defining and linking components global
    var etMobile: TextInputEditText? = null

    // this stores the phone number of the user
    var number : String =""

// ...
// Initialize Firebase Auth
//    auth = Firebase.auth

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

//        setContentView(R.layout.activity_welcome_mobile_enter)
        binding = ActivityWelcomeMobileEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

//        check account session
        val user = FirebaseAuth.getInstance().currentUser
//        SingleToast.show(this, "User Logged:$user", Toast.LENGTH_LONG);
        if (user != null) {
            //User is Logged in
            val intent = Intent(this, chooseYourself::class.java)
            startActivity(intent)
            finish()
        } else {
            //No User is Logged in
        }




        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

//        auth = Firebase.auth


//        val btnSignIn: Button = findViewById(R.id.btn_signIn)
        val imgbg: ImageView = findViewById(R.id.img_bg1)
//        val tvSignUp: TextView = findViewById(R.id.tv_signUp)
        etMobile = findViewById(R.id.et_mobile)
        val checkBox_aggrement: CheckBox = findViewById(R.id.cb_iAcceptThe)

        val btnContinue: MaterialButton = findViewById(R.id.btn_continue)
//        val etPass: TextInputEditText = findViewById(R.id.et_password)
//        val tvForgotpass: TextView = findViewById(R.id.tv_forgotPassword)

//        val etUsrnm = findViewById<TextInputEditText>(R.id.et_username)
//        val etPass = findViewById<TextInputEditText>(R.id.et_password)





            btnContinue.setOnClickListener {
                number = etMobile!!.text.toString()

                var numeric = true
                numeric = number.matches("-?\\d+(\\.\\d+)?".toRegex())

//                if input is numeric
                if(numeric){

//                    if number length is 10
                    if(number.length == 10) {

                        // if checkbox is ticked
                        if (checkBox_aggrement.isChecked) {
                            number = "+91"+number
//                            SingleToast.show(this, "$number", Toast.LENGTH_LONG);

                            progressDialog.setMessage("Sending OTP...")
                            progressDialog.show()

                            login()
//                             progressDialog.dismiss()




                        } else {
                            SingleToast.show(this, "Please accept our Terms and Conditions and Privacy Policy!", Toast.LENGTH_LONG);
                        }
                    }else{
                        SingleToast.show(this, "Please enter 10 Digit Mobile Number!", Toast.LENGTH_LONG);

                    }

                }else{
                    SingleToast.show(this, "Please enter Valid Mobile Number!", Toast.LENGTH_LONG);

                }

            }


        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // This method is called when the verification is completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                number = etMobile!!.text.toString()
//                if client is new then follow add details page
//                    ..

//                if client is already filled details then move to dashboard
                progressDialog.dismiss()
                startActivity(Intent(applicationContext, otpVerification::class.java))
                finish()
                Log.d("GFG" , "onVerificationCompleted Success")
            }

            // Called when verification is failed add log statement to see the exception
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("GFG" , "onVerificationFailed  $e")
                SingleToast.show(this@welcomeMobileEnter, "Something went wrong please try after Some Time!", Toast.LENGTH_LONG);
                progressDialog.dismiss()

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    SingleToast.show(this@welcomeMobileEnter, "Invalid Request!", Toast.LENGTH_LONG);

                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    SingleToast.show(this@welcomeMobileEnter, "The SMS quota of Salon Ease is Exceeded!", Toast.LENGTH_LONG);

                }


            }

            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("GFG","onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token

                // Start a new activity using intent
                // also send the storedVerificationId using intent
                // we will use this id to send the otp back to firebase
                progressDialog.dismiss()
                val intent = Intent(applicationContext,otpVerification::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
//                number = etMobile!!.text.toString()
                intent.putExtra("mobileNo",etMobile!!.text.toString())
                startActivity(intent)
                finish()
            }
        }



        val animationFadeIn = AnimationUtils.loadAnimation(this, R.animator.anim_fade_in)
        imgbg.startAnimation(animationFadeIn)
        val animationMoveDownIn = AnimationUtils.loadAnimation(this, R.animator.anim_move_down_in)
        imgbg.startAnimation(animationMoveDownIn)

        val animationMoveLeftIn = AnimationUtils.loadAnimation(this,R.animator.anim_move_left_in)
//        etMobile.startAnimation(animationMoveLeftIn)
        btnContinue.startAnimation(animationMoveLeftIn)
//        btnSignIn.startAnimation(animationMoveLeftIn)





    }

    private fun login() {
//        number = findViewById<TextInputLayout>(R.id.et_mobile).toString()
//        var number : String
//        number = "8180992103"

//        number = etMobile!!.text.toString()


        // get the phone number from edit text and append the country cde with it
        if (number.isNotEmpty()){
//            number = "+91$number"
            Toast.makeText(this,"$number", Toast.LENGTH_SHORT).show()

            sendVerificationCode(number)
        }else{
            SingleToast.show(this, "Please enter Valid Mobile Number!", Toast.LENGTH_LONG);
        }
    }

    // this method sends the verification code
    // and starts the callback of verification
    // which is implemented above in onCreate
    private fun sendVerificationCode(number: String) {




        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("GFG" , "Auth started")

    }



    override fun onStart() {
//        overridePendingTransition(0, 0)

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
////            reload();
//            val intent = Intent(this, forgotPassword::class.java)
//            startActivity(intent)
//        }

        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            reload();
//        }
//        updateUI(currentUser)

    }
//
//    private fun updateUI(currentUser: FirebaseUser?) {
//        TODO("Not yet implemented")
//    }

    override fun onPause() {
        if (isFinishing) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        super.onPause()
    }




}




















