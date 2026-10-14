package com.example.mp0504

import android.graphics.Color
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var imgRoom: ImageView

    private lateinit var imgLight1: ImageView
    private lateinit var imgLight2: ImageView
    private lateinit var imgLight3: ImageView

    private lateinit var toggleLight: ToggleButton
    private lateinit var txtLightState: TextView

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

        imgRoom = findViewById(R.id.imgRoom)

        imgLight1 = findViewById(R.id.imgLight1)
        imgLight2 = findViewById(R.id.imgLight2)
        imgLight3 = findViewById(R.id.imgLight3)

        toggleLight = findViewById(R.id.toggleLight)
        txtLightState = findViewById(R.id.txtLightState)

        toggleLight.setOnCheckedChangeListener(
            MyLightChangeListener()
        )

        updateLighting(toggleLight.isChecked)
    }

    inner class MyLightChangeListener :
        CompoundButton.OnCheckedChangeListener {

        override fun onCheckedChanged(
            buttonView: CompoundButton,
            isChecked: Boolean
        ) {
            updateLighting(isChecked)
        }
    }

    private fun updateLighting(isOn: Boolean) {
        if (isOn) {
            imgRoom.setImageResource(R.drawable.bg2)
            imgRoom.contentDescription = "불 켜진 방"

            imgLight1.setImageResource(R.drawable.light21)
            imgLight2.setImageResource(R.drawable.light22)
            imgLight3.setImageResource(R.drawable.light23)

            txtLightState.text = "조명이 켜져 있습니다."
            txtLightState.setTextColor(
                Color.rgb(46, 125, 50)
            )
        } else {
            imgRoom.setImageResource(R.drawable.bg1)
            imgRoom.contentDescription = "불 꺼진 방"

            imgLight1.setImageResource(R.drawable.light11)
            imgLight2.setImageResource(R.drawable.light12)
            imgLight3.setImageResource(R.drawable.light13)

            txtLightState.text = "조명이 꺼져 있습니다."
            txtLightState.setTextColor(
                Color.rgb(102, 102, 102)
            )
        }
    }
}