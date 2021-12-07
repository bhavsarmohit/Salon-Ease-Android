package com.example.salon_ease.dashboard_vendor.fragments

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.salon_ease.FirebaseCustomDefinitions
import com.example.salon_ease.R
import com.example.salon_ease.SingleToast
import com.example.salon_ease.databinding.FragmentVendorHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.mikhaellopez.circularimageview.CircularImageView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [vendor_Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class vendor_Home : Fragment(R.layout.fragment_vendor__home) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var services: CircularImageView? = null
    private var bookingHistory: CircularImageView? = null


    //    intialize binding
    private var _binding: FragmentVendorHomeBinding? = null
    private val binding get() = _binding!!

    var database : FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        var backPressedTime:Long = 0



//        when user press back button replace fragment with vendor Home Fragment
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {


                if (backPressedTime + 2000 > System.currentTimeMillis()) {
//            backToast.cancel()

                    requireActivity().finish()


                    return
                } else {
                    SingleToast.show(activity, "Press back again to leave the app", Toast.LENGTH_LONG);

//            backToast.show()
                }
                backPressedTime = System.currentTimeMillis()
//
//                val vendorHome = vendor_Home()
//                val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
//                transaction.replace(R.id.frameLayout,vendorHome)
//                transaction.commit()
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVendorHomeBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Home"



        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()


// Write a message to the database
        database = FirebaseDatabase.getInstance()



//        get Salon Name
        var salonName = database!!.getReference("Users/$numberFir/Vendor/Profile/Salon_Name")

        // Read from the database
        salonName.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                binding.tvSalonName.setText(dataSnapshot.value.toString())

            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to Connect with Database!", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })

//        check status of Stations From database and enable/disable toggle

////        check if customer already applied for vendor access
        var salonStationRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Status")
        var spaStationRef = database!!.getReference("Users/$numberFir/Vendor/Spa_Station/Status")
        var salonAtHomeRef = database!!.getReference("Users/$numberFir/Vendor/Salon_At_Home/Status")


        // Read from the database
        salonStationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

//                if data not found
                if (dataSnapshot.value == "ENABLE") {
                    binding.toggleSalonStation.setChecked(true)
                    binding.cvSalonStation.setEnabled(true)
//                    binding.cvSalonStation.setBackgroundColor(Color.WHITE)
                    binding.cvSalonStation.setBackgroundColor(Color.parseColor("#EDFFEA"))

//                    clearToasts()
                } else if (dataSnapshot.value == "DISABLE") {
                    binding.toggleSalonStation.setChecked(false)
                    binding.cvSalonStation.setEnabled(false)
                    binding.cvSalonStation.setBackgroundColor(Color.parseColor("#FFF1F1"))
//                    clearToasts()

                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to Connect with Database!", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })
        // Read from the database
        spaStationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

//                if data not found
                if (dataSnapshot.value == "ENABLE") {
                    binding.toggleSpaStation.setChecked(true)
                    binding.cvSpaStation.setEnabled(true)
//                    binding.cvSpaStation.setBackgroundColor(Color.WHITE)
                    binding.cvSpaStation.setBackgroundColor(Color.parseColor("#EDFFEA"))
//                    clearToasts()

                } else if (dataSnapshot.value == "DISABLE") {
                    binding.toggleSpaStation.setChecked(false)
                    binding.cvSpaStation.setEnabled(false)
                    binding.cvSpaStation.setBackgroundColor(Color.parseColor("#FFF1F1"))
//                    clearToasts()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to Connect with Database!", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })
// Read from the database
        salonAtHomeRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

//                if data not found
                if (dataSnapshot.value == "ENABLE") {

                    binding.toggleSalonAtHome.setChecked(true)
                    binding.cvSalonAtHome.setEnabled(true)
//                    binding.cvSalonAtHome.setBackgroundColor(Color.WHITE)
                    binding.cvSalonAtHome.setBackgroundColor(Color.parseColor("#EDFFEA"))
//                    clearToasts()
                } else if (dataSnapshot.value == "DISABLE") {
                    binding.toggleSalonAtHome.setChecked(false)
                    binding.cvSalonAtHome.setEnabled(false)
                    binding.cvSalonAtHome.setBackgroundColor(Color.parseColor("#FFF1F1"))
//                    clearToasts()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to Connect with Database!", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })


//        var statusSalonAtHome = checkSalonAtHomeStatus()
//        var statusSalonStation = checkSalonStationStatus()
//        var statusSpaStation = checkSpaStationStatus()
//        SingleToast.show(activity, "status spa:$statusSpaStation", Toast.LENGTH_LONG)
//        Log.w(ContentValues.TAG, "status spa:$statusSpaStation")


//        if(statusSalonAtHome=="ENABLE"){
//            binding.toggleSalonAtHome.setChecked(true)
//            binding.cvSalonAtHome.setEnabled(true)
//            binding.cvSalonAtHome.setBackgroundColor(Color.WHITE)
//
//        }else if(statusSalonAtHome=="DISABLE"){
//            binding.toggleSalonAtHome.setChecked(false)
//            binding.cvSalonAtHome.setEnabled(false)
//            binding.cvSalonAtHome.setBackgroundColor(Color.parseColor("#FFF1F1"))
//
//        }
////        else{
////            binding.cvSalonAtHome.setBackgroundColor(Color.parseColor("#FF000000"))
////
////        }
//
//        if(statusSalonStation=="ENABLE"){
//            binding.toggleSalonStation.setChecked(true)
//            binding.cvSalonStation.setEnabled(true)
//            binding.cvSalonStation.setBackgroundColor(Color.WHITE)
//
//        }else{
//            binding.toggleSalonStation.setChecked(false)
//            binding.cvSalonStation.setEnabled(false)
//            binding.cvSalonStation.setBackgroundColor(Color.parseColor("#FFF1F1"))
//
//        }
//
//        if(statusSpaStation=="ENABLE"){
//            binding.toggleSpaStation.setChecked(true)
//            binding.cvSpaStation.setEnabled(true)
//            binding.cvSpaStation.setBackgroundColor(Color.WHITE)
//        }else{
//            binding.toggleSpaStation.setChecked(false)
//            binding.cvSpaStation.setEnabled(false)
//            binding.cvSpaStation.setBackgroundColor(Color.parseColor("#FFF1F1"))
//
//        }



        binding.cvSalonAtHome.setOnClickListener{
//            moveToVendorSalonAtHome()
//            SingleToast.show(activity, "clicked Salon at Home", Toast.LENGTH_LONG);

//            (activity as dashboard_vendor).replaceFragment(vendor_SalonAtHome(),"Salon At Home")
            val salonAtHome = vendor_SalonAtHome()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout,salonAtHome)
            transaction.commit()

        }
        binding.cvSalonStation.setOnClickListener{
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout,vendor_salonStation())
            transaction.commit()
        }
        binding.cvSpaStation.setOnClickListener{
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout,vendor_spaStation())
            transaction.commit()
        }


//        manage toggle buttons
        binding.toggleSalonStation.setOnCheckedChangeListener { _, isChecked ->

            if(isChecked){

                enableSalonStation()
                binding.cvSalonStation.setEnabled(true)

                binding.toggleSalonStation.setChecked(true)
                binding.cvSalonStation.setEnabled(true)
                binding.cvSalonStation.setBackgroundColor(Color.WHITE)

//                SingleToast.show(activity, "Salon Station Enabled", Toast.LENGTH_LONG)



            }else{
                disableSalonStation()
                binding.cvSalonStation.setEnabled(false)

                binding.toggleSalonStation.setChecked(false)
                binding.cvSalonStation.setEnabled(false)
                binding.cvSalonStation.setBackgroundColor(Color.parseColor("#FFF1F1"))


//                binding.cvSalonStation.setBackgroundColor(Color.parseColor("#FFF1F1"))

//                SingleToast.show(activity, "Salon Station Disabled", Toast.LENGTH_LONG)

            }

//            var status = checkSalonStationStatus()
//            if(status=="ENABLE"){
//                binding.toggleSalonStation.setChecked(true)
//            }else if(status=="DISABLE"){
//                binding.toggleSalonStation.setChecked(false)
//            }else{
//                binding.toggleSalonStation.setChecked(defaultStatus)
//            }

        }

        binding.toggleSpaStation.setOnCheckedChangeListener { _, isChecked ->

            if(isChecked){
                enableSpaStation()
                binding.cvSpaStation.setEnabled(true)

                binding.toggleSpaStation.setChecked(true)
                binding.cvSpaStation.setEnabled(true)
                binding.cvSpaStation.setBackgroundColor(Color.WHITE)

//                SingleToast.show(activity, "Spa Station Enabled", Toast.LENGTH_LONG)

            }else{
                disableSpaStation()
                binding.cvSpaStation.setEnabled(false)

                binding.toggleSpaStation.setChecked(false)
                binding.cvSpaStation.setEnabled(false)
                binding.cvSpaStation.setBackgroundColor(Color.parseColor("#FFF1F1"))

//                binding.cvSpaStation.setBackgroundColor(Color.parseColor("#FFF1F1"))

//                SingleToast.show(activity, "Spa Station Disabled", Toast.LENGTH_LONG)

            }

//            var status = checkSpaStationStatus()
//            if(status=="ENABLE"){
//                binding.toggleSpaStation.setChecked(true)
//            }else if(status=="DISABLE"){
//                binding.toggleSpaStation.setChecked(false)
//            }else{
//                binding.toggleSpaStation.setChecked(defaultStatus)
//            }

        }

        binding.toggleSalonAtHome.setOnCheckedChangeListener { _, isChecked ->


//                SingleToast.show(activity, "defaultStatus:$defaultStatus", Toast.LENGTH_LONG)

            if(isChecked){
                enableSalonAtHome()
                binding.cvSalonAtHome.setEnabled(true)

                binding.toggleSalonAtHome.setChecked(true)
                binding.cvSalonAtHome.setEnabled(true)
                binding.cvSalonAtHome.setBackgroundColor(Color.WHITE)

//                SingleToast.show(activity, "Salon at Home Enabled", Toast.LENGTH_LONG)

            }else{
                disableSalonAtHome()
                binding.cvSalonAtHome.setEnabled(false)

                binding.toggleSalonAtHome.setChecked(false)
                binding.cvSalonAtHome.setEnabled(false)
                binding.cvSalonAtHome.setBackgroundColor(Color.parseColor("#FFF1F1"))


//                binding.cvSalonAtHome.setBackgroundColor(Color.parseColor("#FFF1F1"))


//                SingleToast.show(activity, "Salon at Home Disabled", Toast.LENGTH_LONG)

            }

//            var status = checkSalonAtHomeStatus()
//            if(status=="ENABLE"){
//                binding.toggleSalonAtHome.setChecked(true)
//            }else if(status=="DISABLE"){
//                binding.toggleSalonAtHome.setChecked(false)
//            }else{
//                binding.toggleSalonAtHome.setChecked(defaultStatus)
//            }

        }


//        image slider
        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel(R.drawable.image3, ScaleTypes.FIT ))
        imageList.add(SlideModel(R.drawable.image4, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.image6, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.image7, ScaleTypes.FIT))

        binding.imageSliderBanner.setImageList(imageList)


        return binding.root

    }

    private fun enableSalonStation() {


//        Log.w(ContentValues.TAG, "function called enable salon station")

            // receives logged in user mobile
            val fireCustomDef = FirebaseCustomDefinitions()
            val numberFir = fireCustomDef.getLoggedInProfileMobile()



// Write a message to the database
            val database = Firebase.database

//            check if vendor approved or not

////        check if customer already applied for vendor access
            var myRef1 = database.getReference("Vendors/Applications/$numberFir")
//            var alertShow: Boolean = false

//        Log.w(ContentValues.TAG, "qqqqqqqq")


        // Read from the database
            myRef1.addValueEventListener(object : ValueEventListener {



                override fun onDataChange(dataSnapshot: DataSnapshot) {

//                    Log.w(ContentValues.TAG, "enable salon station:ondatachange")


//                if data not found
                    if (dataSnapshot.getValue<String>() == null) {
                        SingleToast.show(
                            activity,
                            "You are Not Vendor! Please Apply for Vendor!",
                            Toast.LENGTH_LONG
                        )
                    } else if (dataSnapshot.getValue<String>() == "APPROVED") {
                        var myRef = database.getReference("Users/$numberFir/Vendor/Salon_Station/Status")
                        myRef.setValue("ENABLE")



                        Log.w(ContentValues.TAG, "enable salon station:Enabled")


//                        val dialog = AlertDialog.Builder(activity!!)
//                            .setTitle("Alert!")
//                            .setMessage("Salon Station is Enabled...")
//                            .setNeutralButton("Close", { dialog, i ->
//                            })
//                        dialog.show()
                        SingleToast.show(
                            activity,
                            "Salon Station is Enabled",
                            Toast.LENGTH_LONG
                        )
                    } else if (dataSnapshot.getValue<String>() == "PENDING") {
                        SingleToast.show(
                            activity,
                            "Your Vendor Application is Pending!",
                            Toast.LENGTH_LONG
                        )
                    } else if (dataSnapshot.getValue<String>() == "SUSPENDED") {
                        SingleToast.show(
                            activity,
                            "You are Suspended! Please contact with Salon Ease!",
                            Toast.LENGTH_LONG
                        )
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "Failed to Connect with Database!", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
                }
            })
    }

    private fun enableSpaStation() {
        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()

// Write a message to the database
        val database = Firebase.database

//            check if vendor approved or not

////        check if customer already applied for vendor access
        var myRef1 = database.getReference("Vendors/Applications/$numberFir")
//            var alertShow: Boolean = false
        // Read from the database
        myRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

//                if data not found
                if (dataSnapshot.getValue<String>() == null) {
                    SingleToast.show(
                        activity,
                        "You are Not Vendor! Please Apply for Vendor!",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "APPROVED") {
                    var myRef = database.getReference("Users/$numberFir/Vendor/Spa_Station/Status")
                    myRef.setValue("ENABLE")

//                    val dialog = AlertDialog.Builder(activity!!)
//                        .setTitle("Alert!")
//                        .setMessage("Spa Station is Enabled...")
//                        .setNeutralButton("Close", { dialog, i ->
//                        })
//                    dialog.show()
                    SingleToast.show(
                        activity,
                        "Spa Station is Enabled",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "PENDING") {
                    SingleToast.show(
                        activity,
                        "Your Vendor Application is Pending!",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "SUSPENDED") {
                    SingleToast.show(
                        activity,
                        "You are Suspended! Please contact with Salon Ease!",
                        Toast.LENGTH_LONG
                    )
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to Connect with Database!", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })
    }


    private fun enableSalonAtHome() {
        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()

// Write a message to the database
        val database = Firebase.database

//            check if vendor approved or not

////        check if customer already applied for vendor access
        var myRef1 = database.getReference("Vendors/Applications/$numberFir")
//            var alertShow: Boolean = false
        // Read from the database
        myRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

//                if data not found
                if (dataSnapshot.getValue<String>() == null) {
                    SingleToast.show(
                        activity,
                        "You are Not Vendor! Please Apply for Vendor!",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "APPROVED") {
                    var myRef = database.getReference("Users/$numberFir/Vendor/Salon_At_Home/Status")
                    myRef.setValue("ENABLE")

                    SingleToast.show(
                        activity,
                        "Salon at Home is Enabled",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "PENDING") {
                    SingleToast.show(
                        activity,
                        "Your Vendor Application is Pending!",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "SUSPENDED") {
                    SingleToast.show(
                        activity,
                        "You are Suspended! Please contact with Salon Ease!",
                        Toast.LENGTH_LONG
                    )
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to Connect with Database!", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })
    }

    private fun disableSalonStation() {
        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()

// Write a message to the database
        val database = Firebase.database

//            check if vendor approved or not

////        check if customer already applied for vendor access
        var myRef1 = database.getReference("Vendors/Applications/$numberFir")
//            var alertShow: Boolean = false
        // Read from the database
        myRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

//                if data not found
                if (dataSnapshot.getValue<String>() == null) {
                    SingleToast.show(
                        activity,
                        "You are Not Vendor! Please Apply for Vendor!",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "APPROVED") {
                    var myRef = database.getReference("Users/$numberFir/Vendor/Salon_Station/Status")
                    myRef.setValue("DISABLE")

//                    val dialog = AlertDialog.Builder(activity!!)
//                        .setTitle("Alert!")
//                        .setMessage("Salon Station is Disabled...")
//                        .setNeutralButton("Close", { dialog, i ->
//                        })
//                    dialog.show()
                    SingleToast.show(
                        activity,
                        "Salon Station is Disabled",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "PENDING") {
                    SingleToast.show(
                        activity,
                        "Your Vendor Application is Pending!",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "SUSPENDED") {
                    SingleToast.show(
                        activity,
                        "You are Suspended! Please contact with Salon Ease!",
                        Toast.LENGTH_LONG
                    )
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to Connect with Database!", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })
    }

    private fun disableSpaStation() {
        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()

// Write a message to the database
        val database = Firebase.database

//            check if vendor approved or not

////        check if customer already applied for vendor access
        var myRef1 = database.getReference("Vendors/Applications/$numberFir")
//            var alertShow: Boolean = false
        // Read from the database
        myRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

//                if data not found
                if (dataSnapshot.getValue<String>() == null) {
                    SingleToast.show(
                        activity,
                        "You are Not Vendor! Please Apply for Vendor!",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "APPROVED") {
                    var myRef = database.getReference("Users/$numberFir/Vendor/Spa_Station/Status")
                    myRef.setValue("DISABLE")

//                    val dialog = AlertDialog.Builder(activity!!)
//                        .setTitle("Alert!")
//                        .setMessage("Spa Station is Disabled...")
//                        .setNeutralButton("Close", { dialog, i ->
//                        })
//                    dialog.show()
                    SingleToast.show(
                        activity,
                        "Spa Station is Disabled",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "PENDING") {
                    SingleToast.show(
                        activity,
                        "Your Vendor Application is Pending!",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "SUSPENDED") {
                    SingleToast.show(
                        activity,
                        "You are Suspended! Please contact with Salon Ease!",
                        Toast.LENGTH_LONG
                    )
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to Connect with Database!", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })
    }

    private fun disableSalonAtHome() {
        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()

// Write a message to the database
        val database = Firebase.database

//            check if vendor approved or not

////        check if customer already applied for vendor access
        var myRef1 = database.getReference("Vendors/Applications/$numberFir")
//            var alertShow: Boolean = false
        // Read from the database
        myRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

//                if data not found
                if (dataSnapshot.getValue<String>() == null) {
                    SingleToast.show(
                        activity,
                        "You are Not Vendor! Please Apply for Vendor!",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "APPROVED") {
                    var myRef = database.getReference("Users/$numberFir/Vendor/Salon_At_Home/Status")
                    myRef.setValue("DISABLE")

//                    val dialog = AlertDialog.Builder(activity!!)
//                        .setTitle("Alert!")
//                        .setMessage("Salon at Home is Disabled...")
//                        .setNeutralButton("Close", { dialog, i ->
//                        })
//                    dialog.show()
                    SingleToast.show(
                        activity,
                        "Salon at Home is Disabled",
                        Toast.LENGTH_LONG
                    )

                } else if (dataSnapshot.getValue<String>() == "PENDING") {
                    SingleToast.show(
                        activity,
                        "Your Vendor Application is Pending!",
                        Toast.LENGTH_LONG
                    )
                } else if (dataSnapshot.getValue<String>() == "SUSPENDED") {
                    SingleToast.show(
                        activity,
                        "You are Suspended! Please contact with Salon Ease!",
                        Toast.LENGTH_LONG
                    )
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to Connect with Database!", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment vendor_Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            vendor_Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

//    private fun replaceFragment(fragment: Fragment,title : String){
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(com.example.salon_ease.R.id.frameLayout,fragment)
//        fragmentTransaction.commit()
//        binding.vendorDrawerLayout.closeDrawers()
//
////        if(title=="Home" || title=="Terms and Conditions"){
////            setTitle("Salon Ease")
////        }else{
////            setTitle(title)
////        }
//        setTitle(title)
//
////        TITLE_OBJ.setTitle(title)
//    }
}