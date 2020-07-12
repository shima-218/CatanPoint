package com.example.catanpoint.model.entity

import android.graphics.Color

val RED = Pair(Color.rgb(255,213,215),Color.rgb(213,0,11))
val ORANGE = Pair(Color.rgb(255,230,213),Color.rgb(234,94,0))
val BLUE = Pair(Color.rgb(213,216,255),Color.rgb(0,18,234))
val CREAM = Pair(Color.rgb(249,237,219),Color.rgb(182,121,31))


//リファクタリング
// val RED: CustomColor
//    get() = CustomColor(Color.rgb(255,213,215),Color.rgb(213,0,11))
// val ORANGE: CustomColor
//    get() = CustomColor(Color.rgb(255,230,213),Color.rgb(234,94,0))
// val BLUE: CustomColor
//    get() = CustomColor(Color.rgb(213,216,255),Color.rgb(0,18,234))
// val CREAM: CustomColor
//    get() = CustomColor(Color.rgb(249,237,219),Color.rgb(182,121,31))
//class CustomColor(frColor: Int, bgColor: Int){
//}