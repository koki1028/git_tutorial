package net.minpro.myownflashcards

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_word_list.*

class WordListActivity : AppCompatActivity(), AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    lateinit var realm: Realm
    lateinit var results: RealmResults<WordDB>
    lateinit var word_list: ArrayList<String>
    lateinit var adapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        //    画面が開いたとき
        //    2.前画面で設定した背景色を設定
        constraintLayoutWordList.setBackgroundResource(intBackGroundColor)


        //ボタンのクリック処理
        //    「新しい単語の追加」ボタンを押した場合
        //    「EditActivity」へ（ステータスをIntentで渡す）
        buttonAddNewWord.setOnClickListener {
            val intent = Intent(this@WordListActivity, EditActivity::class.java)
            intent.putExtra(getString(R.string.intent_key_status), getString(R.string.status_add))
            startActivity(intent)
        }
        //    「もどる」ボタンを押した場合
        //     今の画面を閉じて「MainActivity」へ
        buttonBack.setOnClickListener {
            finish()
        }

        //リストのクリックリスナー
        listView.onItemClickListener = this
        listView.onItemLongClickListener = this


    }

    override fun onResume() {
        super.onResume()

        //realmインスタンスの取得
        realm = Realm.getDefaultInstance()

        //    1.DBに登録している単語を一覧表示(ListView)
        results = realm.where(WordDB::class.java).findAll().sort(getString(R.string.db_field_quesion))

        //for文を使ってリストの表示形式を修正する
        word_list = ArrayList<String>() //表示形式を修正した新しいリスト
        val length = results.size
//        for (i in 0 .. length - 1 ){
//            word_list.add(results[i].strAnswer + ":" + results[i].strQuestion)
//        }

        results.forEach {
            word_list.add(it.strAnswer + " : " + it.strQuestion)
        }

        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, word_list)
        listView.adapter = adapter




    }

    override fun onPause() {
        super.onPause()

        //realmインスタンスの後片付け
        realm.close()
    }



    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //      リスト内の単語をタップした場合
        //     1.タップした項目をDBから取得
        val selectedDB = results[position]!!
        val strSelectedQuestion = selectedDB.strQuestion
        val strSelectedAnswer = selectedDB.strAnswer
        //     2.EditActivity(単語編集画面)を開く
        //    ＝＞１で取得した情報(問題/こたえ/行番号)と
        //    ステータスをIntentで渡す
        val intent = Intent(this@WordListActivity, EditActivity::class.java).apply {
            putExtra(getString(R.string.intent_key_question), strSelectedQuestion)   //問題
            putExtra(getString(R.string.intent_key_answer), strSelectedAnswer)   //こたえ
            putExtra(getString(R.string.intent_key_position), position)  //行番号
            putExtra(getString(R.string.intent_key_status), getString(R.string.status_change))   //ステータス
        }
        startActivity(intent)


 }

    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {

        //     リスト内の単語を長押しした場合
        //     1.長押しした項目をDBから取得
        val selectedDB = results[position]!!
        //     2.1で取得した内容をDBから削除
        realm.beginTransaction()
        selectedDB.deleteFromRealm()
        realm.commitTransaction()
        //     3.1で取得した内容を一覧(リスト)からも削除
        word_list.removeAt(position)
        //     4.DBから単語帳データを再取得して表示.
        listView.adapter = adapter

        return true

    }










}
