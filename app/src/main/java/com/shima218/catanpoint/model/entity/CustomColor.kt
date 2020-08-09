package com.shima218.catanpoint.model.entity

import android.graphics.Color
import java.io.Serializable

val RED: CustomColor
    get() = CustomColor(Color.rgb(213, 0, 11), Color.rgb(255, 213, 215))
val ORANGE: CustomColor
    get() = CustomColor(Color.rgb(234, 94, 0), Color.rgb(255, 230, 213))
val BLUE: CustomColor
    get() = CustomColor(Color.rgb(0, 18, 234), Color.rgb(213, 216, 255))
val CREAM: CustomColor
    get() = CustomColor(Color.rgb(182, 121, 31), Color.rgb(249, 237, 219))
val BLACK: CustomColor
    get() = CustomColor(Color.rgb(0, 0, 0), Color.rgb(255, 255, 255))

data class CustomColor(val frontColor: Int, val backColor: Int) : Serializable {}