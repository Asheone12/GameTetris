package com.muen.gametetris

import com.tencent.mmkv.MMKV

object MMKVManage {
    private val mmkv = MMKV.defaultMMKV()
    //缓存变量
    private const val KEY_START_X = "startX"
    private const val KEY_START_Y = "startY"
    private const val KEY_LAST_CLICK_TIME = "last_click_time"
    /**
     * 起始x坐标
     */
    var startX: Float
        set(value) {
            mmkv.encode(KEY_START_X, value)
        }
        get() = mmkv.decodeFloat(KEY_START_X)

    /**
     * 起始y坐标
     */
    var startY:Float
        set(value) {
            mmkv.encode(KEY_START_Y, value)
        }
        get() = mmkv.decodeFloat(KEY_START_Y)

    /**
     * 上一次click时间
     */
    var lastClickTime:Long
        set(value) {
            mmkv.encode(KEY_LAST_CLICK_TIME, value)
        }
        get() = mmkv.decodeLong(KEY_LAST_CLICK_TIME)

}