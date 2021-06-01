package com.example.carzilla


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.carzilla.R
import com.example.carzilla.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding.registerButton.setOnClickListener {


            Navigation.findNavController(it).navigate(R.id.phoneVerificationFragment)
        }

        return binding.root
    }


}