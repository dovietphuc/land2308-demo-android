package com.example.demopiegraph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View


class PieGraphView : View {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    public data class PieData(
        val name: String,
        val value: Float,
        val color: Int
    )

    var data: List<PieData> = ArrayList()
        set(value) {
            field = value
            invalidate()
        }
        get() {
            return field
        }

    val paint: Paint = Paint()

    val rect = RectF()

    fun toPercent(pie: PieData) : Float {
        return (100f / dataValueSum()) * pie.value
    }

    fun dataValueSum() : Float {
        var sum = 0.0f
        data.forEach {item ->
            sum += item.value
        }
        return sum
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var startAngle = -90f
        data.forEach { pie ->
            run {
                val percent = toPercent(pie)
                val angle = (360 / 100f) * percent
                val endAngle = startAngle + angle

                paint.color = pie.color
                val radius = Math.min(width / 2, height / 2)
                rect.set(width / 2f - radius, height / 2f - radius,
                    width / 2f + radius, height / 2f + radius)
                canvas?.drawArc(rect,
                    startAngle, angle, true, paint
                )

                startAngle = endAngle
            }
        }
    }
}