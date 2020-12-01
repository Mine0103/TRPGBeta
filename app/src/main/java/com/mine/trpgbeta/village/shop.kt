/*
 * Create by mine on 2020. 11. 13.
 * Copyright (c) 2020. mine. All rights reserved.
 *
 */

package com.mine.trpgbeta.village

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.mine.trpgbeta.R
import com.mine.trpgbeta.variable
import java.util.*

class shop : AppCompatActivity() {
    var drawer: DrawerLayout? = null
    var txt1: TextView? = null
    var tt1: TimerTask? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val `var` = application as variable
        setContentView(R.layout.activity_shop)
        txt1 = findViewById(R.id.money)
        timer1()
    }

    private fun toast(msg: String, isLong: Boolean) {
        if(isLong) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun addButton(text: String?, listener: View.OnClickListener?): Button {
        val btn = Button(this)
        btn.text = text
        btn.setOnClickListener(listener)
        return btn
    }

    private fun addTextView(txt: String?, size: Int, color: Int, gravity: Int): TextView {
        val text = TextView(this)
        text.text = txt
        text.textSize = size.toFloat()
        text.setTextColor(color)
        text.gravity = gravity
        return text
    }

    private fun timer1() {
        val `var` = application as variable
        tt1 = object : TimerTask() {
            @SuppressLint("SetTextI18n")
            override fun run() {
                runOnUiThread { txt1!!.text = "돈: " + `var`.money }
            }
        }
        val timer = Timer()
        timer.schedule(tt1, 100, 100)
    }

    fun buyDialog(v: View) {
        val builder = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        builder.setTitle("구매")
        builder.setNegativeButton("닫기", null)
        val but1 = addButton("포션상점") {
            buyPortionDialog()
        }
        layout.addView(but1)
        val but2 = addButton("잠화상점") {
            buyOtherDialog()
        }
        layout.addView(but2)
        builder.setView(layout)
        builder.show()
    }

    private fun buyPortionDialog() {
        val `var` = application as variable
        val builder = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        builder.setMessage("돈: " + `var`.money)
        val meun = arrayOf<String?>("10회복포션 - 500원", "50회복포션 - 2750원", "100회복포션 - 5500원",
            "500회복포션 - 30250원", "1000회복포션 - 66550원", "2000회복포션 - 146410원",
            "5000회복포션 - 366025원")
        val listView = ListView(this)
        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_list_item_1, meun)
        listView.adapter = adapter
        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val data = parent.getItemAtPosition(position).toString()
            val name = data.split(" - ".toRegex()).toTypedArray()[0]
            val num1 = name.split("회복포션".toRegex()).toTypedArray()[0].toInt()
            val money = data.split(" - ".toRegex()).toTypedArray()[1].split("원".toRegex()).toTypedArray()[0].toInt()
            toast((`var`.money >= money).toString(), true)
            if (`var`.money >= money) {
                buyPortion(num1)
                `var`.money -= money
                builder.setMessage("돈: $money")
            } else {
                showDialog("알림", "돈이 부족합니다.")
            }
        }
        layout.addView(listView)
        builder.setView(layout)
        builder.setNegativeButton("닫기", null)
        builder.show()
    }
    private fun buyPortion(n1: Int) {
        val `var` = application as variable
        var inSize = `var`.insize
        val name = n1.toString() + "회복포션"
        if(inSize==0) {
            `var`.addInsize()
            `var`.itemname[0] = name
            `var`.itemcount[0] = 1
        } else {
            for(i in 0 until inSize) {
                if(`var`.itemname[i] == name) {
                    `var`.itemcount[i]?.plus(1)
                } else {
                    `var`.addInsize()
                    inSize = `var`.insize
                    `var`.itemname[inSize] = name
                    `var`.itemcount[inSize] = 1
                }
            }
        }
        return
    }

    @SuppressLint("SetTextI18n")
    private fun buyOtherDialog() {
        val var1 = application as variable
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val txt = addTextView("돈: ${var1.money}", 20, Color.BLACK, Gravity.CENTER)
        layout.addView(txt)
        val but1 = addButton("슬롯머신 횟수 구매 - 5000원") {
            if(var1.money>=5000) {
                var1.money-=5000
                var1.slotMachine+=1
                txt.text = "돈: ${var1.money}"
            } else {
                showDialog("알림", "돈이 부족합니다.")
            }
        }
        layout.addView(but1)
        AlertDialog.Builder(this)
            .setTitle("잠화 상점")
            .setView(layout)
            .setNegativeButton("닫기", null)
            .show()
    }

    private fun showDialog(title: String?, msg: String?) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msg)
            .setNegativeButton("닫기", null)
            .show()
    }
}