/*
 * Create by mine on 2020. 10. 14.
 * Copyright (c) 2020. mine. All rights reserved.
 *
 */

package com.mine.trpgbeta

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
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
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private var drawer: DrawerLayout? = null
    private var txt1: TextView? = null
    private var txt2: TextView? = null
    private var txt3: TextView? = null
    private var txt4: TextView? = null
    private var txt5: TextView? = null
    private val sdcard = Environment.getExternalStorageDirectory().absolutePath
    private var isStart = false
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Build.VERSION.SDK_INT >= 23) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 3)
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
        but1.setOnLongClickListener {
            val var1 = application as variable
            if (var1.plusStat) {
                showDialog("알림", "이미 보너스 스탯을 받았습니다.")
            }
            if (!var1.plusStat) {
                var1.plusStat = true
                var1.stat2[0]+=10
                toast("보너스 스탯을 받았습니다.", false)
            }
            false
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

        val info = addTextView(
            "\n\nby.mine V1.0.0.11\nCopyright (c) 2020. mine. All rights reserved. ",
            15,
            Color.WHITE,
            Gravity.CENTER
        )
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
        bnl.addBottomButton(
            "포션",
            android.R.drawable.ic_menu_search,
            getRippleDrawable(),
            Color.WHITE
        ) {
            showPortionInventory()
        }
        bnl.setBackgroundColor(Color.TRANSPARENT)
        drawer!!.addView(bnl)

        setContentView(drawer)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setHomeAsUpIndicator(android.R.drawable.ic_menu_add)

        timer1()

        val var1 = application as variable
        val preferences = getSharedPreferences("variable", MODE_PRIVATE)
        if(isStart) {
            readStat(preferences, var1)
            readVariable(preferences, var1)
            readInventory(preferences, var1)
            readPortion(preferences, var1)
        }

    }
    private fun readStat(preferences: SharedPreferences, var1: variable) {
        var1.stat1[0] = preferences.getInt("stat1[0]", 1)
        var1.stat1[1] = preferences.getInt("stat1[1]", 0)
        var1.stat1[2] = preferences.getInt("stat1[2]", 10)

        var1.stat2[0] = preferences.getInt("stat2[0]", 10)
        var1.stat2[1] = preferences.getInt("stat2[1]", 0)
        var1.stat2[2] = preferences.getInt("stat2[2]", 0)
        var1.stat2[3] = preferences.getInt("stat2[3]", 0)
        var1.stat2[4] = preferences.getInt("stat2[4]", 0)
        var1.stat2[5] = preferences.getInt("stat2[5]", 0)
        var1.stat2[6] = preferences.getInt("stat2[6]", 0)
        var1.stat2[7] = preferences.getInt("stat2[7]", 0)

        var1.stat3[0] = preferences.getInt("stat3[0]", 10)
        var1.stat3[1] = preferences.getInt("stat3[1]", 10)
        var1.stat3[2] = preferences.getInt("stat3[2]", 10)
        var1.stat3[3] = preferences.getInt("stat3[3]", 10)
        var1.stat3[4] = preferences.getInt("stat3[4]", 2)

        var1.stat4[0] = preferences.getFloat("stat4[0]", 5.0f).toDouble()
        var1.stat4[1] = preferences.getFloat("stat4[1]", 0.0f).toDouble()
        var1.stat4[2] = preferences.getFloat("stat4[2]", 0.0f).toDouble()
    }

    private fun readVariable(preferences: SharedPreferences, var1: variable) {
        var1.plusStat = preferences.getBoolean("plusStat", false)
        var1.money = preferences.getInt("money", 500)
        var1.insize = preferences.getInt("inSize", 0)
    }

    private fun readInventory(preferences: SharedPreferences, var1: variable) {
        for (n in 0 until var1.insize) {
            val s1 = "im$n"
            var1.itemname[n] = preferences.getString(s1, "")
            val s2 = "ic$n"
            var1.itemcount[n] = preferences.getInt(s2, 0)
        }
    }

    private fun readPortion(preferences: SharedPreferences, var1: variable) {
        for (n in 0..6) {
            var1.portionCount[n] = preferences.getInt("p$n", 0)
        }
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
    override fun onStop() {
        val `var` = application as variable
        val preferences: SharedPreferences = getSharedPreferences("variable", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        saveStat(editor, `var`)
        saveVariable(editor, `var`)
        saveInventory(editor, `var`)
        savePortion(editor, `var`)
        editor.apply()
        if(!isStart) isStart = true
        super.onStop()
    }

    private fun saveStat(editor: SharedPreferences.Editor, var1: variable) {
        editor.putInt("stat1[0]", var1.stat1[0])
        editor.putInt("stat1[1]", var1.stat1[1])
        editor.putInt("stat1[2]", var1.stat1[2])

        editor.putInt("stat2[0]", var1.stat2[0])
        editor.putInt("stat2[1]", var1.stat2[1])
        editor.putInt("stat2[2]", var1.stat2[2])
        editor.putInt("stat2[3]", var1.stat2[3])
        editor.putInt("stat2[4]", var1.stat2[4])
        editor.putInt("stat2[5]", var1.stat2[5])
        editor.putInt("stat2[6]", var1.stat2[6])
        editor.putInt("stat2[7]", var1.stat2[7])

        editor.putInt("stat3[0]", var1.stat3[0])
        editor.putInt("stat3[1]", var1.stat3[1])
        editor.putInt("stat3[2]", var1.stat3[2])
        editor.putInt("stat3[3]", var1.stat3[3])
        editor.putInt("stat3[4]", var1.stat3[4])

        editor.putFloat("stat4[0]", var1.stat4[0].toFloat())
        editor.putFloat("stat4[1]", var1.stat4[1].toFloat())
        editor.putFloat("stat4[2]", var1.stat4[2].toFloat())
    }

    private fun saveVariable(editor: SharedPreferences.Editor, var1: variable) {
        editor.putBoolean("plusStat", var1.plusStat)
        editor.putInt("money", var1.money)
        editor.putInt("inSize", var1.insize)
    }

    private fun saveInventory(editor: SharedPreferences.Editor, var1: variable) {
        for (n in 0 until var1.insize) {
            val s1 = "im$n"
            editor.putString(s1, var1.itemname[n])
            val s2 = "ic$n"
            var1.itemcount[n]?.let { editor.putInt(s2, it) }
        }
    }

    private fun savePortion(editor: SharedPreferences.Editor, var1: variable) {
        for (n in 0..6) {
            editor.putInt("p$n", var1.portionCount[n])
        }
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
        val meuns: Array<String> = arrayOf("패시브 확인", "인벤토리", "기타")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, meuns)
        list.adapter = adapter
        list.onItemClickListener = AdapterView.OnItemClickListener() { parent, view, position, id ->
            if(position==0) {
                showPassive()
            }
            if(position==1) {
                showInventoryList()
            }
            if(position==2) {
                otherFun();
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
                but1.text = meuns[0]
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
                but2.text = meuns[1]
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
                but3.text = meuns[2]
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
                but4.text = meuns[3]
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
                var1.stat2[5]+=1
                but5.text = meuns[4]
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
            val intent = Intent(applicationContext, field::class.java)
            startActivity(intent)
        }
        layout.addView(but1)
        val but2 = addButton("숲") {
            val intent = Intent(applicationContext, forest::class.java)
            startActivity(intent)
        }
        layout.addView(but2)
        val but3 = addButton("심해") {
            val intent = Intent(applicationContext, deepsea::class.java)
            startActivity(intent)
        }
        layout.addView(but3)
        val but4 = addButton("화산") {
            val intent = Intent(applicationContext, volcano::class.java)
            startActivity(intent)
        }
        layout.addView(but4)
        val raid = addButton("레이드") {
            val intent = Intent(applicationContext, raid::class.java);
            startActivity(intent)
        }
        layout.addView(raid)

        dialog.setView(layout)
        dialog.setTitle("사냥")
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }

    private fun showPassive() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("패시브확인")
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val meun = arrayOf("힘", "민첩", "체력", "운", "방어", "히든 패시브")
        val listView = ListView(this)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, meun)
        listView.adapter = adapter
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                if (position == 0) {
                    showStPassive()
                }
                if (position == 1) {
                    showSpPassive()
                }
                if (position == 2) {
                    showHelPassive()
                }
                if (position == 3) {
                    showLuPassive()
                }
                if (position == 4) {
                    showDePassive()
                }
                if (position == 5) {
                    showHiddenPassive()
                }
            }
        layout.addView(listView)
        builder.setView(layout)
        builder.setNegativeButton("닫기", null)
        builder.show()
    }

    private fun showStPassive() {
        val `var` = application as variable
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """1.공격력 10증가 - ${`var`.passive[0][0]}""".trimIndent()+"\n"
        )
        stringBuilder.append(
            """2.몬스터 처치시 최대체력의 2%회복 - ${`var`.passive[0][1]}""".trimIndent()+"\n"
        )
        stringBuilder.append(
            """3.체력이 80%이상일때 공격력 20증가 - ${`var`.passive[0][2]}""".trimIndent()+"\n"
        )
        stringBuilder.append(
            """4.타격시 5%확률로 상대를 2초간 기절시킨다 - ${`var`.passive[0][3]}""".trimIndent()+"\n"
        )
        showDialog("패시브확인 - 힘", stringBuilder.toString())
    }

    private fun showSpPassive() {
        val `var` = application as variable
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """1.공격속도 0.05초 감소 - ${`var`.passive[1][0]}""".trimIndent()+"\n"
        )
        stringBuilder.append(
            """2.공격속도 0.2초 감소 - ${`var`.passive[1][1]}""".trimIndent()+"\n"
        )
        showDialog("패시브확인 - 민첩", stringBuilder.toString())
    }

    private fun showHelPassive() {
        val `var` = application as variable
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """1.체력 50증가 - ${`var`.passive[2][0]}""".trimIndent()+"\n"
        )
        stringBuilder.append(
            """2.체력회복량 5로 증가 - ${`var`.passive[2][1]}""".trimIndent()+"\n"
        )
        showDialog("패시브확인 - 체력", stringBuilder.toString())
    }

    private fun showLuPassive() {
        val `var` = application as variable
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """1.크리티컬확률 2.5%증가 - ${`var`.passive[3][0]}""".trimIndent()+"\n"
        )
        stringBuilder.append(
            """2.공격력 20증가 - ${`var`.passive[3][1]}""".trimIndent()+"\n"
        )
        showDialog("패시브확인 - 운", stringBuilder.toString())
    }

    private fun showDePassive() {
        val `var` = application as variable
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """1.방어력 5증가 - ${`var`.passive[4][0]}""".trimIndent()+"\n"
        )
        stringBuilder.append(
            """2.체력 20증가 - ${`var`.passive[4][1]}""".trimIndent()+"\n"
        )
        stringBuilder.append(
            """5.맞을때 1%의확률 데미지를 받지 않는다. - ${`var`.passive[4][2]}""".trimIndent()+"\n"
        )
        stringBuilder.append(
            """8.[방어5]의 확률이 5%로 늘어난다.${`var`.passive[4][3]}""".trimIndent()+"\n"
        )
        showDialog("패시브확인 - 방어", stringBuilder.toString())
    }

    fun showHiddenPassive() {
        val `var` = application as variable
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """1.레벨업을 할때마다 스탯 30지급 - ${`var`.passive[5][0]}""".trimIndent()
        )
        showDialog("패시브확인 - 히든", stringBuilder.toString())
    }

    private fun showInventoryList() {
        val builder = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        builder.setView(layout)
        val but1 = Button(this)
        but1.text = "인벤토리"
        but1.setOnClickListener { showInventory() }
        layout.addView(but1)
        val but2 = Button(this)
        but2.text = "포션 인벤토리"
        but2.setOnClickListener { showPortionInventory() }
        layout.addView(but2)
        builder.setTitle("인벤토리 리스트")
        builder.setNegativeButton("닫기", null)
        builder.show()
    }

    private fun showInventory() {
        val `var` = application as variable
        val builder = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val listView = ListView(this)
        val adapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_list_item_1,
            `var`.inventory
        )
        listView.adapter = adapter
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val item = parent.getItemAtPosition(position).toString()
                val itemname = item.split(" - ".toRegex()).toTypedArray()[0]
                val itemcount: Int = item.split(" - ".toRegex()).toTypedArray()[1].toInt()
                if (itemname.contains("회복포션")) {
                    val data1: Int = itemname.split("회복포션".toRegex()).toTypedArray()[0].toInt()
                    val n1 = itemcount - 1
                    if (data1 == 10) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[0] += 1
                            toast("10회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이텝 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                    if (data1 == 50) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[1] += 1
                            toast("50회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이텝 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                    if (data1 == 100) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[2] += 1
                            toast("100회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이텝 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                    if (data1 == 500) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[3] += 1
                            toast("500회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이텝 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                    if (data1 == 1000) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[4] += 1
                            toast("1000회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이텝 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                    if (data1 == 2000) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[5] += 1
                            toast("2000회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이텝 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                    if (data1 == 5000) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[6] += 1
                            toast("5000회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이텝 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }
        layout.addView(listView)
        builder.setView(layout)
        builder.setTitle("인벤토리")
        builder.setNegativeButton("닫기", null)
        builder.show()
    }

    private fun showPortionInventory() {
        val `var` = application as variable
        val builder = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val portionName = arrayOfNulls<String>(7)
        portionName[0] = "10회복포션 - " + `var`.portionCount[0]
        portionName[1] = "50회복포션 - " + `var`.portionCount[1]
        portionName[2] = "100회복포션 - " + `var`.portionCount[2]
        portionName[3] = "500회복포션 - " + `var`.portionCount[3]
        portionName[4] = "1000회복포션 - " + `var`.portionCount[4]
        portionName[5] = "2000회복포션 - " + `var`.portionCount[5]
        portionName[6] = "5000회복포션 - " + `var`.portionCount[6]
        val listView = ListView(this)
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, portionName)
        listView.adapter = adapter
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val item = parent.getItemAtPosition(position).toString()
                val itemname = item.split(" - ".toRegex()).toTypedArray()[0]
                val itemcount: Int = item.split(" - ".toRegex()).toTypedArray()[1].toInt()
                cheakUsePortion(itemname, itemcount, position)
                adapter.notifyDataSetChanged()
            }
        layout.addView(listView)
        builder.setView(layout)
        builder.setTitle("포션 인벤토리")
        builder.setNegativeButton("닫기", null)
        builder.show()
    }

    private fun cheakUsePortion(name: String, count: Int, pos: Int) {
        val `var` = application as variable
        val builder = AlertDialog.Builder(this)
        builder.setTitle("포션 사용 확인")
        builder.setMessage(
            """
            ${name}을 사용하시겠습니까?
            남은 갯수: $count
            """.trimIndent()
        )
        builder.setNegativeButton("취소", null)
        builder.setPositiveButton("사용"
        ) { dialog, which ->
            val heal: Int = name.split("회복포션".toRegex()).toTypedArray()[0].toInt()
            if (`var`.stat3[0] + heal <= `var`.stat3[1]) {
                `var`.stat3[0] += (heal)
                `var`.portionCount[pos] -= 1
            }
            if (`var`.stat3[0] + heal > `var`.stat3[1]) {
                `var`.stat3[0] = (`var`.stat3[1])
                `var`.portionCount[pos] -= 1
            }
            if (`var`.stat3[0] == `var`.stat3[1]) {
                toast("이미 최대 체력입니다.", false)
            }
        }
        builder.show()
    }

    private fun otherFun() {
        val dialog = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val but1 = addButton("저장") {
            saveCheck()
        }
        layout.addView(but1)
        val but2 = addButton("불러오기") {
            loadCheck()
        }
        layout.addView(but2)
        val but3 = addButton("초기화") {
            reset()
        }
        layout.addView(but3)

        dialog.setTitle("기타 기능")
        dialog.setView(layout)
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }

    private fun saveCheck() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("저장")
        dialog.setMessage("게임 정보를 내부저장소에 저장하시겠습니까?")
        dialog.setNegativeButton("닫기", null)
        dialog.setPositiveButton("저장") { dialog, which ->
            showDialog("알림", "아직 지원하지않는 기능입니다.")
        }
        dialog.show()
    }
    private fun loadCheck() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("저장")
        dialog.setMessage("게임 정보를 내부저장소에서 불러오시겠습니까?")
        dialog.setNegativeButton("닫기", null)
        dialog.setPositiveButton("불러오기") { dialog, which ->
            showDialog("알림", "아직 지원하지않는 기능입니다.")
        }
        dialog.show()
    }
    private fun reset() {
        val var1 = application as variable
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("확인")
        dialog.setMessage("정말로 초기화 하시겠습니까?\n모든 정보가 초기화되며 복구가 불가능합니다.\n초기화 진행시 앱이 재시작 됩니다.")
        dialog.setNegativeButton("취소", null)
        dialog.setPositiveButton("초기화") { dialog, which ->
            var1.resetVar();
            toast("초기화 되어습니다.", true)
            finishAffinity()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            exitProcess(0)
        }
        dialog.show()
    }
}