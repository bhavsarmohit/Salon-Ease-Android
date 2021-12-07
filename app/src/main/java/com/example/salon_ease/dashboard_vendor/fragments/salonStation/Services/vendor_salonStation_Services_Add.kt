package com.example.salon_ease.dashboard_vendor.fragments.salonStation.Services

import android.app.ProgressDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.salon_ease.FirebaseCustomDefinitions
import com.example.salon_ease.R
import com.example.salon_ease.SingleToast
import com.example.salon_ease.databinding.FragmentVendorSalonStationServicesAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [vendor_salonStation_Services_Add.newInstance] factory method to
 * create an instance of this fragment.
 */
class vendor_salonStation_Services_Add : Fragment(R.layout.fragment_vendor_salon_station__services__add) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var Cat: String = ""
    var subCat: String = ""
    var thirdCat: String = ""


    lateinit var progressDialog: ProgressDialog

    private lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    //    intialize binding
    private var _binding: FragmentVendorSalonStationServicesAddBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

//        when user press back button replace fragment with vendor Home Fragment
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val dialog = AlertDialog.Builder(activity!!)
                    .setTitle("Alert!")
                    .setMessage("Discard Changes!")
                    .setNeutralButton("Cancel", { dialog, i ->

                    })
                    .setPositiveButton("Yes", { dialog, i ->
                        val fragment = vendor_salonStation_Services_HomeShow()
                        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.frameLayout,fragment)
                        transaction.commit()
                    })
                dialog.show()


            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVendorSalonStationServicesAddBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Salon Station"


        progressDialog = ProgressDialog(activity)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()



        setCategoryToSpinnerDataFromFirebase()
        setSubCategoryToSpinnerDataFromFirebase()
        setThirdCategoryToSpinnerDataFromFirebase()


        binding.spinnerCategory.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItemText = parent.getItemAtPosition(position) as String
                if(selectedItemText != "SELECT") {
                    Cat = selectedItemText
                    setSubCategoryToSpinnerDataFromFirebase()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        binding.spinnerSubCategory.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItemText = parent.getItemAtPosition(position) as String
                if(selectedItemText != "SELECT") {
                    subCat = selectedItemText
                    setThirdCategoryToSpinnerDataFromFirebase()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        binding.spinnerThirdCategory.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItemText = parent.getItemAtPosition(position) as String
                if(selectedItemText != "SELECT") {
                    thirdCat = selectedItemText
                    setDataFromFirebaseToForm()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        binding.btnAddService.setOnClickListener{
            addServiceToSalonStation()
        }
        binding.btnDone.setOnClickListener {

            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(com.example.salon_ease.R.id.frameLayout, vendor_salonStation_Services_HomeShow())
            transaction.commit()
        }


        return binding.root
    }


    private fun setThirdCategoryToSpinnerDataFromFirebase() {

        //        spinner
        val spinner = binding.spinnerThirdCategory


        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()


//      set data from firebase to edittext============================
        val rootRef = FirebaseDatabase.getInstance().reference
        val userNameRef = rootRef.child("Vendors/Categories/$Cat/Sub_Categories/$subCat/Third_Sub_Categories/")
//                    var registerUser: Boolean = false
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

//                create array to store values
                val arrayDatabaseServices = ArrayList<String>()
                arrayDatabaseServices.add("SELECT")

                //                extract data from database object
                for (childsnapshot in dataSnapshot.getChildren()) {


                    if (childsnapshot != null) {
                        //                        SingleToast.show(activity, childsnapshot.key.toString(), Toast.LENGTH_LONG)
                        //                        arrayServices.add(ItemsViewModel_services(R.drawable.ic_assignment_apply_for_vendor_service, childsnapshot.key.toString()))
                        arrayDatabaseServices.add(childsnapshot.key.toString())
                    } else {
                        //                        set image opps you haven't added service yet!
                    }

                }

                val arrayAdapter = ArrayAdapter(
                    activity!!,
                    android.R.layout.simple_spinner_item,
                    arrayDatabaseServices
                )
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinner.adapter = arrayAdapter



//                Log.d(ContentValues.TAG, "array database services:"+arrayDatabaseServices.toString()) //Don't ignore errors!


            }


            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(ContentValues.TAG, databaseError.message) //Don't ignore errors!
            }
        }
        userNameRef.addListenerForSingleValueEvent(eventListener)


//        ============================
    }

    private fun setSubCategoryToSpinnerDataFromFirebase() {

        //        spinner
        val spinner = binding.spinnerSubCategory


        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()

        Log.d(ContentValues.TAG, "spinner cat selected:"+spinner.selectedItem) //Don't ignore errors!

//      spinnerCat = spinner.selectedItem.toString()
        //        set data from firebase to edittext============================
        val rootRef = FirebaseDatabase.getInstance().reference
        val userNameRef = rootRef.child("Vendors/Categories/$Cat/Sub_Categories/")
        //                    var registerUser: Boolean = false
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                //                create array to store values
                val arrayDatabaseServices = ArrayList<String>()
                arrayDatabaseServices.add("SELECT")

                //                extract data from database object
                for (childsnapshot in dataSnapshot.getChildren()) {


                    if (childsnapshot != null) {
                        //                        SingleToast.show(activity, childsnapshot.key.toString(), Toast.LENGTH_LONG)
                        //                        arrayServices.add(ItemsViewModel_services(R.drawable.ic_assignment_apply_for_vendor_service, childsnapshot.key.toString()))
                        arrayDatabaseServices.add(childsnapshot.key.toString())
                    } else {
                        //                        set image opps you haven't added service yet!
                    }

                }

                val arrayAdapter = ArrayAdapter(
                    activity!!,
                    android.R.layout.simple_spinner_item,
                    arrayDatabaseServices
                )
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinner.adapter = arrayAdapter

                //                Log.d(ContentValues.TAG, "array database services:"+arrayDatabaseServices.toString()) //Don't ignore errors!


            }


            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(ContentValues.TAG, databaseError.message) //Don't ignore errors!
            }
        }
        userNameRef.addListenerForSingleValueEvent(eventListener)

