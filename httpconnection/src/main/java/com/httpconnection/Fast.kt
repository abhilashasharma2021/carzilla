package com.httpconnection

import android.app.Activity
import android.util.Base64
import android.util.Log
import com.e.mylibrary.ExecutorPost
import com.e.mylibrary.Upload
import com.httpconnection.exception.ExecutorException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.*
import java.net.SocketTimeoutException
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection


class Fast {
    companion object {
        val post = Post()
        val upload = Upload
    }
    class Post : Activity(),ExecutorPost {
        private var pathURL: String? = null
        private var getAuthenticatHeader: String? = null
        private var getHeaderParameter: MutableMap<String, String> = mutableMapOf()
        private var jsonBody = JSONObject()
        private var bodyparams = JSONObject()
        private var getsetContentType: String? = "application/x-www-form-urlencoded"
        private var checkBodytype: String? = null
        private var connectTimeoutL: Int? = 60000
        private var readTimeoutL: Int? = 60000
        companion object;
        override fun url(url: String) {
            pathURL = url
        }

        override fun setContentType(setTypeOfHttp: String) {
            getsetContentType = setTypeOfHttp
        }

        override fun connectTimeout(connectTimeout: Int) {
            connectTimeoutL = connectTimeout
        }

        override fun readTimeout(readTimeout: Int) {
            readTimeoutL = readTimeout
        }

        override fun jsonBody(jsonObject: JSONObject) {
            checkBodytype = "jsonBody"
            jsonBody = jsonObject


        }

        override fun authenticatHeader(username: String, password: String) {
            getAuthenticatHeader = username.plus(password)
            val encodeAuth: String?
            val auth = "$username:$password"
            val data = auth.toByteArray()
            encodeAuth = "Basic " + Base64.encodeToString(data, Base64.DEFAULT)
            getHeaderParameter["Authorization"] = encodeAuth
        }

        override fun headerParameter(listHeader: MutableMap<String, String>) {
            getHeaderParameter = listHeader

        }

        override fun bodyParameter(listBody: MutableMap<String, String>) {
            checkBodytype = "bodyParameter"
            for ((key, value) in listBody) {
                bodyparams.put(key, value)

            }
        }

        private fun encodeParams(params: JSONObject): String {
            val result = StringBuilder()
            var first = true
            val itr = params.keys()
            while (itr.hasNext()) {
                val key = itr.next()
                val value = params[key]
                if (first) first = false else result.append("&")
                result.append(URLEncoder.encode(key, "UTF-8"))
                result.append("=")
                result.append(URLEncoder.encode(value.toString(), "UTF-8"))
            }
            return result.toString()
        }


         fun executorString(response: com.e.mylibrary.Response) {
            var httpURlConnection: HttpsURLConnection? = null
            var writer: BufferedWriter? = null
            var os: OutputStream?

            GlobalScope.launch(Dispatchers.IO){
                try {
                    httpURlConnection = URL(pathURL).openConnection() as HttpsURLConnection
                    httpURlConnection?.requestMethod = "POST"
                    httpURlConnection?.setRequestProperty("Connection", "Keep-Alive")
                    httpURlConnection?.setRequestProperty("Cache-Control", "no-cache")
                    httpURlConnection?.setRequestProperty("Content-Type", getsetContentType)
                    httpURlConnection?.setRequestProperty("Accept", "*/*")
                    for ((key, value) in getHeaderParameter.entries) {
                        httpURlConnection?.setRequestProperty(key, value)
                    }
                    httpURlConnection?.readTimeout = readTimeoutL!!
                    httpURlConnection?.connectTimeout = connectTimeoutL!!
                    httpURlConnection?.doInput = true
                    httpURlConnection?.doOutput = true
                    os = httpURlConnection?.outputStream
                    if (checkBodytype == "bodyParameter") {
                        writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                        writer?.write(encodeParams(bodyparams))
                        writer?.flush()
                        writer?.close()
                        os?.close()
                    } else if (checkBodytype == "jsonBody") {
                        writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                        writer?.write(jsonBody.toString())
                        writer?.flush()
                        writer?.close()
                        os?.close()
                    }

                    httpURlConnection?.connect()
                    val data = httpURlConnection?.inputStream?.bufferedReader()?.readText()
                    httpURlConnection?.disconnect()
                    if (httpURlConnection?.responseCode == 200) {
                        runOnUiThread {
                            response.onResponse(data)

                        }
                    }
                } catch (se: SocketTimeoutException) {
                    val executorException = ExecutorException(
                        1003, se.localizedMessage,
                        "SocketTimeoutException $se"
                    )

                    runOnUiThread {   response.onError(executorException) }

                } catch (e: IOException) {
                    if (httpURlConnection?.errorStream == null) {
                        val executorException = ExecutorException(
                            1004, "Path of url was not correct",
                            "IOException $e"
                        )
                        runOnUiThread {   response.onError(executorException) }
                    } else {
                        val `in` = InputStreamReader(httpURlConnection?.errorStream)
                        val stringBuilder = StringBuilder()
                        val bufferedReader = BufferedReader(`in`)
                        var cp: Int
                        while (bufferedReader.read().also { cp = it } != -1) {
                            stringBuilder.append(cp.toChar())
                        }
                        bufferedReader.close()
                        `in`.close()

                        val executorException = ExecutorException(
                            httpURlConnection?.responseCode, httpURlConnection?.responseMessage,
                            "IOException $e"
                        )
                        runOnUiThread {   response.onError(executorException) }

                    }

                } catch (e: java.lang.Exception) {
                    if (httpURlConnection?.errorStream == null) {
                        val executorException = ExecutorException(
                            httpURlConnection?.responseCode, httpURlConnection?.responseMessage,
                            " java.lang.Exception $e"
                        )
                        runOnUiThread {   response.onError(executorException) }
                    } else {
                        val `in` = InputStreamReader(httpURlConnection?.errorStream)
                        val stringBuilder = StringBuilder()
                        val bufferedReader = BufferedReader(`in`)
                        var cp: Int
                        while (bufferedReader.read().also { cp = it } != -1) {
                            stringBuilder.append(cp.toChar())
                        }
                        bufferedReader.close()
                        `in`.close()
                        val executorException = ExecutorException(
                            httpURlConnection?.responseCode!!, httpURlConnection?.responseMessage,
                            " java.lang.Exception $e"
                        )
                        runOnUiThread {   response.onError(executorException) }

                    }

                } finally {
                    if (httpURlConnection != null) {
                        httpURlConnection?.disconnect()
                    }

                    if (writer != null) {
                        try {
                            writer?.close()
                        } catch (ex: IOException) {
                            Log.e("Executor writer error", "Executor: $ex")
                        }
                    }

                }

            }

        }


    }
}
