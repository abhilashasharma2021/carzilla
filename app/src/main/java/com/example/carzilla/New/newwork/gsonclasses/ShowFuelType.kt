package com.example.carzilla.New.newwork.gsonclasses

data class ShowFuelType(
    val `data`: List<Data?>? = null,
    val message: String? = null,
    val result: Boolean? = null
) {
    data class Data(
        val fuel_typeID: String? = null,
        val name: String? = null
    )
}