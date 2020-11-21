
/*
 * Create by mine on 2020. 11. 19.
 * Copyright (c) 2020. mine. All rights reserved.
 *
 */

package com.mine.trpgbeta.village

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.mine.trpgbeta.BottomNavigationLayout
import com.mine.trpgbeta.R
import com.mine.trpgbeta.variable
import java.lang.StringBuilder
import kotlin.math.ceil
import kotlin.math.floor

class casino: AppCompatActivity() {
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

        val but1 = addButton("슬롯머신") {
            slotMachine()
        }
        layout.addView(but1)

        val bnl = BottomNavigationLayout(this)
        bnl.addBottomButton("확률확인", android.R.drawable.ic_menu_search, getRippleDrawable(), Color.WHITE) {

        }
        bnl.setBackgroundColor(Color.TRANSPARENT)
        val bnlLayout = LinearLayout(this)
        bnlLayout.addView(bnl)
        //drawer!!.addView(bnlLayout)

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

    /*//override fun onBackPressed() {
        //exitCheck()
    //}*/
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
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getRippleDrawable(): RippleDrawable? {
        return RippleDrawable(ColorStateList.valueOf(Color.LTGRAY), ColorDrawable(Color.GRAY), null)
    }
    @SuppressLint("SetTextI18n")
    private fun slotMachine() {
        val `var` = application as variable
        val layout = LinearLayout(this)
        val txt = addTextView("남은 횟수:${`var`.slotMachine}", 20, Color.BLACK, Gravity.CENTER)
        layout.addView(txt)
        AlertDialog.Builder(this)
            .setTitle("슬롯머신")
            .setView(layout)
            .setNegativeButton("닫기", null)
            .setPositiveButton("돌리기") { dialog, which ->
                val slot = `var`.slotMachine
                if(slot>0) {
                    `var`.slotMachine-=1
                    val ran1 = floor(Math.random() * 100)+1
                    if(ran1 in 1.0..40.0) {
                        val ran2 = floor(Math.random() * 100)+1
                        if(ran2 in 1.0..30.0) {
                            `var`.stat2[0] += 1
                            toast("스텟1이 나왔습니다.", false)
                        }
                        else if(ran2 in 31.0..45.0) {
                            `var`.stat2[0] += 3
                            toast("스텟3이 나왔습니다.", false)
                        }
                        else if(ran2 in 46.0..50.0) {
                            `var`.stat2[0] += 5
                            toast("스텟5이 나왔습니다.", false)
                        }
                        else if(ran2 in 51.0..80.0) {
                            `var`.money += 1000
                            toast("1000원이 나왔습니다.", false)
                        }
                        else if(ran2 in 81.0..95.0) {
                            `var`.money += 10000
                            toast("10000원이 나왔습니다.", false)
                        }
                        else if(ran2 in 96.0..100.0) {
                            `var`.money += 100000
                            toast("100000원이 나왔습니다.", false)
                        }
                    } else {
                        toast("꽝", false)
                    }
                    txt.text = "남은 횟수:${`var`.slotMachine}"
                } else {
                    showDialog("알림", "남은횟수가 부족합니다.")
                }
            }
            .setNeutralButton("확률확인") { dialog, which ->
                val str = StringBuilder()
                str.append("당첨될 확률:40%\n")
                str.append("스텟1개:30%\n")
                str.append("스텟3개:15%\n")
                str.append("스텟5개:5%\n")
                str.append("1000원:30%\n")
                str.append("10000원:15%\n")
                str.append("100000원:5%\n")
                showDialog("확률표", str.toString())
            }
            .show()
    }
}