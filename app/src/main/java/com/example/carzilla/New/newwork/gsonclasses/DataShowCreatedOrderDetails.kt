package com.example.carzilla.New.newwork.gsonclasses

data class DataShowCreatedOrderDetails(
    val order_id: String?,
    val garage_shop_id: String?,
    val model: String?,
    val fuel_type: String?,
    val vehicle_number: String?,
    val kilometer_driven: String?,
    val chassis_number: String?,
    val engine_number: String?,
    val fuel_indicator: String?,
    val phone: String?,
    val customer_name: String?,
    val email: String?,
    val tax_number: String?,
    val customer_address: String?,
    val customer_remark: String?,
    val order_status: String?,
    val created_by: String?,
    val created_at: String?,
    val updated_by: String?,
    val updated_at: String?,
    val deleted: Any?,
    val enabled: Any?,
    val day: String?,
    val date: String?,
    val repairtag_count: String?,
    val services_count: String?,
    val spare_parts_count: String?,
    val package_count: String?,
    val checklist_count: String?,
    val images_count: String?,
    val repaire_tag: List<RepaireTag?>?,
    val services: List<Service?>?,
    val sparepart: List<Sparepart?>?,
    val `package`: List<Package?>?,
    val check_list: List<Check?>?,
    val order_image: List<Any?>?,
    val result: Boolean?,
    val message: String?
) {
    data class RepaireTag(
        val id: String?,
        val shop_id: String?,
        val name: String?,
        val color_code: String?
    )

    data class Service(
        val id: String?,
        val shop_id: String?,
        val order_id: String?,
        val name: String?,
        val price: String?,
        val quantity: String?,
        val tax: String?,
        val discount: String?
    )

    data class Sparepart(
        val id: String?,
        val shop_id: String?,
        val type: String?,
        val name: String?,
        val part_number: String?,
        val price: String?,
        val purchage_price: String?,
        val in_stock: String?,
        val min_stock: String?,
        val rack: String?,
        val HSN: String?,
        val company_name: String?,
        val description: String?
    )

    data class Package(
        val id: String?,
        val name: String?,
        val description: String?,
        val packageTypeID: String?,
        val services_id: String?,
        val spareparts_id: String?,
        val shop_id: String?
    )

    data class Check(
        val check_listID: String?,
        val check_liastName: String?
    )
}