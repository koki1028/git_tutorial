package com.example.syuit.androidcaledar

import android.app.Application
import io.realm.Realm

class calendarClass1:Application() {

    override fun onCreate() {
        super.onCreate()
        //Realmの初期化
        Realm.init(this)
    }
}