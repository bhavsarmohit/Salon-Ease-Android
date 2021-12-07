package com.caprusdigi.salon_ease.dashboard_vendor.fragments.salonStation.Services

import android.app.ProgressDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import com.caprusdigi.salon_ease.FirebaseCustomDefinitions
import com.caprusdigi.salon_ease.R
import com.caprusdigi.salon_ease.SingleToast
import com.caprusdigi.salon_ease.databinding.FragmentVendorSalonStationServicesEditServiceBinding
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
 * Use the [vendor_salonStation_Services_EditService.newInstance] factory method to
 * create an instance of this fragment.
 */
class vendor_salonStation_Services_EditService : Fragment(R.layout.fragment_vendor_salon_station__services__edit_service) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var thirdCat: String = ""

    lateinit var progressDialog: ProgressDialog

    private lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null


    //    intialize binding
    private var _binding: FragmentVendorSalonStationServicesEditServiceBinding? = null
    private val binding get() = _binding!!

    //    define service name variable globaly
    var serviceName: String? = null


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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVendorSalonStationServicesEditServiceBinding.inflate(inflater, container, false)


        progressDialog = ProgressDialog(activity)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        val arguments = arguments
        serviceName = arguments!!.getString("ServiceName")!!
        SingleToast.show(activity, "Service Name:$serviceName", Toast.LENGTH_LONG)
        setDataFromFirebaseToForm(serviceName!!)

        binding.btnBack.setOnClickListener {

            val fragment = vendor_salonStation_Services_HomeShow()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout,fragment)
            transaction.commit()
        }


        binding.btnSaveChanges.setOnClickListener {
            addServiceToSalonStation(serviceName!!)
        }

        return binding.root
    }


    private fun addServiceToSalonStation(serviceName: String) {

        progressDialog.setMessage("Adding Service...")
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


            var myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$serviceName/Service_Name")
            myRef.setValue(serviceNm)

            myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$serviceName/Service_Desc")
            myRef.setValue(description)

            myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$serviceName/Service_Price")
            myRef.setValue(originalServicePrice)

            myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$serviceName/Service_Offer_Price")
            myRef.setValue(offerPrice)

            myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$serviceName/Service_Time")
            myRef.setValue(serviceTime)


            Toast.makeText(activity, "$serviceNm Changes Saved Successful...", Toast.LENGTH_SHORT).show()

        }else{
            SingleToast.show(activity, "Please fill all Field!", Toast.LENGTH_LONG)

        }

        progressDialog.dismiss()



//            SingleToast.show(activity, "selected:$spinner_service", Toast.LENGTH_LONG)



    }


    fun setDataFromFirebaseToForm(serviceName: String) {





        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()


//        Retrieve Data from firebase and put on Form===========
        var myRefName = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$serviceName/Service_Name")
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
        var myRefDesc = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$serviceName/Service_Desc")
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
        var myRefPrice = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$serviceName/Service_Price")
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


//        Retrieve Data from firebase and put on Form===========
       var myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$serviceName/Service_Offer_Price")
        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(snapshot!=null) {
                    binding.etOfferPrice.setText(snapshot.getValue<String>())
                    Log.d(ContentValues.TAG, "offer price is: " + snapshot.getValue<String>())
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
            }
        })


//        Retrieve Data from firebase and put on Form===========
        myRef = database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$serviceName/Service_Time")
        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                if(snapshot!=null) {
                    binding.etServiceTime.setText(snapshot.getValue<String>())
                    Log.d(ContentValues.TAG, "service time is: " + snapshot.getValue<String>())
//                }
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
         * @return A new instance of fragment vendor_salonStation_Services_EditService.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                vendor_salonStation_Services_EditService().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}