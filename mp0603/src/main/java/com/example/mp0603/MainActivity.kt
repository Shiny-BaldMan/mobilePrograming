package com.example.mp0603

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mp0603.databinding.ActivityMainBinding

class MainActivity : ScreenActivity() {
    private lateinit var ui: ActivityMainBinding
    private var lastSelection = intArrayOf()
    private var lastShape = PictureCatalog.CIRCLE
    private val choosePictures = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            lastSelection = (result.data?.getIntArrayExtra(PictureCatalog.EXTRA_SELECTION) ?: intArrayOf())
                .filter { it in PictureCatalog.items.indices }.distinct().take(3).toIntArray()
            lastShape = result.data?.getIntExtra(PictureCatalog.EXTRA_SHAPE, PictureCatalog.CIRCLE) ?: PictureCatalog.CIRCLE
            showResult()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        fitSystemBars()
        lastSelection = savedInstanceState?.getIntArray("result") ?: intArrayOf()
        lastShape = savedInstanceState?.getInt("shape") ?: PictureCatalog.CIRCLE
        showResult()
        ui.startButton.setOnClickListener {
            val shape = if (ui.squareChoice.isChecked) PictureCatalog.SQUARE else PictureCatalog.CIRCLE
            choosePictures.launch(Intent(this, GameActivity::class.java).putExtra(PictureCatalog.EXTRA_SHAPE, shape))
        }
    }
    private fun showResult() {
        ui.results.removeAllViews()
        if (lastSelection.isEmpty()) { ui.resultMessage.setText(R.string.result_help); return }
        lastSelection.forEach { index ->
            val card = PictureCatalog.card(this, index)
            ui.results.addView(card, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                marginEnd = (4*resources.displayMetrics.density).toInt()
            })
        }
        ui.resultMessage.setText(if (lastSelection.all { PictureCatalog.items[it].shape == lastShape })
            R.string.correct else R.string.wrong)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntArray("result", lastSelection)
        outState.putInt("shape", lastShape)
        super.onSaveInstanceState(outState)
    }
}
