package com.search.repos.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.search.repos.di.CoroutineScropeIO
import com.search.repos.ui.data.Repos
import com.search.repos.ui.data.RepositoriesRemoteDataSource
import com.test.mynewsapp.api.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoriesViewModel @Inject constructor(
    private val repositoriesRemoteDataSource: RepositoriesRemoteDataSource,
    @CoroutineScropeIO private val ioCoroutineScope: CoroutineScope
) : ViewModel() {

    /**
     * Results of APIs call are stored in [liveDataResults]
     */
    val liveDataResults = MutableLiveData<Result<Repos>>()


    /**
     * API call to search repo
     */
    fun searchRepos(searchRepos: String) {
        ioCoroutineScope.launch(getJobErrorHandler()) {
            val response = repositoriesRemoteDataSource.getRepositoriesData(searchRepos)
            liveDataResults.postValue(response)
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        Log.e("Error", message)
    }



}
