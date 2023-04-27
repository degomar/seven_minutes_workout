package com.example.sevenminutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.databinding.DataBindingUtil
import com.example.sevenminutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    binding.flStart.setOnClickListener {
        intent = Intent(this, ExerciseActivity::class.java)
        startActivity(intent)
    }

        binding.flBmi.setOnClickListener {
            intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)
        }

        binding.flHistory.setOnClickListener {
            intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

}