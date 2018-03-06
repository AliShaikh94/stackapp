package com.alishaikh.wagchallenge

import android.app.Application
import com.alishaikh.wagchallenge.di.AppComponent
import com.alishaikh.wagchallenge.di.DaggerAppComponent

/**
 * Created by alishaikh on 3/5/18.
 */
class StackApp: Application() {


    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
//        DaggerAppComponent.create().inject(this)
    }
}