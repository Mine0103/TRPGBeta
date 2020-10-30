package com.mine.trpgbeta

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.math.ceil

/*
BottomNavigationLayout
© 2020 Dark Tornado, All rights reserved.
MIT License
Copyright (c) 2020 Dark Tornado
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/




class BottomNavigationLayout : FrameLayout {
    private var ctx: Context? = null
    private var layout: LinearLayout? = null
    private var bottom: LinearLayout? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(ctx: Context) {
        this.ctx = ctx
        layout = LinearLayout(ctx)
        layout!!.orientation = LinearLayout.VERTICAL
        layout!!.setPadding(0, 0, 0, dip2px(50))
        super.addView(layout)
        bottom = LinearLayout(ctx)
        val params = LayoutParams(-1, -2)
        params.gravity = Gravity.BOTTOM
        bottom!!.layoutParams = params
        val pad = dip2px(3)
        bottom!!.setPadding(pad, pad, pad, pad)
        super.addView(bottom)
        layoutParams = LinearLayout.LayoutParams(-1, -1)
    }

    fun setBottomBackgroundColor(color: Int) {
        bottom!!.setBackgroundColor(color)
    }

    override fun setBackgroundDrawable(drawable: Drawable) {
        bottom!!.setBackgroundDrawable(drawable)
    }

    override fun setBackground(drawable: Drawable) {
        bottom!!.setBackgroundDrawable(drawable)
    }

    fun addBottomButton(text: String?, res: Int, drawable: Drawable?, color: Int?, listener: OnClickListener?) {
        var drawable = drawable
        if (drawable == null) drawable = ColorDrawable(Color.TRANSPARENT)
        if (color != null) {
            addBottomButton(text, res, drawable, listener, 12f, color)
        } else {
            addBottomButton(text, res, drawable, listener, 12f, Color.BLACK)
        }
    }

    private fun addBottomButton(
        text: String?,
        res: Int,
        drawable: Drawable?,
        listener: OnClickListener?,
        size: Float,
        color: Int
    ) {
        val layout = LinearLayout(ctx)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        val img = TextView(ctx)
        img.text = ""
        img.setBackgroundResource(res)
        img.gravity = Gravity.CENTER
        img.layoutParams = LinearLayout.LayoutParams(dip2px(27), dip2px(27))
        layout.addView(img)
        val txt = TextView(ctx)
        txt.text = text
        txt.textSize = size
        txt.setTextColor(color)
        txt.gravity = Gravity.CENTER
        layout.addView(txt)
        layout.setOnClickListener(listener)
        layout.layoutParams = LinearLayout.LayoutParams(-1, -2, 1F)
        bottom!!.addView(layout)
        bottom!!.weightSum = bottom!!.childCount.toFloat()
        layout.setBackgroundDrawable(drawable)
    }

    fun replace(view: View?) {
        layout!!.removeAllViews()
        layout!!.addView(view)
    }

    override fun addView(view: View) {
        layout!!.addView(view)
    }

    private fun dip2px(dips: Int): Int {
        return ceil(dips * ctx!!.resources.displayMetrics.density.toDouble())
            .toInt()
    }
}