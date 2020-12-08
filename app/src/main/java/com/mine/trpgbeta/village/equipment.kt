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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.mine.trpgbeta.R
import com.mine.trpgbeta.variable
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
    private fun toast(msg: String, isLong: Boolean) {
        if(isLong) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
    private fun showDialog(title: String?, msg: String?) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msg)
            .setNegativeButton("닫기", null)
            .show()
    }
    @SuppressLint("InflateParams")
    fun buyWeapon(v: View) {
        val `var` = application as variable
        val builder = AlertDialog.Builder(this)
            .setTitle("무기구매")
            .setNegativeButton("닫기", null)
            .setMessage("돈: ${`var`.money}")
        val str = getAssat("item/weapon.txt")
        val inflater = LayoutInflater.from(applicationContext)
        val view = inflater.inflate(R.layout.dialog_butweapon, null)
        val items: Array<String> = arrayOf("나뭇가지\n레벨제한:1 공격력:2 방어력:0\n크리티컬확률:0 체력증가:0 체력흡수:0 - 100원",
            "목검\n레벨제한:3 공격력:5 방어력:0\n크리티컬확률:0 체력증가:0 체력흡수:0 - 1300원",
            "단단한목검\n레벨제한:5 공격력:9 방어력:0\n크리티컬확률:0 체력증가:0 체력흡수:0 - 2800원")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        val list = view.findViewById<ListView>(R.id.list)
        list.adapter = adapter
        list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val data = parent.getItemAtPosition(position).toString()
            val name = data.split(" - ".toRegex()).toTypedArray()[0]
            val money = data.split(" - ".toRegex()).toTypedArray()[1].split("원".toRegex()).toTypedArray()[0].toInt()
            toast((`var`.money >= money).toString(), true)
            if (`var`.money >= money) {
                buyPortion(name)
                `var`.money -= money
                builder.setMessage("돈: $money")
            } else {
                showDialog("알림", "돈이 부족합니다.")
            }
        }
        builder
            .setView(view)
            .show()
    }
    private fun buyPortion(name: String) {
        val `var` = application as variable
        var inSize = `var`.insize
        if(inSize==0) {
            `var`.addInsize()
            `var`.itemname[0] = name
            `var`.itemcount[0] = 1
        } else {
            for(i in 0 until inSize) {
                if(`var`.itemname[i] == name) {
                    `var`.itemcount[i]?.plus(1)
                } else {
                    `var`.addInsize()
                    inSize = `var`.insize
                    `var`.itemname[inSize] = name
                    `var`.itemcount[inSize] = 1
                }
            }
        }
        return
    }
}
