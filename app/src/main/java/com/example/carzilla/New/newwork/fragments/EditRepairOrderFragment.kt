package com.example.carzilla.New.newwork.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.carzilla.New.newwork.HomeActivity
import com.example.carzilla.New.newwork.gsonclasses.ShowAllOrders
import com.example.carzilla.New.newwork.gsonclasses.ShowFuelType
import com.example.carzilla.New.newwork.gsonclasses.ShowModelType
import com.example.carzilla.New.newwork.helper.BaseFragment
import com.example.carzilla.New.newwork.helper.Connection
import com.example.carzilla.New.other.BaseUrl
import com.example.carzilla.New.other.Keys
import com.example.carzilla.databinding.FragmentRepairOrderBinding
import com.httpconnection.httpconnectionV2.Http
import com.httpconnection.httpconnectionV2.interfaces.IGetResponse
import com.httpconnection.httpconnectionV2.models.Exception
import com.jaqa.helpers.Craft
import com.jaqa.helpers.Craft.getKey
import kotlinx.coroutines.Job
import org.json.JSONObject

// created on 17/06/21 by monu
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditRepairOrderFragment: BaseFragment() {
   // TODO: Rename and change types of parameters
   private var param1:String? = null
   private var param2:String? = null
   private var _binding:FragmentRepairOrderBinding? = null
   private val binding get() = _binding !!
   val completionJob = Job()
   private val completableJob = Job()
   private lateinit var adapterForModelType:ArrayAdapter<String>
   private lateinit var adapterForFuelType:ArrayAdapter<String>
   private val arryListModelTypeName = arrayListOf<String>()
   private val arryListFuelTypeName = arrayListOf<String>()
   private val arryListModelTypeID = arrayListOf<String>()
   private val arryListFuelTypeID = arrayListOf<String>()
   private var modelTypeID:String = ""
   private var fuelTypeID:String = ""
   companion object {
      @JvmStatic
      fun newInstance(param1:String, param2:String) =
         EditRepairOrderFragment().apply {
            arguments = Bundle().apply {
               putString(ARG_PARAM1, param1)
               putString(ARG_PARAM2, param2)
            }
         }
   }

   override fun onCreate(savedInstanceState:Bundle?) {
      super.onCreate(savedInstanceState)
      arguments?.let {
         param1 = it.getString(ARG_PARAM1)
         param2 = it.getString(ARG_PARAM2)
      }
   }

   override fun onCreateView(
      inflater:LayoutInflater, container:ViewGroup?, savedInstanceState:Bundle?
   ):View? {
      _binding = FragmentRepairOrderBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      showOrders()
      showModelType()
      showFuelType()

      binding.btnSaveDetails.setOnClickListener {
         val isValidatefield = Craft.isValidate(
            binding.edtVehicleNumber, "Please enter vehicle number", false, 20
         ).isValidate(binding.edtKiloMEter, "Please enter kilometer", false, 20)
            .isValidate(
               binding.edtChasisNember, "Please enter chassis number", false, 20
            ).isValidate(
               binding.edtEngineNumber, "Please enter engine number", false, 20
            ).isValidate(
               binding.edtPhoneNumber, "Please enter phone number", false, 20
            )
            .isValidate(binding.edtCumtomerName, "Please enter name", false, 20)
            .isValidate(binding.edtEmail, "Please enter email", false, 20)
            .isValidate(
               binding.edtCustomerRemark, "Please enter remark", false, 20
            ).getValidatedFields()
         val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"
         val getEmail = binding.edtEmail.text.toString().trim()
         if (isValidatefield) {
            if (! getEmail.matches(emailPattern.toRegex())) {
               binding.edtEmail.error = "Please enter valid email address"
            } else {
               addOrder()
            }
         }
      }

   }
   override fun onDestroyView() {
      completionJob.cancel()
      completableJob.cancel()
      super.onDestroyView()
      _binding = null
   }



   private fun showOrders() {
      val dialog = ProgressDialog(requireActivity())
      dialog.setMessage("please wait..")
      dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
      dialog.setCancelable(false)
      dialog.show()
      Http.Post(Connection.SHOW_ALL_ORDERS)
         .bodyParameter(Connection.showAllOrders(activity?.getKey(Keys.ShopUserId) !!))
         .build().executeString(object: IGetResponse {
            override fun onResponse(response:String?) {
               dialog.dismiss()
               if (completionJob.isActive) {
                  if (response?.isNotBlank() == true) {
                     Log.e("flag--", "(ShowOrderFragment.kt:72)-->>$response")
                     val getData =
                        Http().createModelFromClass<ShowAllOrders>(response)
                     if (getData.result == true) {
                        getData.data?.forEach {
                           if (it?.order_id == param1){
                              binding.edtVehicleNumber.setText(it?.vehicle_number)
                              binding.edtKiloMEter.setText(it?.kilometer_driven)
                              binding.edtChasisNember.setText(it?.chassis_number)
                              binding.edtEngineNumber.setText(it?.engine_number)
                              binding.edtPhoneNumber.setText(it?.phone)
                              binding.edtCumtomerName.setText(it?.customer_name)
                              binding.edtEmail.setText(it?.email)
                              binding.edtTaxNumber.setText(it?.tax_number)
                              binding.edtCustomerAddress.setText(it?.customer_address)
                              binding.edtCustomerRemark.setText(it?.customer_remark)
                           }
                        }
                     }
                  }
               }
            }

            override fun onError(error:Exception?) {
               dialog.dismiss()

               Log.e("flag--", "(ShowOrderFragment.kt:72)-->>$error")
            }
         })
   }
   private fun addOrder() {
      val dialog = ProgressDialog(requireActivity())
      dialog.setMessage("please wait..")
      dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
      dialog.show()
      Http.Post(BaseUrl.update_order).bodyParameter(
         Connection.editRepairOrder(
            requireActivity().getKey(Keys.ShopUserId) !!,
            binding.autoCompleteModelType.text.toString(),
            binding.autoCompleteFuelType.text.toString(),
            binding.edtVehicleNumber.text.toString(),
            binding.edtKiloMEter.text.toString(),
            binding.edtEngineNumber.text.toString(),
            binding.edtChasisNember.text.toString(),
            binding.slider.value.toString().plus(" %"),
            binding.edtEmail.text.toString(),
            binding.edtPhoneNumber.text.toString(),
            binding.edtCumtomerName.text.toString(),
            binding.edtTaxNumber.text.toString(),
            binding.edtCustomerAddress.text.toString(),
            binding.edtCustomerRemark.text.toString(),
            param1!!
         )
      ).build().executeString(object: IGetResponse {
         override fun onResponse(response:String?) {
            dialog.dismiss()
            if (response?.isNotBlank() == true) {
               Log.e("flag--", "(RepairOrderFragment.kt:247)-->>$response")
               val getAsJson = JSONObject(response)
               if (getAsJson.getString("result") == "true") {
                  Toast.makeText(
                     requireActivity(),
                     "Order Updated Successfully",
                     Toast.LENGTH_SHORT
                  ).show()
                  (activity as HomeActivity).showCount()
                  requireActivity().onBackPressed()
               } else {
                  Toast.makeText(
                     requireActivity(),
                     getAsJson.getString("message"),
                     Toast.LENGTH_SHORT
                  ).show()
               }
            }
         }

         override fun onError(error:Exception?) {
            dialog.dismiss()
            Toast.makeText(
               requireActivity(), "Server Error", Toast.LENGTH_SHORT
            ).show()
            Log.e("flag--", "(RepairOrderFragment.kt:247)-->>$error")
         }
      })
   }


   private fun showModelType() {
      Http.Post(Connection.MAKE_MODEL).bodyParameter(
         Connection.showModeltype(
            requireActivity().getKey(
               Keys.ShopUserId
            ) !!
         )
      ).build().executeString(object: IGetResponse {
         override fun onResponse(response:String?) {
            if (completableJob.isActive) {
               if (response?.isNotBlank() == true) {
                  Log.e("flag--", "(RepairOrderFragment.kt:84)-->>$response")
                  val getData =
                     Http().createModelFromClass<ShowModelType>(response)
                  if (getData.result == true) {
                     arryListModelTypeID.clear()
                     arryListModelTypeName.clear()

                     getData.data?.forEach {
                        arryListModelTypeName.add(it?.name !!)
                        arryListModelTypeID.add(it.make_modelID !!)
                     }

                     adapterForModelType = ArrayAdapter(
                        requireActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        arryListModelTypeName
                     )
                     binding.autoCompleteModelType.setAdapter(
                        adapterForModelType
                     )
                     binding.autoCompleteModelType.setText(
                        binding.autoCompleteModelType.adapter.getItem(
                           0
                        ).toString(), false
                     )
                     binding.autoCompleteModelType.setOnItemClickListener { parent, view, position, id ->
                        modelTypeID = arryListModelTypeID[position]
                        binding.autoCompleteModelType.setText(
                           binding.autoCompleteModelType.adapter.getItem(
                              position
                           ).toString(), false
                        )
                     }
                  }
               }

               if (arryListModelTypeName.size !=0){
                  modelTypeID = binding.autoCompleteModelType.adapter.getItem(0).toString()
               }

            }
         }

         override fun onError(error:Exception?) {
            Log.e("flag--", "(RepairOrderFragment.kt:84)-->>$error")
         }
      })
   }




   private fun showFuelType() {
      Http.Post(Connection.FUEL_TYPE).bodyParameter(
         Connection.showFueltype(
            requireActivity().getKey(
               Keys.ShopUserId
            ) !!
         )
      ).build().executeString(object: IGetResponse {
         override fun onResponse(response:String?) {
            if (completableJob.isActive) {
               if (response?.isNotBlank() == true) {
                  Log.e(
                     "flag--",
                     "onResponse(RepairOrderFragment.kt:149)-->>$response"
                  )
                  val getData =
                     Http().createModelFromClass<ShowFuelType>(response)
                  if (getData.result == true) {
                     arryListFuelTypeName.clear()
                     arryListFuelTypeID.clear()

                     getData.data?.forEach {
                        arryListFuelTypeName.add(it?.name !!)
                        arryListFuelTypeID.add(it.fuel_typeID !!)
                     }

                     adapterForFuelType = ArrayAdapter(
                        requireActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        arryListFuelTypeName
                     )
                     binding.autoCompleteFuelType.setAdapter(
                        adapterForFuelType
                     )
                     binding.autoCompleteFuelType.setText(
                        binding.autoCompleteFuelType.adapter.getItem(
                           0
                        ).toString(), false
                     )
                     binding.autoCompleteFuelType.setOnItemClickListener { parent, view, position, id ->
                        fuelTypeID = arryListFuelTypeID[position]
                        binding.autoCompleteFuelType.setText(
                           binding.autoCompleteFuelType.adapter.getItem(
                              position
                           ).toString(), false
                        )
                     }
                  }
               }

               if (arryListFuelTypeName.size != 0){
                  fuelTypeID = binding.autoCompleteFuelType.adapter.getItem(0).toString()
               }
            }
         }

         override fun onError(error:Exception?) {
            Log.e("flag--", "onError(RepairOrderFragment.kt:189)-->>$error")
         }
      })
   }

}

