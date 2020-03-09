package com.search.repos.di.module

import com.search.repos.api.AuthInterceptor
import com.search.repos.api.GithubService
import com.search.repos.di.CoroutineScropeIO
import com.search.repos.di.RepositoriesAPI
import com.search.repos.ui.data.RepositoriesRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * [NetworkModule] includes:
 *
 * [Retrofit] Type-safe HTTP client for Android
 *
 * [OkHttpClient] is an HTTP client that’s efficient by default:
 * HTTP/2 support allows all requests to the same host to share a socket.
 * Connection pooling reduces request latency (if HTTP/2 isn’t available).
 * Transparent GZIP shrinks download sizes.
 * Response caching avoids the network completely for repeat requests.
 *
 *[CoroutineScope] : Coroutines are a great way to write asynchronous code that is perfectly readable and maintainable.
 */
@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class NetworkModule {

    @Singleton
    @Provides
    fun provideGithubService(@RepositoriesAPI okhttpClient: OkHttpClient,
                           converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, GithubService::class.java)


    @Singleton
    @Provides
    fun provideRepositoriesRemoteDataSource(legoService: GithubService)
            = RepositoriesRemoteDataSource(legoService)

    @RepositoriesAPI
    @Provides
    fun providePrivateOkHttpClient(
            upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder().addInterceptor(AuthInterceptor()).build()
    }



    @CoroutineScropeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)


    private fun createRetrofit(
            okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(GithubService.ENDPOINT)
                .client(okhttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }

    private fun <T> provideService(okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }
}
