package com.example.carzilla.New.newwork.gsonclasses

data class DataShowServices(
    val result: Boolean?,
    val message: String?,
    val `data`: List<Data?>?
) {
    data class Data(
        val id: String?,
        val shop_id: String?,
        val name: String?,
        val price: String?,
        var isSelected: Int = 0

    )
}