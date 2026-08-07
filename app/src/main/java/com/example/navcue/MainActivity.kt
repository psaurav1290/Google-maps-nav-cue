package com.example.navcue

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var status: TextView
    private lateinit var log: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        status = findViewById(R.id.status)
        log = findViewById(R.id.log)

        findViewById<Button>(R.id.openAccessibility).setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        findViewById<Button>(R.id.clearLog).setOnClickListener {
            NavCueStore.clear()
            render()
        }

        render()
    }

    override fun onResume() {
        super.onResume()
        render()
    }

    private fun render() {
        status.text = if (NavAccessibilityService.isRunning)
            "Accessibility service: RUNNING"
        else
            "Accessibility service: OFF"

        log.text = NavCueStore.snapshot().ifEmpty {
            "No navigation cues detected yet.\n\nStart Google Maps navigation after enabling the service."
        }
    }
}