//        ============================

    }

    private fun setCategoryToSpinnerDataFromFirebase() {

        //        spinner
        val spinner = binding.spinnerCategory


        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()


//        set data from firebase to edittext============================
        val rootRef = FirebaseDatabase.getInstance().reference
        val userNameRef = rootRef.child("Vendors/Categories/")
//                    var registerUser: Boolean = false
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


//                create array to store values
                val arrayDatabaseServices = ArrayList<String>()
                arrayDatabaseServices.add("SELECT")

//                extract data from database object
                for (childsnapshot in dataSnapshot.getChildren()) {


                    if(childsnapshot != null) {
//                        SingleToast.show(activity, childsnapshot.key.toString(), Toast.LENGTH_LONG)
//                        arrayServices.add(ItemsViewModel_services(R.drawable.ic_assignment_apply_for_vendor_service, childsnapshot.key.toString()))
                        arrayDatabaseServices.add(childsnapshot.key.toString())
                    }else{
//                        set image opps you haven't added service yet!
                    }

                }

                val arrayAdapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, arrayDatabaseServices)
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinner.adapter = arrayAdapter

//                Log.d(ContentValues.TAG, "array database services:"+arrayDatabaseServices.toString()) //Don't ignore errors!


            }


            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(ContentValues.TAG, databaseError.message) //Don't ignore errors!
            }
        }
        userNameRef.addListenerForSingleValueEvent(eventListener)
//        ============================

    }

    private fun addServiceToSalonStation() {

        progressDialog.setMessage("Saving Changes...")
        progressDialog.show()

//        var spinner_service = binding.spinnerSelectService.selectedItem.toString()
        var serviceNm = binding.etServiceName!!.text.toString()
        var description = binding.etDescription!!.text.toString()
//        var products = binding.etProducts!!.text.toString()
        var serviceTime = binding.etServiceTime!!.text.toString()
        var originalServicePrice = binding.etOriginalPrice!!.text.toString()
        var offerPrice = binding.etOfferPrice!!.text.toString()

//        check if fields is not empty
//        if(serviceNm.isNotEmpty() && products.isNotEmpty() && serviceTime.isNotEmpty() && originalServicePrice.isNotEmpty() && offerPrice.isNotEmpty()) {
        if(serviceNm.isNotEmpty() && description.isNotEmpty() && originalServicePrice.isNotEmpty()) {
//            SingleToast.show(activity, "Fields not Empty", Toast.LENGTH_LONG)


            // Write a message to the database
            val database = Firebase.database

            // receives logged in user mobile
            val fireCustomDef = FirebaseCustomDefinitions()
            val numberFir = fireCustomDef.getLoggedInProfileMobile()


            var myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$thirdCat/Service_Name")
            myRef.setValue(serviceNm)

            myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$thirdCat/Service_Desc")
            myRef.setValue(description)

            myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$thirdCat/Service_Price")
            myRef.setValue(originalServicePrice)

            myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$thirdCat/Service_Offer_Price")
            myRef.setValue(offerPrice)

            myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$thirdCat/Service_Time")
            myRef.setValue(serviceTime)


            myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$thirdCat/First_Category")
            myRef.setValue(Cat)

            myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$thirdCat/Sub_Category")
            myRef.setValue(subCat)




            Toast.makeText(activity, "$serviceNm is Added Successful", Toast.LENGTH_SHORT).show()


            progressDialog.dismiss()

        }else{
            SingleToast.show(activity, "Please fill all Field!", Toast.LENGTH_LONG)

        }




//            SingleToast.show(activity, "selected:$spinner_service", Toast.LENGTH_LONG)



    }

    fun setDataFromFirebaseToForm(){





        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()


//        Retrieve Data from firebase and put on Form===========
        var myRefName = database!!.getReference("Vendors/Categories/$Cat/Sub_Categories/$subCat/Third_Sub_Categories/$thirdCat/Category_Name")
        // Read from the database
        myRefName.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                binding.etServiceName.setText(snapshot.getValue<String>())
                binding.etServiceName.isEnabled = false
                Log.d(ContentValues.TAG, "name is: " + snapshot.getValue<String>())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })


//        Retrieve Data from firebase and put on Form===========
        var myRefDesc = database!!.getReference("Vendors/Categories/$Cat/Sub_Categories/$subCat/Third_Sub_Categories/$thirdCat/Category_Desc")
        // Read from the database
        myRefDesc.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                binding.etDescription.setText(snapshot.getValue<String>())
                Log.d(ContentValues.TAG, "Desc is: " + snapshot.getValue<String>())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })


//        Retrieve Data from firebase and put on Form===========
        var myRefPrice = database!!.getReference("Vendors/Categories/$Cat/Sub_Categories/$subCat/Third_Sub_Categories/$thirdCat/Category_Price")
        // Read from the database
        myRefPrice.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                binding.etOriginalPrice.setText(snapshot.getValue<String>())
                Log.d(ContentValues.TAG, "price is: " + snapshot.getValue<String>())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
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
         * @return A new instance of fragment vendor_salonStation_Services_Add.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            vendor_salonStation_Services_Add().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}