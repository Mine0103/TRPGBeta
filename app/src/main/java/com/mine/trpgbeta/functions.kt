/*
 * Create by mine on 2020. 11. 17.
 * Copyright (c) 2020. mine. All rights reserved.
 *
 */

package com.mine.trpgbeta

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.math.ceil

class functions(context: Context) {

    private var ctx: Context? = null

    init {
        init(context)
    }

    private fun init(ctx: Context) {
        this.ctx = ctx
    }

    private fun dip2px(dips: Int): Int {
        return ceil(dips * (ctx?.resources?.displayMetrics?.density?.toDouble()!!)).toInt()
    }
    private fun addTextView(text: String, size: Int, color: Int, gravity: Int?): TextView {
        val txt = TextView(ctx)
        txt.text = text
        txt.textSize = size.toFloat()
        txt.setTextColor(color)
        if (gravity != null) {
            txt.gravity = gravity
        }
        return txt
    }
    private fun addButton(txt: String, listener: View.OnClickListener?): Button {
        val btn = Button(ctx)
        btn.text = txt
        if(listener!=null) {
            btn.setOnClickListener(listener)
        }
        return btn
    }
    private fun toast(msg: String, isLong: Boolean) {
        if(isLong) {
            Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
        }
    }
    private fun showDialog(title: String, msg: String) {
        ctx?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton("닫기", null)
                .show()
        }
    }
}