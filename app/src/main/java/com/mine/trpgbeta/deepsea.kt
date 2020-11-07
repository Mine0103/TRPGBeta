/*
 * Create by mine on 2020. 10. 14.
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
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

class deepsea: AppCompatActivity() {
    private var moname = ""
    //체력 공격력 공격속도 경험치 돈
    private var monster: Array<Int> = arrayOf(0, 0, 0, 0, 0)
    private var monran = 0
    private var drawer: DrawerLayout? = null
    private var txt1: TextView? = null
    private var exp = 0
    private var money = 0
    private var attsp = 0;
    private var def = 0;
    private var isHunting = false
    private var tt1: TimerTask? = null
    private var tt2: TimerTask? = null
    private var tt3: TimerTask? = null
    private var tt4: TimerTask? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout0 = LinearLayout(this)
        layout0.orientation = LinearLayout.VERTICAL
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val toolbar = Toolbar(this)
        toolbar.title = "TRPG(베타) - 사냥(들판)"
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setBackgroundColor(Color.TRANSPARENT)
        ViewCompat.setElevation(toolbar, dip2px(5).toFloat())
        //setSupportActionBar(toolbar)
        layout0.addView(toolbar)

        txt1 = addTextView("", 20, Color.WHITE, Gravity.CENTER)
        layout.addView(txt1)

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

        huntingCheck1()
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
    private fun huntingCheck1() {
        val dialog = AlertDialog.Builder(this)
        val info = StringBuffer()
        monran = floor(Math.random() * 100).toInt()+1
        if(monran==1) {
            moname = "미믹(H)"
            monster[0] = 30000
            monster[1] = 1
            monster[2] = 5
            monster[3] = 2000
            monster[4] = 10000
            info.append("체력: " + monster[0] + " ")
            info.append("공격력: " + monster[1] + " ")
            info.append("공격속도: " + monster[2] + "초 ")
            info.append("경험지: " + monster[3] + " ")
            info.append("돈: " + monster[4] + " ")
            dialog.setMessage(moname + "을(를) 만났습니다. 싸우시겠습니까?\n" + info)
        }
        if(monran in 2..50) {
            moname = "상어"
            monster[0] = 100
            monster[1] = 75
            monster[2] = 5
            monster[3] = 50
            monster[4] = 100
            info.append("체력: " + monster[0] + " ")
            info.append("공격력: " + monster[1] + " ")
            info.append("공격속도: " + monster[2] + "초 ")
            info.append("경험지: " + monster[3] + " ")
            info.append("돈: " + monster[4] + " ")
            dialog.setMessage(moname + "을(를) 만났습니다. 싸우시겠습니까?\n" + info)
        }
        if(monran in 51..80) {
            moname = "고래"
            monster[0] = 300
            monster[1] = 50
            monster[2] = 5
            monster[3] = 75
            monster[4] = 175
            info.append("체력: " + monster[0] + " ")
            info.append("공격력: " + monster[1] + " ")
            info.append("공격속도: " + monster[2] + "초 ")
            info.append("경험지: " + monster[3] + " ")
            info.append("돈: " + monster[4] + " ")
            dialog.setMessage(moname + "을(를) 만났습니다. 싸우시겠습니까?\n" + info)
        }
        if(monran in 81..95) {
            moname = "고래상어"
            monster[0] = 500
            monster[1] = 75
            monster[2] = 5
            monster[3] = 120
            monster[4] = 300
            info.append("체력: " + monster[0] + " ")
            info.append("공격력: " + monster[1] + " ")
            info.append("공격속도: " + monster[2] + "초 ")
            info.append("경험지: " + monster[3] + " ")
            info.append("돈: " + monster[4] + " ")
            dialog.setMessage(moname + "을(를) 만났습니다. 싸우시겠습니까?\n" + info)
        }
        if(monran in 96..100) {
            moname = "메갈로돈(B)"
            monster[0] = 750
            monster[1] = 200
            monster[2] = 5
            monster[3] = 250
            monster[4] = 550
            info.append("체력: " + monster[0] + " ")
            info.append("공격력: " + monster[1] + " ")
            info.append("공격속도: " + monster[2] + "초 ")
            info.append("경험지: " + monster[3] + " ")
            info.append("돈: " + monster[4] + " ")
            dialog.setMessage(moname + "을(를) 만났습니다. 싸우시겠습니까?\n" + info)
        }
        dialog.setTitle("사냥 - 심해")
        dialog.setNegativeButton("다시 찾아보기") { dialog, which ->
            huntingCheck()
        }
        dialog.setPositiveButton("싸우기") { dialog, which ->
            toast(monster[0].toString(), true)
            isHunting = true
            hunting()
        }
        dialog.setNeutralButton("나가기") { dialog, which ->
            finish()
        }
        dialog.show()
    }
    private fun huntingCheck() {
        val dialog = AlertDialog.Builder(this)
        val info = StringBuffer()
        monran = floor(Math.random() * 100).toInt()+1
        if(monran in 1..50) {
            moname = "상어"
            monster[0] = 100
            monster[1] = 75
            monster[2] = 5
            monster[3] = 50
            monster[4] = 100
            info.append("체력: " + monster[0] + " ")
            info.append("공격력: " + monster[1] + " ")
            info.append("공격속도: " + monster[2] + "초 ")
            info.append("경험지: " + monster[3] + " ")
            info.append("돈: " + monster[4] + " ")
            dialog.setMessage(moname + "을(를) 만났습니다. 싸우시겠습니까?\n" + info)
        }
        if(monran in 51..80) {
            moname = "고래"
            monster[0] = 300
            monster[1] = 50
            monster[2] = 5
            monster[3] = 75
            monster[4] = 175
            info.append("체력: " + monster[0] + " ")
            info.append("공격력: " + monster[1] + " ")
            info.append("공격속도: " + monster[2] + "초 ")
            info.append("경험지: " + monster[3] + " ")
            info.append("돈: " + monster[4] + " ")
            dialog.setMessage(moname + "을(를) 만났습니다. 싸우시겠습니까?\n" + info)
        }
        if(monran in 81..95) {
            moname = "고래상어"
            monster[0] = 500
            monster[1] = 75
            monster[2] = 5
            monster[3] = 120
            monster[4] = 300
            info.append("체력: " + monster[0] + " ")
            info.append("공격력: " + monster[1] + " ")
            info.append("공격속도: " + monster[2] + "초 ")
            info.append("경험지: " + monster[3] + " ")
            info.append("돈: " + monster[4] + " ")
            dialog.setMessage(moname + "을(를) 만났습니다. 싸우시겠습니까?\n" + info)
        }
        if(monran in 96..100) {
            moname = "메갈로돈(B)"
            monster[0] = 750
            monster[1] = 200
            monster[2] = 5
            monster[3] = 250
            monster[4] = 550
            info.append("체력: " + monster[0] + " ")
            info.append("공격력: " + monster[1] + " ")
            info.append("공격속도: " + monster[2] + "초 ")
            info.append("경험지: " + monster[3] + " ")
            info.append("돈: " + monster[4] + " ")
            dialog.setMessage(moname + "을(를) 만났습니다. 싸우시겠습니까?\n" + info)
        }
        dialog.setTitle("사냥 - 심해")
        dialog.setNegativeButton("다시 찾아보기") { dialog, which ->
            huntingCheck()
        }
        dialog.setPositiveButton("싸우기") { dialog, which ->
            toast(monster[0].toString(), true)
            isHunting = true
            hunting()
        }
        dialog.setNeutralButton("나가기") { dialog, which ->
            finish()
        }
        dialog.show()
    }
    private fun hunting() {
        val `var` = application as variable
        val timer = Timer()
        tt1 = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val crit = floor(Math.random() * 1000).toInt() + 1
                    if (`var`.stat4[1] * 1000 >= crit) {
                        if (`var`.stat3[4] * 2.let { monster[0] -= it; monster[0] } < 0) {
                            monster[0] = 0
                            txt1!!.append(moname + "에게 " + `var`.stat3[4] * 2 + "만큼의 데미지를 주었습니다.\n남은체력: " + monster[0] + " *크리티컬*\n")
                        }
                        if (`var`.stat3[4] * 2.let { monster[0] -= it; monster[0] } >= 0) {
                            monster[0] -= `var`.stat3[4]
                            txt1!!.append(moname + "에게 " + `var`.stat3[4] * 2 + "만큼의 데미지를 주었습니다.\n남은체력: " + monster[0] + " *크리티컬*\n")
                        }
                    }
                    if (`var`.stat4[1] * 1000 < crit) {
                        if (`var`.stat3[4].let { monster[0] -= it; monster[0] } < 0) {
                            monster[0] = 0
                            txt1!!.append(moname + "에게 " + `var`.stat3[4] + "만큼의 데미지를 주었습니다.\n남은체력: " + monster[0] + "\n")
                        }
                        if (`var`.stat3[4].let { monster[0] -= it; monster[0] } >= 0) {
                            monster[0] -= floor((`var`.stat3[4] / 2).toDouble()).toInt()
                            txt1!!.append(moname + "에게 " + `var`.stat3[4] + "만큼의 데미지를 주었습니다.\n남은체력: " + monster[0] + "\n")
                        }
                    }
                    if (`var`.passive[0][3]) {
                        val ran1 = floor(Math.random() * 100).toInt() + 1
                        if (ran1 <= 5) {
                            val thread = Thread {
                                try {
                                    tt2!!.cancel()
                                    Thread.sleep(2000)
                                    tt2 = object : TimerTask() {
                                        override fun run() {
                                            runOnUiThread {
                                                val def = floor(`var`.stat4[2]).toInt()
                                                val ran1 =
                                                    floor(Math.random() * 100)
                                                        .toInt() + 1
                                                if (`var`.passive[4][4]) {
                                                    if (`var`.passive[4][7]) {
                                                        if (ran1 <= 5) {
                                                            txt1!!.append(moname + "에게서 데미지를 받지 않았습니다.\n남은체력: " + `var`.stat3[0] + "\n")
                                                        }
                                                    }
                                                    if (!`var`.passive[4][7]) {
                                                        if (ran1 <= 1) {
                                                            txt1!!.append(moname + "에게서 데미지를 받지 않았습니다.\n남은체력: " + `var`.stat3[0] + "\n")
                                                        }
                                                    }
                                                }
                                                if (!`var`.passive[4][4]) {
                                                    if (monster[1] > def) {
                                                        `var`.stat3[0] -= (monster[1] - def)
                                                        txt1!!.append(moname + "에게서 " + (monster[1] - def) + "만큼의 데미지를 받았습니다.\n남은체력: " + `var`.stat3[0] + "\n")
                                                    }
                                                    if (monster[1] <= def) {
                                                        txt1!!.append(moname + "에게서 데미지를 받지 않았습니다.\n남은체력: " + `var`.stat3[0] + "\n")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    timer.schedule(tt2, 0, monster[2].toLong())
                                } catch (e: InterruptedException) {
                                    e.printStackTrace()
                                }
                            }
                            thread.start()
                            runOnUiThread { txt1!!.append(moname + "를(을) 기절시켰습니다.") }
                        }
                    }
                }
            }
        }
        tt2 = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    def = floor(`var`.stat4[2]).toInt()
                    val ran1 = floor(Math.random() * 100).toInt() + 1
                    if (`var`.passive[4][4]) {
                        if (`var`.passive[4][7]) {
                            if (ran1 <= 5) {
                                txt1!!.append(moname + "에게서 데미지를 받지 않았습니다.\n남은체력: " + `var`.stat3[0] + "\n")
                            }
                        }
                        if (!`var`.passive[4][7]) {
                            if (ran1 <= 1) {
                                txt1!!.append(moname + "에게서 데미지를 받지 않았습니다.\n남은체력: " + `var`.stat3[0] + "\n")
                            }
                        }
                    }
                    if (!`var`.passive[4][4]) {
                        if (monster[1] > def) {
                            `var`.stat3[0] -= (monster[1] - def)
                            txt1!!.append(moname + "에게서 " + (monster[1] - def) + "만큼의 데미지를 받았습니다.\n남은체력: " + `var`.stat3[0] + "\n")
                        }
                        if (monster[1] <= def) {
                            txt1!!.append(moname + "에게서 데미지를 받지 않았습니다.\n남은체력: " + `var`.stat3[0] + "\n")
                        }
                    }
                }
            }
        }
        tt3 = object : TimerTask() {
            override fun run() {
                if (monster[0] <= 0) {
                    runOnUiThread { txt1!!.append("\n") }
                    exp += monster[3]
                    if (`var`.passive[0][1]) {
                        val hp = floor(`var`.stat3[1] * 0.02).toInt()
                        if (`var`.stat3[0] + hp <= `var`.stat3[1]) {
                            `var`.stat3[0]+=hp
                        }
                        if (`var`.stat3[1] + hp > `var`.stat3[1]) {
                            `var`.stat3[0] = (`var`.stat3[1])
                        }
                    }
                    money += monster[4]
                    showKillDialog()
                    timercancle()
                }
            }
        }
        tt4 = object : TimerTask() {
            override fun run() {
                if (`var`.stat3[0] <= 0) {
                    exp = floor(exp / 2.toDouble()).toInt()
                    money = floor(money / 2.toDouble()).toInt()
                    showDeadDialog()
                    timercancle()
                    `var`.stat3[0] = (`var`.stat3[1])
                }
            }
        }
        attsp = `var`.stat4[0].toInt() * 1000
        timer.schedule(tt1, attsp.toLong(), attsp.toLong())
        timer.schedule(tt2, monster[2]*1000.toLong(), monster[2]*1000.toLong())
        timer.schedule(tt3, 10, 10)
        timer.schedule(tt4, 10, 10)
    }
    fun showDeadDialog() {
        if (!this.isFinishing) {
            val dialog = android.app.AlertDialog.Builder(this)
            val `var` = application as variable
            dialog.setTitle("죽었습니다")
            dialog.setMessage(
                """
                당신은 ${moname}에게 죽었습니다.
                돌아가기를 눌러 메인화면으로 돌아가십시요.
                """.trimIndent()
            )
            dialog.setNegativeButton(
                "돌아가기"
            ) { dialog, which ->
                `var`.stat1[1]+=exp
                `var`.money+=money
                finish()
            }
            runOnUiThread {
                dialog.show()
            }
        }
    }

    fun showKillDialog() {
        if (!this.isFinishing) {
            val dialog = android.app.AlertDialog.Builder(this)
            val `var` = application as variable
            dialog.setTitle("☆승리했따☆")
            dialog.setMessage(
                """
                당신은 ${moname}를 죽였습니다.
                돌아가기 버튼을 누르면 메인화면으로 돌아갑니다.
                다시사냥 버튼을 누르면 사냥을 시작합니다.
                """.trimIndent()
            )
            dialog.setNegativeButton(
                "돌아가기"
            ) { dialog, which ->
                `var`.stat1[1]+=exp
                `var`.money+=money
                finish()
            }
            dialog.setPositiveButton(
                "다시사냥"
            ) { dialog, which -> huntingCheck() }
            runOnUiThread {
                dialog.show()
            }
        }
    }

    fun timercancle() {
        tt1!!.cancel()
        tt2!!.cancel()
        tt3!!.cancel()
        tt4!!.cancel()
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
}