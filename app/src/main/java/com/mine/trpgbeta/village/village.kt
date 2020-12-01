/*
 * Create by mine on 2020. 11. 13.
 * Copyright (c) 2020. mine. All rights reserved.
 *
 */

package com.mine.trpgbeta.village

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.mine.trpgbeta.R
import com.mine.trpgbeta.variable
import java.util.*
import kotlin.math.ceil

class village: AppCompatActivity() {
    private var drawer: DrawerLayout? = null
    private var txt1: TextView? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val var1 = application as variable
        setContentView(R.layout.activity_village)
        txt1 = findViewById(R.id.time)
        val but1 = findViewById<Button>(R.id.shop)
        but1.setOnClickListener {
            val intent = Intent(applicationContext, shop::class.java)
            if(var1.time in 9..18) startActivity(intent)
            else showDialog("알림", "상점은 9시부터 18시까지 엽니다.")
        }
        val but2 = findViewById<Button>(R.id.casino)
        but2.setOnClickListener {
            val intent = Intent(applicationContext, casino::class.java)
            if(var1.time in 9..23) startActivity(intent)
            else showDialog("알림", "도박장은 9시부터 23시까지 엽니다.")
        }
        val but3 = findViewById<Button>(R.id.equipment)
        but3.setOnClickListener {
            val intent = Intent(applicationContext, equipment::class.java)
            if(var1.time in 9..18) startActivity(intent)
            else showDialog("알림", "장비상점은 9시부터 23시까지 엽니다.")
        }
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setHomeAsUpIndicator(android.R.drawable.ic_menu_add)

        val tt1: TimerTask = object : TimerTask() {
            @SuppressLint("SetTextI18n")
            override fun run() {
                runOnUiThread {
                    txt1?.text = "  시간: ${var1.time}시"
                }
            }
        }
        Timer().schedule(tt1, 100, 100)

    }

    @SuppressLint("RtlHardcoded", "SetTextI18n")
    private fun createDrawerLayout(): LinearLayout? {
        try {
            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            val title = TextView(this)
            title.text = "TRPG"
            title.textSize = 25F
            title.setTextColor(Color.WHITE)
            title.setBackgroundColor(Color.BLUE)
            val pad = dip2px(15)
            title.setPadding(pad, pad, pad, dip2px(20))
            val margin = LinearLayout.LayoutParams(-1, -2)
            margin.setMargins(0, 0, 0, dip2px(10))
            title.layoutParams = margin
            layout.addView(title)
            /*val list = ListView(this)
            val meuns = arrayOf("패시브확인", "인벤토리")
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, meuns)
            list.adapter = adapter
            list.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    if (position == 0) {
                        showPassive()
                    }
                    if (position == 1) {
                        showInventoryList()
                    }
                }
            layout.addView(list)*/
            val params = DrawerLayout.LayoutParams(-1, -1)
            params.gravity = Gravity.LEFT
            layout.layoutParams = params
            layout.setBackgroundColor(Color.WHITE)
            return layout
        } catch (e: Exception) {
            toast(e.toString(), true)
        }
        return null
    }
    @SuppressLint("RtlHardcoded")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        try {
            if (drawer!!.isDrawerOpen(Gravity.LEFT)) {
                drawer!!.closeDrawer(Gravity.LEFT)
            } else {
                drawer!!.openDrawer(Gravity.LEFT)
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dip2px(dips: Int): Int {
        return ceil(dips * this.resources.displayMetrics.density.toDouble()).toInt()
    }
    private fun addTextView(text: String, size: Int, color: Int, gravity: Int?): TextView {
        val txt = TextView(this)
        txt.text = text
        txt.textSize = size.toFloat()
        txt.setTextColor(color)
        if (gravity != null) {
            txt.gravity = gravity
        }
        return txt
    }
    private fun addButton(txt: String, listener: View.OnClickListener?): Button {
        val btn = Button(this)
        btn.text = txt
        if(listener!=null) {
            btn.setOnClickListener(listener)
        }
        return btn
    }
    private fun toast(msg: String, isLong: Boolean) {
        if(isLong) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
    private fun showDialog(title: String, msg: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msg)
            .setNegativeButton("닫기", null)
            .show()
    }
}