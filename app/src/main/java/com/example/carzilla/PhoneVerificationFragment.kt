package com.example.carzilla

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.carzilla.databinding.FragmentPhoneVerificationBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhoneVerificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhoneVerificationFragment : Fragment() {

    lateinit var binding:FragmentPhoneVerificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPhoneVerificationBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        binding.verifyButton.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.dashboardFragment)
        }


        return binding.root
    }


}