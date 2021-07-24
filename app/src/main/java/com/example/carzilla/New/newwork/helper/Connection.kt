package com.example.carzilla.New.newwork.helper

object Connection {
    const val BASE_URL =
        "https://maestrosinfotech.org/car_zilla/appservices/process.php?action="
    const val CREATED_ORDER_COUNT = BASE_URL.plus("created_order_count")
    const val MAKE_MODEL = BASE_URL.plus("make_model")
    const val FUEL_TYPE = BASE_URL.plus("fuel_type")
    const val REPAIR_ORDER = BASE_URL.plus("repair_order")
    const val SHOW_ALL_ORDERS = BASE_URL.plus("showAllOrder")
    const val SHOW_REPAIRE_TAG = BASE_URL.plus("show_repair_tag")
    const val SHOW_SERVICE_TAG = BASE_URL.plus("show_services")
    const val ADD_TAG_TO_ORDER = BASE_URL.plus("add_TagsOnOrder")
    const val ADD_SERVICE_INTO_ORDER = BASE_URL.plus("addServicesInPlaceOrder")
        const val ADD_SERVICE_INTO_USER_PROIFLE = BASE_URL.plus("add_services")
        const val SHOW_CREATED_ORDER_DETAILS = BASE_URL.plus("show_order_details")
    const val ADD_SPARE_PARTS = BASE_URL.plus("addSparePart")
        const val SHOW_SPARE_PARTS = BASE_URL.plus("show_spare_type")


    fun showCreatedOrderCount(shopID: String): MutableMap<String, String> {
        return mutableMapOf("shop_id" to shopID)
    }

    fun showModeltype(shopID: String): MutableMap<String, String> {
        return mutableMapOf("shop_id" to shopID)
    }

    fun showFueltype(shopID: String): MutableMap<String, String> {
        return mutableMapOf("shop_id" to shopID)
    }

    fun showAllOrders(shopID: String): MutableMap<String, String> {
        return mutableMapOf("shop_id" to shopID)
    }

    fun addRepairOrder(
        shopID: String,
        make_model: String,
        fuel_type: String,
        vehicle_number: String,
        kilometer_driven: String,
        engine_number: String,
        chassis_number: String,
        fuel_indicator: String,
        email: String,
        phone_number: String,
        customer_name: String,
        tax_number: String,
        cust_address: String,
        cust_remark: String
    ): MutableMap<String, String> {
        return mutableMapOf(
            "make_model" to make_model,
            "fuel_type" to fuel_type,
            "shop_id" to shopID,
            "vehicle_number" to vehicle_number,
            "kilometer_driven" to kilometer_driven,
            "engine_number" to engine_number,
            "chassis_number" to chassis_number,
            "fuel_indicator" to fuel_indicator,
            "email" to email,
            "phone_number" to phone_number,
            "customer_name" to customer_name,
            "tax_number" to tax_number,
            "cust_address" to cust_address,
            "cust_remark" to cust_remark
        )
    }


    fun editRepairOrder(
        shopID: String,
        make_model: String,
        fuel_type: String,
        vehicle_number: String,
        kilometer_driven: String,
        engine_number: String,
        chassis_number: String,
        fuel_indicator: String,
        email: String,
        phone_number: String,
        customer_name: String,
        tax_number: String,
        cust_address: String,
        cust_remark: String,
        orderId: String
    ): MutableMap<String, String> {
        return mutableMapOf(
            "make_model" to make_model,
            "fuel_type" to fuel_type,
            "shop_id" to shopID,
            "vehicle_number" to vehicle_number,
            "kilometer_driven" to kilometer_driven,
            "engine_number" to engine_number,
            "chassis_number" to chassis_number,
            "fuel_indicator" to fuel_indicator,
            "email" to email,
            "phone_number" to phone_number,
            "customer_name" to customer_name,
            "tax_number" to tax_number,
            "cust_address" to cust_address,
            "cust_remark" to cust_remark,
            "order_id" to orderId)
    }


    fun updateProfile(
        ownerName: String,
        workshopName: String,
        email: String,
        phoneNumber: String,
        workshopAddress: String
    ): MutableMap<String, String> {
        return mutableMapOf(
            "" to ownerName,
            "" to workshopName,
            "" to email,
            "" to phoneNumber,
            "" to workshopAddress
        )
    }

    fun showReapairTag(shopID: String,orderID: String): MutableMap<String, String> {
        return mutableMapOf("shop_id" to shopID,"order_id" to orderID)
    }

    fun addReapairTag(shopID: String, colorCode: String, name: String): MutableMap<String, String> {
        return mutableMapOf("shop_id" to shopID, "color code" to colorCode, "name" to name)
    }

    fun addTagToOrderID(shopID: String, orderId: String, tagIDS: String): MutableMap<String, String> {
        return mutableMapOf("shop_id" to shopID, "order_id" to orderId, "repair_tag_id" to tagIDS)
    }

    fun showServicesTag(shopID: String,customerOrderId:String): MutableMap<String, String> {
        return mutableMapOf("shop_id" to shopID,"order_id" to customerOrderId)
    }

    fun showServicesInToUserProfile(shopID: String,customerOrderId:String,price:String,qty:String,name:String): MutableMap<String, String> {
        return mutableMapOf("shop_id" to shopID,"order_id" to customerOrderId,"price" to price,"quantity" to qty,"name" to name)
    }

    fun addServiceTagInToOrder(shopID: String,orderId:String,serviceId:String): MutableMap<String, String> {
        return mutableMapOf("shop_id" to shopID,"order_id" to orderId,"service_id" to serviceId   )
    }

    fun addSparePart(shopID: String,orderId:String,type:String,sparePartName:String,sparePartNumber:String): MutableMap<String, String> {
        return mutableMapOf("shop_id" to shopID,"order_id" to orderId,"type" to type ,"sparePartName" to sparePartName,"sparePartNumber" to sparePartNumber)
    }

    fun showCreatedOrderDetails(shopID: String,orderId:String): MutableMap<String, String> {
        return mutableMapOf("garage_shop_id" to shopID,"order_id" to orderId  )
    }


}