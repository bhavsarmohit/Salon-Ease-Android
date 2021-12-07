package com.example.salon_ease.dashboard_vendor.fragments.salonAtHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.salon_ease.R
import com.example.salon_ease.databinding.FragmentVendorSalonAtHomeAddImagesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [vendor_salonAtHome_BookingHistory.newInstance] factory method to
 * create an instance of this fragment.
 */
class vendor_salonAtHome_BookingHistory : Fragment(R.layout.fragment_vendor_salon_at_home__booking_history) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //    intialize binding
    private var _binding: FragmentVendorSalonAtHomeAddImagesBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentVendorSalonAtHomeAddImagesBinding.inflate(inflater, container, false)


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment vendor_salonAtHome_BookingHistory.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            vendor_salonAtHome_BookingHistory().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}