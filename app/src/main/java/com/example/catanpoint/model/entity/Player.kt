package com.example.catanpoint.model.entity

import android.graphics.Color

class Player(var name: String = "Player", var color: Pair<Int,Int> = Pair(Color.BLACK,Color.BLACK)) {
    var numOfCities: Int = 0
    var numOfSettlements: Int = 2
    var numOfDevPoints: Int = 0
    var numOfUsedKnights: Int = 0
    var lengthOfLongestRoads: Int = 1
    var hasLongestRoads: Boolean = false
    var hasLargestArmy: Boolean = false
    val points: Int //computed propertyとして定義する
        get() = calcPoints()

    //Kotlinの場合、getter/setterは暗黙的に定義される。オーバーライドするときのみ明示的に定義する。

    private fun calcPoints(): Int{
        var points: Int = numOfCities * 2 + numOfSettlements + numOfDevPoints
        if(hasLongestRoads){
            points += 2
        }
        if(hasLargestArmy){
            points += 2
        }
        if(points >= 100) {
            return 99
        }
        return points
    }

    fun reset(){
        this.numOfCities = 0
        this.numOfSettlements = 2
        this.numOfDevPoints = 0
        this.numOfUsedKnights = 0
        this.lengthOfLongestRoads = 1
        this.hasLargestArmy = false
        this.hasLongestRoads = false
    }


}