package com.shima218.catanpoint.model.usecase

import com.shima218.catanpoint.model.entity.Player
import java.util.Collections.max

fun decideLongestRoads(players: List<Pair<Player, Int>>, preWinnerIndex: Int): Int {
    return decideTopPlayer(players,preWinnerIndex,5,1)
}

fun decideLargestArmy(players: List<Pair<Player, Int>>, preWinnerIndex: Int): Int {
    return decideTopPlayer(players,preWinnerIndex,3,2)
}

private fun decideTopPlayer(
    players: List<Pair<Player, Int>>,
    preWinnerIndex: Int,
    threshold: Int,
    option: Int
): Int {

    val playersCounts: MutableList<Int> = mutableListOf()
    //option 1:最長交易路　2:最大騎士力
    when (option) {
        1 -> {
            for (player in players) {
                playersCounts.add(player.first.lengthOfLongestRoads)
            }
        }
        2 -> {
            for (player in players) {
                playersCounts.add(player.first.numOfUsedKnights)
            }
        }
        else -> {
            return -1
        }
    }

    val max = max(playersCounts)
    val maxCount = playersCounts.count {it==max}

    //最大が閾値以下なら、得点を得られるプレイヤーはいない
    if (max < threshold){
        return -1
    }

    //最大が閾値以上かつ、単独の場合、そのプレイヤが得点を得る
    if(maxCount == 1){
        return playersCounts.indexOf(max)
    }

    //最大が閾値以上かつ、同値がある場合
    if(preWinnerIndex != -1 && playersCounts[preWinnerIndex]==max){
        //追いつかれた場合は、現在得点を得ているプレイヤーのまま変わらない
        //分断された場合で、最大だったプレイヤーが分断されても同値で最大の場合、最大だったプレイヤーのまま変わらない
        return preWinnerIndex
    }
    //分断された場合で、最大だったプレイヤー以外が同値の場合、誰もポイントは入らない
    return -1

}


