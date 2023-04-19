package com.olegkirsanov.bullseye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.olegkirsanov.bullseye.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var sliderValue = 0
    private var targetValue = randomValue()
    private var totalScore = 0
    private var currentRound = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        startNewGame()

        binding.hitMeButton.setOnClickListener {
            showResult()
            totalScore += pointsForCurrentRound()
            binding.gameScoreTextView?.text = totalScore.toString()
        }

        binding.startOverButton?.setOnClickListener {
            startNewGame()
        }

        binding.infoButton?.setOnClickListener {
            navigateToAbout()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun navigateToAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun differenceAmount() = abs(sliderValue - targetValue)

    private fun randomValue() = Random.nextInt(1, 100)

    private fun pointsForCurrentRound(): Int {
        val maxScore = 100
        val difference = differenceAmount()

        val bonusPoints = when {
            difference == 0 -> 100
            difference == 1 -> 50
            else -> 0
        }

        return maxScore - difference + bonusPoints
    }

    private fun startNewGame() {
        totalScore = 0
        currentRound = 1
        targetValue = randomValue()

        binding.gameScoreTextView?.text = totalScore.toString()
        binding.gameRoundTextView?.text = currentRound.toString()
        binding.targetTextView.text = targetValue.toString()

        resetSlider()
    }

    private fun resetSlider() {
        sliderValue = 50
        binding.seekBar.progress = sliderValue
    }

    private fun showResult() {
        val dialogTitle = alertTitle()
        val dialogMessage = getString(
            R.string.result_dialog_message,
            sliderValue,
            pointsForCurrentRound()
        )

        val builder = AlertDialog.Builder(this)

        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(R.string.result_dialog_button_text) { dialog, _ ->
            dialog.dismiss()
            targetValue = randomValue()
            binding.targetTextView.text = targetValue.toString()
            currentRound ++
            binding.gameRoundTextView?.text = currentRound.toString()
            resetSlider()
        }

        builder.create().show()
    }

    private fun alertTitle(): String {
        val difference = differenceAmount()

        val alertTitle = when {
            difference == 0 -> getString(R.string.alert_title_1)
            difference < 5 -> getString(R.string.alert_title_2)
            difference < 10 -> getString(R.string.alert_title_3)
            else -> {
                getString(R.string.alert_title_4)
            }
        }

        return alertTitle
    }
}