package com.example.mp0304

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var email: EditText
    lateinit var birth: EditText
    lateinit var phone: EditText
    lateinit var res: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        username = findViewById(R.id.txtName)
        password = findViewById(R.id.txtPw)
        email = findViewById(R.id.txtMail)
        birth = findViewById(R.id.txtBirth)
        phone = findViewById(R.id.txtPhone)
        res = findViewById(R.id.txtResult)
    }

    fun onClickResult(view: View?) {
        if (username.text.toString().isEmpty() ||
            username.text.toString().isEmpty() ||
            username.text.toString().isEmpty() ||
            username.text.toString().isEmpty() ||
            username.text.toString().isEmpty()
            ) {
            res.text = "모든항목을 입력하세요"
        } else {
            res.text = "이름 : ${username.text} \n" +
            "비밀번호 : ${password.text} \n" +
            "이메일 : ${email.text} \n" +
            "생년월일 : ${birth.text} \n" +
            "전화번호 : ${phone.text} \n"
        }
    }
}