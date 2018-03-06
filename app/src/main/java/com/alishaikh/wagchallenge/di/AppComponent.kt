package com.alishaikh.wagchallenge.di

import android.app.Activity
import android.app.Application
import com.alishaikh.wagchallenge.MainActivity
import com.alishaikh.wagchallenge.StackApp
import dagger.Component
import javax.inject.Singleton

/**
 * Created by alishaikh on 3/5/18.
 */

@Singleton
@Component(modules = [UsersModule::class])
interface AppComponent {
    fun inject(application: StackApp)
    fun inject(activity: MainActivity)
}