/*
 * Create by mine on 2020. 10. 14.
 * Copyright (c) 2020. mine. All rights reserved.
 *
 */

package com.mine.trpgbeta

import android.app.Application

class variable: Application() {
    var plusStat = false
    //레벨, 현 경험치, 요구 경험치
    var stat1: Array<Int> = arrayOf(1, 0, 10)
    //스탯 힘 민첩 체력 운 방어 지능 지혜
    var stat2: Array<Int> = arrayOf(10, 0, 0, 0, 0, 0, 0, 0)
    //현 체력, 최대 체력, 현 마나, 최대 마나, 공격력
    var stat3: Array<Int> = arrayOf(10, 10, 10, 10, 2)
    //공격속도, 치명타확률, 방어력
    var stat4: Array<Double> = arrayOf(5.0, 0.0, 0.0)
    //체력 회복량, 마나 회복량
    var stat5: Array<Int> = arrayOf(2, 1)
    var money = 500
    var itemname = arrayOfNulls<String>(0)
    var itemcount = arrayOfNulls<Int>(0)
    var inventory = arrayOfNulls<String>(0)
    var insize = 0
    fun addInsize() {
        insize += 1
        val n1: Int = itemname.size + 1
        val n2: Int = itemcount.size + 1
        val n3: Int = inventory.size+ 1
        itemname = arrayOfNulls<String>(n1)
        itemcount = arrayOfNulls<Int>(n2)
        inventory = arrayOfNulls<String>(n3)
    }
    fun inventorySetting() {
        for (n in itemname.indices) {
            if (itemname[n] == null) {
                itemname[n] = ""
            }
        }
        for (n in inventory.indices) {
            if (inventory[n] == null) {
                inventory[n] = ""
            }
        }
    }
    fun setInventory() {
        for (n in itemname.indices) {
            inventory[n] = itemname[n].toString() + " - " + itemcount[n]
        }
    }
    //10 50 100 500 1000 2000 5000
    var portionCount: Array<Int> = arrayOf(0, 0, 0, 0, 0, 0, 0)

    fun resetVar() {
        plusStat = false
        //레벨, 현 경험치, 요구 경험치
        stat1 = arrayOf(1, 0, 10)
        //스탯 힘 민첩 체력 운 방어 지능 지혜
        stat2 = arrayOf(10, 0, 0, 0, 0, 0, 0, 0)
        //현 체력, 최대 체력, 현 마나, 최대 마나, 공격력
        stat3 = arrayOf(10, 10, 10, 10, 2)
        //공격속도, 치명타확률, 방어력
        stat4 = arrayOf(5.0, 0.0, 0.0)
        //체력 회복량, 마나 회복량
        stat5 = arrayOf(2, 1)
        money = 500
        itemname = arrayOfNulls<String>(0)
        itemcount = arrayOfNulls<Int>(0)
        inventory = arrayOfNulls<String>(0)
        insize = 0
        portionCount = arrayOf(0, 0, 0, 0, 0, 0, 0)
    }
}