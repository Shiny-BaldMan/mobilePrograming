package com.example.mp0702

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mp0702.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var ui: ActivityMainBinding
    private var active = 0
    private var selected = intArrayOf(-1,-1)
    private val symbols = intArrayOf(R.drawable.sign_great,R.drawable.sign_small,R.drawable.sign_equal)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        selected = savedInstanceState?.getIntArray("selected") ?: intArrayOf(-1,-1)
        active = savedInstanceState?.getInt("active") ?: 0
        registerForContextMenu(ui.sign1)
        registerForContextMenu(ui.sign2)
        ui.resetButton.setOnClickListener { selected.fill(-1); render() }
        render()
    }
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu,v,menuInfo)
        active = if (v.id == R.id.sign1) 0 else 1
        menu.setHeaderTitle(if (active == 0) "고양이 비교" else "강아지 비교")
        menuInflater.inflate(R.menu.compare_menu,menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val choice = when(item.itemId) {
            R.id.greater -> 0
            R.id.less -> 1
            R.id.equal -> 2
            else -> return super.onContextItemSelected(item)
        }
        selected[active] = choice
        render()
        Toast.makeText(this, if (choice == active) "맞았습니다" else "틀렸습니다", Toast.LENGTH_SHORT).show()
        return true
    }
    private fun render() {
        listOf(ui.sign1,ui.sign2).forEachIndexed { index, image ->
            val value = selected[index]
            image.setImageResource(if (value < 0) R.drawable.sign else symbols[value])
            val text = listOf(ui.result1,ui.result2)[index]
            text.text = when {
                value < 0 -> "아직 선택하지 않았어요."
                value == index -> "맞았습니다"
                else -> "틀렸습니다"
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntArray("selected",selected); outState.putInt("active",active)
        super.onSaveInstanceState(outState)
    }
}
