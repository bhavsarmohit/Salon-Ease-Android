package com.example.salon_ease.dashboard_vendor.fragments.salonAtHome.Services.subCategories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.salon_ease.R
import com.example.salon_ease.databinding.FragmentVendorSalonAtHomeServicesSubCategoriesAddBinding
import com.example.salon_ease.databinding.FragmentVendorSalonAtHomeServicesSubCategoriesRemoveBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [vendor_salonAtHome_Services_subCategories_Remove.newInstance] factory method to
 * create an instance of this fragment.
 */
class vendor_salonAtHome_Services_subCategories_Remove : Fragment(R.layout.fragment_vendor_salon_at_home__services_sub_categories__remove) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    //    intialize binding
    private var _binding: FragmentVendorSalonAtHomeServicesSubCategoriesRemoveBinding? = null
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
        _binding = FragmentVendorSalonAtHomeServicesSubCategoriesRemoveBinding.inflate(inflater, container, false)

        // declaring an array using arrayOf()
        val arrayname = arrayOf(1, 2, 3, 4, 5)

        val spinnerProduct = binding.spinnerSelectSubCategory
        if (spinnerProduct != null) {
            val adapter = ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_item, arrayname)
            adapter.setDropDownViewResource(R.layout.textview_with_padding_spinner)
            spinnerProduct.adapter = adapter

            spinnerProduct.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    Toast.makeText(activity,"Selected Item:" + arrayname[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
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
         * @return A new instance of fragment vendor_salonAtHome_Services_subCategories_Remove.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            vendor_salonAtHome_Services_subCategories_Remove().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}