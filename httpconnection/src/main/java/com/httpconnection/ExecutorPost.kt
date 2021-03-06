package com.e.mylibrary

import org.json.JSONObject

interface ExecutorPost {
    fun url(url:String)
    fun setContentType(setTypeOfHttp:String="application/x-www-form-urlencoded")
    fun connectTimeout(connectTimeout:Int=60000)
    fun readTimeout(readTimeout:Int=60000)
    fun jsonBody(jsonObject: JSONObject)
    fun authenticatHeader(username:String, password:String)
    fun headerParameter(listHeader: MutableMap<String, String>)
    fun bodyParameter(listBody: MutableMap<String, String>)

}