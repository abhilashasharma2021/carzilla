package com.example.carzilla.New.newwork.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carzilla.New.newwork.gsonclasses.DataShowCreatedOrderDetails
import com.example.carzilla.New.newwork.helper.BaseFragment
import com.example.carzilla.New.newwork.helper.Connection
import com.example.carzilla.New.newwork.helper.Connection.SHOW_CREATED_ORDER_DETAILS
import com.example.carzilla.New.newwork.helper.GenricAdapter
import com.example.carzilla.New.newwork.helper.ViewHolder
import com.example.carzilla.New.other.Keys
import com.example.carzilla.R
import com.example.carzilla.databinding.FragmentOrderDetailsBinding
import com.httpconnection.httpconnectionV2.Http
import com.httpconnection.httpconnectionV2.interfaces.IGetResponse
import com.httpconnection.httpconnectionV2.models.Exception
import com.jaqa.helpers.Craft.getKey
import com.jaqa.helpers.Craft.materialCardView
import com.jaqa.helpers.Craft.textView
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OrderDetailsFragment : BaseFragment(), View.OnClickListener {
    lateinit var binding: FragmentOrderDetailsBinding
     var param1: String? = null
    private var param2: String? = null
    var isRvShowtagVisible = false


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageViewBack.setOnClickListener(this)
        binding.materialCardViewReapairTagAdd.setOnClickListener(this)
        binding.cardViewOrderTag.setOnClickListener(this)
        binding.materialCardViewAdd3.setOnClickListener(this)
        binding.cardViewOrderService.setOnClickListener(this)
        showAddedtags()
        showCreatedOrderDetail()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.imageViewBack -> {
                requireActivity().onBackPressed()
            }
            binding.materialCardViewReapairTagAdd -> {
                val openTagSheet = RepaireTagSheet(this)
                openTagSheet.arguments = bundleOf("order_id" to param1)
                openTagSheet.show(requireActivity().supportFragmentManager, "tag")
            }
            binding.materialCardViewAdd3 -> {
                val openTagSheet = ServiceTagSheet(this)
                openTagSheet.show(requireActivity().supportFragmentManager, "tag")
            }
            binding.cardViewOrderTag -> {
                binding.rvShowAddedTags1.isVisible = !binding.rvShowAddedTags1.isVisible
            }
            binding.cardViewOrderService -> {
                binding.rvShowOrderServices.isVisible = !binding.rvShowOrderServices.isVisible
            }
        }
    }

    fun showAddedtags() {
        val arr = arrayListOf<DataShowTags>()
        for (i in 0 until 4) {
            arr.add(DataShowTags("Volkswagen"))
        }

        binding.rvShowAddedTags1.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = showTags(arr)
        }
    }

    fun showCreatedOrderDetail(){
        Http.Post(SHOW_CREATED_ORDER_DETAILS)
            .bodyParameter(Connection.showCreatedOrderDetails(requireActivity().getKey(Keys.ShopUserId)!!,param1!!))
            .build()
            .executeString(object : IGetResponse {
                override fun onResponse(response: String?) {
                    Log.e("OrderDetailsFragment", "onResponse: $response");
                    if (response?.isNotBlank()== true){
                        val getData = Http().createModelFromClass<DataShowCreatedOrderDetails>(response)
                        if (getData.result == true){
                            with(binding){
                                rvShowOrderServices.apply {
                                    layoutManager = LinearLayoutManager(requireActivity())
                                    adapter = RvShowAddedServices(getData.services as ArrayList<DataShowCreatedOrderDetails.Service>)
                                }
                            }
                        }
                    }
                }

                override fun onError(error: Exception?) {
                    Log.e("OrderDetailsFragment", "onError: $error");
                }

            })
    }


}

data class DataShowTags(val dummy: String)
class showTags(items: ArrayList<DataShowTags>) : GenricAdapter<DataShowTags>(items) {
    var mColors = arrayOf(
        "#45ddc0", "#dea42d", "#b83800", "#dd0244", "#c90000", "#465400",
        "#ff004d", "#ff6700", "#5d6eff", "#3955ff", "#0a24ff", "#004380", "#6b2e53",
        "#a5c996", "#f94fad", "#ff85bc", "#ff906b", "#b6bc68", "#296139"
    )

    override fun configure(item: DataShowTags, holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            // textView(R.id.textViewName).text = item.dummy
            materialCardView(R.id.cardViewSelectedTag).setCardBackgroundColor(
                Color.parseColor(mColors[position % 40]))
        }

    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.show_added_tags
    }
}


class RvShowAddedServices(items: ArrayList<DataShowCreatedOrderDetails.Service>):GenricAdapter<DataShowCreatedOrderDetails.Service>(items){
    override fun configure(
        item: DataShowCreatedOrderDetails.Service,
        holder: ViewHolder,
        position: Int
    ) {
with(holder.itemView){
    textView(R.id.tx).text = item.name
    textView(R.id.textViewPrice).text = item.price
}


    }
    override fun getItemViewType(position: Int): Int {
        return R.layout.rv_show_services
    }
}