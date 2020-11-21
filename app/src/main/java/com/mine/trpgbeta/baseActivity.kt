/*
 * Create by mine on 2020. 11. 19.
 * Copyright (c) 2020. mine. All rights reserved.
 *
 */

package com.mine.trpgbeta

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import kotlin.math.ceil

class baseActivity: AppCompatActivity() {
    private var drawer: DrawerLayout? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout0 = LinearLayout(this)
        layout0.orientation = LinearLayout.VERTICAL
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val toolbar = Toolbar(this)
        toolbar.title = "TRPG(베타)"
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setBackgroundColor(Color.TRANSPARENT)
        ViewCompat.setElevation(toolbar, dip2px(5).toFloat())
        layout0.addView(toolbar)

        drawer = DrawerLayout(this)
        layout0.addView(layout)
        drawer!!.addView(layout0)
        val layout2 = createDrawerLayout()
        drawer!!.addView(layout2)
        drawer!!.setBackgroundResource(R.drawable.background)
        setContentView(drawer)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setHomeAsUpIndicator(android.R.drawable.ic_menu_add)
    }

    override fun onBackPressed() {
        exitCheck()
    }
    private fun exitCheck() {
        AlertDialog.Builder(this)
            .setTitle("게임 종료")
            .setMessage("")
            .setNegativeButton("취소", null)
            .setPositiveButton("종료") { dialog, which ->
                finish()
            }
            .show()
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