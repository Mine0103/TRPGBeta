/*
 * Create by mine on 2020. 10. 14.
 * Copyright (c) 2020. mine. All rights reserved.
 *
 */

package com.mine.trpgbeta

import android.app.Application

class variable: Application() {
    var plusStat = false
    var passive = arrayOf(
        booleanArrayOf(false, false, false, false),
        booleanArrayOf(false, false),
        booleanArrayOf(false, false),
        booleanArrayOf(false, false),
        booleanArrayOf(false, false, false, false, false, false, false, false)
    )
    var hiddenPassive = arrayOf(false)
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
        itemname = arrayOfNulls(n1)
        itemcount = arrayOfNulls(n2)
        inventory = arrayOfNulls(n3)
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
    //렙제 공격력 방어력 크확 체력증가 체력흡수
    var equipmentName = arrayOf(
        "나뭇가지\n레벨제한:1 공격력:2 방어력:1\n크리티컬확률:1 체력증가:1 체력흡수:1",
        "가죽투구\n레벨제한:1 공격력:0 방어력:5\n크리티컬확률:0 체력증가:10 체력흡수:0",
        "가죽갑옷\n레벨제한:1 공격력:0 방어력:5\n크리티컬확률:0 체력증가:10 체력흡수:0",
        "가죽바지\n레벨제한:1 공격력:0 방어력:5\n크리티컬확률:0 체력증가:10 체력흡수:0",
        "가죽신발\n레벨제한:1 공격력:0 방어력:5\n크리티컬확률:0 체력증가:10 체력흡수:0"
    )
    var equipmentStat = arrayOf(
        intArrayOf(0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0)
    )
    fun setEquipmentStat() {
        setWeaponStat()
        setArmorStat1()
        setArmorStat2()
        setArmorStat3()
        setArmorStat4()
    }
    private fun setWeaponStat() {
        val data1 = equipmentName[0].split("\n")
        val data2 = data1[1].split(" ")
        val level = data2[0].split("레벨제한:")[1].toInt()
        val att = data2[1].split("공격력:")[1].toInt()
        val def = data2[2].split("방어력:")[1].toDouble()
        val data3 = data1[2].split(" ")
        val critp = data3[0].split("크리티컬확률:")[1].toDouble()
        val plusHp = data3[1].split("체력증가:")[1].toInt()
        val addHp = data3[2].split("체력흡수:")[1].toInt()
        if(stat1[0]>=level) equipmentStat[0] = intArrayOf(level, att, (def).toInt(), (critp).toInt(), plusHp, addHp)
    }
    private fun setArmorStat1() {
        val data1 = equipmentName[1].split("\n")
        val data2 = data1[1].split(" ")
        val level = data2[0].split("레벨제한:")[1].toInt()
        val att = data2[1].split("공격력:")[1].toInt()
        val def = data2[2].split("방어력:")[1].toDouble()
        val data3 = data1[2].split(" ")
        val critp = data3[0].split("크리티컬확률:")[1].toDouble()
        val plusHp = data3[1].split("체력증가:")[1].toInt()
        val addHp = data3[2].split("체력흡수:")[1].toInt()
        if(stat1[0]>=level) equipmentStat[1] = intArrayOf(level, att, (def).toInt(), (critp).toInt(), plusHp, addHp)
    }
    private fun setArmorStat2() {
        val data1 = equipmentName[2].split("\n")
        val data2 = data1[1].split(" ")
        val level = data2[0].split("레벨제한:")[1].toInt()
        val att = data2[1].split("공격력:")[1].toInt()
        val def = data2[2].split("방어력:")[1].toDouble()
        val data3 = data1[2].split(" ")
        val critp = data3[0].split("크리티컬확률:")[1].toDouble()
        val plusHp = data3[1].split("체력증가:")[1].toInt()
        val addHp = data3[2].split("체력흡수:")[1].toInt()
        if(stat1[0]>=level) equipmentStat[2] = intArrayOf(level, att, (def).toInt(), (critp).toInt(), plusHp, addHp)
    }
    private fun setArmorStat3() {
        val data1 = equipmentName[3].split("\n")
        val data2 = data1[1].split(" ")
        val level = data2[0].split("레벨제한:")[1].toInt()
        val att = data2[1].split("공격력:")[1].toInt()
        val def = data2[2].split("방어력:")[1].toDouble()
        val data3 = data1[2].split(" ")
        val critp = data3[0].split("크리티컬확률:")[1].toDouble()
        val plusHp = data3[1].split("체력증가:")[1].toInt()
        val addHp = data3[2].split("체력흡수:")[1].toInt()
        if(stat1[0]>=level) equipmentStat[3] = intArrayOf(level, att, (def).toInt(), (critp).toInt(), plusHp, addHp)
    }
    private fun setArmorStat4() {
        val data1 = equipmentName[4].split("\n")
        val data2 = data1[1].split(" ")
        val level = data2[0].split("레벨제한:")[1].toInt()
        val att = data2[1].split("공격력:")[1].toInt()
        val def = data2[2].split("방어력:")[1].toDouble()
        val data3 = data1[2].split(" ")
        val critp = data3[0].split("크리티컬확률:")[1].toDouble()
        val plusHp = data3[1].split("체력증가:")[1].toInt()
        val addHp = data3[2].split("체력흡수:")[1].toInt()
        if(stat1[0]>=level) equipmentStat[4] = intArrayOf(level, att, (def).toInt(), (critp).toInt(), plusHp, addHp)
    }

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