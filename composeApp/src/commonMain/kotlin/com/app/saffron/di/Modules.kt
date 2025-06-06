package com.app.saffron.di

import com.app.saffron.base.HttpClientFactory
import com.app.saffron.data.database.AppDatabase
import com.app.saffron.data.database.dao.FlowerDao
import com.app.saffron.data.repository.AuthRepo
import com.app.saffron.data.repository.FlowerRepo
import com.app.saffron.data.repository.impl.AuthRepoImpl
import com.app.saffron.data.repository.impl.FlowerRepoImpl
import com.app.saffron.ui.flower.FlowerViewModel
import com.app.saffron.ui.login.LoginViewModel
import com.app.saffron.ui.profile.ProfileViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    single { HttpClientFactory.create(get()) }

//    single <AppDatabase>{ DatabaseFactory(get()).getDatabase()}

    single<FlowerDao> { get<AppDatabase>().flowerDao() }

    singleOf(::FlowerRepoImpl).bind<FlowerRepo>()
//    single<FlowerRepo> {
//        FlowerRepoImpl(
//            httpClient = get(),
//            flowerDao = get()
//        )
//    }
    singleOf(::AuthRepoImpl).bind<AuthRepo>()

    viewModelOf(::FlowerViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ProfileViewModel)

//    single { FlowerRepository() }
}
