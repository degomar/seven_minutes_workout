package com.example.sevenminutesworkout

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.sevenminutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    lateinit var binding : ActivityFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_finish)

        setSupportActionBar(binding.tbFinish)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.tbFinish.setOnClickListener {
            onBackPressed()
        }
        binding.btnFinish.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val dao = (application as WorkoutApp).db.historyDao()
        addDatetoDatabase(dao)
    }

    fun addDatetoDatabase(historyDAO: HistoryDAO){

        val c = Calendar.getInstance()
        val dateTime = c.time
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        Log.e("data" , "" + dateTime)

        val date = sdf.format(dateTime)

        lifecycleScope.launch{
            historyDAO.insert(HistoryEntity(""))
        }

    }
}