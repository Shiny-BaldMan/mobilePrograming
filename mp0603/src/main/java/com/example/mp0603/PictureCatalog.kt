package com.example.mp0603

import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

data class Picture(val image: Int, val label: Int, val shape: Int)

object PictureCatalog {
    const val CIRCLE = 0
    const val SQUARE = 1
    const val EXTRA_SHAPE = "target_shape"
    const val EXTRA_SELECTION = "selected_pictures"
    // 섞인 순서로 배치하여 도형별 배열 위치를 외우지 않고 그림을 비교하게 한다.
    val items = listOf(
        Picture(R.drawable.shape08, R.string.clock, 2),
        Picture(R.drawable.shape04, R.string.tent, SQUARE),
        Picture(R.drawable.shape03, R.string.gift, CIRCLE),
        Picture(R.drawable.shape02, R.string.window, CIRCLE),
        Picture(R.drawable.shape09, R.string.orange, 2),
        Picture(R.drawable.shape06, R.string.pizza, SQUARE),
        Picture(R.drawable.shape07, R.string.mountain, 2),
        Picture(R.drawable.shape01, R.string.tile, CIRCLE),
        Picture(R.drawable.shape05, R.string.wheel, SQUARE)
    )
    fun card(context: Context, index: Int): LinearLayout {
        val item = items[index]
        val dp = context.resources.displayMetrics.density
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding((6*dp).toInt(), (10*dp).toInt(), (6*dp).toInt(), (10*dp).toInt())
            setBackgroundResource(R.drawable.picture_background)
            addView(ImageView(context).apply {
                setImageDrawable(android.graphics.drawable.StateListDrawable().apply {
                    val picture = androidx.appcompat.content.res.AppCompatResources.getDrawable(context, item.image)!!
                    val checkedPicture = androidx.appcompat.content.res.AppCompatResources.getDrawable(context, item.image)!!
                    val check = androidx.appcompat.content.res.AppCompatResources.getDrawable(context, R.drawable.ok_check)!!
                    addState(intArrayOf(android.R.attr.state_selected), android.graphics.drawable.LayerDrawable(arrayOf(checkedPicture, check)))
                    addState(intArrayOf(), picture)
                })
                isDuplicateParentStateEnabled = true
                importantForAccessibility = android.view.View.IMPORTANT_FOR_ACCESSIBILITY_NO
            }, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (76*dp).toInt()))
            addView(TextView(context).apply {
                setText(item.label); gravity = Gravity.CENTER; textSize = 14f
                setTextColor(android.graphics.Color.rgb(25,44,72))
            })
        }
    }
}
