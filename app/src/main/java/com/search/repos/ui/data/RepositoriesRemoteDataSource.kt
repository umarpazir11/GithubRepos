package com.search.repos.ui.data

import com.search.repos.BuildConfig
import com.search.repos.api.GithubService
import com.test.mynewsapp.api.BaseDataSource
import javax.inject.Inject

/**
 * Works with the [GithubService] to get data.
 */
class RepositoriesRemoteDataSource @Inject constructor(private val service: GithubService) : BaseDataSource() {

    suspend fun getRepositoriesData(searchReposText: String)
            = getResult { service.getRepositories(searchReposText) }

}
