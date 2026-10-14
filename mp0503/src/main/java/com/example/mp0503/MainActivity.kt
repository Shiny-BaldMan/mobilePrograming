package com.example.mp0503

import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private var resultToast: Toast? = null

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

        radioGroup = findViewById(R.id.radioGroup)

        val imgAnswer1 = findViewById<ImageView>(R.id.imgAnswer1)
        val imgAnswer2 = findViewById<ImageView>(R.id.imgAnswer2)
        val imgAnswer3 = findViewById<ImageView>(R.id.imgAnswer3)
        val imgAnswer4 = findViewById<ImageView>(R.id.imgAnswer4)

        radioGroup.setOnCheckedChangeListener(
            MyRadioChangeListener()
        )

        // 그림을 눌러도 해당 라디오버튼이 선택된다.
        imgAnswer1.setOnClickListener {
            radioGroup.check(R.id.radioAnswer1)
        }

        imgAnswer2.setOnClickListener {
            radioGroup.check(R.id.radioAnswer2)
        }

        imgAnswer3.setOnClickListener {
            radioGroup.check(R.id.radioAnswer3)
        }

        imgAnswer4.setOnClickListener {
            radioGroup.check(R.id.radioAnswer4)
        }
    }

    inner class MyRadioChangeListener :
        RadioGroup.OnCheckedChangeListener {

        override fun onCheckedChanged(
            group: RadioGroup,
            checkedId: Int
        ) {
            val message = when (checkedId) {
                R.id.radioAnswer2 -> "맞았습니다"

                R.id.radioAnswer1,
                R.id.radioAnswer3,
                R.id.radioAnswer4 -> "틀렸습니다"

                else -> return
            }

            resultToast?.cancel()

            resultToast = Toast.makeText(
                this@MainActivity,
                message,
                Toast.LENGTH_SHORT
            )

            resultToast?.show()
        }
    }

    override fun onDestroy() {
        resultToast?.cancel()
        super.onDestroy()
    }
}