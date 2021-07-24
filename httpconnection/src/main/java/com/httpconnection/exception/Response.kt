package com.httpconnection.exception

data class Response(
 val result: Result?,
 val executorException: ExecutorException?,
 val responseCode: Int? = null,
 val responseMessage: String? = null
)

data class ExecutorException(
 val responseCode: Int?,
 val responseMessage: String?,
 val Exception: String?
)

data class Result(val responseString: String? = null)