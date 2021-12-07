package com.caprusdigi.salon_ease.dashboard_vendor.fragments.spaStation.Services.subCategories

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.caprusdigi.salon_ease.FirebaseCustomDefinitions
import com.caprusdigi.salon_ease.R
import com.caprusdigi.salon_ease.SingleToast

import com.caprusdigi.salon_ease.databinding.FragmentVendorSpaStationServicesSubCategoriesAddBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [vendor_spaStation_Services_subCategories_Add.newInstance] factory method to
 * create an instance of this fragment.
 */
class vendor_spaStation_Services_subCategories_Add : Fragment(R.layout.fragment_vendor_spa_station__services_sub_categories__add) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    //    intialize binding
    private var _binding: FragmentVendorSpaStationServicesSubCategoriesAddBinding? = null
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
        _binding = FragmentVendorSpaStationServicesSubCategoriesAddBinding.inflate(inflater, container, false)

        val arguments = arguments
        serviceName = arguments!!.getString("ServiceName")!!
        SingleToast.show(activity, "Service Name:$serviceName", Toast.LENGTH_LONG)


        (requireActivity() as AppCompatActivity).supportActionBar?.title = serviceName

        binding.btnAddSubcategory.setOnClickListener {
            addServiceSubcategoryToSpaStation()
        }

//        binding.btnDone.setOnClickListener {
//
////            transfer value to next fragment
//            val fragment = vendor_salonStation_Services_subCategories_HomeShow()
//            val arguments = Bundle()
//            arguments.putString("ServiceName", serviceName)
//            fragment.setArguments(arguments)
//
//
//            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
//            transaction.replace(com.example.salon_ease.R.id.frameLayout, fragment)
//            transaction.commit()
//        }

        return binding.root
    }


    private fun addServiceSubcategoryToSpaStation() {
//        var spinner_service = binding.spinnerSelectService.selectedItem.toString()
        var subCategoryNm = binding.etServiceName!!.text.toString()
        var products = binding.etProducts!!.text.toString()
        var serviceTime = binding.etServiceTime!!.text.toString()
        var originalServicePrice = binding.etOriginalServicePrice!!.text.toString()
        var offerPrice = binding.etOfferPrice!!.text.toString()

//        var serviceNm: String = ""
//        check if fields is not empty
        if(subCategoryNm.isNotEmpty() && products.isNotEmpty() && serviceTime.isNotEmpty() && originalServicePrice.isNotEmpty() && offerPrice.isNotEmpty()) {
            SingleToast.show(activity, "Fields not Empty", Toast.LENGTH_LONG)

            // receives logged in user mobile
            val fireCustomDef = FirebaseCustomDefinitions()
            val numberFir = fireCustomDef.getLoggedInProfileMobile()


//            Toast.makeText(activity, name, Toast.LENGTH_SHORT).show()

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

                        var myRef = database.getReference("Users/$numberFir/Vendor/Spa_Station/Services/$serviceName/Sub_Categories/$subCategoryNm/Service_Name")
                        myRef.setValue(subCategoryNm)

                        myRef = database.getReference("Users/$numberFir/Vendor/Spa_Station/Services/$serviceName/Sub_Categories/$subCategoryNm/Products")
                        myRef.setValue(products)

                        myRef = database.getReference("Users/$numberFir/Vendor/Spa_Station/Services/$serviceName/Sub_Categories/$subCategoryNm/Service_Time")
                        myRef.setValue(serviceTime)

                        myRef = database.getReference("Users/$numberFir/Vendor/Spa_Station/Services/$serviceName/Sub_Categories/$subCategoryNm/Original_Service_Price")
                        myRef.setValue(originalServicePrice)

                        myRef = database.getReference("Users/$numberFir/Vendor/Spa_Station/Services/$serviceName/Sub_Categories/$subCategoryNm/Offer_Price")
                        myRef.setValue(offerPrice)



                        val dialog = AlertDialog.Builder(activity!!)
                            .setTitle("Successful!")
                            .setMessage("$subCategoryNm is Added Successful...")
                            .setNeutralButton("Close", { dialog, i ->

                            })
                        dialog.show()

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
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
//                SingleToast.show(, "Please enter OTP!", Toast.LENGTH_LONG);
                }
            })



//            Toast.makeText(activity, "outside name:" + name, Toast.LENGTH_SHORT).show()

        }else{
            SingleToast.show(activity, "Please fill all Field!", Toast.LENGTH_LONG)

        }



//            SingleToast.show(activity, "selected:$spinner_service", Toast.LENGTH_LONG)



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment vendor_spaStation_Services_subCategories_Add.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            vendor_spaStation_Services_subCategories_Add().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}