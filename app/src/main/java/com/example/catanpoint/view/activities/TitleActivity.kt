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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)
        this.displayPlayers()
    }

    private fun displayPlayers(){
        val players = listOf(
            Triple("プレイヤー１", RED, R.id.player1),
            Triple("プレイヤー２", ORANGE, R.id.player2),
            Triple("プレイヤー３", BLUE, R.id.player3),
            Triple("プレイヤー４", CREAM, R.id.player4)
        )

        for(player in players) {
            val view: TextView = findViewById<View>(player.third).findViewById(R.id.player)
            view.hint = player.first
            view.setTextColor(player.second.second)
            val btn: Button = findViewById<View>(player.third).findViewById(R.id.player_color)
            btn.setBackgroundColor(player.second.second)
        }
    }

    fun start(view: View){
        val intent = Intent(this, MainActivity::class.java)

        val players = listOf(
            Triple("player1", RED, R.id.player1),
            Triple("player2", ORANGE, R.id.player2),
            Triple("player3", BLUE, R.id.player3),
            Triple("player4", CREAM, R.id.player4)
        )

        for (player in players) {
            var playerName: String = findViewById<View>(player.third).findViewById<TextView>(R.id.player).text.toString()
            if (playerName == ""){
                playerName=player.first //1つの変数に複数の意味を持たせているので後で変える
            }
            intent.putExtra(player.first, Player(playerName,player.second))
        }
        startActivity(intent)
    }


}