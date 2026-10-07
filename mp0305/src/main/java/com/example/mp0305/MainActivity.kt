package com.example.mp0305

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mp0305.R

class MainActivity : AppCompatActivity() {

    // 1. ImageView 객체 변수 선언 [4]
    private lateinit var imageView: ImageView

    // 2. 고양이 이미지 리소스 ID 배열 정의
    private val catImages = arrayOf(
        R.drawable.cat1,
        R.drawable.cat2,
        R.drawable.cat3,
        R.drawable.cat4,
        R.drawable.cat5
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 3. findViewById()로 ImageView 연결 [4]
        imageView = findViewById(R.id.imageView_id)
    }

    // 4. XML의 android:onClick="onButtonClick"과 연결되는 이벤트 메서드 [4]
    fun onButtonClick(view: View?) {
        // 다음 이미지 인덱스로 이동 (마지막에 도달하면 0으로 순환)
        currentIndex = (currentIndex + 1) % catImages.size

        // 이미지 뷰의 이미지 변경 [4]
        imageView.setImageResource(catImages[currentIndex])
    }
}