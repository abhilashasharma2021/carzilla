package com.example.carzilla.New.newwork.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.carzilla.New.newwork.HomeActivity
import com.example.carzilla.New.newwork.helper.BaseFragment
import com.example.carzilla.New.newwork.helper.Connection
import com.example.carzilla.databinding.FragmentEditProfileBinding
import com.httpconnection.httpconnectionV2.Http
import com.httpconnection.httpconnectionV2.interfaces.IGetResponse
import com.httpconnection.httpconnectionV2.models.Exception
import com.jaqa.helpers.Craft
// created on 26/06/21 by monu
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class EditProfileFragment : BaseFragment(), View.OnClickListener {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private var param1:String? = null
    private var param2:String? = null
    companion object {
        @JvmStatic
        fun newInstance(param1:String, param2:String) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


        return binding.root
    }

    override fun onDestroyView() {
        if (param1 == "HomeActivity") {

            (activity as HomeActivity).binding.bottomView.isVisible = true
            val param =
                (activity as HomeActivity).binding.rlContent.layoutParams as ViewGroup.MarginLayoutParams
            param.bottomMargin = 58
            (activity as HomeActivity).binding.fragmentContainer.isVisible = false
        }
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.materialButtoSave.setOnClickListener(this)
        binding.imageViewBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.materialButtoSave -> {
                val getValidatedFields = Craft.isValidate(binding.editTextOwnerName)
                    .isValidate(binding.editTextWorkShopName)
                    .isValidate(binding.editTextEmailAddress)
                    .isValidate(binding.editTextPhoneNumber)
                    .isValidate(binding.editTextWorkShopAddress)
                    .getValidatedFields()
                if (getValidatedFields) {
                    //call method here
                }
            }

            binding.imageViewBack ->{
                requireActivity().onBackPressed()
            }
        }
    }

    private fun showProfile() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("please wait..")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        progressDialog.show()
        Http.Post("")
            .bodyParameter(mutableMapOf())
            .build()
            .executeString(object : IGetResponse {
                override fun onResponse(response: String?) {
                    Log.e("flag--", "onResponse(EditProfileFragment: showProfile:45)->$response")
                    progressDialog.dismiss()
                }

                override fun onError(error: Exception?) {
                    Log.e("flag--", "onError(EditProfileFragment: showProfile:45)->$error")
                    progressDialog.dismiss()
                }
            })
    }

    private fun updateProfile() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("please wait..")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        progressDialog.show()
        Http.Post("")
            .bodyParameter(
                Connection.updateProfile(
                    binding.editTextOwnerName.text.toString().trim(),
                    binding.editTextWorkShopName.text.toString().trim(),
                    binding.editTextEmailAddress.text.toString().trim(),
                    binding.editTextPhoneNumber.text.toString().trim(),
                    binding.editTextWorkShopAddress.text.toString().trim()
                )
            )
            .build()
            .executeString(object : IGetResponse {
                override fun onResponse(response: String?) {
                    Log.e("flag--", "onResponse(EditProfileFragment: updateProfile:87)->$response")
                    progressDialog.dismiss()
                }

                override fun onError(error: Exception?) {
                    Log.e("flag--", "onError(EditProfileFragment: updateProfile:87)->$error")
                    progressDialog.dismiss()
                }
            })
    }

}