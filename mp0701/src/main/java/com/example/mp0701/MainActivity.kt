package com.example.mp0701

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.FrameLayout
import androidx.core.view.doOnLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mp0701.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var ui: ActivityMainBinding
    private var operation = 0
    private var left = 0
    private var answer = 0
    private var total = 0
    private var choices = intArrayOf()
    private var picked = -1
    private val digits = intArrayOf(R.drawable.num0,R.drawable.num1,R.drawable.num2,R.drawable.num3,R.drawable.num4,R.drawable.num5,R.drawable.num6,R.drawable.num7,R.drawable.num8,R.drawable.num9)
    private val buttons = intArrayOf(R.drawable.btn0,R.drawable.btn1,R.drawable.btn2,R.drawable.btn3,R.drawable.btn4,R.drawable.btn5,R.drawable.btn6,R.drawable.btn7,R.drawable.btn8,R.drawable.btn9)
    private val signs = arrayOf("+", "−", "×", "÷")
    private val labels = arrayOf("덧셈", "뺄셈", "곱셈", "나눗셈")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        supportActionBar?.title = "빈칸 계산"
        if (savedInstanceState == null) newQuestion() else {
            operation = savedInstanceState.getInt("operation")
            left = savedInstanceState.getInt("left")
            answer = savedInstanceState.getInt("answer")
            total = savedInstanceState.getInt("total")
            choices = savedInstanceState.getIntArray("choices") ?: intArrayOf(answer)
            picked = savedInstanceState.getInt("picked", -1)
            render()
        }
        ui.chalkboard.doOnLayout { board ->
            ui.equation.layoutParams = (ui.equation.layoutParams as FrameLayout.LayoutParams).apply {
                width = (board.width * 0.84f).toInt()
                height = (board.height * 0.20f).toInt()
                leftMargin = (board.width * 0.08f).toInt()
                topMargin = (board.height * 0.36f).toInt()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        operation = when (item.itemId) {
            R.id.add -> 0
            R.id.subtract -> 1
            R.id.multiply -> 2
            R.id.divide -> 3
            else -> return super.onOptionsItemSelected(item)
        }
        newQuestion()
        return true
    }
    private fun newQuestion() {
        answer = Random.nextInt(1,10)
        left = Random.nextInt(1,10)
        if (operation == 3) left *= answer
        total = when (operation) {
            0 -> left + answer
            1 -> left - answer
            2 -> left * answer
            else -> left / answer
        }
        choices = ((1..9).filter { it != answer }.shuffled().take(2) + answer).shuffled().toIntArray()
        picked = -1
        render()
    }
    private fun render() {
        ui.operationLabel.text = labels[operation]
        ui.equation.removeAllViews()
        val tokens = "$left ${signs[operation]} ? = $total".filter { it != ' ' }
        tokens.forEach { token ->
            val resource = when (token) {
                '+' -> R.drawable.op1
                '−', '-' -> R.drawable.op2
                '×' -> R.drawable.op3
                '÷' -> R.drawable.op4
                '=' -> R.drawable.op5
                '?' -> R.drawable.op6
                else -> digits[token.digitToInt()]
            }
            val image = ImageView(this).apply {
                setImageResource(resource)
                contentDescription = token.toString()
                scaleType = ImageView.ScaleType.FIT_CENTER
                setPadding(2,0,2,0)
            }
            ui.equation.addView(image, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f))
        }
        listOf(ui.choice0,ui.choice1,ui.choice2).forEachIndexed { index, button ->
            val value = choices[index]
            button.setImageResource(buttons[value])
            button.contentDescription = "$value 선택"
            button.setOnClickListener {
                picked = value
                val message = if (picked == answer) "맞았습니다" else "틀렸습니다"
                ui.feedback.text = "$value 선택 · $message"
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
        ui.feedback.text = if (picked < 0) "아래 숫자 중 하나를 선택하세요." else "$picked 선택 · ${if (picked == answer) "맞았습니다" else "틀렸습니다"}"
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("operation",operation); outState.putInt("left",left)
        outState.putInt("answer",answer); outState.putInt("total",total)
        outState.putIntArray("choices",choices); outState.putInt("picked",picked)
        super.onSaveInstanceState(outState)
    }
}
