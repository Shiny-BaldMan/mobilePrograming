package com.example.mp0502

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var checkFireTruck: CheckBox
    private lateinit var checkMixer: CheckBox
    private lateinit var checkStethoscope: CheckBox
    private lateinit var checkGloves: CheckBox

    private lateinit var btnResult: Button
    private lateinit var txtResult: TextView

    private var resultShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        checkFireTruck = findViewById(R.id.checkFireTruck)
        checkMixer = findViewById(R.id.checkMixer)
        checkStethoscope = findViewById(R.id.checkStethoscope)
        checkGloves = findViewById(R.id.checkGloves)

        btnResult = findViewById(R.id.btnResult)
        txtResult = findViewById(R.id.txtResult)

        val listener = MyCheckedChangeListener()

        checkFireTruck.setOnCheckedChangeListener(listener)
        checkMixer.setOnCheckedChangeListener(listener)
        checkStethoscope.setOnCheckedChangeListener(listener)
        checkGloves.setOnCheckedChangeListener(listener)

        btnResult.setOnClickListener {
            if (resultShown) {
                resetQuiz()
            } else {
                showResult()
            }
        }
    }

    inner class MyCheckedChangeListener :
        CompoundButton.OnCheckedChangeListener {

        override fun onCheckedChanged(
            buttonView: CompoundButton,
            isChecked: Boolean
        ) {
            if (isChecked) {
                Toast.makeText(
                    this@MainActivity,
                    "${buttonView.text}를 선택했습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showResult() {
        val selectedItems = mutableListOf<String>()

        if (checkFireTruck.isChecked) {
            selectedItems.add("소방차")
        }

        if (checkMixer.isChecked) {
            selectedItems.add("거품기")
        }

        if (checkStethoscope.isChecked) {
            selectedItems.add("청진기")
        }

        if (checkGloves.isChecked) {
            selectedItems.add("장갑")
        }

        if (selectedItems.isEmpty()) {
            txtResult.text = "물건을 먼저 선택해주세요."
            txtResult.setTextColor(Color.rgb(216, 67, 67))

            Toast.makeText(
                this,
                "선택한 물건이 없습니다.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        Toast.makeText(
            this,
            "선택한 물건: ${selectedItems.joinToString(", ")}",
            Toast.LENGTH_SHORT
        ).show()

        val isCorrect =
            checkFireTruck.isChecked &&
                    checkGloves.isChecked &&
                    !checkMixer.isChecked &&
                    !checkStethoscope.isChecked

        if (isCorrect) {
            txtResult.text = "맞았습니다"
            txtResult.setTextColor(Color.rgb(46, 125, 50))
        } else {
            txtResult.text = "틀렸습니다"
            txtResult.setTextColor(Color.rgb(216, 67, 67))
        }

        resultShown = true
        btnResult.text = "재시작하기"

        setCheckBoxesEnabled(false)
    }

    private fun resetQuiz() {
        checkFireTruck.isChecked = false
        checkMixer.isChecked = false
        checkStethoscope.isChecked = false
        checkGloves.isChecked = false

        resultShown = false

        btnResult.text = "결과보기"
        txtResult.text = "소방관의 물품을 선택해주세요."
        txtResult.setTextColor(Color.rgb(216, 67, 67))

        setCheckBoxesEnabled(true)
    }

    private fun setCheckBoxesEnabled(enabled: Boolean) {
        checkFireTruck.isEnabled = enabled
        checkMixer.isEnabled = enabled
        checkStethoscope.isEnabled = enabled
        checkGloves.isEnabled = enabled
    }
}