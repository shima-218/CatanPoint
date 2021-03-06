package com.shima218.catanpoint.model.entity

import java.io.Serializable

data class Player(var name: String = "Player", var color: CustomColor = BLACK): Serializable {
    var numOfCities: Int = 0
    var numOfSettlements: Int = 2
    var numOfDevPoints: Int = 0
    var numOfUsedKnights: Int = 0
    var lengthOfLongestRoads: Int = 1
    var hasLongestRoads: Boolean = false
    var hasLargestArmy: Boolean = false
    val points: Int
        get() = calcPoints()

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