package net.minpro.myownflashcards

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    lateinit var realm: Realm

    var strQuestion: String = ""    //問題
    var strAnswer: String = ""  //こたえ
    var intPosition: Int = 0    //行番号

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //     画面が開いたとき（onCreateメソッド）
        //    1.WordListActivityから渡されたIntentの受け取り
        val bundle = intent.extras
        val strStatus = bundle.getString(getString(R.string.intent_key_status))
        textViewStatus.text = strStatus

        //    =>  修正の場合は問題・こたえの表示も
        if (strStatus == getString(R.string.status_change)){
            strQuestion = bundle.getString(getString(R.string.intent_key_question))   //問題
            strAnswer = bundle.getString(getString(R.string.intent_key_answer)) //こたえ
            editTextQuestion.setText(strQuestion)
            editTextAnswer.setText(strAnswer)

            intPosition = bundle.getInt(getString(R.string.intent_key_position))  //行番号

        }

        //     2.前画面で設定した背景色を設定
        constraintLayoutEdit.setBackgroundResource(intBackGroundColor)


        //     登録ボタンを押したとき
        buttonRegister.setOnClickListener {

            if (strStatus == getString(R.string.status_add)){
                //    1.「新しい単語の追加」の場合
                //    => 単語の登録処理(addNewWordメソッド)
                addNewWord()
            } else {
                //    Todo 2.「登録した単語の修正」の場合
                //    => 単語の修正処理(changeWordメソッド)
                changeWord()
            }


        }

        // もどるボタンを押したとき
        buttonBack2.setOnClickListener {
            // 1.今の画面を閉じて単語一覧画面に戻る
            finish()
        }

    }

    override fun onResume() {
        super.onResume()

        //Realmインスタンスの取得
        realm = Realm.getDefaultInstance()
    }

    override fun onPause() {
        super.onPause()

        //realmインスタンスの後片付け
        realm.close()
    }


    private fun changeWord() {
        //Todo 単語の修正処理（changeWordメソッド）
        // 1.選択した行番号のレコードをDBから取得
        val results = realm.where(WordDB::class.java).findAll().sort(getString(R.string.db_field_quesion))
        val selectedDB = results[intPosition]!!

        // 2.入力した問題・こたえで1のレコードを更新
        realm.beginTransaction()
        selectedDB.strQuestion = editTextQuestion.text.toString()
        selectedDB.strAnswer = editTextAnswer.text.toString()
        realm.commitTransaction()

        // 3.入力した文字を入力欄から消す
        editTextQuestion.setText("")
        editTextAnswer.setText("")

        // 4.修正完了メッセージ表示(Toast)
        Toast.makeText(this@EditActivity, "修正が完了しました", Toast.LENGTH_SHORT).show()

        // 5.今の画面を閉じて単語一覧画面に戻る
        finish()
    }


    private fun addNewWord() {
        //     新しい単語の登録処理（addNewWordメソッド）

        //     1.入力した問題・こたえをDBに登録
        realm.beginTransaction()    //開始処理
        val wordDB = realm.createObject(WordDB::class.java)
        wordDB.strQuestion = editTextQuestion.text.toString()
        wordDB.strAnswer = editTextAnswer.text.toString()
        realm.commitTransaction()   //終了処理

        //     2.入力した文字を入力欄から消す
        editTextQuestion.setText("")
        editTextAnswer.setText("")


        //    3.登録完了メッセージ表示(Toast)
        Toast.makeText(this@EditActivity, "登録が完了しました", Toast.LENGTH_SHORT).show()

    }



}












