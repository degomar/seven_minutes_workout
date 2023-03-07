package com.example.sevenminutesworkout

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.sevenminutesworkout.databinding.ActivityExerciseBinding
import kotlin.math.log

class ExerciseActivity : AppCompatActivity() {
    lateinit var binding: ActivityExerciseBinding
    var restTimer : CountDownTimer? = null
    var restProgress = 0

    var exerciseTimer : CountDownTimer? = null
    var exerciseProgress = 0

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

    private fun setupRestView(){
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
    }

    private fun setupExerciseView(){
        binding?.progressBar?.visibility = View.GONE
        binding?.tvTitle?.text = "Nome do exercício"
        binding?.flButtonExercise?.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        setExerciseProgressBar()
    }

    fun setResetProgressBar(){

        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000) {

            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }
            override fun onFinish() {
                setupExerciseView()
            }
        }.start()
    }
    fun setExerciseProgressBar(){

        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30000, 1000) {

            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }
            
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,
                    "Os 30 segundos se esgotaram, vamos descansar um pouco",
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
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        restTimer = null
        exerciseTimer = null
    }

}