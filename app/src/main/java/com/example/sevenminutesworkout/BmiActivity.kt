package com.example.sevenminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.sevenminutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

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

        binding.rbUsUnits.setOnCheckedChangeListener { buttonView , ischeckedButton ->
            if (binding?.llInfos?.isVisible == true){
                binding?.llInfos?.visibility = View.GONE
            }
            if (binding.rbUsUnits.isChecked) {
                makeVisibileUsMetricsView()
            } else if (binding.rbMetricUnits.isChecked){
                makeVisibileMetricsView()
            }
        }

        binding.tbBmi.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculate?.setOnClickListener {
            val df= DecimalFormat("#.##")


            if (verifyInputsEmpty()){
                var heightValue : Float = binding?.etiMetricHeight?.text.toString().toFloat() / 100
                var weightValue : Float = binding?.etiMetricWeight?.text.toString().toFloat()

                var bmi = weightValue / (heightValue * heightValue)

                val valueBmi = df.format(bmi).toFloat()
                setDisplayBmi(valueBmi)
            }
            else {
                if (verifyUsInputsEmpty()){
                    val usHeightValueInch: String =
                        binding?.etiMetricInchs?.text.toString()
                    val usHeightValueFeet: String =
                    binding?.etiUsMetricHeight?.text.toString()
                    val usWeightValuePounds: Float =
                    binding?.etiUsMetricWeight?.text.toString().toFloat()

                    val usHeightValue =
                        usHeightValueFeet.toFloat() + usHeightValueInch.toFloat() * 12
                    val bmi = 703 * (usWeightValuePounds / (usHeightValue * usHeightValue))

                    val valueBmi = df.format(bmi).toFloat()
                    setDisplayBmi(valueBmi)

                }
                Toast.makeText(
                    this, "Por favor preencha os campos para o calculo",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
    private fun verifyInputsEmpty(): Boolean {
        var inputFilled = true


        if (binding?.etiMetricHeight?.text.toString().isEmpty()){ inputFilled = false }
        else if (binding?.etiMetricWeight?.text.toString().isEmpty()) { inputFilled = false }
        else {
            binding?.btnCalculate?.setBackgroundColor(getResources().getColor(R.color.app_color_accent))
        }
        return  inputFilled
    }

    private fun verifyUsInputsEmpty(): Boolean {
        var inputFilled = true

        if (binding?.etiUsMetricHeight?.text.toString().isEmpty()){ inputFilled = false }
        else if (binding?.etiUsMetricWeight?.text.toString().isEmpty()) { inputFilled = false }
        else if (binding?.etiMetricInchs?.text.toString().isEmpty()) { inputFilled = false }
        else {
            binding?.btnCalculate?.setBackgroundColor(getResources().getColor(R.color.app_color_accent))
        }
        return  inputFilled
    }

    private fun setDisplayBmi(bmi: Float) {
        var bmiLabel : String = ""
        var bmiDescription: String = ""

        when {
          bmi >= 40 -> {
              bmiLabel = "Obesidade Grau III"
              bmiDescription= "Muito cuidado, seu grau de obesidade, oferece riscos a sua saúde, procure um médicoe pratique hábitos saudáveis"
          }
          bmi in 35.0..39.9 -> {
              bmiLabel = "Obesidade Grau II"
              bmiDescription= "Atenção, fique atento pois esta no grau de obesidade, procure um médico, redobre os cuidados e pratique hábitos saudáveis"
          }
          bmi in 30.0..34.9 -> {
              bmiLabel = "Obesidade Grau I"
              bmiDescription= "Atenção, fique atento pois esta no grau de obesidade, redobre os cuidados e pratique hábitos saudáveis"
          }
          bmi in 25.0..29.9 -> {
              bmiLabel = "Acima do peso"
              bmiDescription= "Opa, fique atento com o sobrepeso, procure realizar uma boa dieta e exercícios físicos"
          }
          bmi in 18.5..24.9 -> {
              bmiLabel = "Peso normal"
              bmiDescription= "Parabéns, você esta dentro do peso, continue com uma boa dieta e exercícios físicos"
          }
          bmi in 17.0.. 18.4 -> {
              bmiLabel = "Abaixo do peso"
              bmiDescription= "Ops, você esta abaixo do peso, fique atento com a desnutrição e se alimente regularmente com uma dieta saudável"
          }
          bmi < 16.9 ->{
              bmiLabel = "Muito abaixo do peso"
              bmiDescription= "Ops, você esta muito abaixo do peso, fique atento aos cuidados e ingira alimentos saudaveis"
          }
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        binding?.llInfos?.visibility = View.VISIBLE
        binding?.tvResultIbmPorcentText?.text = bmiValue
        binding?.tvResultIbmInfoText?.text = bmiLabel
        binding?.tvResultIbmTips?.text = bmiDescription
    }

    private fun makeVisibileUsMetricsView(){
        binding?.llUnits?.visibility = View.GONE
        binding?.llUsUnits?.visibility = View.VISIBLE
        binding?.etiMetricWeight?.text!!.clear()
        binding?.etiMetricHeight?.text!!.clear()


    }    private fun makeVisibileMetricsView(){
        binding?.llUsUnits?.visibility = View.GONE
        binding?.llUnits?.visibility = View.VISIBLE
        binding?.etiMetricInchs?.text!!.clear()
        binding?.etiUsMetricHeight?.text!!.clear()
        binding?.etiUsMetricWeight?.text!!.clear()


    }
}