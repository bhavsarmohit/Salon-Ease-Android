package com.caprusdigi.salon_ease.dashboard_vendor

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.caprusdigi.salon_ease.FirebaseCustomDefinitions
import com.caprusdigi.salon_ease.R
import com.caprusdigi.salon_ease.SingleToast
import com.caprusdigi.salon_ease.dashboard_customer.dashboard_customer
import com.caprusdigi.salon_ease.dashboard_vendor.fragments.*
import com.caprusdigi.salon_ease.databinding.ActivityDashboardVendorBinding
import com.caprusdigi.salon_ease.welcomeMobileEnter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.lang.Exception

class dashboard_vendor : AppCompatActivity() {

//    view binding
    private lateinit var binding: ActivityDashboardVendorBinding


    private lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    lateinit var number: String
     var name: String? = null

//
    lateinit var toggle: ActionBarDrawerToggle

//    lateinit var drawerLayout: DrawerLayout
//    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_dashboard_vendor)
//        supportActionBar?.hide()
        binding = ActivityDashboardVendorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

//        loadProfile()



        checkUser()

//        logout button click logout the user
//        binding.logoutBtn.setOnClickListener{
//            firebaseAuth.signOut()
//            checkUser()
//        }

//        drawerLayout = findViewById(R.id.vendor_drawerLayout)
//        navView = findViewById(R.id.navigationView)

        toggle = ActionBarDrawerToggle(this, binding.vendorDrawerLayout, R.string.open, R.string.close)

        binding.vendorDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        replaceFragment(vendor_Home(),"Home")

        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){

                R.id.nav_home -> replaceFragment(vendor_Home(),it.title.toString())
                R.id.nav_editProfile -> replaceFragment(vendor_EditProfile(),it.title.toString())
                R.id.nav_editSalonProfile -> replaceFragment(vendor_Salon_EditProfile(),it.title.toString())

//                R.id.nav_editProfile -> {
//                intent = Intent(this, editProfile::class.java)
//                startActivity(intent) }

                R.id.nav_share -> {
//                    share the app
                    try {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Salon Ease")
                        var shareMessage = "\nLet me recommend you Salon Ease application, try it \n\n"
//                        shareMessage =
//                            """
//                            ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
//
//
//                            """.trimIndent()
                        shareMessage =
                            """
                            ${shareMessage}https://play.google.com/store/apps/details?id=com.caprusdigi.salon_ease
                            
                            
                            """.trimIndent()
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    } catch (e: Exception) {
                        //e.toString();
                    }
                }

//                sign out firebase authentication
                R.id.nav_signOut -> {
                    auth.signOut()
                    intent = Intent(this, welcomeMobileEnter::class.java)
                    startActivity(intent)
//                    checkUser()
                }

                R.id.nav_forCustomerOpen -> {
//                    openCustomerDashboard()
                    SingleToast.show(this, "Locked!", Toast.LENGTH_LONG)

                }

                R.id.nav_notifications -> replaceFragment(vendor_Notifications(),it.title.toString())
                R.id.nav_termsAndConditions -> replaceFragment(vendor_termsAndConditions(),it.title.toString())

//                R.id.nav_home -> replaceFragment(vendor_termsAndConditions(),it.title.toString())
//                R.id.nav_home -> replaceFragment(vendor_termsAndConditions(),it.title.toString())


            }
            true
        }

//        nav_toogle_menu_custom.setOnClickListener {
//            openCloseNavigationDrawer()
//        }


// receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()

        // receives logged in user mobile
//        val usrName = fireCustomDef.getReceiveUserCustomDataFirebase(numberFir+"/Name")

//        update ui data from firebase to nav header
        val navHeader_mob: TextView = binding.navigationView.getHeaderView(0).findViewById(R.id.tv_mobile)
        navHeader_mob.setText(numberFir)



//        set Name to Nav Header===========
        var myRef = database!!.getReference("Users/"+numberFir+"/Name")
        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val navHeader_name: TextView = binding.navigationView.getHeaderView(0).findViewById(R.id.tv_name)
                navHeader_name.setText(snapshot.getValue<String>())
                Log.d(TAG, "name is: " + snapshot.getValue<String>())
//
//                name = snapshot.getValue<String>().toString()


            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })


//        ===========




    }

    private fun openCustomerDashboard() {
        val intent = Intent(this , dashboard_customer::class.java)
        startActivity(intent)
        finish()
    }

//    private fun loadProfile() {
//        val user = auth.currentUser
//        val userReference = databaseReference?.child(user?.uid!!)
//        userReference?.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//    }

    private fun getLoggedInProfileMobile(): String {


//        get current user
        val firebaseUser = auth.currentUser
        if(firebaseUser == null){
//            logged out
            startActivity(Intent(this, welcomeMobileEnter::class.java))
            finish()
        }else{
//            logged in get phone number and return
            number = firebaseUser.phoneNumber.toString()

        }
        return number
    }

    fun openCloseNavigationDrawer() {
        if (binding.vendorDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.vendorDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.vendorDrawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    // function to replace fragment
    fun replaceFragment(fragment: Fragment,title : String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        binding.vendorDrawerLayout.closeDrawers()

//        if(title=="Home" || title=="Terms and Conditions"){
//            setTitle("Salon Ease")
//        }else{
//            setTitle(title)
//        }
        setTitle(title)

//        TITLE_OBJ.setTitle(title)
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
                            SingleToast.show(this, "Vendor is Logged as:"+phone, Toast.LENGTH_LONG);

        }
    }

    override fun onStart() {
        super.onStart()
        checkUser()
    }

//    double back tap to exit======================
//    private var backPressedTime:Long = 0
////    lateinit var backToast:Toast
//    override fun onBackPressed() {
//
////        backToast = Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG)
//        if (backPressedTime + 2000 > System.currentTimeMillis()) {
////            backToast.cancel()
//            super.onBackPressed()
//            return
//        } else {
//            SingleToast.show(this, "Press back again to leave the app", Toast.LENGTH_LONG);
//
////            backToast.show()
//        }
//        backPressedTime = System.currentTimeMillis()
//    }
//    ======================
}