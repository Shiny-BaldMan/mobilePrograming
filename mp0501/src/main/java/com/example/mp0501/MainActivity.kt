package com.example.mp0501

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var selectedHouse: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 이 액티비티에서 사용할 화면 파일을 지정한다.
        setContentView(R.layout.activity_main)

        // 상태 표시줄과 하단 시스템 영역에 화면이 가리지 않도록 한다.
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

        // XML에 만든 이미지와 버튼을 찾아 변수에 연결한다.
        selectedHouse = findViewById(R.id.selectedHouse)

        val btnAnswer1 = findViewById<ImageButton>(R.id.btnAnswer1)
        val btnAnswer2 = findViewById<ImageButton>(R.id.btnAnswer2)
        val btnAnswer3 = findViewById<ImageButton>(R.id.btnAnswer3)
        val btnAnswer4 = findViewById<ImageButton>(R.id.btnAnswer4)

        // 클릭을 처리할 리스너 객체를 만든다.
        val listener = MyClickListener()

        // 네 버튼에 같은 리스너를 연결한다.
        btnAnswer1.setOnClickListener(listener)
        btnAnswer2.setOnClickListener(listener)
        btnAnswer3.setOnClickListener(listener)
        btnAnswer4.setOnClickListener(listener)
    }

    inner class MyClickListener : View.OnClickListener {

        override fun onClick(v: View) {

            // 클릭한 버튼에 맞는 이미지 리소스를 고른다.
            val imageResource = when (v.id) {
                R.id.btnAnswer1 -> R.drawable.answer1
                R.id.btnAnswer2 -> R.drawable.answer2
                R.id.btnAnswer3 -> R.drawable.answer3
                R.id.btnAnswer4 -> R.drawable.answer4
                else -> return
            }

            // 선택한 집을 달팽이 위에 표시한다.
            selectedHouse.setImageResource(imageResource)
            selectedHouse.visibility = View.VISIBLE

            // 2번 버튼을 눌렀는지 확인한다.
            val message = if (v.id == R.id.btnAnswer2) {
                "맞았습니다"
            } else {
                "틀렸습니다"
            }

            // 결과를 잠깐 표시한다.
            Toast.makeText(
                this@MainActivity,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}