package com.example.carzilla.New.newwork.gsonclasses

data class ShowModelType(
    val `data`: List<Data?>? = null,
    val message: String? = null,
    val result: Boolean? = null
) {
    data class Data(
        val make_modelID: String? = null,
        val name: String? = null
    )
}