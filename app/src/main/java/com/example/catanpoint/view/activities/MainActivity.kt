package com.example.catanpoint.view.activities

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.catanpoint.R
import com.example.catanpoint.model.entity.Player
import com.example.catanpoint.model.usecase.decideLargestArmy
import com.example.catanpoint.model.usecase.decideLongestRoads


class MainActivity : AppCompatActivity() {

    private var players = mutableListOf<Pair<Player, Int>>()
    private var playerWithLongestRoadsIndex = -1
    private var playerWithLargestArmyIndex = -1
    private var quitDialog: AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = this.intent
        this.receivePlayers(intent)
        this.displayPlayers()
        this.displayAllPoints()
    }

    private fun receivePlayers(intent: Intent) {

        //受け取ったプレイヤー人数を、変数と画面に反映
        val maxPlayerKeys = listOf(
            Pair("player1", R.id.player1),
            Pair("player2", R.id.player2),
            Pair("player3", R.id.player3),
            Pair("player4", R.id.player4)
        )
        val playerNum: Int = intent.getIntExtra("playerNum", 4)
        val playerKeys = mutableListOf<Pair<String, Int>>()
        for ((iteration, playerKey) in maxPlayerKeys.withIndex()) {
            if (iteration < playerNum) {
                playerKeys.add(iteration, playerKey)
            } else {
                findViewById<View>(playerKey.second).visibility = View.GONE
            }
        }

        //受け取ったプレイヤー情報を変数に格納
        for ((iteration, playerKey) in playerKeys.withIndex()) {
            val player = intent.getSerializableExtra(playerKey.first)
            if (player is Player) {
                players.add(
                    iteration,
                    Pair(player, playerKey.second)
                )
            }
        }
    }

    private fun displayPlayers() {

        val countTitles = listOf(
            Pair("都市", R.id.cities),
            Pair("開拓地", R.id.settlements),
            Pair(this.modulateCountTitleText("最長交易路"), R.id.roads),
            Pair("騎士力", R.id.knights),
            Pair("発展", R.id.develops)
        )

        //プレイヤー毎
        for (player in players) {
            findViewById<View>(player.second).setBackgroundColor(player.first.color.backColor)
            this.displayPlayerName(player)
            //項目毎
            for (countTitle in countTitles) {
                this.displayCountTitle(player, countTitle)
                this.displayUpDownButton(player, countTitle)
            }
        }
    }

    private fun displayPlayerName(player: Pair<Player, Int>) {
        val playerName =
            findViewById<View>(player.second).findViewById<TextView>(R.id.player_name)
        playerName.text = this.modulatePlayerNameText(java.lang.String.valueOf(player.first.name)).first
        playerName.maxLines = this.modulatePlayerNameText(java.lang.String.valueOf(player.first.name)).second
        playerName.setTextColor(player.first.color.frontColor)
    }

    private fun displayCountTitle(player: Pair<Player, Int>, countTitle: Pair<String, Int>) {
        val titleView =
            findViewById<View>(player.second).findViewById<View>(countTitle.second)
                .findViewById<TextView>(R.id.count_title)
        titleView.text = java.lang.String.valueOf(countTitle.first)
        titleView.setTextColor(player.first.color.frontColor)
    }

    private fun displayUpDownButton(player: Pair<Player, Int>, countTitle: Pair<String, Int>) {
        //ボタンを定義
        val btn: GradientDrawable =
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.button,
                null
            ) as GradientDrawable
        btn.setColor(player.first.color.backColor)
        btn.setStroke(2, player.first.color.frontColor)
        //+ボタン
        val upView =
            findViewById<View>(player.second).findViewById<View>(countTitle.second)
                .findViewById<TextView>(R.id.up)
        upView.setTextColor(player.first.color.frontColor)
        upView.background = btn
        //-ボタン
        val downView =
            findViewById<View>(player.second).findViewById<View>(countTitle.second)
                .findViewById<TextView>(R.id.down)
        downView.setTextColor(player.first.color.frontColor)
        downView.background = btn
    }

    private fun modulatePlayerNameText(string: String): Pair<String, Int> {
        if (string.length >= 6) {
            return Pair(
                string.substring(
                    0,
                    3
                ) + System.getProperty("line.separator") + string.substring(3, string.length), 2
            )
        }
        return Pair(string, 1)
    }

    fun changeCount(view: View) {
        val (player: Player?, id: Int?) = this.decidePlayer(view)
        if (player != null && id != null) {
            this.calculateCount(view, player, id)
            this.displayAllPoints()
        }
    }

    private fun decidePlayer(view: View): Pair<Player?, Int?> {
        for (player in players) {
            if (view.parent.parent == findViewById(player.second)) {
                return player
            }
        }
        return Pair(null, null)
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
        this.displaySpecialPoints(playerWithLongestRoadsIndex,R.id.longest_roads,this.modulateCountTitleText("最長交易路"))
        this.displaySpecialPoints(playerWithLargestArmyIndex,R.id.largest_army,this.modulateCountTitleText("最大騎士力"))
    }

    private fun modulateCountTitleText(string: String): String {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            return string.substring(
                    0,
                    2
                ) + System.getProperty("line.separator") + string.substring(2, string.length)
        }
        return string
    }

    private fun displaySpecialPoints(index: Int, id: Int, text: String) {
        //初期化
        for (player in players) {
            val view =
                findViewById<View>(player.second).findViewById<TextView>(id)
            view.text = text
            view.setTextColor(player.first.color.backColor)
            view.background = null
        }
        //表示
        val frameWidth = 2
        if (index != -1) {
            val frame: GradientDrawable =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.special_point_frame,
                    null
                ) as GradientDrawable
            frame.setStroke(frameWidth, players[index].first.color.frontColor)
            val view =
                findViewById<View>(players[index].second).findViewById<TextView>(id)
            view.setTextColor(players[index].first.color.frontColor)
            view.background = frame
        }
    }

    private fun displayPoints(player: Player, id: Int) {
        this.displayPoint(player,id)
        this.displayCount(player,id,player.numOfCities, R.id.cities)
        this.displayCount(player,id,player.numOfSettlements, R.id.settlements)
        this.displayCount(player,id,player.lengthOfLongestRoads, R.id.roads)
        this.displayCount(player,id,player.numOfUsedKnights, R.id.knights)
        this.displayCount(player,id,player.numOfDevPoints, R.id.develops)
    }

    private fun displayPoint(player: Player, id: Int){
        val pointsView = findViewById<View>(id).findViewById<TextView>(R.id.points)
        pointsView.text = java.lang.String.valueOf(player.points)
        pointsView.setTextColor(player.color.frontColor)
    }

    private fun displayCount(player: Player, playerId: Int, count: Int, countId: Int){
        val view = findViewById<View>(playerId).findViewById<View>(countId)
            .findViewById<TextView>(R.id.count)
        view.text = java.lang.String.valueOf(count)
        view.setTextColor(player.color.frontColor)
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
        this.quit()
    }

    private fun quit() {
        if (quitDialog == null) {
            quitDialog = AlertDialog.Builder(this)
                .setTitle("ゲームを終了しますか？")
                .setPositiveButton("終了する") { _, _ ->
                    val intent = Intent(this, TitleActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("キャンセル", null)
        }
        val btn = quitDialog!!.show()
        btn.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE)
        btn.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE)
    }

    override fun onBackPressed() {
        this.quit()
    }
}