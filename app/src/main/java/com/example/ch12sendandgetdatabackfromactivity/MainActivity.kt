package com.example.ch12sendandgetdatabackfromactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.Validators.and
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val SECOND_ACTIVITY = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input_weight.hint = "wegiht (lbs)"
        input_height.hint = "height (inches)"

        btn_send_data.setOnClickListener {
            val m_intent = Intent (this, SecondActivity::class.java)
            val m_bundle = Bundle ()

            input_weight.text.toString().toFloatOrNull()?.let { it1 ->
                m_bundle.putFloat("weight",
                    it1
                )
            }
            input_height.text.toString().toFloatOrNull()?.let { it1 ->
                m_bundle.putFloat("height",
                    it1
                )
            }
            m_intent.putExtra("main_activity_data", m_bundle)

            startActivityForResult(m_intent, SECOND_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == SECOND_ACTIVITY) and (resultCode == Activity.RESULT_OK)) {
            val bmi = data!!.getFloatExtra("second_activity_data", 1.0F)
            val bmiString = "%.2f".format(bmi)
            clearInputs()
            txt_bmi.setText("BMI : $bmiString ${getBMIDescription(bmi)}")

        }
    }

    override fun onResume() {
        super.onResume()
        clearInputs()
    }
    private fun getBMIDescription(bmi : Float) : String {

        return when (bmi) {
            in 1.0..18.5 -> "Underweight"
            in 18.6..24.9 -> "Normal weight"
            in 25.0..29.9 -> "Overweight"
            else -> "Obese"
        }
    }
    private fun clearInputs () {
        input_weight.setText("")
        input_height.setText("")
    }

}