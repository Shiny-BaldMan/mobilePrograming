package com.example.mp0505

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var imgWindow: ImageView
    private lateinit var imgLight: ImageView
    private lateinit var imgCat: ImageView

    private lateinit var switchCat: Switch
    private lateinit var switchLight: Switch

    private lateinit var txtState: TextView

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

        imgWindow = findViewById(R.id.imgWindow)
        imgLight = findViewById(R.id.imgLight)
        imgCat = findViewById(R.id.imgCat)

        switchCat = findViewById(R.id.switchCat)
        switchLight = findViewById(R.id.switchLight)

        txtState = findViewById(R.id.txtState)

        val listener = MySwitchChangeListener()

        switchCat.setOnCheckedChangeListener(listener)
        switchLight.setOnCheckedChangeListener(listener)

        updateCat(switchCat.isChecked)
        updateLight(switchLight.isChecked)
        updateStateText()
    }

    inner class MySwitchChangeListener :
        CompoundButton.OnCheckedChangeListener {

        override fun onCheckedChanged(
            buttonView: CompoundButton,
            isChecked: Boolean
        ) {
            when (buttonView.id) {
                R.id.switchCat -> {
                    updateCat(isChecked)
                }

                R.id.switchLight -> {
                    updateLight(isChecked)
                }
            }

            updateStateText()
        }
    }

    private fun updateCat(isOn: Boolean) {
        imgCat.visibility = if (isOn) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    private fun updateLight(isOn: Boolean) {
        if (isOn) {
            imgLight.setImageResource(R.drawable.light32)
            imgLight.contentDescription = "켜진 조명"

            // 창문 이미지를 원래 밝기로 표시한다.
            imgWindow.clearColorFilter()
        } else {
            imgLight.setImageResource(R.drawable.light31)
            imgLight.contentDescription = "꺼진 조명"

            // 창문 이미지에 반투명 검정을 입혀 어둡게 표시한다.
            imgWindow.setColorFilter(
                Color.argb(120, 0, 0, 0),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }

    private fun updateStateText() {
        val catState = if (switchCat.isChecked) {
            "ON"
        } else {
            "OFF"
        }

        val lightState = if (switchLight.isChecked) {
            "ON"
        } else {
            "OFF"
        }

        txtState.text = "고양이: $catState / 라이트: $lightState"
    }
}