package com.example.catanpoint.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.catanpoint.R
import com.example.catanpoint.model.entity.*

class TitleActivity : AppCompatActivity() {

    private var players = mutableListOf(
        Pair(Player("", RED), R.id.player1),
        Pair(Player("", ORANGE), R.id.player2),
        Pair(Player("", BLUE), R.id.player3),
        Pair(Player("", CREAM), R.id.player4)
    )

    private val playerAndStrings = listOf(
        Triple(players[0], "プレイヤー１", "player1"),
        Triple(players[1], "プレイヤー２", "player2"),
        Triple(players[2], "プレイヤー３", "player3"),
        Triple(players[3], "プレイヤー４", "player4")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)
        this.displayAllPlayers()
    }

    private fun displayAllPlayers() {
        for (playerAndString in playerAndStrings) {
            val player = playerAndString.first.first
            val id = playerAndString.first.second
            val defaultName = playerAndString.second
            displayPlayer(player, id, defaultName)
        }
    }

    private fun displayPlayer(player: Player, id: Int, defaultName: String) {
        val view: TextView = findViewById<View>(id).findViewById(R.id.player)
        view.hint = defaultName
        view.setTextColor(player.color.frontColor)
        val btn: Button = findViewById<View>(id).findViewById(R.id.player_color)
        btn.setBackgroundColor(player.color.frontColor)
    }

    fun changeColor(view: View) {
        val (player: Pair<Player?, Int?>?, defaultName: String?, _) = this.decidePlayer(view)
        if (player != null && defaultName != null) {
            if (player.first != null && player.second != null) {
                this.rotateColor(player.first!!)
                displayPlayer(player.first!!, player.second!!, defaultName)
            }
        }
    }

    private fun rotateColor(player: Player) {
        val colors = listOf(
            RED, ORANGE, BLUE, CREAM
        )

        //リストの色と一致したら、indexにはリストの次の色の番号が入る
        var index = 0
        for (color in colors) {
            index++
            if (player.color == color) {
                break
            }
        }
        if (index == colors.size) {
            index = 0
        }

        //色を変える
        player.color = colors[index]


    }

    private fun decidePlayer(view: View): Triple<Pair<Player?, Int?>?, String?, String?> {
        for (playerAndString in playerAndStrings) {
            if (view.parent == findViewById(playerAndString.first.second)) {
                return playerAndString
            }
        }
        return Triple(null, null, null)
    }

    fun start(view: View) {
        val intent = Intent(this, MainActivity::class.java)

        for (playerAndString in playerAndStrings) {
            val player = playerAndString.first.first
            val id = playerAndString.first.second
            val defaultName = playerAndString.second
            val key = playerAndString.third

            var playerName: String =
                findViewById<View>(id).findViewById<TextView>(R.id.player).text.toString()
            if (playerName == "") {
                playerName = defaultName
            }
            player.name = playerName
            intent.putExtra(key, player)
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        moveTaskToBack (true)
    }


}