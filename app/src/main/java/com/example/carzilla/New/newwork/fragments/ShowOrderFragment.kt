package com.example.carzilla.New.newwork.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carzilla.New.newwork.HomeActivity
import com.example.carzilla.New.newwork.gsonclasses.ShowAllOrders
import com.example.carzilla.New.newwork.helper.BaseFragment
import com.example.carzilla.New.newwork.helper.Connection
import com.example.carzilla.New.newwork.helper.Connection.SHOW_ALL_ORDERS
import com.example.carzilla.New.newwork.helper.GenricAdapter
import com.example.carzilla.New.newwork.helper.ViewHolder
import com.example.carzilla.New.other.Keys
import com.example.carzilla.R
import com.example.carzilla.databinding.FragmentShowOrderBinding
import com.httpconnection.httpconnectionV2.Http
import com.httpconnection.httpconnectionV2.interfaces.IGetResponse
import com.httpconnection.httpconnectionV2.models.Exception
import com.jaqa.helpers.Craft.getKey
import com.jaqa.helpers.Craft.imageView
import com.jaqa.helpers.Craft.textView
import kotlinx.coroutines.Job
import java.util.*

// created on 17/06/21 by monu
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ShowOrderFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowOrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private var _binding: FragmentShowOrderBinding? = null
    private val binding get() = _binding!!
    val completionJob = Job()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowOrderBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgAdd.setOnClickListener {
            multipleStackNavigator?.start(
                RepairOrderFragment.newInstance("ShowOrderFragment", "")
            )
        }
        binding.imgBack.setOnClickListener { requireActivity().onBackPressed() }
        showOrders()
    }

    override fun onDestroyView() {
        completionJob.cancel()
        (activity as HomeActivity).binding.bottomView.isVisible = true
        val param =
            (activity as HomeActivity).binding.rlContent.layoutParams as ViewGroup.MarginLayoutParams
        param.bottomMargin = 58
        (activity as HomeActivity).binding.fragmentContainer.isVisible = false
        super.onDestroyView()
        _binding = null
    }

    fun editOpen(orderId: String) {
        val get = EditRepairOrderFragment.newInstance(orderId, "")
        multipleStackNavigator?.start(get)
    }

    fun detailOfOrder(orderId: String) {
        val get = OrderDetailsFragment.newInstance(orderId, "")
        multipleStackNavigator?.start(get)
    }

    private fun showOrders() {
        Http.Post(SHOW_ALL_ORDERS)
            .bodyParameter(Connection.showAllOrders(requireActivity().getKey(Keys.ShopUserId)!!))
            .build().executeString(object : IGetResponse {
                override fun onResponse(response: String?) {
                    if (completionJob.isActive) {
                        if (response?.isNotBlank() == true) {
                            Log.e("flag--", "(ShowOrderFragment.kt:72)-->>$response")
                            val getData =
                                Http().createModelFromClass<ShowAllOrders>(response)
                            if (getData.result == true) {
                                binding.recFavourite.apply {
                                    layoutManager = LinearLayoutManager(requireActivity())
                                    adapter = RvShowAllOrders(
                                        getData.data as ArrayList<ShowAllOrders.Data>,
                                        this@ShowOrderFragment
                                    )
                                }
                            }
                        }
                    }
                }

                override fun onError(error: Exception?) {
                    Log.e("flag--", "(ShowOrderFragment.kt:72)-->>$error")
                }
            })
    }

}

private class RvShowAllOrders(
    items: ArrayList<ShowAllOrders.Data>,
    val requireActivity: ShowOrderFragment
) : GenricAdapter<ShowAllOrders.Data>(items) {
    override fun configure(item: ShowAllOrders.Data, holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            textView(R.id.txtName).text = item.day
            textView(R.id.txtComment).text = item.date
            textView(R.id.txtCustomerName).text = item.customer_name
            textView(R.id.txtCustomerMobileNumber).text = item.phone
            imageView(R.id.imgEdit).setOnClickListener {
                item.order_id?.let { it1 -> requireActivity.editOpen(it1) }
            }

            setOnClickListener {
                item.order_id?.let { it1 -> requireActivity.detailOfOrder(it1) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.show_created_adapter
    }
}

