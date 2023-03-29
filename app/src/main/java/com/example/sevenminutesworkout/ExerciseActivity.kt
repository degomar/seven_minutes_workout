package com.example.sevenminutesworkout

import android.media.MediaPlayer
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.sevenminutesworkout.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    lateinit var binding: ActivityExerciseBinding
    var restTimer : CountDownTimer? = null
    var restProgress = 0

    var exerciseTimer : CountDownTimer? = null
    var exerciseProgress = 0

    var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    var exerciseAdapter : ExerciseStatusAdapter? = null

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exercise)

        setSupportActionBar(binding.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tts = TextToSpeech(this, this)



        binding.toolbarExercise.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    private fun setupExerciseStatusRecyclerView(){

        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter

    }

    private fun setupRestView(){

        try {
            val soundUri = Uri.parse(
                "com.example.sevenminutesworkout" + R.raw.app_src_main_res_raw_press_start)
            player = MediaPlayer.create(applicationContext,soundUri)
            player?.isLooping = false
            player?.start()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE

        binding?.flButtonExercise?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.imgExercise?.visibility = View.INVISIBLE

        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExercise?.visibility = View.VISIBLE

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvUpcomingExercise?.text = exerciseList!![currentExercisePosition +1].getName()

        setResetProgressBar()
    }

    private fun setupExerciseView(){
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE

        binding?.flButtonExercise?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.imgExercise?.visibility = View.VISIBLE

        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExercise?.visibility = View.INVISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakout(exerciseList!![currentExercisePosition].getName())

        binding?.imgExercise?.setImageResource(exerciseList!![currentExercisePosition].getimage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

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
                currentExercisePosition++
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
             if (currentExercisePosition < exerciseList?.size!! - 1){
                 setupRestView()
             } else {
                 Toast.makeText(
                     this@ExerciseActivity,
                     "Parabéns, você completou os 7 minutos de atividade.",
                     Toast.LENGTH_LONG
                 ).show()
             }
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

        if (tts != null){
            tts?.stop()
            tts?.shutdown()
        }

        restTimer = null
        exerciseTimer = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_NOT_SUPPORTED ||
                result == TextToSpeech.LANG_MISSING_DATA) {
                Log.e("tts","tts não sukportado")
            }

        } else {
            Log.e("tts","erro de inicialização do tts")
        }
    }

    fun speakout(text: String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

}