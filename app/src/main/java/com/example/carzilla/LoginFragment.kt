package com.example.carzilla

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.carzilla.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    lateinit var binding:FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding.loginButton.setOnClickListener {


            Navigation.findNavController(it).navigate(R.id.registerFragment)
        }

        return binding.root
    }


}