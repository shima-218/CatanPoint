package com.example.catanpoint.view.activities

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.catanpoint.R
import com.example.catanpoint.model.entity.*
import com.example.catanpoint.model.usecase.decideLargestArmy
import com.example.catanpoint.model.usecase.decideLongestRoads


class MainActivity : AppCompatActivity() {

    private var players = mutableListOf<Pair<Player, Int>>()
    private var playerWithLongestRoadsIndex = -1
    private var playerWithLargestArmyIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = this.intent

        val playerIntents = listOf(
            Triple("player1", RED, R.id.player1),
            Triple("player2", ORANGE, R.id.player2),
            Triple("player3", BLUE, R.id.player3),
            Triple("player4", CREAM, R.id.player4)
        )

        for ((iteration, player) in playerIntents.withIndex()) {
            val playerName: String? = intent.getStringExtra(player.first)
            if (playerName != null) {
                players.add(iteration,Pair(
                    Player(playerName, player.second),
                    player.third
                ))
            }
        }

        this.displayPlayers()
        this.displayAllPoints()
    }

    fun changeCount(view: View) {
        val (player: Player?, id: Int?) = this.decidePlayer(view)
        if (player != null && id != null) {
            this.calculateCount(view, player, id)
            this.displayAllPoints()
        }
    }

    private fun calculateCount(view: View, player: Player, id: Int) {
        when (view) {
            findViewById<View>(id).findViewById<View>(R.id.cities)
                .findViewById<Button>(R.id.up) -> {
                player.numOfCities = this.upCount(player.numOfCities)
                player.numOfSettlements = this.downCount(player.numOfSettlements)
            }
            findViewById<View>(id).findViewById<View>(R.id.settlements)
                .findViewById<Button>(R.id.up) -> {
                player.numOfSettlements = this.upCount(player.numOfSettlements)
            }
            findViewById<View>(id).findViewById<View>(R.id.roads)
                .findViewById<Button>(R.id.up) -> {
                player.lengthOfLongestRoads = this.upCount(player.lengthOfLongestRoads)
            }
            findViewById<View>(id).findViewById<View>(R.id.knights)
                .findViewById<Button>(R.id.up) -> {
                player.numOfUsedKnights = this.upCount(player.numOfUsedKnights)
            }
            findViewById<View>(id).findViewById<View>(R.id.develops)
                .findViewById<Button>(R.id.up) -> {
                player.numOfDevPoints = this.upCount(player.numOfDevPoints)
            }
            findViewById<View>(id).findViewById<View>(R.id.cities)
                .findViewById<Button>(R.id.down) -> {
                player.numOfCities = this.downCount(player.numOfCities)
            }
            findViewById<View>(id).findViewById<View>(R.id.settlements)
                .findViewById<Button>(R.id.down) -> {
                player.numOfSettlements = this.downCount(player.numOfSettlements)
            }
            findViewById<View>(id).findViewById<View>(R.id.roads)
                .findViewById<Button>(R.id.down) -> {
                player.lengthOfLongestRoads = this.downCount(player.lengthOfLongestRoads)
            }
            findViewById<View>(id).findViewById<View>(R.id.knights)
                .findViewById<Button>(R.id.down) -> {
                player.numOfUsedKnights = this.downCount(player.numOfUsedKnights)
            }
            findViewById<View>(id).findViewById<View>(R.id.develops)
                .findViewById<Button>(R.id.down) -> {
                player.numOfDevPoints = this.downCount(player.numOfDevPoints)
            }
        }
    }

    private fun upCount(count: Int): Int {
        if (count < 99) {
            return count + 1
        }
        return count
    }

    private fun downCount(count: Int): Int {
        if (count > 0) {
            return count - 1
        }
        return count
    }

    private fun displayPoints(player: Player, id: Int) {

        val numOfXXXs = listOf(
            Pair(player.numOfCities, R.id.cities),
            Pair(player.numOfSettlements, R.id.settlements),
            Pair(player.lengthOfLongestRoads, R.id.roads),
            Pair(player.numOfUsedKnights, R.id.knights),
            Pair(player.numOfDevPoints, R.id.develops)
        )

        val pointsView = findViewById<View>(id).findViewById<TextView>(R.id.points)
        pointsView.text = java.lang.String.valueOf(player.points)
        pointsView.setTextColor(player.color.second)

        for (numOfXXX in numOfXXXs) {
            val view = findViewById<View>(id).findViewById<View>(numOfXXX.second)
                .findViewById<TextView>(R.id.count)
            view.text = java.lang.String.valueOf(numOfXXX.first)
            view.setTextColor(player.color.second)
        }

    }

    private fun displayAllPoints() {
        this.decideSpecialPoints()
        this.displaySpecialPoints()
        for (player in players) {
            this.displayPoints(player.first, player.second)
        }
    }

    private fun decideSpecialPoints() {
        playerWithLongestRoadsIndex = decideLongestRoads(players, playerWithLongestRoadsIndex)
        playerWithLargestArmyIndex = decideLargestArmy(players, playerWithLargestArmyIndex)
        for ((iteration, player) in players.withIndex()) {
            player.first.hasLongestRoads = (iteration == playerWithLongestRoadsIndex)
            player.first.hasLargestArmy = (iteration == playerWithLargestArmyIndex)
        }
    }

    private fun displaySpecialPoints() {
        for (player in players) {
            val roadsView = findViewById<View>(player.second).findViewById<View>(R.id.roads)
                .findViewById<TextView>(R.id.count_title)
            roadsView.text = java.lang.String.valueOf("最長交易路")
            val knightsView = findViewById<View>(player.second).findViewById<View>(R.id.knights)
                .findViewById<TextView>(R.id.count_title)
            knightsView.text = java.lang.String.valueOf("騎士力")
        }


        if (playerWithLongestRoadsIndex != -1) {
            val titleView =
                findViewById<View>(players[playerWithLongestRoadsIndex].second).findViewById<View>(R.id.roads)
                    .findViewById<TextView>(R.id.count_title)
            titleView.text = java.lang.String.valueOf("★最長交易路★")
        }
        if (playerWithLargestArmyIndex != -1) {
            val titleView =
                findViewById<View>(players[playerWithLargestArmyIndex].second).findViewById<View>(R.id.knights)
                    .findViewById<TextView>(R.id.count_title)
            titleView.text = java.lang.String.valueOf("★騎士力★")
        }
    }

    private fun displayPlayers() {

        val countTitles = listOf(
            Pair("都市", R.id.cities),
            Pair("開拓地", R.id.settlements),
            Pair("最長交易路", R.id.roads),
            Pair("騎士力", R.id.knights),
            Pair("発展", R.id.develops)
        )

        for (player in players) {
            val playerName =
                findViewById<View>(player.second).findViewById<TextView>(R.id.player_name)
            playerName.text = java.lang.String.valueOf(player.first.name)
            playerName.setTextColor(player.first.color.second)

            val view = findViewById<View>(player.second)
            view.setBackgroundColor(player.first.color.first)

            for ((iteration, _) in countTitles.withIndex()) {
                val titleView =
                    findViewById<View>(player.second).findViewById<View>(countTitles[iteration].second)
                        .findViewById<TextView>(R.id.count_title)
                titleView.text = java.lang.String.valueOf(countTitles[iteration].first)
                titleView.setTextColor(player.first.color.second)
                val btn: GradientDrawable =
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.button,
                        null
                    ) as GradientDrawable
                btn.setColor(player.first.color.first)
                btn.setStroke(2, player.first.color.second)
                val upView =
                    findViewById<View>(player.second).findViewById<View>(countTitles[iteration].second)
                        .findViewById<TextView>(R.id.up)
                upView.setTextColor(player.first.color.second)
                upView.background = btn
                val downView =
                    findViewById<View>(player.second).findViewById<View>(countTitles[iteration].second)
                        .findViewById<TextView>(R.id.down)
                downView.setTextColor(player.first.color.second)
                downView.background = btn
            }
        }
    }

    private fun decidePlayer(view: View): Pair<Player?, Int?> {
        for (player in players) {
            if (view.parent.parent == findViewById(player.second)) {
                return Pair(player.first, player.second)
            }
        }
        return Pair(null, null)
    }

    fun resetAllPoints(view: View) {
        playerWithLongestRoadsIndex = -1
        playerWithLargestArmyIndex = -1
        for (player in players) {
            player.first.reset()
        }
        this.displayAllPoints()
    }

    fun quit(view: View) {
        val intent = Intent(this, TitleActivity::class.java)
        startActivity(intent)
    }
}