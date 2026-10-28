package com.example.mp0703

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mp0703.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var ui: ActivityMainBinding
    private val animals = intArrayOf(R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5,R.drawable.image6)
    private val names = arrayOf("딱정벌레","쥐","고양이","돼지","타조","들소")
    private var order = intArrayOf()
    private var direction = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        order = savedInstanceState?.getIntArray("order") ?: (0..5).shuffled().toIntArray()
        direction = savedInstanceState?.getInt("direction") ?: 0
        ui.sortButton.setOnClickListener {
            PopupMenu(this,ui.sortButton).apply {
                menuInflater.inflate(R.menu.sort_menu,menu)
                setOnMenuItemClickListener { item ->
                    direction = if (item.itemId == R.id.ascending) 1 else -1
                    order = if (direction == 1) (0..5).toList().toIntArray() else (5 downTo 0).toList().toIntArray()
                    render()
                    Toast.makeText(this@MainActivity, if (direction == 1) "오름차순 정렬입니다" else "내림차순 정렬입니다", Toast.LENGTH_SHORT).show()
                    true
                }
                show()
            }
        }
        ui.shuffleButton.setOnClickListener { direction = 0; order.shuffle(); render() }
        render()
    }
    private fun render() {
        ui.animalList.removeAllViews()
        val dp = resources.displayMetrics.density
        order.forEachIndexed { rank, index ->
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
                setPadding(12,8,12,8)
            }
            row.addView(ImageView(this).apply { setImageResource(animals[index]); contentDescription = names[index] }, LinearLayout.LayoutParams((88*dp).toInt(),(76*dp).toInt()))
            row.addView(TextView(this).apply { text = "${rank+1}. ${names[index]}"; textSize = 19f; setPadding((20*dp).toInt(),0,0,0) })
            ui.animalList.addView(row)
        }
        ui.direction.visibility = if (direction == 0) View.GONE else View.VISIBLE
        ui.direction.setImageResource(if (direction == 1) R.drawable.bg_small2big else R.drawable.bg_big2small)
        ui.sortLabel.text = when(direction) { 1 -> "작은 동물 → 큰 동물"; -1 -> "큰 동물 → 작은 동물"; else -> "아직 정렬하지 않았어요." }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntArray("order",order); outState.putInt("direction",direction)
        super.onSaveInstanceState(outState)
    }
}
