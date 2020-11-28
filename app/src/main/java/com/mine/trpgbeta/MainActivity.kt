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
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.LimitExceededException
import android.view.Gravity
import android.view.LayoutInflater
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
import com.google.android.gms.ads.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mine.trpgbeta.hunting.*
import com.mine.trpgbeta.village.village
import java.io.*
import java.net.URL
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
    private var plusatt = arrayOf(
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0),
        intArrayOf(0, 0),
        intArrayOf(0, 0),
        intArrayOf(0, 0)
    )
    private var minusattap = arrayOf(
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 0.0)
    )
    private var plusHp = arrayOf(
        intArrayOf(0, 0),
        intArrayOf(0, 0),
        intArrayOf(0, 0),
        intArrayOf(0, 0),
        intArrayOf(0, 0)
    )
    private var pluscrit = arrayOf(
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 0.0)
    )
    private var plusdef = arrayOf(
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 0.0)
    )
    private var am: AssetManager? = null
    private var mAdView: AdView? = null
    private val verName = arrayOf("1.0.0.15", "")
    private val verCode = arrayOf(15, 0)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RtlHardcoded", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(Build.VERSION.SDK_INT >= 23) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 3)
            }
        }
        txt1 = findViewById(R.id.txt1)
        txt2 = findViewById(R.id.txt2)
        txt3 = findViewById(R.id.txt3)
        txt4 = findViewById(R.id.txt4)
        txt5 = findViewById(R.id.txt5)
        val village = findViewById<Button>(R.id.village)
        village.setOnClickListener {
            val intent = Intent(applicationContext, village::class.java)
            startActivity(intent)
        }
        val chat = findViewById<Button>(R.id.chat)
        chat.setOnClickListener {
            val intent = Intent(applicationContext, chatting::class.java)
            startActivity(intent)
        }
        val bn: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bn.setOnNavigationItemSelectedListener { item->
            showPortionInventory()
            return@setOnNavigationItemSelectedListener true
        }
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setHomeAsUpIndicator(android.R.drawable.ic_menu_add)

        am = resources.assets

        timer1()
        onBlock()

        val var1 = application as variable
        val preferences = getSharedPreferences("variable", MODE_PRIVATE)
        if(isStart) {
            readStat(preferences, var1)
            readVariable(preferences, var1)
            readInventory(preferences, var1)
            readPortion(preferences, var1)
        }

    }

    private fun debugging() {
        val var1 = application as variable
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val but1 = addButton("장비스텟확인") {
            showDialog("장비 스텟", var1.equipmentStat[0][1].toString())
        }
        layout.addView(but1)
        AlertDialog.Builder(this)
            .setView(layout)
            .setTitle("디버그")
            .setNegativeButton("닫기", null)
            .show()
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
        var1.name = preferences.getString("name", "1").toString()
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
        AlertDialog.Builder(this)
            .setTitle("게임 종료")
            .setMessage("")
            .setNegativeButton("취소", null)
            .setPositiveButton("종료") { dialog, which ->
                finish()
            }
            .show()
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
        editor.putString("name", var1.name)
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

    private fun dip2px(dips: Int): Int {
        return ceil(dips * this.resources.displayMetrics.density.toDouble()).toInt()
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
        return ""
    }
    private fun onBlock() {
        var cache = ""
        Thread {
            run {
                cache = getDataFormServer().toString()
            }
        }
        val data = cache.split("::")
        verName[1] = data[0]
        //toast(cache, false)
        /*verCode[1] = data[1].toInt()
        if(verCode[0]<verCode[1]) {
            AlertDialog.Builder(this)
                .setTitle("업데이트 알림")
                .setMessage("${data[2]}\n현재버전: ${verName[0]} 최신버전: ${verName[1]}")
                .setNegativeButton("닫기", null)
                .show()
        }*/
    }
    private fun getDataFormServer(): String? {
        try {
            val url = URL("https://github.com/Mine0103/TRPGBeta_isBlocked/blob/master/isBlocked.txt")
            val con = url.openConnection()
            var str = ""
            if(con!=null) {
                con.connectTimeout = 5000
                con.useCaches = false
                val isr = InputStreamReader(con.getInputStream())
                val br = BufferedReader(isr)
                str = br.readLine()
                var line = ""
                while (true) {
                    line = br.readLine()
                    str+="\n${line}"
                }
            }
            return str
        } catch(e: java.lang.Exception) {
            showDialog("error", e.toString())
            return null
        } finally {

        }
    }

    private fun timer1() {
        val var1 = application as variable
        val tt1: TimerTask = object : TimerTask() {
            override fun run() {
                setText()
                setStat()
                levelUp()
                setExp()
                var1.inventorySetting()
                var1.setInventory()
                setPassive1()
                setPassive2()
                setHiddenPassive()
                var1.setEquipmentStat()
                if(var1.time==24) var1.time = 0
            }
        }
        val tt2: TimerTask = object : TimerTask() {
            override fun run() {
                healing()
            }
        }
        val tt3: TimerTask = object : TimerTask() {
            override fun run() {
                var1.time+=1
            }
        }
        val timer = Timer()
        timer.schedule(tt1, 100, 100)
        timer.schedule(tt2, 5000, 5000)
        timer.schedule(tt3, 12500, 12500)
    }
    @SuppressLint("SetTextI18n")
    private fun setText() {
        val var1 = application as variable
        runOnUiThread {
            txt1?.text = "레벨: ${var1.stat1[0]} 경험치: [${var1.stat1[1]}/${var1.stat1[2]}]"
            txt2?.text = "체력: [${var1.stat3[0]}/${var1.stat3[1]}] 마나: [${var1.stat3[2]}/${var1.stat3[3]}]"
            txt3?.text = "스탯: ${var1.stat2[0]} 힘: ${var1.stat2[1]} 민첩: ${var1.stat2[2]} 체력: ${var1.stat2[3]} 운: ${var1.stat2[4]} 방어: ${var1.stat2[5]}\n지능: ${var1.stat2[6]} 지혜: ${var1.stat2[7]}"
            txt4?.text = "공격력: ${var1.stat3[4]} 공격속도: ${var1.stat4[0]} 치명타확률: ${var1.stat4[1]}%\n방어력: ${var1.stat4[2]} 마나회복량: ${var1.stat5[1]}"
            txt5?.text = "돈: ${var1.money} 시간: ${var1.time}시"
        }
    }
    private fun setStat() {
        val var1 = application as variable
        val eq = var1.equipmentStat
        val att = eq[0][1]+eq[1][1]+eq[2][1]+eq[3][1]+eq[4][1]
        val def = eq[0][2]+eq[1][2]+eq[2][2]+eq[3][2]+eq[4][2]*0.1
        val critp = eq[0][3]+eq[1][3]+eq[2][3]+eq[3][3]+eq[4][3]*0.1
        val plusHp = eq[0][4]+eq[1][4]+eq[2][4]+eq[3][4]+eq[4][4]
        val addHp = eq[0][5]+eq[1][5]+eq[2][5]+eq[3][5]+eq[4][5]
        var1.stat3[4] = (2*var1.stat2[1])+att
        var1.stat4[0] = 5.0-(var1.stat2[2]*0.001)
        var1.stat3[1] = 10+(5*var1.stat2[3])+plusHp
        var1.stat4[1] = 0.1*var1.stat2[4]+critp
        var1.stat4[2] = 0.1*var1.stat2[5]+def
        var1.stat3[3] = 10+(var1.stat2[6]*2)
        var1.stat5[1] = (1+ floor(var1.stat2[7]*0.2)).toInt()
    }
    private fun levelUp() {
        val `var` = application as variable
        val n1 = (floor(((`var`.stat1[0] - 1) / 9).toDouble()) + 1).toInt() * 10
        if (`var`.stat1[1] >= `var`.stat1[2]) {
            `var`.stat1[1]-=(`var`.stat1[2])
            `var`.stat1[0]+=1
            `var`.stat1[2]+=n1
            `var`.stat2[0]+=5
            if (`var`.hiddenPassive[0]) {
                `var`.stat2[0]+=25
            }
        }
    }
    private fun setExp() {
        val var1 = application as variable
        val exp = arrayOf(0)
        /*try {
            ip = am?.open("exp.txt")
            val data = ip.toString().split("\n")
            for(i in data.indices) {
                val lv = data[i].split("::")[0].toInt()
                val exp = data[i].split("::")[1].toInt()
                toast("$lv::$exp", false)
                /*if(var1.stat1[0]==lv) {
                    var1.stat1[2] = exp
                }*/
            }
        } catch (e: Exception) {
             //e.printStackTrace()
        }
        try {
            ip?.close()
        } catch (e: Exception) {
            //e.printStackTrace()
        }*/

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

    fun setPassive1() {
        val var1 = application as variable

        if(var1.stat2[1]>=50) {
            var1.passive[0][0] = true
        }
        if(var1.stat2[1]>=100) {
            var1.passive[0][1] = true
        }
        if(var1.stat2[1]>=150) {
            var1.passive[0][2] = true
        }
        if(var1.stat2[1]>=200) {
            var1.passive[0][3] = true
        }

        if(var1.stat2[2]>=50) {
            var1.passive[1][0] = true
        }
        if(var1.stat2[2]>=100) {
            var1.passive[1][1] = true
        }

        if(var1.stat2[3]>=50) {
            var1.passive[2][0] = true
        }
        if(var1.stat2[3]>=100) {
            var1.passive[2][1] = true
        }

        if(var1.stat2[4]>=50) {
            var1.passive[3][0] = true
        }
        if(var1.stat2[4]>=100) {
            var1.passive[3][1] = true
        }

        if(var1.stat2[5]>=50) {
            var1.passive[4][0] = true
        }
        if(var1.stat2[5]>=100) {
            var1.passive[4][1] = true
        }
        if(var1.stat2[5]>=250) {
            var1.passive[4][4] = true
        }
        if(var1.stat2[5]>=400) {
            var1.passive[4][7] = true
        }
    }
    fun setPassive2() {
        val var1 = application as variable

        if(var1.passive[0][0]) {
            plusatt[0][0] = 10
        }
        if(var1.passive[0][2]) {
            val hel = floor(var1.stat3[1] * 0.8).toInt()
            if (var1.stat3[1] >= hel) {
                plusatt[0][2] = 20
            }
        }

        if (var1.passive[1][0]) {
            minusattap[1][0] = 0.05
        }
        if (var1.passive[1][1]) {
            minusattap[1][1] = 0.2
        }

        if (var1.passive[2][0]) {
            plusHp[2][0] = 50
        }

        if (var1.passive[3][0]) {
            pluscrit[3][0] = 2.5
        }
        if (var1.passive[3][1]) {
            plusatt[3][1] = 20
        }

        if (var1.passive[4][0]) {
            plusdef[4][0] = 5.0
        }
        if (var1.passive[4][1]) {
            plusHp[4][1] = 20
        }
    }
    fun setHiddenPassive() {
        val var1 = application as variable
        if(var1.stat2[1]>=200&&var1.stat2[2]>=200&&var1.stat2[3]>=200&&var1.stat2[4]>=200&&var1.stat2[5]>=200) {
            var1.hiddenPassive[0] = true
        }
    }

    fun showMeun(v: View) {
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

        AlertDialog.Builder(this)
            .setView(layout)
            .setTitle("메뉴")
            .setNegativeButton("닫기", null)
            .show()
    }

    @SuppressLint("SetTextI18n")
    fun stat1(v: View) {
        val var1 = application as variable

        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.stat1, null)
        val txt = view.findViewById<TextView>(R.id.txt1)
        txt.text = "스탯: ${var1.stat2[0]}"
        val but1 = view.findViewById<Button>(R.id.but1)
        but1.text = "힘: ${var1.stat2[1]}"
        but1.setOnClickListener {
            if(var1.stat2[0]>0) {
                var1.stat2[0]-=1
                var1.stat2[1]+=1
                txt.text = "스탯: ${var1.stat2[0]}"
                but1.text = "힘: ${var1.stat2[1]}"
            } else {
                showDialog("알림", "스탯이 부족합니다.")
            }
        }
        val but2 = view.findViewById<Button>(R.id.but2)
        but2.text = "민첩: ${var1.stat2[2]}"
        but2.setOnClickListener {
            if(var1.stat2[0]>0) {
                var1.stat2[0]-=1
                var1.stat2[2]+=1
                txt.text = "스탯: ${var1.stat2[0]}"
                but2.text = "민첩: ${var1.stat2[2]}"
            } else {
                showDialog("알림", "스탯이 부족합니다.")
            }
        }
        val but3 = view.findViewById<Button>(R.id.but3)
        but3.text = "체력: ${var1.stat2[3]}"
        but3.setOnClickListener {
            if(var1.stat2[0]>0) {
                var1.stat2[0]-=1
                var1.stat2[3]+=1
                txt.text = "스탯: ${var1.stat2[0]}"
                but3.text = "체력: ${var1.stat2[3]}"
            } else {
                showDialog("알림", "스탯이 부족합니다.")
            }
        }
        val but4 = view.findViewById<Button>(R.id.but4)
        but4.text = "운: ${var1.stat2[4]}"
        but4.setOnClickListener {
            if(var1.stat2[0]>0) {
                var1.stat2[0]-=1
                var1.stat2[4]+=1
                txt.text = "스탯: ${var1.stat2[0]}"
                but4.text = "운: ${var1.stat2[4]}"
            } else {
                showDialog("알림", "스탯이 부족합니다.")
            }
        }
        val but5 = view.findViewById<Button>(R.id.but5)
        but5.text = "방어: ${var1.stat2[5]}"
        but5.setOnClickListener {
            if(var1.stat2[0]>0) {
                var1.stat2[0]-=1
                var1.stat2[5]+=1
                txt.text = "스탯: ${var1.stat2[0]}"
                but5.text = "방어: ${var1.stat2[5]}"
            } else {
                showDialog("알림", "스탯이 부족합니다.")
            }
        }
        val but6 = view.findViewById<Button>(R.id.but6)
        but6.text = "지능: ${var1.stat2[6]}"
        but6.setOnClickListener {
            if(var1.stat2[0]>0) {
                var1.stat2[0]-=1
                var1.stat2[6]+=1
                txt.text = "스탯: ${var1.stat2[0]}"
                but6.text = "지능: ${var1.stat2[6]}"
            } else {
                showDialog("알림", "스탯이 부족합니다.")
            }
        }
        val but7 = view.findViewById<Button>(R.id.but7)
        but7.text = "지혜: ${var1.stat2[7]}"
        but7.setOnClickListener {
            if(var1.stat2[0]>0) {
                var1.stat2[0]-=1
                var1.stat2[7]+=1
                txt.text = "스탯: ${var1.stat2[0]}"
                but7.text = "지혜: ${var1.stat2[7]}"
            } else {
                showDialog("알림", "스탯이 부족합니다.")
            }
        }

        AlertDialog.Builder(this)
            .setView(view)
            .setNegativeButton("닫기", null)
            .show()
    }

    fun hunting(v: View) {
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
        val but5 = addButton("눈의 언덕") {
            val intent = Intent(applicationContext, SnowHill::class.java)
            startActivity(intent)
        }
        layout.addView(but5)
        val raid = addButton("레이드") {
            val intent = Intent(applicationContext, raid::class.java);
            startActivity(intent)
        }
        layout.addView(raid)

        AlertDialog.Builder(this)
            .setView(layout)
            .setTitle("사냥")
            .setNegativeButton("닫기", null)
            .show()
    }

    private fun showPassive() {
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
        AlertDialog.Builder(this)
            .setTitle("패시브 확인")
            .setView(layout)
            .setNegativeButton("닫기", null)
            .show()
    }
    private fun showStPassive() {
        val `var` = application as variable
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """1.공격력 10증가 - ${`var`.passive[0][0]}""".trimIndent() + "\n"
        )
        stringBuilder.append(
            """2.몬스터 처치시 최대체력의 2%회복 - ${`var`.passive[0][1]}""".trimIndent() + "\n"
        )
        stringBuilder.append(
            """3.체력이 80%이상일때 공격력 20증가 - ${`var`.passive[0][2]}""".trimIndent() + "\n"
        )
        stringBuilder.append(
            """4.타격시 5%확률로 상대를 2초간 기절시킨다 - ${`var`.passive[0][3]}""".trimIndent() + "\n"
        )
        showDialog("패시브확인 - 힘", stringBuilder.toString())
    }
    private fun showSpPassive() {
        val `var` = application as variable
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """1.공격속도 0.05초 감소 - ${`var`.passive[1][0]}""".trimIndent() + "\n"
        )
        stringBuilder.append(
            """2.공격속도 0.2초 감소 - ${`var`.passive[1][1]}""".trimIndent() + "\n"
        )
        showDialog("패시브확인 - 민첩", stringBuilder.toString())
    }
    private fun showHelPassive() {
        val `var` = application as variable
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """1.체력 50증가 - ${`var`.passive[2][0]}""".trimIndent() + "\n"
        )
        stringBuilder.append(
            """2.체력회복량 5로 증가 - ${`var`.passive[2][1]}""".trimIndent() + "\n"
        )
        showDialog("패시브확인 - 체력", stringBuilder.toString())
    }
    private fun showLuPassive() {
        val `var` = application as variable
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """1.크리티컬확률 2.5%증가 - ${`var`.passive[3][0]}""".trimIndent() + "\n"
        )
        stringBuilder.append(
            """2.공격력 20증가 - ${`var`.passive[3][1]}""".trimIndent() + "\n"
        )
        showDialog("패시브확인 - 운", stringBuilder.toString())
    }
    private fun showDePassive() {
        val `var` = application as variable
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """1.방어력 5증가 - ${`var`.passive[4][0]}""".trimIndent() + "\n"
        )
        stringBuilder.append(
            """2.체력 20증가 - ${`var`.passive[4][1]}""".trimIndent() + "\n"
        )
        stringBuilder.append(
            """5.맞을때 1%의확률 데미지를 받지 않는다. - ${`var`.passive[4][2]}""".trimIndent() + "\n"
        )
        stringBuilder.append(
            """8.[방어5]의 확률이 5%로 늘어난다.${`var`.passive[4][3]}""".trimIndent() + "\n"
        )
        showDialog("패시브확인 - 방어", stringBuilder.toString())
    }
    private fun showHiddenPassive() {
        val `var` = application as variable
        val stringBuilder = StringBuilder()
        stringBuilder.append(
            """1.레벨업을 할때마다 스탯 30지급 - ${`var`.hiddenPassive[0]}""".trimIndent()
        )
        showDialog("패시브확인 - 히든", stringBuilder.toString())
    }

    private fun showInventoryList() {
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
        val but3 = addButton("장비칸") {
            equipmentInventory()
        }
        layout.addView(but3)
        AlertDialog.Builder(this)
            .setView(layout)
            .setTitle("인벤토리 리스트")
            .setNegativeButton("닫기", null)
            .show()
    }
    private fun showInventory() {
        val `var` = application as variable
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
                val n1 = itemcount - 1
                if (itemname.contains("회복포션")) {
                    val data1: Int = itemname.split("회복포션".toRegex()).toTypedArray()[0].toInt()
                    if (data1 == 10) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[0] += 1
                            toast("10회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이템 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                    if (data1 == 50) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[1] += 1
                            toast("50회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이템 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                    if (data1 == 100) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[2] += 1
                            toast("100회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이템 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                    if (data1 == 500) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[3] += 1
                            toast("500회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이템 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                    if (data1 == 1000) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[4] += 1
                            toast("1000회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이템 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                    if (data1 == 2000) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[5] += 1
                            toast("2000회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이템 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                    if (data1 == 5000) {
                        if (itemcount > 0) {
                            `var`.itemcount[position] = n1
                            `var`.portionCount[6] += 1
                            toast("5000회복포션 1개를 가방에 넣었습니다", false)
                        }
                        if (itemcount == 0) {
                            showDialog("아이템 부족", itemname + "의 갯수가 부족합니다.")
                        }
                    }
                }
                if(item.contains("검")) {
                    val data = parent.getItemAtPosition(position).toString().split("\n")
                    val name = "${data[0]}\n${data[1]}\n${data[2]}"
                    if (itemcount > 0) {
                        `var`.itemcount[position] = n1
                        `var`.equipmentName[0] = name
                        toast(name+"을(를) 장비했습니다", false)
                    }
                    if (itemcount == 0) {
                        showDialog("아이템 부족", itemname + "의 갯수가 부족합니다.")
                    }
                }
                adapter.notifyDataSetChanged()
            }
        layout.addView(listView)
        AlertDialog.Builder(this)
            .setView(layout)
            .setTitle("인벤토리")
            .setNegativeButton("닫기", null)
            .show()
    }
    private fun showPortionInventory() {
        val `var` = application as variable
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
        AlertDialog.Builder(this)
            .setView(layout)
            .setTitle("포션 인벤토리")
            .setNegativeButton("닫기", null)
            .show()
    }
    private fun cheakUsePortion(name: String, count: Int, pos: Int) {
        val `var` = application as variable
        AlertDialog.Builder(this)
            .setTitle("포션 사용 확인")
            .setMessage("""${name}을 사용하시겠습니까?남은 갯수: $count""".trimIndent())
            .setNegativeButton("취소", null)
            .setPositiveButton("사용") { dialog, which ->
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
            .show()
    }
    private fun equipmentInventory() {
        val var1 = application as variable
        val layout = LinearLayout(this)
        val list = ListView(this)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, var1.equipmentName)
        list.adapter = adapter
        list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val `var` = application as variable
            var inSize = `var`.insize
            val name = var1.equipmentName[position]+"\n무기종류:검"
            if(!parent.getItemAtPosition(position).toString().contains("empty")) {
                if(inSize==0) {
                    `var`.addInsize()
                    `var`.itemname[0] = name
                    `var`.itemcount[0] = 1
                } else {
                    for(i in 0 until inSize) {
                        if(`var`.itemname[i] == name) {
                            `var`.itemcount[i] = `var`.itemcount[i]?.plus(1)
                        } else {
                            `var`.addInsize()
                            inSize = `var`.insize - 1
                            `var`.itemname[inSize] = name
                            `var`.itemcount[inSize] = 1
                        }
                    }
                }
            }
            var1.equipmentName[position] = "empty\n레벨제한:0 공격력:0 방어력:0\n크리티컬확률:0 체력증가:0 체력흡수:0"
            adapter.notifyDataSetChanged()
        }
        layout.addView(list)
        layout.orientation = LinearLayout.VERTICAL
        AlertDialog.Builder(this)
            .setTitle("인번토리 - 장비")
            .setView(layout)
            .setNegativeButton("닫기", null)
            .show()
    }

    private fun otherFun() {
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
        val but4 = addButton("이름변경") {
            changeName()
        }
        layout.addView(but4)

        AlertDialog.Builder(this)
            .setTitle("기타 기능")
            .setView(layout)
            .setNegativeButton("닫기", null)
            .show()
    }

    private fun saveCheck() {
        AlertDialog.Builder(this)
            .setTitle("저장")
            .setMessage("게임 정보를 내부저장소에 저장하시겠습니까?")
            .setNegativeButton("닫기", null)
            .setPositiveButton("저장") { dialog, which ->
                showDialog("알림", "아직 지원하지않는 기능입니다.")
            }
            .show()
    }
    private fun loadCheck() {
        AlertDialog.Builder(this)
            .setTitle("저장")
            .setMessage("게임 정보를 내부저장소에서 불러오시겠습니까?")
            .setNegativeButton("닫기", null)
            .setPositiveButton("불러오기") { dialog, which ->
                showDialog("알림", "아직 지원하지않는 기능입니다.")
            }
            .show()
    }
    private fun reset() {
        val var1 = application as variable
        AlertDialog.Builder(this)
            .setTitle("확인")
            .setMessage("정말로 초기화 하시겠습니까?\n모든 정보가 초기화되며 복구가 불가능합니다.\n초기화 진행시 앱이 재시작 됩니다.")
            .setNegativeButton("취소", null)
            .setPositiveButton("초기화") { dialog, which ->
                var1.resetVar()
                toast("초기화 되어습니다.", true)
                finishAffinity()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                exitProcess(0)
            }
            .show()
    }
    private fun changeName() {
        val var1 = application as variable
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val loc1 = EditText(this)
        loc1.hint = "이름"
        loc1.setText(var1.getName())
        layout.addView(loc1)
        AlertDialog.Builder(this)
            .setTitle("이름 변경")
            .setView(layout)
            .setNegativeButton("취소", null)
            .setPositiveButton("변경") { dialog, which ->
                var1.name = loc1.text.toString()
            }
            .show()
    }
}
