package com.example.catanpoint.view.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.catanpoint.R
import com.example.catanpoint.model.entity.Player


class MainActivity : AppCompatActivity() {

    private val players = listOf(
        Pair(Player("player1"),R.id.player1),
        Pair(Player("player2"),R.id.player2),
        Pair(Player("player3"),R.id.player3),
        Pair(Player("player4"),R.id.player4))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.displayAllPoints()
    }

    fun cityUp(view: View) {
        val (player: Player?, id: Int?) = this.decidePlayer(view)
        if (player != null && id != null) {
            player.numOfCities++
            this.displayPoint(player, id)
        }
    }

    fun cityDown(view: View){
        val (player: Player?, id: Int?) = this.decidePlayer(view)
        if (player != null && id != null) {
            if (player.numOfCities > 0) {
                player.numOfCities--
            }
            this.displayPoint(player, id)
        }
    }

    fun settlementUp(view: View){
        val (player: Player?, id: Int?) = this.decidePlayer(view)
        if (player != null && id != null) {
            player.numOfSettlements++
            this.displayPoint(player, id)
        }
    }

    fun settlementDown(view: View){
        val (player: Player?, id: Int?) = this.decidePlayer(view)
        if (player != null && id != null) {
            if (player.numOfSettlements > 0) {
                player.numOfSettlements--
            }
            this.displayPoint(player, id)
        }
    }

    private fun displayPoint(player: Player, id: Int){
        val scoreView= findViewById<View>(id).findViewById<TextView>(R.id.points)
        scoreView.text = java.lang.String.valueOf(player.points)
        val numOfCitiesView = findViewById<View>(id).findViewById<TextView>(R.id.numOfCities)
        numOfCitiesView.text = java.lang.String.valueOf(player.numOfCities)
        val numOfSettlementsView = findViewById<View>(id).findViewById<TextView>(R.id.numOfSettlements)
        numOfSettlementsView.text = java.lang.String.valueOf(player.numOfSettlements)
    }

    private fun displayAllPoints(){
        for (player in players){
            this.displayPoint(player.first,player.second)
        }
    }

    private fun decidePlayer(view: View): Pair<Player?, Int?> {
        for (player in players){
            if (view.parent==findViewById(player.second)) {
                return Pair(player.first, player.second)
            }
        }
        return Pair(null,null)
    }

    fun resetAllPoints(view: View) {
        for (player in players) {
            player.first.resetAllPoints()
        }
        this.displayAllPoints()
    }
}