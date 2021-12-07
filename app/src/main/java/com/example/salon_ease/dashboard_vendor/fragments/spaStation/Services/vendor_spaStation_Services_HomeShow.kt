package com.example.salon_ease.dashboard_vendor.fragments.spaStation.Services

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salon_ease.FirebaseCustomDefinitions
import com.example.salon_ease.R
import com.example.salon_ease.dashboard_vendor.fragments.salonAtHome.Services.CustomAdapter_services
import com.example.salon_ease.dashboard_vendor.fragments.salonAtHome.Services.ItemsViewModel_services
import com.example.salon_ease.dashboard_vendor.fragments.spaStation.Services.subCategories.vendor_spaStation_Services_subCategories_HomeShow
import com.example.salon_ease.dashboard_vendor.fragments.vendor_spaStation
import com.example.salon_ease.databinding.FragmentVendorSpaStationServicesHomeShowBinding
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
 * Use the [vendor_spaStation_Services_HomeShow.newInstance] factory method to
 * create an instance of this fragment.
 */
class vendor_spaStation_Services_HomeShow : Fragment(R.layout.fragment_vendor_spa_station__services__home_show) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //    intialize binding
    private var _binding: FragmentVendorSpaStationServicesHomeShowBinding? = null
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

                val fragment = vendor_spaStation()
                val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.frameLayout,fragment)
                transaction.commit()

            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVendorSpaStationServicesHomeShowBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Spa Station"



        binding.layoutAddService.setOnClickListener {
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(com.example.salon_ease.R.id.frameLayout, vendor_spaStation_Services_Add())
            transaction.commit()
        }
        binding.layoutRemoveService.setOnClickListener {
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(com.example.salon_ease.R.id.frameLayout, vendor_spaStation_Services_Remove())
            transaction.commit()
        }




        //        recycler view
        // getting the recyclerview by its id
        val recyclerview = binding.recyclerviewServices


        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()


//        set data from firebase to edittext============================
        val rootRef = FirebaseDatabase.getInstance().reference
        val userNameRef = rootRef.child("Users/$numberFir/Vendor/Spa_Station/Services/")
//                    var registerUser: Boolean = false
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                // this creates a vertical layout Manager
                recyclerview.layoutManager = LinearLayoutManager(activity)


// ArrayList of class ItemsViewModel
                val arrayServices = ArrayList<ItemsViewModel_services>()



//                create array to store values
//                val arrayDatabaseServices = ArrayList<String>()


//                extract data from database object
                for (childsnapshot in dataSnapshot.getChildren()) {


                    if(childsnapshot != null) {
//                        SingleToast.show(activity, childsnapshot.key.toString(), Toast.LENGTH_LONG)
                        arrayServices.add(ItemsViewModel_services(R.drawable.ic_assignment_apply_for_vendor_service, childsnapshot.key.toString()))
                    }else{
//                        set image opps you haven't added service yet!
                    }

                }



                // This loop will create 20 Views containing
                // the image with the count of view
//                for (i in arrayDatabaseServices) {
//                    arrayServices.add(ItemsViewModel_services(R.drawable.ic_assignment_apply_for_vendor_service, i))
//                }


                // This will pass the ArrayList to our Adapter
                val adapter = CustomAdapter_services(arrayServices)

                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter

                adapter.setOnItemClickListener(object : CustomAdapter_services.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        val serviceNameSelected =
                            (recyclerview.findViewHolderForAdapterPosition(position)!!.itemView.findViewById(
                                com.example.salon_ease.R.id.tv_serviceName
                            ) as TextView).text.toString()

//                        SingleToast.show(activity, "Position:$position, Value:$serviceNameSelected", Toast.LENGTH_LONG)



//            transfer value to next fragment
                        val fragment = vendor_spaStation_Services_EditService()
                        val arguments = Bundle()
                        arguments.putString("ServiceName", serviceNameSelected)
                        fragment.setArguments(arguments)

//                        replacing fragment
                        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.frameLayout, fragment)
                        transaction.commit()
                    }

                })



            }


            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(ContentValues.TAG, databaseError.message) //Don't ignore errors!
            }
        }
        userNameRef.addListenerForSingleValueEvent(eventListener)
//        ============================

        binding.recyclerviewServices.setOnClickListener {
            recyclerview
        }



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment vendor_spaStation_Services_HomeShow.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            vendor_spaStation_Services_HomeShow().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}