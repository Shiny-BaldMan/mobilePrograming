package com.example.mp0603

import android.content.Intent
import android.os.Bundle
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.example.mp0603.databinding.ActivityGameBinding

class GameActivity : ScreenActivity() {
    private lateinit var ui: ActivityGameBinding
    private val chosen = linkedSetOf<Int>()
    private val cards = mutableListOf<LinearLayout>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityGameBinding.inflate(layoutInflater)
        setContentView(ui.root)
        fitSystemBars()
        val shape = intent.getIntExtra(PictureCatalog.EXTRA_SHAPE, PictureCatalog.CIRCLE)
        val name = getString(if (shape == PictureCatalog.SQUARE) R.string.square else R.string.circle)
        ui.targetShape.setImageResource(if (shape == PictureCatalog.SQUARE) R.drawable.square else R.drawable.circle)
        ui.targetShape.contentDescription = name
        ui.instruction.text = getString(R.string.pick_help, name)
        chosen.addAll((savedInstanceState?.getIntArray("chosen") ?: intArrayOf())
            .filter { it in PictureCatalog.items.indices }.take(3))
        PictureCatalog.items.indices.forEach { index ->
            val card = PictureCatalog.card(this, index)
            card.isFocusable = true
            card.setOnClickListener {
                when {
                    index in chosen -> chosen.remove(index)
                    chosen.size < 3 -> chosen.add(index)
                    else -> Toast.makeText(this, R.string.limit, Toast.LENGTH_SHORT).show()
                }
                refresh()
            }
            val margin = (4*resources.displayMetrics.density).toInt()
            ui.pictureGrid.addView(card, GridLayout.LayoutParams(
                GridLayout.spec(index/3), GridLayout.spec(index%3, 1f)
            ).apply { width = 0; setMargins(margin, margin, margin, margin) })
            cards.add(card)
        }
        refresh()
        ui.resultButton.setOnClickListener {
            if (chosen.isEmpty()) {
                Toast.makeText(this, R.string.empty, Toast.LENGTH_SHORT).show()
            } else {
                setResult(RESULT_OK, Intent()
                    .putExtra(PictureCatalog.EXTRA_SHAPE, shape)
                    .putExtra(PictureCatalog.EXTRA_SELECTION, chosen.toIntArray()))
                finish()
            }
        }
    }
    private fun refresh() {
        ui.count.text = getString(R.string.count, chosen.size)
        cards.forEachIndexed { index, card ->
            card.isSelected = index in chosen
            card.contentDescription = getString(if (card.isSelected) R.string.selected else R.string.unselected,
                getString(PictureCatalog.items[index].label))
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntArray("chosen", chosen.toIntArray())
        super.onSaveInstanceState(outState)
    }
}
