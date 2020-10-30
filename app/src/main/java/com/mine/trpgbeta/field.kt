package com.mine.trpgbeta

import android.annotation.SuppressLint
import android.content.DialogInterface
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
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

class field : AppCompatActivity() {
    private var moname = ""
    //체력 공격력 공격속도 경험치 돈
    private var monster: Array<Int> = arrayOf(0, 0, 0, 0, 0)
    private var monran = 0
    private var drawer: DrawerLayout? = null
    private var txt1: TextView? = null
    private var exp = 0
    private var money = 0
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
        setSupportActionBar(toolbar)
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
        bnl.addBottomButton("포션", android.R.drawable.ic_menu_search, getRippleDrawable(), Color.WHITE) {
            //showPortionInventory()
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
            monster = arrayOf(30000, 1, 5, 2000, 10000)
            info.append("체력: "+monster[0]+" ")
            info.append("공격력: "+monster[1]+" ")
            info.append("공격속도: "+monster[2]+"초 ")
            info.append("경험지: "+monster[3]+" ")
            info.append("돈: "+monster[4]+" ")
            dialog.setMessage(moname+"을(를) 만났습니다. 싸우시겠습니까?\n"+info)
        }
        if(monran in 2..50) {
            moname = "토끼"
            monster = arrayOf(10, 1, 5, 1, 1)
            info.append("체력: "+monster[0]+" ")
            info.append("공격력: "+monster[1]+" ")
            info.append("공격속도: "+monster[2]+"초 ")
            info.append("경험지: "+monster[3]+" ")
            info.append("돈: "+monster[4]+" ")
            dialog.setMessage(moname+"을(를) 만났습니다. 싸우시겠습니까?\n"+info)
        }
        if(monran in 51..80) {
            moname = "여우"
            monster = arrayOf(15, 2, 5, 2, 5)
            info.append("체력: "+monster[0]+" ")
            info.append("공격력: "+monster[1]+" ")
            info.append("공격속도: "+monster[2]+"초 ")
            info.append("경험지: "+monster[3]+" ")
            info.append("돈: "+monster[4]+" ")
            dialog.setMessage(moname+"을(를) 만났습니다. 싸우시겠습니까?\n"+info)
        }
        if(monran in 81..95) {
            moname = "늑대"
            monster = arrayOf(30, 5, 5, 5, 15)
            info.append("체력: "+monster[0]+" ")
            info.append("공격력: "+monster[1]+" ")
            info.append("공격속도: "+monster[2]+"초 ")
            info.append("경험지: "+monster[3]+" ")
            info.append("돈: "+monster[4]+" ")
            dialog.setMessage(moname+"을(를) 만났습니다. 싸우시겠습니까?\n"+info)
        }
        if(monran in 96..100) {
            moname = "사나운 늑대(B)"
            monster = arrayOf(50, 15, 5, 25, 40)
            info.append("체력: "+monster[0]+" ")
            info.append("공격력: "+monster[1]+" ")
            info.append("공격속도: "+monster[2]+"초 ")
            info.append("경험지: "+monster[3]+" ")
            info.append("돈: "+monster[4]+" ")
            dialog.setMessage(moname+"을(를) 만났습니다. 싸우시겠습니까?\n"+info)
        }
        dialog.setTitle("사냥 - 들판")
        dialog.setNegativeButton("다시 찾아보기") { dialog, which ->
            huntingCheck()
        }
        dialog.setPositiveButton("싸우기") { dialog, which ->
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
            moname = "토끼"
            monster = arrayOf(10, 1, 5, 1, 1)
            info.append("체력: "+monster[0]+" ")
            info.append("공격력: "+monster[1]+" ")
            info.append("공격속도: "+monster[2]+"초 ")
            info.append("경험지: "+monster[3]+" ")
            info.append("돈: "+monster[4]+" ")
            dialog.setMessage(moname+"을(를) 만났습니다. 싸우시겠습니까?\n"+info)
        }
        if(monran in 51..80) {
            moname = "여우"
            monster = arrayOf(15, 2, 5, 2, 5)
            info.append("체력: "+monster[0]+" ")
            info.append("공격력: "+monster[1]+" ")
            info.append("공격속도: "+monster[2]+"초 ")
            info.append("경험지: "+monster[3]+" ")
            info.append("돈: "+monster[4]+" ")
            dialog.setMessage(moname+"을(를) 만났습니다. 싸우시겠습니까?\n"+info)
        }
        if(monran in 81..95) {
            moname = "늑대"
            monster = arrayOf(30, 5, 5, 5, 15)
            info.append("체력: "+monster[0]+" ")
            info.append("공격력: "+monster[1]+" ")
            info.append("공격속도: "+monster[2]+"초 ")
            info.append("경험지: "+monster[3]+" ")
            info.append("돈: "+monster[4]+" ")
            dialog.setMessage(moname+"을(를) 만났습니다. 싸우시겠습니까?\n"+info)
        }
        if(monran in 96..100) {
            moname = "사나운 늑대(B)"
            monster = arrayOf(50, 15, 5, 25, 40)
            info.append("체력: "+monster[0]+" ")
            info.append("공격력: "+monster[1]+" ")
            info.append("공격속도: "+monster[2]+"초 ")
            info.append("경험지: "+monster[3]+" ")
            info.append("돈: "+monster[4]+" ")
            dialog.setMessage(moname+"을(를) 만났습니다. 싸우시겠습니까?\n"+info)
        }
        dialog.setTitle("사냥 - 들판")
        dialog.setNegativeButton("다시 찾아보기") { dialog, which ->
            huntingCheck()
        }
        dialog.setPositiveButton("싸우기") { dialog, which ->
            hunting()
        }
        dialog.setNeutralButton("나가기") { dialog, which ->
            finish()
        }
        dialog.show()
    }
    private fun hunting() {
        val var1 = application as variable
        val tt1: TimerTask = object : TimerTask() {
            override fun run() {
                val att = var1.stat2[4]
                val mhp = monster[0]-att
                if(mhp>0) {
                    monster[0]-=att
                }
                if(mhp<=0) {
                    monster[0] = 0
                }
                txt1?.append(moname+"에게 "+att+"만큼의 데미지를 주었습니다.\n남은체력: "+monster[0])
            }
        }
        val tt2: TimerTask = object : TimerTask() {
            override fun run() {
                val att = (monster[1]-floor(var1.stat4[3])).toInt()
                val php = var1.stat2[0]-att
                if(att<=0) txt1?.append(moname+"에게서 데미지를 받지 않았습니다.\n남은체력: "+monster[0])
                else {
                    if(php>0) {
                        var1.stat2[0]-=att
                    }
                    if(php<=0) {
                        var1.stat2[0] = 0
                    }
                    txt1?.append(moname+"에게서 "+att+"만큼의 데미지를 주었습니다.\n남은체력: "+monster[0])
                }

            }
        }
        val tt3: TimerTask = object : TimerTask() {
            override fun run() {
                if(var1.stat2[0]==0) {
                    showDeadDialog()
                }
                if(monster[0]==0) {
                    exp+=monster[3]
                    money+=monster[4]
                    showKillDialog()
                }
            }
        }
        val pas = (var1.stat4[0]*1000).toLong()
        val map = (monster[2]*1000).toLong()
        val timer = Timer()
        timer.schedule(tt1, pas, pas)
        timer.schedule(tt2, map, map)
        timer.schedule(tt3, 100, 100)
    }
    private fun showDeadDialog() {
        val var1 = application as variable
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(moname+"에게 죽었습니다.")
        dialog.setTitle("플레이어 사망")
        dialog.setNegativeButton("나가기")  { dialog, which ->
            var1.stat1[1]+= floor((exp/2).toDouble()).toInt()
            var1.money+=floor((money/2).toDouble()).toInt()
            finish();
        }
        dialog.show()
    }
    private fun showKillDialog() {
        val var1 = application as variable
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(moname+"를 죽였습니다.")
        dialog.setTitle("몬스터 사망")
        dialog.setNegativeButton("나가기") { dialog, which ->
            var1.stat1[1]+=exp
            var1.money+=money
            finish()
        }
        dialog.setPositiveButton("사냥하기") { dialog, which ->
            huntingCheck1()
        }
    }
}