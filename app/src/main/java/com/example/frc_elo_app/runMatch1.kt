package com.example.frc_elo_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.math.roundToInt

var redAlliance = Array(3){emptyTeam}
var blueAlliance = Array(3){emptyTeam}

class runMatch1 : AppCompatActivity() {

    var counter = 0
    var displayString = ""
    var inputName = ""
    var inputNumber = 0
    var redAlliancesDisplay = ""
    var blueAlliancesDisplay = ""

    var driverStationInt = 1
    var allianceInt = 0
    //Hey, Zynh0722 was here
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_match1)
        counter = 0
        displayString = "Enter the team number in RED alliance station 1: "
        findViewById<TextView>(R.id.tv_prompt).text = displayString
    }

    fun teamEntered(view: View) {

        if (counter > 4) {
            counter = 0
            runningPtTwo()
        }

        var userInput = findViewById<EditText>(R.id.et_input).text.toString()
        inputNumber = userInput.toInt()

        if (teamsByRank.find {it.number == inputNumber} != null) {
            val foundName = teamsByRank.find {it.number == inputNumber}!!.name
            val foundRating = teamsByRank.find {it.number == inputNumber}!!.rating
            if (allianceInt == 0) {
                redAlliancesDisplay = redAlliancesDisplay + "Team $inputNumber, team $foundName " +
                        "with an elo rating of ${foundRating.roundToInt()}\n"
            } else {
                blueAlliancesDisplay = blueAlliancesDisplay + "Team $inputNumber, team $foundName " +
                        "with an elo rating of ${foundRating.roundToInt()}\n"
            }
            findViewById<TextView>(R.id.tv_red_teams).text = redAlliancesDisplay
            findViewById<TextView>(R.id.tv_blue_teams).text = blueAlliancesDisplay

            //Keep track of who is on which alliance
            if (allianceInt == 0) {
                redAlliance[driverStationInt - 1] = teamsByRank.find {it.number == inputNumber}!!
            } else {
                blueAlliance[driverStationInt - 1] = teamsByRank.find {it.number == inputNumber}!!
            }
        } else {
            inputName = findViewById<EditText>(R.id.et_input2).text.toString()

            val newTeam = Team(inputNumber, inputName)

            teamsByRank.add(0, newTeam)

            if (allianceInt == 0) {
                redAlliancesDisplay = redAlliancesDisplay + "Team $inputNumber, team ${newTeam.name} " +
                        "with an elo rating of ${newTeam.rating.roundToInt()}\n"
            } else {
                blueAlliancesDisplay = blueAlliancesDisplay + "Team $inputNumber, team ${newTeam.name} " +
                        "with an elo rating of ${newTeam.rating.roundToInt()}\n"
            }

            findViewById<TextView>(R.id.tv_red_teams).text = redAlliancesDisplay
            findViewById<TextView>(R.id.tv_blue_teams).text = blueAlliancesDisplay

            //Keep track of who is on which alliance
            if (allianceInt == 0) {
                redAlliance[driverStationInt - 1] = teamsByRank.find {it.number == inputNumber}!!
            } else {
                blueAlliance[driverStationInt - 1] = teamsByRank.find {it.number == inputNumber}!!
            }
        }
        counter++

        driverStationInt = counter % 3 + 1
        allianceInt = counter / 3

        displayString = "Enter the team number in ${if(allianceInt == 0) "RED" else "BLUE"} " +
                "alliance station $driverStationInt: "
        findViewById<TextView>(R.id.tv_prompt).text = displayString
    }

    fun runningPtTwo() {
        val partTwoIntent = Intent(this, run_match_two::class.java)
        startActivity(partTwoIntent)
    }
}
