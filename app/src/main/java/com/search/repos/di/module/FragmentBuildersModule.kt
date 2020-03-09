package com.search.repos.di.module

import com.search.repos.ui.main.RepositoriesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun headLinesFragment(): RepositoriesFragment

}
