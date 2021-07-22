package com.example.carzilla.New.newwork.gsonclasses

data class ShowOrderCounts(
    val `data`: Data? = null,
    val message: String? = null,
    val result: Boolean? = null
) {
    data class Data(
        val complete: String? = null,
        val create: String? = null,
        val progress: String? = null
    )
}