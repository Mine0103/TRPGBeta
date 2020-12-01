/*
 * Create by mine on 2020. 11. 28.
 * Copyright (c) 2020. mine. All rights reserved.
 *
 */

package com.mine.trpgbeta.village

import android.annotation.SuppressLint
import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.mine.trpgbeta.R
import java.io.InputStream

class equipment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipment)
    }
    private fun getAssat(path: String): String {
        val assetManager : AssetManager = resources.assets
        val inputStream: InputStream = assetManager.open(path)
        return inputStream.bufferedReader().use { it.readText() }
    }
    @SuppressLint("InflateParams")
    fun buyWeapon(v: View) {
        val str = getAssat("item/weapon.txt")
        val inflater = LayoutInflater.from(applicationContext)
        val view = inflater.inflate(R.layout.dialog_butweapon, null)
        val items = str.split("\n")
        for(i in items) {
            i.replace(" ", "\n")
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        val list = view.findViewById<ListView>(R.id.list)
        list.adapter = adapter
        AlertDialog.Builder(this)
            .setTitle("무기구매")
            .setView(view)
            .setNegativeButton("닫기", null)
            .show()
    }
}
