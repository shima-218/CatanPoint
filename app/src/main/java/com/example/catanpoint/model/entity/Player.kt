package com.example.catanpoint.model.entity

class Player(var name: String = "Player") {
    var numOfCities: Int = 0
    var numOfSettlements: Int = 2
    var numOfDevPoints: Int = 0
    var numOfUsedKnights: Int = 0
    var lengthOfLongestRoads: Int = 1
    var hasLongestRoads: Boolean = false
    var hasLargestArmy: Boolean = false
    val points: Int //computed propertyとして定義する
        get() = calcScore()

    //Kotlinの場合、getter/setterは暗黙的に定義される。オーバーライドするときのみ明示的に定義する。

    fun calcScore(): Int{
        var points: Int = numOfCities * 2 + numOfSettlements + numOfDevPoints
        if(hasLongestRoads){
            points += 2
        }
        if(hasLargestArmy){
            points += 2
        }
        return points
    }

    fun resetAllPoints(){
        this.numOfCities = 0
        this.numOfSettlements = 0
        this.numOfDevPoints = 0
        this.numOfUsedKnights = 0
        this.lengthOfLongestRoads = 0
        this.hasLargestArmy = false
        this.hasLongestRoads = false
    }


}