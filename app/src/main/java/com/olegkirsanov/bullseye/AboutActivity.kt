package com.olegkirsanov.bullseye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.olegkirsanov.bullseye.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        title = getString(R.string.about_nav_title)

        binding.aboutButton?.setOnClickListener {
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        finish()
    }
}