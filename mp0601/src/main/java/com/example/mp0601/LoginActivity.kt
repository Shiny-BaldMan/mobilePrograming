package com.example.mp0601

import android.os.Bundle
import android.widget.Toast
import com.example.mp0601.databinding.ActivityLoginBinding

class LoginActivity : ScreenActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(ui.root)
        fitSystemBars()
        ui.loginButton.setOnClickListener {
            val name = ui.username.text.toString().trim()
            val password = ui.password.text.toString()
            val message = if (name.isBlank() || password.isBlank()) getString(R.string.required)
                else getString(R.string.login_info, name, password)
            // 교재의 출력 요구를 재현하는 데모이며 서버 인증이나 정보 저장은 하지 않는다.
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}
