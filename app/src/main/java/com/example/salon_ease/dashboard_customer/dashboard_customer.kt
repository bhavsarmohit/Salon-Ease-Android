package com.example.salon_ease.dashboard_customer

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.salon_ease.FirebaseCustomDefinitions
import com.example.salon_ease.R
import com.example.salon_ease.SingleToast
import com.example.salon_ease.dashboard_vendor.dashboard_vendor
import com.example.salon_ease.dashboard_vendor.fragments.vendor_EditProfile
import com.example.salon_ease.dashboard_vendor.fragments.vendor_Home
import com.example.salon_ease.dashboard_vendor.fragments.vendor_Notifications
import com.example.salon_ease.dashboard_vendor.fragments.vendor_termsAndConditions
import com.example.salon_ease.databinding.ActivityDashboardCustomerBinding
import com.example.salon_ease.welcomeMobileEnter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class dashboard_customer : AppCompatActivity() {


    //    view binding
    private lateinit var binding: ActivityDashboardCustomerBinding


    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    lateinit var number: String
    var name: String? = null

    //
    lateinit var toggle: ActionBarDrawerToggle


    lateinit var progressDialog: ProgressDialog

//    lateinit var drawerLayout: DrawerLayout
//    lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_dashboard_customer)
//        supportActionBar?.hide()

        binding = ActivityDashboardCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

//        loadProfile()


//        checkUser()

        // Write a message to the database
        val database = Firebase.database

        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()

//        updateNavMenuVendorModeText()


//        val builder = AlertDialog.Builder(this)
//        // Create the AlertDialog
//        var alertDialog: AlertDialog = builder.create()
//        // Set other dialog properties
//        alertDialog.setCancelable(false)

//        set navbar menu title
//        binding.navigationView.menu.findItem(R.id.nav_forVendorApplyOpen).setTitle("testing")

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

//        logout button click logout the user
//        binding.logoutBtn.setOnClickListener{
//            firebaseAuth.signOut()
//            checkUser()
//        }

//        drawerLayout = findViewById(R.id.vendor_drawerLayout)
//        navView = findViewById(R.id.navigationView)

        toggle =
            ActionBarDrawerToggle(this, binding.customerDrawerLayout, R.string.open, R.string.close)

        binding.customerDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        replaceFragment(vendor_Home(), "Home")

//        add listeners to nav list menu
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {


                R.id.nav_home -> replaceFragment(vendor_Home(), it.title.toString())
                R.id.nav_editProfile -> replaceFragment(vendor_EditProfile(), it.title.toString())

//                R.id.nav_editProfile -> {
//                intent = Intent(this, editProfile::class.java)
//                startActivity(intent) }

//                sign out firebase authentication

                R.id.nav_forVendorApplyOpen -> {


//        set Name to Nav Header===========

////        check if customer already applied for vendor access
                    var myRef = database.getReference("Vendors/Applications/$numberFir")
                    var alertShow: Boolean = false
                    // Read from the database
                    myRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

//                if data not found
                            if (dataSnapshot.getValue<String>() == null) {

                                Log.d(
                                    ContentValues.TAG,
                                    "check IFNULL:" + numberFir
                                ) //Don't ignore errors!
                                Log.d(
                                    ContentValues.TAG,
                                    "check return123else:" + dataSnapshot.getValue<String>()
                                ) //Don't ignore errors!



//                                sample dialog
//                                val dialog = AlertDialog.Builder(this).setTitle("Kotlin Study").setMessage("Alert Dialog")
//                                    .setPositiveButton("Confirm", { dialog, i ->
//                                        Toast.makeText(this@MainActivity, "Hello Friends", Toast.LENGTH_LONG).show()
//                                    })
//                                    .setNegativeButton("Cancel", { dialog, i -> })
//                                dialog.show()


                                if(alertShow == false) {
                                    alertShow = true
                                    val dialog = AlertDialog.Builder(this@dashboard_customer)
                                        .setTitle("Confirm")
                                        .setMessage("Are you really wan't to Apply for Vendor!")
                                        .setPositiveButton("Yes" , { dialog, i ->
                                            //                                        Toast.makeText(this@dashboard_customer, "Hello Friends", Toast.LENGTH_LONG).show()


                                            // receives logged in user mobile
                                            val fireCustomDef = FirebaseCustomDefinitions()
                                            val numberFir = fireCustomDef.getLoggedInProfileMobile()

                                            var myRef =
                                                database.getReference("Vendors/Applications/$numberFir")
                                            myRef.setValue("PENDING")

                                            Toast.makeText(
                                                applicationContext,
                                                "Applied Successful",
                                                Toast.LENGTH_LONG
                                            ).show()

                                        })
                                        .setNegativeButton("Cancel", { dialog, i ->

                                            Toast.makeText(
                                                applicationContext,
                                                "Canceled",
                                                Toast.LENGTH_LONG
                                            ).show()


                                        })
                                    dialog.show()
                                }



                            }else if (dataSnapshot.getValue<String>() == "PENDING") {

                                //create new user
                                Log.d(
                                    ContentValues.TAG,
                                    "check return123if:" + dataSnapshot.getValue<String>()
                                ) //Don't ignore errors!



                                if(alertShow == false) {
                                    alertShow = true
                                    val dialog = AlertDialog.Builder(this@dashboard_customer)
                                        .setTitle("Message")
                                        .setMessage("Already applied for Vendor Access, please wait for Salon Ease Approval")
                                        .setNeutralButton("Ok", { dialog, i ->

                                        })
                                    dialog.show()

                                    Log.d(
                                        ContentValues.TAG,
                                        "registerUser:" + numberFir
                                    ) //Don't ignore errors!
                                }



                            }else if (dataSnapshot.getValue<String>() == "APPROVED") {
                                openVendorDashboard()
                            }


                            updateNavMenuVendorModeText()
                        }



                        override fun onCancelled(error: DatabaseError) {
                        Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
                    }
                })

