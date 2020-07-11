package com.example.catanpoint.view.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.catanpoint.R
import com.example.catanpoint.model.entity.RED
import com.example.catanpoint.model.entity.ORANGE
import com.example.catanpoint.model.entity.BLUE
import com.example.catanpoint.model.entity.CREAM

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
            view.text = player.first
            view.setTextColor(player.second.second)
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
            val playerName: TextView = findViewById<View>(player.third).findViewById(R.id.player)
            intent.putExtra(player.first, playerName.text.toString())
            intent.putExtra(player.first + "Color", player.second)
        }

        startActivity(intent)
    }


}