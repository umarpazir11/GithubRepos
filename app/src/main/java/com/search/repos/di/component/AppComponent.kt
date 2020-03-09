package com.search.repos.di.component

import android.app.Application
import com.search.repos.BaseApplication
import com.search.repos.di.module.NetworkModule
import com.search.repos.di.module.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Dagger AppComponents
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, NetworkModule::class, MainActivityModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: BaseApplication)
}
