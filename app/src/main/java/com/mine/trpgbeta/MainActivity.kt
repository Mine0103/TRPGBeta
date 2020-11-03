/*
 * Create by mine on 2020. 10. 14.
 * Copyright (c) 2020. mine. All rights reserved.
 *
 */

package com.mine.trpgbeta

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import java.io.*
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

class MainActivity : AppCompatActivity() {
    private var drawer: DrawerLayout? = null
    private var txt1: TextView? = null
    private var txt2: TextView? = null
    private var txt3: TextView? = null
    private var txt4: TextView? = null
    private var txt5: TextView? = null
    private val sdcard = Environment.getExternalStorageDirectory().absolutePath;
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Build.VERSION.SDK_INT >= 23) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 3);
            }
        }
        val layout0 = LinearLayout(this)
        layout0.orientation = LinearLayout.VERTICAL
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val toolbar = Toolbar(this)
        toolbar.title = "TRPG(베타)"
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setBackgroundColor(Color.TRANSPARENT)
        ViewCompat.setElevation(toolbar, dip2px(5).toFloat())
        setSupportActionBar(toolbar)
        layout0.addView(toolbar)

        txt1 = addTextView("", 20, Color.WHITE, Gravity.LEFT)
        layout.addView(txt1)
        txt2 = addTextView("", 20, Color.WHITE, Gravity.LEFT)
        txt3 = addTextView("", 20, Color.WHITE, Gravity.LEFT)
        layout.addView(txt3)
        txt4 = addTextView("", 20, Color.WHITE, Gravity.LEFT)
        layout.addView(txt4)
        txt5 = addTextView("", 20, Color.WHITE, Gravity.LEFT)
        layout.addView(txt5)
        val lay1 = LinearLayout(this)
        lay1.orientation = LinearLayout.HORIZONTAL
        lay1.weightSum = 0.5f
        val lay2 = LinearLayout(this)
        lay2.orientation = LinearLayout.HORIZONTAL
        val but1 = addButton("스탯") {
            stat1()
        }
        but1.width = windowManager.defaultDisplay.width/2
        lay1.addView(but1)
        val but2 = addButton("사냥") {
            hunting()
        }
        but2.width = windowManager.defaultDisplay.width/2
        lay1.addView(but2)
        val but3 = addButton("마을") {
            val intent = Intent(applicationContext, village::class.java)
            startActivity(intent)
        }
        but3.width = windowManager.defaultDisplay.width/2
        lay2.addView(but3)
        val meun = addButton("메뉴") {
            showMeun()
        }
        meun.width = windowManager.defaultDisplay.width/2
        lay2.addView(meun)

        layout.addView(lay1)
        layout.addView(lay2)

        val info = addTextView("\n\nby.mine V1.0.0.11\nCopyright (c) 2020. mine. All rights reserved. ", 15, Color.WHITE, Gravity.CENTER)
        layout.addView(info)

        val scroll = ScrollView(this)
        scroll.addView(layout)
        layout0.addView(scroll)
        drawer = DrawerLayout(this)
        drawer!!.addView(layout0)
        val layout2 = createDrawerLayout()
        drawer!!.addView(layout2)
        drawer!!.setBackgroundResource(R.drawable.background)

        val bnl = BottomNavigationLayout(this)
        bnl.addBottomButton("포션", android.R.drawable.ic_menu_search, getRippleDrawable(), Color.WHITE) {
            showPortionInventory()
        }
        bnl.setBackgroundColor(Color.TRANSPARENT)
        drawer!!.addView(bnl)

        setContentView(drawer)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setHomeAsUpIndicator(android.R.drawable.ic_menu_add)

        timer1()
    }

    override fun onBackPressed() {
        exitCheck()
    }
    private fun exitCheck() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("게임 종료")
        dialog.setMessage("TRPG를 종료하시겠습니까?")
        dialog.setNegativeButton("취소", null)
        dialog.setPositiveButton("종료") { dialog, which ->
            finish()
        }
        dialog.show()
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
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(title)
        dialog.setMessage(msg)
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getRippleDrawable(): RippleDrawable? {
        return RippleDrawable(ColorStateList.valueOf(Color.LTGRAY), ColorDrawable(Color.GRAY), null)
    }
    private fun saveData(path: String, msg: String) {
        try {
            val file = File(path)
            val fos = FileOutputStream(file)
            val str = java.lang.String(msg)
            fos.write(str.bytes)
            fos.close()
        } catch (e: IOException) {
            toast(e.toString(), true)
        }
    }
    private fun readData(path: String): String {
        try {
            val file = File(path)
            if(!(file.exists())) return "";
            val fis = FileInputStream(file)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            val line = br.readLine()
            fis.close()
            isr.close()
            br.close()
            return line
        } catch (e: IOException) {
            toast(e.toString(), true)
        }
        return "";
    }

    private fun timer1() {
        val var1 = application as variable
        val tt1: TimerTask = object : TimerTask() {
            override fun run() {
                setText()
                setStat()
                levelUp()
                var1.inventorySetting()
                var1.setInventory()
            }
        }
        val tt2: TimerTask = object : TimerTask() {
            override fun run() {
                healing()
            }
        }
        val timer = Timer()
        timer.schedule(tt1, 100, 100)
        timer.schedule(tt2, 5000, 5000)
    }
    @SuppressLint("SetTextI18n")
    private fun setText() {
        val var1 = application as variable
        runOnUiThread {
            txt1?.text = "체력: ["+var1.stat3[0]+"/"+var1.stat3[1]+"] 레벨: "+var1.stat1[0]+" 경험치: ["+var1.stat1[1]+"/"+var1.stat1[2]+"]"
            txt2?.text = "체력: ["+var1.stat3[0]+"/"+var1.stat3[1]+"] "+" 마나: ["+var1.stat3[2]+"/"+var1.stat3[3]+"]"
            txt3?.text = "스탯: "+var1.stat2[0]+" 힘: "+var1.stat2[1]+" 민첩: "+var1.stat2[2]+" 체력 "+var1.stat2[3]+" 운: "+var1.stat2[4]+" 방어: "+var1.stat2[5]+""
            var st1 = "지능: "+var1.stat2[6]+" 지혜: "+var1.stat2[7]
            txt4?.text = "공격력: "+var1.stat3[4]+" 공격속도: "+var1.stat4[0]+" 치명타확률: "+var1.stat4[1]+"%\n방어력: "+var1.stat4[2]+""//+"마나회복량: "+var1.stat4[3]
            txt5?.text = "돈: "+var1.money
        }
    }
    private fun setStat() {
        val var1 = application as variable
        var1.stat3[4] = 2+(2*var1.stat2[1])
        var1.stat4[0] = 5.0-(var1.stat2[2]*0.001)
        var1.stat3[1] = 10+(5*var1.stat2[3])
        var1.stat4[1] = 0.1*var1.stat2[4]
        var1.stat4[2] = 0.1*var1.stat2[5]
        var1.stat3[3] = 10+var1.stat2[6]
        var1.stat5[1] = (1+ floor(var1.stat2[7].toDouble())).toInt()
    }
    private fun levelUp() {
        val var1 = application as variable
        val n1 = (floor(((var1.stat1[0] - 1) / 9).toDouble()) + 1).toInt() * 10
        if (var1.stat1[1] >= var1.stat1[2]) {
            var1.stat1[0]+=1
            var1.stat1[1]-=var1.stat1[2]
            var1.stat1[2]+=n1
            var1.stat2[0]+=5
            /*if (var1.getHiddenPassive(0)) {
                var1.addstat(25)
            }*/
        }
    }
    private fun healing() {
        val var1 = application as variable
        if(var1.stat3[0]+var1.stat5[0]>var1.stat3[1]) {
            var1.stat3[0] = var1.stat3[1]
        } else {
            var1.stat3[0]+=var1.stat5[0]
        }
        if(var1.stat3[2]+var1.stat5[1]>var1.stat3[3]) {
            var1.stat3[2] = var1.stat3[3]
        } else {
            var1.stat3[2]+=var1.stat5[1]
        }
    }

    private fun showMeun() {
        val dialog = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val list = ListView(this)
        val meuns: Array<String> = arrayOf("패시브 확인", "인벤토리")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, meuns)
        list.adapter = adapter
        list.onItemClickListener = AdapterView.OnItemClickListener() { parent, view, position, id ->
            if(position==0) {
                showPassive()
            }
            if(position==1) {
                showInventoryList()
            }
        }
        layout.addView(list)

        dialog.setView(layout)
        dialog.setTitle("메뉴")
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun stat1() {
        val var1 = application as variable
        val dialog = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val txt1 = addTextView("스탯: " + var1.stat2[0], 20, Color.BLACK, Gravity.CENTER)
        layout.addView(txt1)
        val meuns: Array<String> = arrayOf(
            "힘: " + var1.stat2[1],
            "민첩: " + var1.stat2[2],
            "체력: " + var1.stat2[3],
            "운: " + var1.stat2[4],
            "방어: " + var1.stat2[5]
        )
        val but1 = addButton(meuns[0], null)
        but1.setOnClickListener {
            if(var1.stat2[0]>0) {
                var1.stat2[0]-=1
                var1.stat2[1]+=1
                but1.text = meuns[0];
                txt1.text = "스탯: "+var1.stat2[0]
            } else {
                showDialog("스탯부족", "스텟이 부족합니다.")
            }
        }
        layout.addView(but1)
        val but2 = addButton(meuns[1], null)
        but2.setOnClickListener {
            if(var1.stat2[0]>0) {
                var1.stat2[0]-=1
                var1.stat2[2]+=1
                but2.text = meuns[0];
                txt1.text = "스탯: "+var1.stat2[0]
            } else {
                showDialog("스탯부족", "스텟이 부족합니다.")
            }
        }
        layout.addView(but2)
        val but3 = addButton(meuns[2], null)
        but3.setOnClickListener {
            if(var1.stat2[0]>0) {
                var1.stat2[0]-=1
                var1.stat2[3]+=1
                but3.text = meuns[0];
                txt1.text = "스탯: "+var1.stat2[0]
            } else {
                showDialog("스탯부족", "스텟이 부족합니다.")
            }
        }
        layout.addView(but3)
        val but4 = addButton(meuns[3], null)
        but4.setOnClickListener {
            if(var1.stat2[0]>0) {
                var1.stat2[0]-=1
                var1.stat2[4]+=1
                but2.text = meuns[0];
                txt1.text = "스탯: "+var1.stat2[0]
            } else {
                showDialog("스탯부족", "스텟이 부족합니다.")
            }
        }
        layout.addView(but4)
        val but5 = addButton(meuns[4], null)
        but5.setOnClickListener {
            if(var1.stat2[0]>0) {
                var1.stat2[0]-=1
                var1.stat2[2]+=1
                but2.text = meuns[0];
                txt1.text = "스탯: "+var1.stat2[0]
            } else {
                showDialog("스탯부족", "스텟이 부족합니다.")
            }
        }
        layout.addView(but5)

        dialog.setView(layout)
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }

    private fun hunting() {
        val dialog = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val but1 = addButton("들판") {
            val intent = Intent(applicationContext, field::class.java);
            startActivity(intent)
        }
        layout.addView(but1)

        dialog.setView(layout)
        dialog.setTitle("사냥")
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }

    private fun showPassive() {
        val dialog = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val but1 = addButton("힘 패시브") {
            showStPassive()
        }
        layout.addView(but1)
        val but2 = addButton("민첩 패시브") {
            showSpPassive()
        }
        layout.addView(but2)
        val but3 = addButton("체력 패시브") {
            showHpPassive()
        }
        layout.addView(but3)
        val but4 = addButton("운 패시브") {
            showLuPassive()
        }
        layout.addView(but4)
        val but5 = addButton("방어 패시브") {
            showDePassive()
        }
        layout.addView(but5)
        val but6 = addButton("히든 패시브") {
            showHiddenPassive()
        }
        layout.addView(but6)

        dialog.setView(layout)
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }
    private fun showStPassive() {
        val dialog = AlertDialog.Builder(this)
        val str = StringBuffer();
        dialog.setMessage(str)
        dialog.setTitle("패시브 - 힘")
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }
    private fun showSpPassive() {
        val dialog = AlertDialog.Builder(this)
        val str = StringBuffer();
        dialog.setMessage(str)
        dialog.setTitle("패시브 - 민첩")
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }
    private fun showHpPassive() {
        val dialog = AlertDialog.Builder(this)
        val str = StringBuffer();
        dialog.setMessage(str)
        dialog.setTitle("패시브 - 체력")
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }
    private fun showLuPassive() {
        val dialog = AlertDialog.Builder(this)
        val str = StringBuffer();
        dialog.setMessage(str)
        dialog.setTitle("패시브 - 운")
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }
    private fun showDePassive() {
        val dialog = AlertDialog.Builder(this)
        val str = StringBuffer();
        dialog.setMessage(str)
        dialog.setTitle("패시브 - 방어")
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }
    private fun showHiddenPassive() {
        val dialog = AlertDialog.Builder(this)
        val str = StringBuffer();
        dialog.setMessage(str)
        dialog.setTitle("패시브 - 히든")
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }

    private fun showInventoryList() {
        val dialog = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val but1 = addButton("인벤토리") {
            showInventory()
        }
        layout.addView(but1)
        val but2 = addButton("포션 인벤토리") {
            showPortionInventory()
        }
        layout.addView(but2)

        dialog.setView(layout)
        dialog.setTitle("인벤토리")
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }
    private fun showInventory() {
        val var1 = application as variable
        val dialog = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val listview = ListView(this)
        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, var1.inventory)
        listview.adapter = adapter
        layout.addView(listview)

        dialog.setView(layout)
        dialog.setTitle("인벤토리")
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }
    private fun showPortionInventory() {
        val dialog = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        dialog.setView(layout)
        dialog.setTitle("포션 인벤토리")
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }
    private fun cheakUsePortion() {

    }
}