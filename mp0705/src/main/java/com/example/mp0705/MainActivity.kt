package com.example.mp0705

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mp0705.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var ui: ActivityMainBinding
    private var positive = 0
    private var negative = 0
    private var positiveView: TextView? = null
    private var negativeView: TextView? = null
    private val fruitImages = intArrayOf(R.drawable.fruit1,R.drawable.fruit2,R.drawable.fruit3,R.drawable.fruit4)
    private val values = intArrayOf(3,6,8,1)
    private val names = arrayOf("체리","레몬","사과","바나나")
    private var fruit = intArrayOf(0,1,2)
    private var subtract = booleanArrayOf(false,false)
    private var feedback = "정답과 오답 횟수는 위쪽에 표시됩니다."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        setSupportActionBar(ui.toolbar)
        supportActionBar?.title = "과일 계산"
        if (savedInstanceState == null) newQuestion() else {
            positive = savedInstanceState.getInt("positive")
            negative = savedInstanceState.getInt("negative")
            fruit = savedInstanceState.getIntArray("fruit") ?: intArrayOf(0,1,2)
            subtract = savedInstanceState.getBooleanArray("subtract") ?: booleanArrayOf(false,false)
            feedback = savedInstanceState.getString("feedback") ?: "답을 입력해 주세요."
            render()
        }
        ui.nextButton.setOnClickListener { newQuestion() }
        ui.checkButton.setOnClickListener {
            val answer = ui.answerInput.text.toString().trim().toIntOrNull()
            if (answer == null) {
                ui.answerInput.error = "정수를 입력해 주세요."
                return@setOnClickListener
            }
            var result = values[fruit[0]]
            for (i in 0..1) result += if (subtract[i]) -values[fruit[i+1]] else values[fruit[i+1]]
            if (answer == result) { positive++; feedback = "맞았습니다" }
            else { negative++; feedback = "틀렸습니다" }
            ui.feedback.text = feedback
            updateCounts()
            Toast.makeText(this,feedback,Toast.LENGTH_SHORT).show()
        }
    }
    private fun newQuestion() {
        fruit = (0..3).shuffled().take(3).toIntArray()
        subtract = BooleanArray(2) { Random.nextBoolean() }
        feedback = "답을 입력하고 정답 확인을 눌러 주세요."
        ui.answerInput.text.clear()
        ui.answerInput.error = null
        render()
    }
    private fun render() {
        listOf(ui.fruit0,ui.fruit1,ui.fruit2).forEachIndexed { index, image ->
            image.setImageResource(fruitImages[fruit[index]])
            image.contentDescription = names[fruit[index]]
        }
        ui.operator0.text = if (subtract[0]) "−" else "+"
        ui.operator1.text = if (subtract[1]) "−" else "+"
        ui.feedback.text = feedback
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
        positiveView?.contentDescription = "정답 $positive 회"
        negativeView?.contentDescription = "오답 $negative 회"
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("positive",positive); outState.putInt("negative",negative)
        outState.putIntArray("fruit",fruit); outState.putBooleanArray("subtract",subtract)
        outState.putString("feedback",feedback)
        super.onSaveInstanceState(outState)
    }
}
