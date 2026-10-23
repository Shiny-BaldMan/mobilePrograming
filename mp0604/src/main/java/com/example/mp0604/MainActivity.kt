package com.example.mp0604

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.mp0604.databinding.ActivityMainBinding

class MainActivity : ScreenActivity() {
    private lateinit var ui: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        fitSystemBars()
        ui.openButton.setOnClickListener { openWebPage() }
        ui.urlInput.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_GO) { openWebPage(); true } else false
        }
    }
    private fun openWebPage() {
        val entered = ui.urlInput.text.toString().trim()
        val address = if (entered.contains("://")) entered else "https://$entered"
        val uri = Uri.parse(address)
        val allowed = uri.scheme.equals("https", true) || uri.scheme.equals("http", true)
        if (entered.isEmpty() || entered.any { it.isWhitespace() } || !allowed ||
            uri.host.isNullOrBlank() || !Patterns.WEB_URL.matcher(address).matches()) {
            ui.urlInput.error = getString(R.string.invalid)
            return
        }
        try {
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, R.string.no_browser, Toast.LENGTH_LONG).show()
        }
    }
}
