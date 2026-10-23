package com.example.mp0602

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources

/** 그림의 상대 좌표(0~1)를 화면 크기에 맞춰 변환한다. */
class DifferenceBoard(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val targets = arrayOf(
        listOf(PointF(.815f,.19f), PointF(.49f,.24f), PointF(.13f,.70f), PointF(.25f,.60f), PointF(.82f,.82f)),
        listOf(PointF(.66f,.29f), PointF(.22f,.49f), PointF(.42f,.75f), PointF(.44f,.45f), PointF(.86f,.39f))
    )
    private val locations get() = targets[scene]
    private val discovered = mutableSetOf<Int>()
    private val panels = arrayOf(RectF(), RectF())
    private val pen = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(218, 43, 68); style = Paint.Style.STROKE; strokeWidth = 4f
    }
    private var scene = 0
    private val pictures = arrayOf(
        intArrayOf(R.drawable.cat01, R.drawable.cat02),
        intArrayOf(R.drawable.dog01, R.drawable.dog02)
    )
    private var images = pictures[0].map { AppCompatResources.getDrawable(context, it)!! }
    private val marker = AppCompatResources.getDrawable(context, R.drawable.ok_select)!!
    var onGuess: ((Int, Int) -> Unit)? = null

    fun configure(selectedScene: Int, saved: IntArray) {
        scene = selectedScene.coerceIn(0,1)
        images = pictures[scene].map { AppCompatResources.getDrawable(context, it)!! }
        discovered.clear()
        discovered.addAll(saved.filter { it in locations.indices })
        invalidate()
    }
    fun foundIndices(): IntArray = discovered.sorted().toIntArray()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val gap = 16 * resources.displayMetrics.density
        setMeasuredDimension(w, resolveSize((w * 2f + gap).toInt(), heightMeasureSpec))
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val gap = 16 * resources.displayMetrics.density
        val panelHeight = (h - gap) / 2f
        panels[0].set(0f, 0f, w.toFloat(), panelHeight)
        panels[1].set(0f, panelHeight + gap, w.toFloat(), h.toFloat())
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        panels.forEachIndexed { index, area ->
            canvas.save()
            canvas.translate(area.left, area.top)
            canvas.scale(area.width()/1000f, area.height()/1000f)
            images[index].setBounds(0,0,1000,1000)
            images[index].draw(canvas)
            discovered.forEach { id ->
                val p = locations[id]
                val radius = 95
                marker.setBounds((p.x*1000).toInt()-radius, (p.y*1000).toInt()-radius,
                    (p.x*1000).toInt()+radius, (p.y*1000).toInt()+radius)
                marker.draw(canvas)
            }
            canvas.restore()
        }
    }
    private var downX = 0f
    private var downY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> { downX = event.x; downY = event.y; return true }
            MotionEvent.ACTION_UP -> {
                val slop = android.view.ViewConfiguration.get(context).scaledTouchSlop
                if (kotlin.math.abs(event.x-downX) > slop || kotlin.math.abs(event.y-downY) > slop) return true
                performClick()
                val area = panels.firstOrNull { it.contains(event.x,event.y) } ?: return true
                val x = (event.x-area.left)/area.width()
                val y = (event.y-area.top)/area.height()
                val id = locations.indexOfFirst { (x-it.x)*(x-it.x)+(y-it.y)*(y-it.y) <= .095f*.095f }
                val message = when {
                    id < 0 -> R.string.wrong
                    !discovered.add(id) -> R.string.duplicate
                    discovered.size == locations.size -> R.string.complete
                    else -> R.string.correct
                }
                onGuess?.invoke(message, discovered.size)
                invalidate()
                return true
            }
            MotionEvent.ACTION_CANCEL -> return true
        }
        return true
    }
    override fun performClick(): Boolean { super.performClick(); return true }
}
