package com.example.alcoolougasolina

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var etPrecoAlcool: EditText
    private lateinit var etPrecoGasolina: EditText
    private lateinit var btCalc: Button
    private lateinit var textMsg: TextView

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    lateinit var switch: Switch
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    @Suppress("NAME_SHADOWING")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etPrecoAlcool = findViewById(R.id.edAlcool)
        etPrecoGasolina = findViewById(R.id.edGasolina)
        btCalc = findViewById(R.id.btCalcular)
        textMsg = findViewById(R.id.textMsg)
        switch = findViewById(R.id.swPercentual)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Recuperar os valores salvos
        val precoAlcool = sharedPreferences.getFloat("precoAlcool", 0.0f)
        val precoGasolina = sharedPreferences.getFloat("precoGasolina", 0.0f)
        val isChecked = sharedPreferences.getBoolean("isChecked", false)
        var swPercentual = if (isChecked) 75 else 70

        etPrecoAlcool.setText(precoAlcool.toString())
        etPrecoGasolina.setText(precoGasolina.toString())

        switch.setOnCheckedChangeListener { _, isChecked ->
            swPercentual = if (isChecked) 75 else 70
            updatePercentualText(swPercentual)
        }

        btCalc.setOnClickListener {
            val precoAlcoolText = etPrecoAlcool.text.toString()
            val precoGasolinaText = etPrecoGasolina.text.toString()

            if (precoAlcoolText.isNotEmpty() && precoGasolinaText.isNotEmpty()) {
                val precoAlcool = precoAlcoolText.toFloat()
                val precoGasolina = precoGasolinaText.toFloat()

                val percentual = precoAlcool / precoGasolina * 100
                val percentualFormatado = String.format("%.2f", percentual)

                if (percentual <= swPercentual) {
                    textMsg.text =
                        "É mais rentável abastecer com álcool. Já que o álcool tem $percentualFormatado% da Gasolina, já que é menor que $swPercentual%"
                } else {
                    textMsg.text =
                        "É mais rentável abastecer com gasolina. Já que o álcool tem $percentualFormatado% da Gasolina, já que é maior que $swPercentual%"
                }

                with(sharedPreferences.edit()) {
                    putFloat("precoAlcool", precoAlcool)
                    putFloat("precoGasolina", precoGasolina)
                    putInt("swPercentual", swPercentual)
                    putBoolean("isChecked", switch.isChecked)
                    apply()
                }
            } else {
                textMsg.text = "Preciso dos valores de preço do álcool e da gasolina."
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updatePercentualText(swPercentual: Int) {
        switch.text = resources.getString(if (swPercentual == 75) R.string.percentual_75 else R.string.percentual_70)
    }

    override fun onResume() {
        super.onResume()
        Log.d("PDM24", "No onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.v("PDM24", "No onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.e("PDM24", "No onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.w("PDM24", "No onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.wtf("PDM24", "No Destroy")
    }
}