package com.example.salon_ease.dashboard_vendor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentTransaction
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.salon_ease.R
import com.example.salon_ease.SingleToast
import com.example.salon_ease.dashboard_vendor.fragments.salonStation.Services.vendor_salonStation_Services_HomeShow
import com.example.salon_ease.dashboard_vendor.fragments.spaStation.Services.vendor_spaStation_Services_HomeShow
import com.example.salon_ease.databinding.FragmentVendorSalonStationBinding
import com.example.salon_ease.databinding.FragmentVendorSpaStationBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [vendor_spaStation.newInstance] factory method to
 * create an instance of this fragment.
 */
class vendor_spaStation : Fragment(R.layout.fragment_vendor_spa_station) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    //    intialize binding
    private var _binding: FragmentVendorSpaStationBinding? = null
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

                val fragment = vendor_Home()
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
        _binding = FragmentVendorSpaStationBinding.inflate(inflater, container, false)


//        code here
        binding.layoutServices.setOnClickListener {
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout, vendor_spaStation_Services_HomeShow())
            transaction.commit()
        }
        binding.layoutBookingHistory.setOnClickListener {
//            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
//            transaction.replace(R.id.frameLayout, vendor_salonAtHome_BookingHistory())
//            transaction.commit()
            SingleToast.show(activity, "Locked!", Toast.LENGTH_LONG);

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment vendor_spaStation.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            vendor_spaStation().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}