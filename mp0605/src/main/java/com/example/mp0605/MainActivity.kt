package com.example.mp0605

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.mp0605.databinding.ActivityMainBinding

class MainActivity : ScreenActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        fitSystemBars()
        ui.sendButton.setOnClickListener {
            // 버튼을 누른 시점의 입력값을 가져온다.
            val recipient = ui.recipientInput.text.toString().trim()
            if (!Patterns.EMAIL_ADDRESS.matcher(recipient).matches()) {
                ui.recipientInput.error = getString(R.string.invalid)
                return@setOnClickListener
            }
            val subject = ui.subjectInput.text.toString()
            val body = ui.bodyInput.text.toString()
            // ACTION_SENDTO + mailto:를 처리하는 앱의 인텐트 필터와 매칭된다.
            // URI 인코딩은 한글, 줄바꿈, &, # 등이 주소 구분자로 해석되는 것을 막는다.
            val uri = Uri.parse("mailto:${Uri.encode(recipient)}?subject=${Uri.encode(subject)}&body=${Uri.encode(body)}")
            val composeMail = Intent(Intent.ACTION_SENDTO, uri).apply {
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }
            try {
                startActivity(composeMail)
            } catch (_: ActivityNotFoundException) {
                Toast.makeText(this, R.string.no_mail, Toast.LENGTH_LONG).show()
            }
        }
    }
}
