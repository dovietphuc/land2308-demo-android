package com.example.demoanimator

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Property
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.PathInterpolator
import android.widget.TextView
import androidx.core.animation.addListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        textView.setOnClickListener {
//            startAnim(textView)
            val animation =
                AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in)
            textView.startAnimation(animation)
        }
    }

    val SCALE = object : Property<View, Float>(Float::class.java, "scale") {
        override fun get(view: View?): Float {
            return view!!.scaleX
        }

        override fun set(view: View?, value: Float?) {
            view?.scaleX = value!!
            view?.scaleY = value
        }
    }

    fun startAnim(view: TextView) {
        val valueAnimator =
            ObjectAnimator.ofFloat(view, View.ROTATION, -10f, 10f)
        valueAnimator.duration = 100
        valueAnimator.repeatCount = ObjectAnimator.INFINITE
        valueAnimator.repeatMode = ObjectAnimator.REVERSE
        valueAnimator.start()
    }
}