package com.example.mp0704

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mp0704.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var positive = 0
    private var negative = 0
    private var positiveView: TextView? = null
    private var negativeView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        supportActionBar?.title = "한 표"
        positive = savedInstanceState?.getInt("positive") ?: 0
        negative = savedInstanceState?.getInt("negative") ?: 0
        ui.agreeButton.setOnClickListener { positive++; updateCounts() }
        ui.disagreeButton.setOnClickListener { negative++; updateCounts() }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        menu.findItem(R.id.positive).setActionView(R.layout.positive_count)
        menu.findItem(R.id.negative).setActionView(R.layout.negative_count)
        positiveView = menu.findItem(R.id.positive).actionView?.findViewById(R.id.count)
        negativeView = menu.findItem(R.id.negative).actionView?.findViewById(R.id.count)
        updateCounts()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.reset -> { positive = 0; negative = 0; updateCounts() }
            R.id.close -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
    private fun updateCounts() {
        positiveView?.text = positive.toString()
        negativeView?.text = negative.toString()
        positiveView?.contentDescription = "찬성 $positive 회"
        negativeView?.contentDescription = "반대 $negative 회"
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("positive",positive); outState.putInt("negative",negative)
        super.onSaveInstanceState(outState)
    }
}
