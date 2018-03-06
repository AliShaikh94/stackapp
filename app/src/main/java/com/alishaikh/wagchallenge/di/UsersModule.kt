package com.alishaikh.wagchallenge.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.app.Application
import com.alishaikh.wagchallenge.api.UsersApi
import com.alishaikh.wagchallenge.repo.UsersRepository

/**
 * Created by alishaikh on 3/5/18.
 */

@Module
class UsersModule {

    @Provides
    @Singleton
    fun provideUsersApi(): UsersApi = UsersApi.create()

    @Provides
    @Singleton
    fun provideUsersRepository(usersApi: UsersApi): UsersRepository = UsersRepository(usersApi)
}