//=========----00000

                }


                R.id.nav_signOut -> {
                    auth.signOut()
                    intent = Intent(this, welcomeMobileEnter::class.java)
                    startActivity(intent)
//                    checkUser()
                }

                R.id.nav_notifications -> replaceFragment(
                    vendor_Notifications(),
                    it.title.toString()
                )
                R.id.nav_termsAndConditions -> replaceFragment(
                    vendor_termsAndConditions(),
                    it.title.toString()
                )

//                R.id.nav_home -> replaceFragment(vendor_termsAndConditions(),it.title.toString())
//                R.id.nav_home -> replaceFragment(vendor_termsAndConditions(),it.title.toString())


            }
            true
        }

//        nav_toogle_menu_custom.setOnClickListener {
//            openCloseNavigationDrawer()
//        }


        // receives logged in user mobile
//        val usrName = fireCustomDef.getReceiveUserCustomDataFirebase(numberFir+"/Name")

//        update ui data from firebase to nav header
        val navHeader_mob: TextView =
            binding.navigationView.getHeaderView(0).findViewById(R.id.tv_mobile)
        navHeader_mob.text = numberFir


//        set Name to Nav Header===========
        var myRef = database.getReference("Users/" + numberFir + "/Name")
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val navHeader_name: TextView =
                    binding.navigationView.getHeaderView(0).findViewById(R.id.tv_name)
                navHeader_name.text = snapshot.getValue<String>()
                Log.d(ContentValues.TAG, "name is: " + snapshot.getValue<String>())
//
//                name = snapshot.getValue<String>().toString()


            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })


//        ===========


    }

    private fun openVendorDashboard() {
        val intent = Intent(this , dashboard_vendor::class.java)
        startActivity(intent)
        finish()
    }

    private fun updateNavMenuVendorModeText() {

        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()

        val rootRef = FirebaseDatabase.getInstance().reference
        val userNameRef = rootRef.child("Vendors/Applications/$numberFir")
//                    var registerUser: Boolean = false
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            if (!dataSnapshot.exists()) {

                if(dataSnapshot.getValue<String>() == "PENDING") {

                    //create new user
                    Log.d(ContentValues.TAG, "check return123if:"+dataSnapshot.getValue<String>()) //Don't ignore errors!


//                    set navbar menu title
                    binding.navigationView.menu.findItem(R.id.nav_forVendorApplyOpen).setTitle("Applied for Vendor")


                    Log.d(ContentValues.TAG, "registerUser:" + numberFir) //Don't ignore errors!
                }
//                            }
                else if(dataSnapshot.getValue<String>() == "APPROVED") {

                    //create new user
                    Log.d(ContentValues.TAG, "check return123if:"+dataSnapshot.getValue<String>()) //Don't ignore errors!


//                    set navbar menu title
                    binding.navigationView.menu.findItem(R.id.nav_forVendorApplyOpen).setTitle("Switch to Vendor")


                    Log.d(ContentValues.TAG, "registerUser:" + numberFir) //Don't ignore errors!
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(ContentValues.TAG, databaseError.message) //Don't ignore errors!
            }
        }
        userNameRef.addListenerForSingleValueEvent(eventListener)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        binding.customerDrawerLayout.closeDrawers()

//        if(title=="Home" || title=="Terms and Conditions"){
//            setTitle("Salon Ease")
//        }else{
//            setTitle(title)
//        }
        setTitle(title)

//        TITLE_OBJ.setTitle(title)
    }

    private fun checkUser() {
//        get current user
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
//            logged out
            startActivity(Intent(this, welcomeMobileEnter::class.java))
            finish()
        } else {
//            logged in get phone number of user
            val phone = firebaseUser.phoneNumber
//            set phone number
//            binding.mobiletv.text = phone
            SingleToast.show(this, "Customer is Logged as:" + phone, Toast.LENGTH_LONG)

        }
    }

    override fun onStart() {
        super.onStart()
        checkUser()
        updateNavMenuVendorModeText()

    }
}