package net.minpro.myownflashcards

import android.app.Application
import io.realm.Realm

/**
 * Created by keybowNew on 2017/06/19.
 */
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        //Realmの初期化
        Realm.init(this)
    }
}