package com.example.mp0602

import android.os.Bundle
import android.widget.Toast
import com.example.mp0602.databinding.ActivityGameBinding

class GameActivity : ScreenActivity() {
    companion object { const val EXTRA_SCENE = "selected_scene" }
    private lateinit var ui: ActivityGameBinding
    private var notice: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityGameBinding.inflate(layoutInflater)
        setContentView(ui.root)
        fitSystemBars()
        val scene = intent.getIntExtra(EXTRA_SCENE, 0).coerceIn(0,1)
        ui.sceneTitle.setText(if (scene == 0) R.string.park else R.string.camp)
        val found = savedInstanceState?.getIntArray("found") ?: intArrayOf()
        ui.board.configure(scene, found)
        ui.progress.text = getString(R.string.progress, ui.board.foundIndices().size)
        ui.board.onGuess = { message, count ->
            ui.progress.text = getString(R.string.progress, count)
            notice?.cancel()
            notice = Toast.makeText(this, message, Toast.LENGTH_SHORT).also { it.show() }
        }
        ui.backButton.setOnClickListener { finish() }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntArray("found", ui.board.foundIndices())
        super.onSaveInstanceState(outState)
    }
    override fun onDestroy() { notice?.cancel(); super.onDestroy() }
}
