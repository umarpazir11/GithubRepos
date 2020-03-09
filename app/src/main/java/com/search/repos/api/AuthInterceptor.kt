package com.search.repos.api

import android.util.Base64
import com.search.repos.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * A {@see RequestInterceptor} that adds an auth token to requests
 */
class AuthInterceptor() : Interceptor {
    var encode: String = Base64.encodeToString(
        (BuildConfig.USERNAME+ ":" + BuildConfig.PASSWORD).toByteArray(),
        Base64.DEFAULT
    ).replace("\n", "")

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader(
                "Authorization", "Basic $encode").build()
        return chain.proceed(request)
    }
}
