package com.example.sevenminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.sevenminutesworkout.databinding.ActivityExerciseBinding
import kotlin.math.log

class ExerciseActivity : AppCompatActivity() {
    lateinit var binding: ActivityExerciseBinding
    var restTimer : CountDownTimer? = null
    var restProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exercise)

        setSupportActionBar(binding.toolbarExercise)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarExercise.setNavigationOnClickListener {
            onBackPressed()
        }

        setResetProgressBar()
    }

    fun setResetProgressBar(){

        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000) {

            override fun onTick(p0: Long) {
                Log.i("metodo", "entrou no metodo")
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,
                    "Agora iremos iniciar o exercício",
                    Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
    }

}