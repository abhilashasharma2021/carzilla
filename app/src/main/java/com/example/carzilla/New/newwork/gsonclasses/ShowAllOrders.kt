package com.example.carzilla.New.newwork.gsonclasses

data class ShowAllOrders(
    val `data`: List<Data?>? = null,
    val message: String? = null,
    val result: Boolean? = null
) {
    data class Data(
        val chassis_number: String? = null,
        val created_at: String? = null,
        val created_by: String? = null,
        val customer_address: String? = null,
        val customer_name: String? = null,
        val customer_remark: String? = null,
        val date: String? = null,
        val day: String? = null,
        val deleted: Any? = null,
        val email: String? = null,
        val enabled: Any? = null,
        val engine_number: String? = null,
        val fuel_indicator: String? = null,
        val fuel_type: String? = null,
        val garage_shop_id: String? = null,
        val kilometer_driven: String? = null,
        val model: String? = null,
        val order_id: String? = null,
        val order_status: String? = null,
        val phone: String? = null,
        val tax_number: String? = null,
        val updated_at: String? = null,
        val updated_by: String? = null,
        val vehicle_number: String? = null
    )
}