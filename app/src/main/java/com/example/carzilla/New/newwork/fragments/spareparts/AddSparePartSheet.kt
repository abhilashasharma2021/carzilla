package com.example.carzilla.New.newwork.fragments.spareparts

import android.app.Dialog
import android.app.ProgressDialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.example.carzilla.New.newwork.fragments.OrderDetailsFragment
import com.example.carzilla.New.newwork.gsonclasses.DataShowServices
import com.example.carzilla.New.newwork.helper.Connection.ADD_SERVICE_INTO_ORDER
import com.example.carzilla.New.newwork.helper.Connection.addServiceTagInToOrder
import com.example.carzilla.New.other.Keys
import com.example.carzilla.databinding.ActivitySaveSparePartBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.httpconnection.httpconnectionV2.Http
import com.httpconnection.httpconnectionV2.interfaces.IGetResponse
import com.httpconnection.httpconnectionV2.models.Exception
import com.jaqa.helpers.Craft.getKey
import kotlinx.coroutines.Job
import org.json.JSONObject

class AddSparePartSheet(val sparePartSheet: SparePartSheet,val orderDetailsFragment: OrderDetailsFragment) : BottomSheetDialogFragment(),
    View.OnClickListener {
    lateinit var binding: ActivitySaveSparePartBinding
    val job = Job()
    lateinit var rvShowServices: RvShowServices
    val arrayList = arrayListOf<DataShowServices.Data>()
    var mutableListOfTags = mutableSetOf<String>()
    var isAddingNewService = false
    var getSelectedItemData = arrayListOf<DataShowServices.Data>()
    var getSelectedItemId: String? = null
    var getServiceName: String? = null
    var isNewService = false
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet = (it as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheet).peekHeight = Resources.getSystem().displayMetrics.heightPixels
            behavior.isDraggable = false
        }

        return dialog
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ActivitySaveSparePartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

    override fun onClick(v: View?) {
        when (v) {

        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    fun getSelectedTags(
        id: String?,
        position: Int,
        items: ArrayList<DataShowServices.Data>,
        localList: ArrayList<DataShowServices.Data>
    ) {
        mutableListOfTags.clear()
        localList.forEach {
            if (it.isSelected == 1) {
                it.id?.let { it1 -> mutableListOfTags.add(it1) }
            }
        }
        Log.e("RepaireTagSheet", "getSelectedTags: $mutableListOfTags")

    }



    fun addInToOrder() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("please wait..")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        progressDialog.show()
        Http.Post(ADD_SERVICE_INTO_ORDER)
            .bodyParameter(
                addServiceTagInToOrder(
                    requireActivity().getKey(Keys.ShopUserId)!!,
                    orderDetailsFragment.param1!!,
                    getSelectedItemId!!
                )
            )
            .build()
            .executeString(object : IGetResponse {
                override fun onResponse(response: String?) {
                    if (job.isActive) {
                        Log.e("ServiceTagSheet", "onResponse: $response")
                        progressDialog.dismiss()
                        if (response?.isNotBlank() == true) {
                            val getAsJson = JSONObject(response)
                            if (getAsJson.getString("result") == "true") {
                                dismiss()
                                Toast.makeText(
                                    requireActivity(),
                                    "Service Added Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    requireActivity(),
                                    getAsJson.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                override fun onError(error: Exception?) {
                    progressDialog.dismiss()
                    Log.e("ServiceTagSheet", "onError: $error")
                }
            })
    }

  /*  fun addServiceInToUserProfile() {
        Http.Post(ADD_SPARE_PARTS)
            .bodyParameter(
                showServicesInToUserProfile(
                    requireActivity().getKey(Keys.ShopUserId)!!,
                    orderDetailsFragment.param1!!,
                    binding.addNewServiceLayout.editTextInputPrice.text.toString(),
                    binding.addNewServiceLayout.editTextInputQty.text.toString(),
                    binding.addNewServiceLayout.textViewServiceName.text.toString())
            )
            .build()
            .executeString(object : IGetResponse {
                override fun onResponse(response: String?) {
                    if (job.isActive) {
                        Log.e("ServiceTagSheet", "onResponse: $response")
                        if (response?.isNotBlank() == true) {
                            val getAsJson = JSONObject(response)
                            if (getAsJson.getString("result") == "true") {
                                val getAsJsonData = JSONObject(getAsJson.getString("data"))
                                getSelectedItemId = getAsJsonData.getString("id")
                                addInToOrder()
                            } else {
                                Toast.makeText(
                                    requireActivity(),
                                    getAsJson.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                override fun onError(error: Exception?) {
                    Log.e("ServiceTagSheet", "onError: $error")
                }
            })
    }*/
}

