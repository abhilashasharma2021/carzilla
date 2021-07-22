@file:Suppress("PackageName")

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
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.carzilla.New.newwork.helper.Connection.addReapairTag
import com.example.carzilla.New.newwork.helper.GenricAdapter
import com.example.carzilla.New.newwork.helper.ViewHolder
import com.example.carzilla.New.other.BaseUrl.add_repair_tag
import com.example.carzilla.New.other.Keys
import com.example.carzilla.R
import com.example.carzilla.databinding.AddRepaireTagBinding
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
import org.json.JSONObject

class AddTagSheet(val repaireTagSheet: RepaireTagSheet) : BottomSheetDialogFragment(),
    View.OnClickListener {
    lateinit var binding: AddRepaireTagBinding
    var getTagColor: String? = null
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
        binding = AddRepaireTagBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textViewClose.setOnClickListener(this)
        var mColors = arrayOf(
            "#45ddc0", "#dea42d", "#b83800", "#dd0244", "#c90000", "#465400",
            "#ff004d", "#ff6700", "#5d6eff", "#3955ff", "#0a24ff", "#004380", "#6b2e53",
            "#a5c996", "#f94fad", "#ff85bc", "#ff906b", "#b6bc68", "#296139"
        )
        val arrayListTagColors = arrayListOf<ShowTagColors>()
        mColors.forEach {
            arrayListTagColors.add(ShowTagColors(it))
        }

        binding.rvShowTagColors.apply {
            layoutManager = GridLayoutManager(requireActivity(), 6)
            adapter = RvShowTagColor(
                arrayListTagColors,
                requireActivity(),
                this@AddTagSheet,
                repaireTagSheet
            )
        }
        binding.materialButtonAddTag.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.textViewClose -> {
                dismiss()
            }
            binding.materialButtonAddTag -> {
                val getResult = Craft.isValidate(binding.editTagName).getValidatedFields()
                if (getResult) {
                    addTagApi()
                }
            }
        }
    }

    fun addTagApi() {
        val dialog = ProgressDialog(requireActivity())
        dialog.setMessage("please wait..")
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.setCancelable(false)
        dialog.show()
        Toast.makeText(
            requireActivity(),
            requireActivity().getKey(Keys.ShopUserId)!!,
            Toast.LENGTH_SHORT
        ).show()
        Http.Post(add_repair_tag)
            .bodyParameter(
                addReapairTag(
                    requireActivity().getKey(Keys.ShopUserId)!!,
                    getTagColor!!,
                    binding.editTagName.text.toString()
                )
            )
            .build()
            .executeString(object : IGetResponse {
                override fun onResponse(response: String?) {
                    dialog.dismiss()
                    Log.e("AddTagSheet", "onResponse: $response")
                    if (response?.isNotBlank() == true) {
                        val getAsJson = JSONObject(response)
                        if (getAsJson.getString("result") == "true") {
                            repaireTagSheet.showRepairTag()
                            dismiss()
                        } else {
                            Toast.makeText(
                                requireActivity(),
                                getAsJson.getString("result"),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                }

                override fun onError(error: Exception?) {
                    dialog.dismiss()
                    Log.e("AddTagSheet", "onResponse: $error")

                }

            })
    }

}


data class ShowTagColors(val color: String, var isSelectedColor: Boolean? = false)
private class RvShowTagColor(
    items: ArrayList<ShowTagColors>,
    val requireActivity: FragmentActivity,
    val addTagSheet: AddTagSheet,
    val repaireTagSheet: RepaireTagSheet
) : GenricAdapter<ShowTagColors>(items) {

    override fun configure(item: ShowTagColors, holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            materialCardView(R.id.cardViewTagColor).setCardBackgroundColor(Color.parseColor(item.color))
            imageView(R.id.imageViewRightTick).isVisible = item.isSelectedColor == true
            Log.e("AddTagSheet", "configure: ${addTagSheet.getTagColor}")

            setOnClickListener {
                items.forEach {
                    it.isSelectedColor = false
                }
                item.isSelectedColor = item.isSelectedColor != true
                addTagSheet.getTagColor = item.color
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_tag_color
    }
}