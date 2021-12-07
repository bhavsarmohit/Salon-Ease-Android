package com.caprusdigi.salon_ease.dashboard_vendor.fragments.salonStation.Services

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.caprusdigi.salon_ease.FirebaseCustomDefinitions
import com.caprusdigi.salon_ease.R
import com.caprusdigi.salon_ease.SingleToast
import com.caprusdigi.salon_ease.databinding.FragmentVendorSalonStationServicesRemoveBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [vendor_salonStation_Services_Remove.newInstance] factory method to
 * create an instance of this fragment.
 */
class vendor_salonStation_Services_Remove : Fragment(R.layout.fragment_vendor_salon_station__services__remove) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var thirdCat: String

    //    intialize binding
    private var _binding: FragmentVendorSalonStationServicesRemoveBinding? = null
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
        _binding = FragmentVendorSalonStationServicesRemoveBinding.inflate(inflater, container, false)



        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Salon Station"

        updateSpinnerFromDatabase()
        setDataFromFirebaseToForm()

        binding.spinnerSelectProduct.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItemText = parent.getItemAtPosition(position) as String

                    thirdCat = selectedItemText
                    setDataFromFirebaseToForm()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        binding.btnRemoveService.setOnClickListener{

            if(binding.spinnerSelectProduct.getSelectedItem() != null){
                val selectedService: String = binding.spinnerSelectProduct.getSelectedItem().toString()

                thirdCat = selectedService



                // receives logged in user mobile
                val fireCustomDef = FirebaseCustomDefinitions()
                val numberFir = fireCustomDef.getLoggedInProfileMobile()


//        set data from firebase to edittext============================
                val rootRef = FirebaseDatabase.getInstance().reference
                val myRef = rootRef.child("Users/$numberFir/Vendor/Salon_Station/Services/$selectedService")

                var status:Boolean = false
                // Read from the database
                myRef.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if(status==false) {
                            snapshot.getRef().removeValue()
                            status = true

                            val dialog = AlertDialog.Builder(requireActivity())
                                .setTitle("Successful!")
                                .setMessage("$selectedService is Removed Successful...")
                                .setNeutralButton("Close", { dialog, i ->

                                })
                            dialog.show()
                        }

//                        SingleToast.show(activity, "$status", Toast.LENGTH_LONG);


                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
//                    val navHeader_name: TextView = binding.navigationView.getHeaderView(0).findViewById(R.id.tv_name)
//                    navHeader_name.setText(snapshot.getValue<String>())
//                    Log.d(ContentValues.TAG, "name is: " + snapshot.getValue<String>())
//
//                name = snapshot.getValue<String>().toString()


                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
                    }
                })

            }else{
                SingleToast.show(activity, "There is no Service Available to Remove!", Toast.LENGTH_LONG)

            }



            updateSpinnerFromDatabase()
        }


        binding.btnDone.setOnClickListener {
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(com.caprusdigi.salon_ease.R.id.frameLayout, vendor_salonStation_Services_HomeShow())
            transaction.commit()
        }

        return binding.root
    }

    private fun updateSpinnerFromDatabase() {
        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()


//        set data from firebase to edittext============================
        val rootRef = FirebaseDatabase.getInstance().reference
        val userNameRef = rootRef.child("Users/$numberFir/Vendor/Salon_Station/Services/")
//                    var registerUser: Boolean = false
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


//                create array to store values
                val arrayServices = ArrayList<String>()

                for (childsnapshot in dataSnapshot.getChildren()) {


                    if(childsnapshot != null) {
                        arrayServices.add(childsnapshot.key.toString())
                    }

                }

//                set values to spinner
                val spinnerProduct = binding.spinnerSelectProduct
                if (spinnerProduct != null) {
                    val adapter = ArrayAdapter(
                        requireActivity(),
                        android.R.layout.simple_spinner_item, arrayServices
                    )
                    adapter.setDropDownViewResource(R.layout.textview_with_padding_spinner)
                    spinnerProduct.adapter = adapter
                }else{
//                    binding.spinnerSelectProduct.setEnabled(false)
//                    binding.btnRemoveService.isEnabled = false
                }

            }


            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(ContentValues.TAG, databaseError.message) //Don't ignore errors!
            }
        }
        userNameRef.addListenerForSingleValueEvent(eventListener)
//        ============================
    }


    fun setDataFromFirebaseToForm(){

        Log.d(ContentValues.TAG, "setDataFrom")




        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()

        var auth: FirebaseAuth
        var databaseReference : DatabaseReference? = null
        var database : FirebaseDatabase? = null


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        if(binding.spinnerSelectProduct.getSelectedItem() != null) {
            val selectedService: String = binding.spinnerSelectProduct.getSelectedItem().toString()


            //        Retrieve Data from firebase and put on Form===========
            var myRefName =
                database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$selectedService/Service_Name")
            // Read from the database
            myRefName.addValueEventListener(object : ValueEventListener {
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
            myRefName =
                database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$selectedService/Service_Desc")
            // Read from the database
            myRefName.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    binding.etDescription.setText(snapshot.getValue<String>())
                    binding.etDescription.isEnabled = false
                    Log.d(ContentValues.TAG, "name is: " + snapshot.getValue<String>())
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                    //                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
                }
            })


            //        Retrieve Data from firebase and put on Form===========
            myRefName =
                database!!.getReference("Users/$numberFir/Vendor/Salon_Station/Services/$selectedService/Service_Price")
            // Read from the database
            myRefName.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    binding.etOriginalPrice.setText(snapshot.getValue<String>())
                    binding.etOriginalPrice.isEnabled = false
                    Log.d(ContentValues.TAG, "name is: " + snapshot.getValue<String>())
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                    //                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
                }
            })
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment vendor_salonStation_Services_Remove.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            vendor_salonStation_Services_Remove().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}