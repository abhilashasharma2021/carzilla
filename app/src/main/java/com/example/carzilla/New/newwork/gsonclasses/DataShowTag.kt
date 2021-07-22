package com.example.carzilla.New.newwork.gsonclasses

data class DataShowTag(
    val `data`: List<Data?>?,
    val message: String?, // successful
    val result: Boolean? // true
) {
    data class Data(
        val color_code: String?, // #FFFF00
        val id: String?, // 1
        val name: String?, // Body & paint
        val shop_id: String?, // 0
        var isSelected: Int = 0
    )
}