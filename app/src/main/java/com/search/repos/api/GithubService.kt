package com.search.repos.api

import com.search.repos.ui.data.Repos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * News REST API access points
 */
interface GithubService {

    companion object {
        const val ENDPOINT = "https://api.github.com/"
    }

    @GET("search/repositories")
    suspend fun getRepositories(@Query("q") search: String? = null): Response<Repos>
}