package com.example.carzilla.New.newwork.fragments.servicetag

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carzilla.New.newwork.fragments.OrderDetailsFragment
import com.example.carzilla.New.newwork.gsonclasses.DataShowServices
import com.example.carzilla.New.newwork.helper.Connection.ADD_SERVICE_INTO_ORDER
import com.example.carzilla.New.newwork.helper.Connection.ADD_SERVICE_INTO_USER_PROIFLE
import com.example.carzilla.New.newwork.helper.Connection.SHOW_SERVICE_TAG
import com.example.carzilla.New.newwork.helper.Connection.addServiceTagInToOrder
import com.example.carzilla.New.newwork.helper.Connection.showServicesInToUserProfile
import com.example.carzilla.New.newwork.helper.Connection.showServicesTag
import com.example.carzilla.New.newwork.helper.GenricAdapter
import com.example.carzilla.New.newwork.helper.ViewHolder
import com.example.carzilla.New.other.Keys
import com.example.carzilla.R
import com.example.carzilla.databinding.ServaiceLayoutSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.httpconnection.httpconnectionV2.Http
import com.httpconnection.httpconnectionV2.interfaces.IGetResponse
import com.httpconnection.httpconnectionV2.models.Exception
import com.jaqa.helpers.Craft
import com.jaqa.helpers.Craft.getKey
import com.jaqa.helpers.Craft.imageView
import com.jaqa.helpers.Craft.materialCardView
import com.jaqa.helpers.Craft.textView
import kotlinx.coroutines.Job
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ServiceTagSheet(val orderDetailsFragment: OrderDetailsFragment) : BottomSheetDialogFragment(),
    View.OnClickListener {
    lateinit var binding: ServaiceLayoutSheetBinding
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
            val bottomSheet =
                (it as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheet).peekHeight =
                Resources.getSystem().displayMetrics.heightPixels
            behavior.isDraggable = false
        }

        return dialog
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ServaiceLayoutSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvShowServices = RvShowServices(arrayList, this)
        showServiceTag()
        binding.addNewServiceLayout.imageViewBack.setOnClickListener {
            isNewService = false
            Craft.crossFadeHideShow(binding.ConstraintLayoutSearch, binding.addNewServiceLayout.addNewServiceLayoutParent)
            binding.addNewServiceLayout.editTextInputPrice.setText("0")
            binding.addNewServiceLayout.editTextInputQty.setText("0")
            binding.addNewServiceLayout.editTextInputTax.setText("0")
            binding.addNewServiceLayout.editTextInputDiscount.setText("0")
        }
        binding.recyclerViewSearchedItem.isVisible = false
        binding.editTextSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                rvShowServices.filter(binding.editTextSearch.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Handler().postDelayed({
                    binding.recyclerViewSearchedItem.isVisible =
                        binding.editTextSearch.text?.isEmpty() != true
                    val getDataAvailableListener =
                        rvShowServices.filter(binding.editTextSearch.text.toString())
                    binding.imageViewAddService.isVisible = getDataAvailableListener.size == 0
                    binding.imageViewAddService.setOnClickListener {
                        isNewService = true
                        getServiceName = binding.editTextSearch.text.toString()
                        binding.addNewServiceLayout.textViewServiceName.text = getServiceName
                        Craft.crossFadeHideShow(
                            binding.addNewServiceLayout.addNewServiceLayoutParent,
                            binding.ConstraintLayoutSearch
                        )
                    }
                }, 300)

            }
        }

        binding.editTextSearch.addTextChangedListener(textWatcher)
        showSoftKeyboard(binding.editTextSearch)
        binding.editTextSearch.requestFocus()


        val getValidateField = Craft.isValidate(binding.addNewServiceLayout.editTextInputPrice)
            .isValidate(binding.addNewServiceLayout.editTextInputQty)
            .isValidate(binding.addNewServiceLayout.editTextInputTax)
            .isValidate(binding.addNewServiceLayout.editTextInputDiscount).getValidatedFields()

        binding.addNewServiceLayout.materialButtonSave.setOnClickListener {
            if (getValidateField) {
                if (isNewService) addServiceInToUserProfile() else addInToOrder()
            }
        }

    }

    override fun onClick(v: View?) {
        when (v) {

        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }


    fun showServiceTag() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("please wait..")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        progressDialog.show()
        Http.Post(SHOW_SERVICE_TAG)
            .bodyParameter(
                showServicesTag(
                    requireActivity().getKey(Keys.ShopUserId)!!,
                    orderDetailsFragment.param1!!
                )
            )
            .build()
            .executeString(object : IGetResponse {
                override fun onResponse(response: String?) {
                    Log.e("flag--", "onResponse(RepaireTagSheet: showServiceTag:65)->$response")
                    progressDialog.dismiss()
                    if (job.isActive) {
                        if (response?.isNotBlank() == true) {
                            val getData = Http().createModelFromClass<DataShowServices>(response)
                            if (getData.result == true) {
                                arrayList.clear()
                                val arr = arrayListOf<Int>()
                                var count = 0
                                getData.data?.forEach {
                                    if (it != null) {
                                        arrayList.add(it)
                                        if (it.isSelected == 1) {
                                            count++
                                        }

                                    }
                                }

                                binding.textViewTotalServicesSelected.text =
                                    count.toString() + "  Services Selected"

                                binding.recyclerViewSearchedItem.apply {
                                    layoutManager = LinearLayoutManager(requireActivity())
                                    adapter = rvShowServices
                                }
                            }
                        }
                    }
                }

                override fun onError(error: Exception?) {
                    Log.e("flag--", "onError(RepaireTagSheet: showRepairTag:65)->$error")
                    progressDialog.dismiss()
                }
            })
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
        binding.textViewTotalServicesSelected.text =
            mutableListOfTags.size.toString() + "  Services Selected"
    }


    fun showAddServiceLayout(item: DataShowServices.Data) {
        getSelectedItemData.clear()
        getSelectedItemData.add(item)
        Craft.crossFadeHideShow(
            binding.addNewServiceLayout.addNewServiceLayoutParent,
            binding.ConstraintLayoutSearch
        )
        getServiceName = item.name
        binding.addNewServiceLayout.textViewServiceName.text = getServiceName
        binding.addNewServiceLayout.textViewServicePrice.text = item.price
        binding.addNewServiceLayout.editTextInputPrice.setText(item.price)
        getSelectedItemId = item.id


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

    fun addServiceInToUserProfile() {
        Http.Post(ADD_SERVICE_INTO_USER_PROIFLE)
            .bodyParameter(
                showServicesInToUserProfile(
                    requireActivity().getKey(Keys.ShopUserId)!!,
                    orderDetailsFragment.param1!!,
                    binding.addNewServiceLayout.editTextInputPrice.text.toString(),
                    binding.addNewServiceLayout.editTextInputQty.text.toString(),
                    binding.addNewServiceLayout.textViewServiceName.text.toString()
                )
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
    }
}

class RvShowServices(
    items: ArrayList<DataShowServices.Data>,
    val serviceTagSheet: ServiceTagSheet
) :
    GenricAdapter<DataShowServices.Data>(items) {
    var localList = ArrayList<DataShowServices.Data>()

    init {
        localList = items
    }

    var mColors = arrayOf(
        "#71CAE9", "#FF9800", "#009688", "#673AB7", "#999999", "#454545", "#00FF00",
        "#FF0000", "#0000FF", "#800000", "#808000", "#00FF00", "#008000", "#00FFFF",
        "#000080", "#800080", "#f40059", "#0080b8", "#350040", "#650040", "#750040",
        "#45ddc0", "#dea42d", "#b83800", "#dd0244", "#c90000", "#465400",
        "#ff004d", "#ff6700", "#5d6eff", "#3955ff", "#0a24ff", "#004380", "#6b2e53",
        "#a5c996", "#f94fad", "#ff85bc", "#ff906b", "#b6bc68", "#296139"
    )

    override fun configure(item: DataShowServices.Data, holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            materialCardView(R.id.materialCardView).setCardBackgroundColor(Color.parseColor(mColors[position % 40]))
            textView(R.id.textViewTitle).text = item.name
            textView(R.id.textViewSubTtile).text = item.id
            textView(R.id.textViewLetter).text = "" + (item.name?.get(0) ?: "")
            if (item.isSelected == 1) {
                imageView(R.id.imageView).setImageResource(R.drawable.ic_right)
            } else {
                imageView(R.id.imageView).setImageResource(R.drawable.add_plus)
            }
            setOnClickListener {
                if (item.isSelected == 0) {
                    item.isSelected = 1
                    imageView(R.id.imageView).setImageResource(R.drawable.ic_right)
                } else {
                    item.isSelected = 0
                    imageView(R.id.imageView).setImageResource(R.drawable.add_plus)
                }
                serviceTagSheet.getSelectedTags(item.id, position, items, localList)
                serviceTagSheet.showAddServiceLayout(item)
                //    notifyItemChanged(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.service_item_layout
    }

    fun filter(char: String): ArrayList<DataShowServices.Data> {
        if (char.isNotEmpty()) {
            update(localList.filter {
                it.name!!.toLowerCase().startsWith(char.toLowerCase())
            } as ArrayList<DataShowServices.Data>)

        } else {
            update(localList)
        }
        return localList.filter {
            it.name!!.toLowerCase(Locale.ROOT).startsWith(char.toLowerCase())
        } as ArrayList<DataShowServices.Data>
    }
}
