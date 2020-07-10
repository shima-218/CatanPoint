package com.example.catanpoint.model.usecase

import com.example.catanpoint.model.entity.Player

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

    val playersCounts: MutableList<Pair<Int,Boolean>> = mutableListOf()
    //option 1:最長交易路　2:最大騎士力
    when (option) {
        1 -> {
            for ((iteration, _) in players.withIndex()) {
                playersCounts.add(Pair(players[iteration].first.lengthOfLongestRoads,players[iteration].first.hasLongestRoads))
            }
        }
        2 -> {
            for ((iteration, _) in players.withIndex()) {
                playersCounts.add(Pair(players[iteration].first.numOfUsedKnights,players[iteration].first.hasLargestArmy))
            }
        }
        else -> {
            return -1
        }
    }

    var winnerIndex: Int = preWinnerIndex
    var winnersCount: Int = -1

    //今までの「最大」のプレイヤーの保持数を取得
    if (winnerIndex != -1) {
        winnersCount = playersCounts[winnerIndex].first
    }

    //今までの「最大」のプレイヤーの保持数が閾値以下に減っていたら、初期値に戻す
    if (winnersCount < threshold) {
        winnerIndex = -1
        winnersCount = 0
    }

    //新しい「最大持ち」のプレイヤーを決める
    for ((iteration, player) in playersCounts.withIndex()) {
        if (player.first >= threshold && player.first > winnersCount) {
            winnerIndex = iteration
            winnersCount = player.first
        }
    }

    return winnerIndex
}


