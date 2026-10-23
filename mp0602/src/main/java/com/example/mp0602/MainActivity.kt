package com.example.mp0602

import android.content.Intent
import android.os.Bundle
import com.example.mp0602.databinding.ActivityMainBinding

class MainActivity : ScreenActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        fitSystemBars()
        ui.startButton.setOnClickListener {
            val scene = if (ui.campChoice.isChecked) 1 else 0
            startActivity(Intent(this, GameActivity::class.java).putExtra(GameActivity.EXTRA_SCENE, scene))
        }
    }
}
