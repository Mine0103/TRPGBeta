/*
 * Create by mine on 2020. 11. 23.
 * Copyright (c) 2020. mine. All rights reserved.
 *
 */

package com.mine.trpgbeta

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class chatting: AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)
        val var1 = application as variable
        val db = FirebaseFirestore.getInstance()

        val chatContent = findViewById<EditText>(R.id.chat_content)
        val chatSend = findViewById<Button>(R.id.chat_send)

        val chatListView = findViewById<ListView>(R.id.listview)

        db.collection("chatting").orderBy("create_at").addSnapshotListener { value, error ->
            val dataList: ArrayList<HashMap<String, Any>> = ArrayList()
            if (value != null) {
                for (doc: QueryDocumentSnapshot in value) run {
                    val mapData: HashMap<String, Any> = HashMap()
                    mapData["chat_content"] = doc["chat_content"]!!
                    val time = doc.get("create_at") as Timestamp
                    mapData["create_at"] =
                        SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초").format(time.toDate())
                    dataList.add(mapData)
                }
            }

            val adapter = SimpleAdapter(this, dataList, android.R.layout.simple_list_item_2, arrayOf("chat_content", "create_at"),intArrayOf(android.R.id.text1, android.R.id.text2))
            chatListView.adapter = adapter
        }

        chatSend.setOnClickListener {
            val chatMap: HashMap<String, Any> = HashMap()
            chatMap["chat_content"] = var1.getName()+":\n"+chatContent.text.toString()
            chatMap["create_at"] = Date()
            db.collection("chatting").add(chatMap).addOnSuccessListener {
                Toast.makeText(applicationContext, "전송완료", Toast.LENGTH_SHORT).show()
                chatContent.setText("")
            }
        }
    }
}