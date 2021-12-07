package com.example.salon_ease.dashboard_vendor.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.salon_ease.FirebaseCustomDefinitions
import com.example.salon_ease.R
import com.example.salon_ease.SingleToast
import com.example.salon_ease.databinding.FragmentVendorEditProfileBinding
import com.example.salon_ease.databinding.FragmentVendorSalonEditProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [vendor_Salon_EditProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class vendor_Salon_EditProfile : Fragment(R.layout.fragment_vendor__salon__edit_profile) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    //    intialize binding
    private var _binding: FragmentVendorSalonEditProfileBinding? = null
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

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Salon Profile"


//        set user data to edit text from firebase
        setEditTextUserData()
        _binding = FragmentVendorSalonEditProfileBinding.inflate(inflater, container, false)


        binding.imgEditProfilePen.setOnClickListener {
            SingleToast.show(activity, "Locked!", Toast.LENGTH_LONG);

        }



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        write code here
        binding.btnSaveProfile.setOnClickListener{

            setUserDataToFirebase()

        }

    }


    private fun setUserDataToFirebase() {
        val name:String = binding.etSalonName.text.toString()
//        val email:String = binding.etEmail.text.toString()
        val mobile:String = binding.etMobile.text.toString()
//        val location:String = binding.etLocation.text.toString()
        val address:String = binding.etAddress.text.toString()
        val city:String = binding.etCity.text.toString()
        val pin:String = binding.etPin.text.toString()
        val openingTime:String = binding.etOpeningTime.text.toString()
        val closingTime:String = binding.etClosingTime.text.toString()

        if (name.isNotEmpty() && mobile.isNotEmpty() && city.isNotEmpty() && pin.isNotEmpty() && openingTime.isNotEmpty() && closingTime.isNotEmpty() && address.isNotEmpty()) {
//            Toast.makeText(activity, name, Toast.LENGTH_SHORT).show()

// Write a message to the database
            val database = Firebase.database

//        create user structure
//        var myRef = database.getReference("Users/")
//        myRef.setValue(mobile)

            // receives logged in user mobile
            val fireCustomDef = FirebaseCustomDefinitions()
            val numberFir = fireCustomDef.getLoggedInProfileMobile()


//            Toast.makeText(this,"mobile:"+mobile+"name:"+name, Toast.LENGTH_SHORT).show()

            var myRef = database.getReference("Users/$numberFir/Vendor/Profile/Salon_Name")
            myRef.setValue(name)

            myRef = database.getReference("Users/$numberFir/Vendor/Profile/Mobile")
            myRef.setValue(mobile)

            myRef = database.getReference("Users/$numberFir/Vendor/Profile/Address")
            myRef.setValue(address)

            myRef = database.getReference("Users/$numberFir/Vendor/Profile/City")
            myRef.setValue(city)

//            myRef = database.getReference("Users/"+numberFir+"/Email")
//            myRef.setValue(email)

//            myRef = database.getReference("Users/"+numberFir+"/Location")
//            myRef.setValue(location)

            myRef = database.getReference("Users/$numberFir/Vendor/Profile/Pin")
            myRef.setValue(pin)

            myRef = database.getReference("Users/$numberFir/Vendor/Profile/Opening_Time")
            myRef.setValue(openingTime)

            myRef = database.getReference("Users/$numberFir/Vendor/Profile/Closing_Time")
            myRef.setValue(closingTime)



        } else {
            Toast.makeText(activity, "Please Fill Required Fields!", Toast.LENGTH_SHORT).show()
        }
//        Toast.makeText(activity, "outside name:"+name, Toast.LENGTH_SHORT).show()
    }



    override fun onDestroy() {
        super.onDestroy()
//        to avoids memory leaks
        _binding = null
    }

    fun setEditTextUserData(){

        // receives logged in user mobile
        val fireCustomDef = FirebaseCustomDefinitions()
        val numberFir = fireCustomDef.getLoggedInProfileMobile()


//        set data from firebase to edittext============================
        val rootRef = FirebaseDatabase.getInstance().reference
        val userNameRef = rootRef.child("Users/$numberFir/Vendor/Profile/")
//                    var registerUser: Boolean = false
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childsnapshot in dataSnapshot.getChildren()) {
//                    childsnapshot.getValue(userProfile::class.java)
//                    val name = childsnapshot.child("Name").getValue(userProfile::class.java)
//                    list.add(meters)
//                    val etName = view.findViewById<TextInputEditText>(R.id.et_name)
//                    etName.setText(name)

                    if (childsnapshot.getKey().equals("Salon_Name")) {

//                        name = childsnapshot.getValue().toString()
                        Log.w(ContentValues.TAG, "Name:${childsnapshot.getValue()}")
                        binding.etSalonName.setText(childsnapshot.getValue().toString())



//                SingleToast.show(, "Name:"+childsnapshot.getValue(), Toast.LENGTH_LONG);
//                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()

                    }
//                    else if (childsnapshot.getKey().equals("Email")) {
//
////                        name = childsnapshot.getValue().toString()
//                        Log.w(ContentValues.TAG, "Email:${childsnapshot.getValue()}")
//
//                        binding.etEmail.setText(childsnapshot.getValue().toString())
//
//
//
//
////                SingleToast.show(, "Name:"+childsnapshot.getValue(), Toast.LENGTH_LONG);
////                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
//
//                    }
                    else if (childsnapshot.getKey().equals("Mobile")) {

//                        name = childsnapshot.getValue().toString()
                        Log.w(ContentValues.TAG, "Mobile:${childsnapshot.getValue()}")

                        binding.etMobile.setText(childsnapshot.getValue().toString())

                        binding.etMobile.isEnabled = false



//                SingleToast.show(, "Name:"+childsnapshot.getValue(), Toast.LENGTH_LONG);
//                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()

                    } else if (childsnapshot.getKey().equals("Address")) {

//                        name = childsnapshot.getValue().toString()
                        Log.w(ContentValues.TAG, "Address:${childsnapshot.getValue()}")


                        binding.etAddress.setText(childsnapshot.getValue().toString())



//                SingleToast.show(, "Name:"+childsnapshot.getValue(), Toast.LENGTH_LONG);
//                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()

                    } else if (childsnapshot.getKey().equals("City")) {

//                        name = childsnapshot.getValue().toString()
                        Log.w(ContentValues.TAG, "City:${childsnapshot.getValue()}")


                        binding.etCity.setText(childsnapshot.getValue().toString())

//                SingleToast.show(, "Name:"+childsnapshot.getValue(), Toast.LENGTH_LONG);
//                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()

                    } else if (childsnapshot.getKey().equals("Pin")) {

//                        name = childsnapshot.getValue().toString()
                        Log.w(ContentValues.TAG, "Pin:${childsnapshot.getValue()}")

                        binding.etPin.setText(childsnapshot.getValue().toString())


//                SingleToast.show(, "Name:"+childsnapshot.getValue(), Toast.LENGTH_LONG);
//                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()

                    }
//                    else if (childsnapshot.getKey().equals("Location")) {
//
////                        name = childsnapshot.getValue().toString()
//                        Log.w(ContentValues.TAG, "Location:${childsnapshot.getValue()}")
//
//                        binding.etLocation.setText(childsnapshot.getValue().toString())
//
//                        binding.etLocation.isEnabled = false
//
////                SingleToast.show(, "Name:"+childsnapshot.getValue(), Toast.LENGTH_LONG);
////                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
//
//                    }
                    else if (childsnapshot.getKey().equals("Opening_Time")) {

//                        name = childsnapshot.getValue().toString()
                        Log.w(ContentValues.TAG, "OpeningTime:${childsnapshot.getValue()}")

                        binding.etOpeningTime.setText(childsnapshot.getValue().toString())


//                SingleToast.show(, "Name:"+childsnapshot.getValue(), Toast.LENGTH_LONG);
//                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()

                    } else if (childsnapshot.getKey().equals("Closing_Time")) {

//                        name = childsnapshot.getValue().toString()
                        Log.w(ContentValues.TAG, "ClosingTime:${childsnapshot.getValue()}")

                        binding.etClosingTime.setText(childsnapshot.getValue().toString())


//                SingleToast.show(, "Name:"+childsnapshot.getValue(), Toast.LENGTH_LONG);
//                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()

                    }


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
         * @return A new instance of fragment vendor_Salon_EditProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            vendor_Salon_EditProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}