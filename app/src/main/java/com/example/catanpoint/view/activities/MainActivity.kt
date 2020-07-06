package com.example.catanpoint.view.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.catanpoint.R
import com.example.catanpoint.model.entity.Player


class MainActivity : AppCompatActivity() {

    private val players = listOf(
        Pair(Player("こうじ"), R.id.player1),
        Pair(Player("かとけん"), R.id.player2),
        Pair(Player("さくまこうたろう"), R.id.player3),
        Pair(Player("エリサマリガジェゴヒロヤス"), R.id.player4)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.displayPlayers()
        this.displayAllPoints()
    }

    fun changeCount(view: View) {
        val (player: Player?, id: Int?) = this.decidePlayer(view)
        if (player != null && id != null) {
            this.calculateCount(view, player, id)
            this.displayPoints(player, id)
        }
    }

    private fun calculateCount(view: View, player: Player, id: Int) {
        when (view) {
            findViewById<View>(id).findViewById<View>(R.id.cities)
                .findViewById<Button>(R.id.up) -> {
                player.numOfCities = this.upCount(player.numOfCities)
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

    private fun upCount(count: Int): Int{
        return count + 1
    }

    private fun downCount(count: Int): Int{
        if (count > 0){
            return count - 1
        }
        return count
    }

    private fun displayPoints(player: Player, id: Int) {
        val scoreView = findViewById<View>(id).findViewById<TextView>(R.id.points)
        scoreView.text = java.lang.String.valueOf(player.points)
        val numOfCitiesView = findViewById<View>(id).findViewById<View>(R.id.cities)
            .findViewById<TextView>(R.id.count)
        numOfCitiesView.text = java.lang.String.valueOf(player.numOfCities)
        val numOfSettlementsView = findViewById<View>(id).findViewById<View>(R.id.settlements)
            .findViewById<TextView>(R.id.count)
        numOfSettlementsView.text = java.lang.String.valueOf(player.numOfSettlements)
        val numOfRoadsView = findViewById<View>(id).findViewById<View>(R.id.roads)
            .findViewById<TextView>(R.id.count)
        numOfRoadsView.text = java.lang.String.valueOf(player.lengthOfLongestRoads)
        val numOfKnightsView = findViewById<View>(id).findViewById<View>(R.id.knights)
            .findViewById<TextView>(R.id.count)
        numOfKnightsView.text = java.lang.String.valueOf(player.numOfUsedKnights)
        val numOfDevelopsView = findViewById<View>(id).findViewById<View>(R.id.develops)
            .findViewById<TextView>(R.id.count)
        numOfDevelopsView.text = java.lang.String.valueOf(player.numOfDevPoints)
    }

    private fun displayAllPoints() {
        for (player in players) {
            this.displayPoints(player.first, player.second)
        }
    }

    private fun displayPlayers(){
        for (player in players) {
            val playerName = findViewById<View>(player.second).findViewById<TextView>(R.id.player_name)
            playerName.text = java.lang.String.valueOf(player.first.name)
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
        for (player in players) {
            player.first.reset()
        }
        this.displayAllPoints()
    }
}