package com.example.mp0601

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock

class MainActivity : ScreenActivity() {
    private val timer = Handler(Looper.getMainLooper())
    private var deadline = 0L
    private val openLogin = Runnable {
        startActivity(Intent(this, LoginActivity::class.java))
        finish() // 뒤로 가기로 로딩 화면이 다시 나타나지 않게 한다.
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fitSystemBars()
        deadline = savedInstanceState?.getLong("deadline") ?: (SystemClock.elapsedRealtime() + 2000L)
    }
    override fun onResume() {
        super.onResume()
        timer.postDelayed(openLogin, (deadline - SystemClock.elapsedRealtime()).coerceAtLeast(0L))
    }
    override fun onPause() {
        timer.removeCallbacks(openLogin)
        super.onPause()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong("deadline", deadline)
        super.onSaveInstanceState(outState)
    }
}
