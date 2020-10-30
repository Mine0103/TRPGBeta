package com.mine.trpgbeta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import java.util.*

class intro: AppCompatActivity() {
    var tt1: TimerTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this)
        layout.setBackgroundResource(R.drawable.intro)
        setContentView(layout)
        tt1 = object: TimerTask() {
            override fun run() {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
                tt1?.cancel()
            }
        }
        val timer = Timer();
        timer.schedule(tt1, 5000, 5000)
    }
}