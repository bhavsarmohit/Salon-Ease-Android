package com.example.salon_ease.dashboard_vendor.fragments.spaStation.Services.subCategories

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.salon_ease.databinding.FragmentVendorSpaStationServicesSubCategoriesRemoveBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [vendor_spaStation_Services_subCategories_Remove.newInstance] factory method to
 * create an instance of this fragment.
 */
class vendor_spaStation_Services_subCategories_Remove : Fragment(R.layout.fragment_vendor_spa_station__services_sub_categories__remove) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    //    intialize binding
    private var _binding: FragmentVendorSpaStationServicesSubCategoriesRemoveBinding? = null
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


//            transfer value to next fragment
                        val fragment = vendor_spaStation_Services_subCategories_HomeShow()
                        val arguments = Bundle()
                        arguments.putString("ServiceName", serviceName)
                        fragment.setArguments(arguments)

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
        _binding = FragmentVendorSpaStationServicesSubCategoriesRemoveBinding.inflate(inflater, container, false)

        val arguments = arguments
        serviceName = arguments!!.getString("ServiceName")!!
        SingleToast.show(activity, "Service Name:$serviceName", Toast.LENGTH_LONG)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = serviceName

        updateSpinnerFromDatabase()

        binding.btnRemoveSubCategory.setOnClickListener{


            if(binding.spinnerSelectSubCategory.getSelectedItem() != null){
                val selectedService: String = binding.spinnerSelectSubCategory.getSelectedItem().toString()


                // receives logged in user mobile
                val fireCustomDef = FirebaseCustomDefinitions()
                val numberFir = fireCustomDef.getLoggedInProfileMobile()


//        set data from firebase to edittext============================
                val rootRef = FirebaseDatabase.getInstance().reference
                val myRef = rootRef.child("Users/$numberFir/Vendor/Spa_Station/Services/$serviceName/Sub_Categories/$selectedService")


                var status:Boolean = false
                // Read from the database
                myRef.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(status==false) {
                            snapshot.getRef().removeValue()
                            status = true

                            val dialog = AlertDialog.Builder(activity!!)
                                .setTitle("Successful!")
                                .setMessage("$selectedService is Removed Successful...")
                                .setNeutralButton("Close", { dialog, i ->

                                })
                            dialog.show()
                        }




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

//            transfer value to next fragment
            val fragment = vendor_spaStation_Services_subCategories_HomeShow()
            val arguments = Bundle()
            arguments.putString("ServiceName", serviceName)
            fragment.setArguments(arguments)


            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(com.example.salon_ease.R.id.frameLayout, fragment)
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
        val userNameRef = rootRef.child("Users/$numberFir/Vendor/Spa_Station/Services/$serviceName/Sub_Categories/")
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
                val spinnerProduct = binding.spinnerSelectSubCategory
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment vendor_spaStation_Services_subCategories_Remove.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            vendor_spaStation_Services_subCategories_Remove().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}