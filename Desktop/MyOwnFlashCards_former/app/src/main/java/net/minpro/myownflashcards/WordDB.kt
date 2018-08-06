package net.minpro.myownflashcards

import io.realm.RealmObject

/**
 * Created by keybowNew on 2017/06/19.
 */
//モデルクラスの作成
open class WordDB : RealmObject() {
    //フィールドの設定
    //問題
    open var strQuestion: String = ""
    //こたえ
    open var strAnswer: String = ""
}