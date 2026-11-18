package com.jarvis.app

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.util.Locale

class MainActivity : Activity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var ready = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(Color.parseColor("#050B14"))
            setPadding(48, 48, 48, 48)
        }

        val title = TextView(this).apply {
            text = "J.A.R.V.I.S."
            textSize = 34f
            setTextColor(Color.parseColor("#00E5FF"))
            gravity = Gravity.CENTER
        }

        val status = TextView(this).apply {
            text = "Systems standing by"
            textSize = 16f
            setTextColor(Color.parseColor("#7FDBFF"))
            gravity = Gravity.CENTER
            setPadding(0, 16, 0, 64)
        }

        val button = Button(this).apply {
            text = "WAKE JARVIS"
            setOnClickListener {
                if (ready) {
                    status.text = "Online"
                    speak("Yes, Sir. How may I assist you?")
                } else {
                    status.text = "Voice engine not ready"
                }
            }
        }

        layout.addView(title)
        layout.addView(status)
        layout.addView(button)
        setContentView(layout)

        tts = TextToSpeech(this, this)
    }

    override fun onInit(result: Int) {
        if (result == TextToSpeech.SUCCESS) {
            tts?.setLanguage(Locale.UK)
            ready = true
        }
    }

    private fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "jarvis")
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}
