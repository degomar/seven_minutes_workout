package com.example.sevenminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.sevenminutesworkout.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {
    lateinit var binding : ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bmi)

        setSupportActionBar(binding.tbBmi)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculadora IMC"
        }

        binding.tbBmi.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}