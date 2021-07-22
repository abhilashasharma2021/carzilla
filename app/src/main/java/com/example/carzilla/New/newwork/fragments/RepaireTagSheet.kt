package com.example.carzilla.New.newwork.fragments

import android.app.Dialog
import android.app.ProgressDialog
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carzilla.New.newwork.gsonclasses.DataShowTag
import com.example.carzilla.New.newwork.helper.Connection.ADD_TAG_TO_ORDER
import com.example.carzilla.New.newwork.helper.Connection.SHOW_REPAIRE_TAG
import com.example.carzilla.New.newwork.helper.Connection.addTagToOrderID
import com.example.carzilla.New.newwork.helper.Connection.showReapairTag
import com.example.carzilla.New.newwork.helper.GenricAdapter
import com.example.carzilla.New.newwork.helper.ViewHolder
import com.example.carzilla.New.other.Keys
import com.example.carzilla.R
import com.example.carzilla.databinding.ActivityShowRepairTagBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.httpconnection.httpconnectionV2.Http
import com.httpconnection.httpconnectionV2.interfaces.IGetResponse
import com.httpconnection.httpconnectionV2.models.Exception
import com.jaqa.helpers.Craft.getKey
import com.jaqa.helpers.Craft.imageView
import com.jaqa.helpers.Craft.materialCardView
import com.jaqa.helpers.Craft.textView
import org.json.JSONObject

class RepaireTagSheet(val orderDetailsFragment: OrderDetailsFragment) : BottomSheetDialogFragment(),
    View.OnClickListener {
    lateinit var binding: ActivityShowRepairTagBinding
    var mutableListOfTags = mutableSetOf<String>()
    var getSelectedTagIDS = ""
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
        binding = ActivityShowRepairTagBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imgBack.setOnClickListener(this)
        binding.imgcar.setOnClickListener(this)
        binding.loginButton.setOnClickListener(this)
        showRepairTag()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.imgBack -> {
                dismiss()
            }
            binding.loginButton -> {
                addRepairTagToOrder()
            }

            binding.imgcar -> {
                val addTagsSheet = AddTagSheet(this)
                addTagsSheet.show(requireActivity().supportFragmentManager, "tag")
            }
        }
    }

    fun showRepairTag() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("please wait..")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        progressDialog.show()
        Http.Post(SHOW_REPAIRE_TAG)
            .bodyParameter(
                showReapairTag(
                    requireActivity().getKey(Keys.ShopUserId)!!,
                    arguments?.get("order_id") as String
                )
            )
            .build()
            .executeString(object : IGetResponse {
                override fun onResponse(response: String?) {
                    Log.e("flag--", "onResponse(RepaireTagSheet: showRepairTag:65)->$response")
                    progressDialog.dismiss()
                    if (response?.isNotBlank() == true) {
                        val getData = Http().createModelFromClass<DataShowTag>(response)
                        if (getData.result == true) {
                            binding.loginButton.isVisible = true
                            binding.rvShowRepaireTag.apply {
                                layoutManager = LinearLayoutManager(requireActivity())
                                adapter = RvShowReapaireTags(getData.data as ArrayList<DataShowTag.Data>, this@RepaireTagSheet)
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

    fun addRepairTagToOrder() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("please wait..")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        progressDialog.show()
        Http.Post(ADD_TAG_TO_ORDER)
            .bodyParameter(
                addTagToOrderID(
                    requireActivity().getKey(Keys.ShopUserId)!!,
                    orderDetailsFragment.param1!!,
                    getSelectedTagIDS
                )
            )
            .build()
            .executeString(object : IGetResponse {
                override fun onResponse(response: String?) {
                    Log.e(
                        "flag--",
                        "onResponse(RepaireTagSheet: addRepairTagToOrder:125)->$response"
                    )
                    progressDialog.dismiss()
                    if (response?.isNotBlank() == true) {
                        val getAsJson = JSONObject(response)
                        if (getAsJson.getString("result") == "true") {
                            Toast.makeText(
                                requireActivity(),
                                "Tags Added successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            dismiss()
                        } else {
                            Toast.makeText(
                                requireActivity(),
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }

                override fun onError(error: Exception?) {
                    Log.e("flag--", "onError(RepaireTagSheet: showRepairTag:65)->$error")
                    progressDialog.dismiss()
                }
            })
    }

    fun getSelectedTags(id: String?, position: Int, items: ArrayList<DataShowTag.Data>) {
        mutableListOfTags.clear()
        items.forEach {
            if (it.isSelected == 1) {
                it.id?.let { it1 -> mutableListOfTags.add(it1) }
            }
        }
        getSelectedTagIDS = mutableListOfTags.toString().replace("[", "").replace("]", "")
        Log.e("RepaireTagSheet", "getSelectedTags: $mutableListOfTags")
    }
}

private class RvShowReapaireTags(
    items: ArrayList<DataShowTag.Data>,
    val repaireTagSheet: RepaireTagSheet
) : GenricAdapter<DataShowTag.Data>(items) {
    var mColors = arrayOf(
        "#45ddc0", "#dea42d", "#b83800", "#dd0244", "#c90000", "#465400",
        "#ff004d", "#ff6700", "#5d6eff", "#3955ff", "#0a24ff", "#004380", "#6b2e53",
        "#a5c996", "#f94fad", "#ff85bc", "#ff906b", "#b6bc68", "#296139"
    )

    override fun configure(item: DataShowTag.Data, holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            textView(R.id.txtName).text = item.name
            materialCardView(R.id.relImage).setCardBackgroundColor(Color.parseColor(mColors[position % 40]))
            imageView(R.id.imageViewRightTick).isVisible = item.isSelected != 0

            setOnClickListener {
                if (item.isSelected == 0) {
                    item.isSelected = 1
                } else {
                    item.isSelected = 0
                }
                repaireTagSheet.getSelectedTags(item.id, position, items)
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_repaire_tag
    }
}