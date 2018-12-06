package ru.yandex.dunaev.mick.bookingme

import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import android.text.TextPaint
import android.content.Intent
import android.graphics.Color
import android.text.method.LinkMovementMethod
import androidx.core.content.ContextCompat.startActivity
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.text.Layout
import android.view.MotionEvent
import android.text.Selection




fun TextView.setSpanColor(subtext: String, color: Int, colorClick: Int, onClick: () -> Unit) {
    setText(text, TextView.BufferType.SPANNABLE)
    val str = text as Spannable
    val i = text.indexOf(subtext)
    if (i < 0) return
    val clickableSpan = object : TouchableSpan() {
        override fun onClick(textView: View) {
            onClick()
            Log.v("TextSpan", "Click on $subtext")

        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            Log.v("TextSpan","Update drawable $isPressed")
            ds.isUnderlineText = false
            ds.color = if (mIsPressed && selectionStart != -1 && text.toString().substring(
                    selectionStart,
                    selectionEnd
                ).equals(subtext)
            ) colorClick else color
        }
    }
    str.setSpan(clickableSpan, i, i + subtext.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    movementMethod = LinkTouchMovementMethod()
    highlightColor = Color.TRANSPARENT
}

fun TextView.setSpanColor(subtext: String, color: Int) {
    setText(text, TextView.BufferType.SPANNABLE)
    val str = text as Spannable
    val i = text.indexOf(subtext)
    if (i < 0) return
    str.setSpan(ForegroundColorSpan(color), i, i + subtext.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}

abstract class TouchableSpan() : ClickableSpan() {
    var mIsPressed: Boolean = false

    fun setPressed(isSelected: Boolean) {
        mIsPressed = isSelected
    }
}

class LinkTouchMovementMethod : LinkMovementMethod() {
    private var mPressedSpan: TouchableSpan? = null

    override fun onTouchEvent(textView: TextView, spannable: Spannable, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            mPressedSpan = getPressedSpan(textView, spannable, event)
            if (mPressedSpan != null) {
                mPressedSpan!!.setPressed(true)
                Selection.setSelection(
                    spannable, spannable.getSpanStart(mPressedSpan),
                    spannable.getSpanEnd(mPressedSpan)
                )
            }
        } else if (event.action == MotionEvent.ACTION_MOVE) {
            val touchedSpan = getPressedSpan(textView, spannable, event)
            if (mPressedSpan != null && touchedSpan !== mPressedSpan) {
                mPressedSpan!!.setPressed(false)
                mPressedSpan = null
                Selection.removeSelection(spannable)
            }
        } else {
            if (mPressedSpan != null) {
                mPressedSpan!!.setPressed(false)
                super.onTouchEvent(textView, spannable, event)
            }
            mPressedSpan = null
            Selection.removeSelection(spannable)
        }
        return true
    }

    private fun getPressedSpan(
        textView: TextView,
        spannable: Spannable,
        event: MotionEvent
    ): TouchableSpan? {

        val x = event.x.toInt() - textView.totalPaddingLeft + textView.scrollX
        val y = event.y.toInt() - textView.totalPaddingTop + textView.scrollY

        val layout = textView.layout
        val position = layout.getOffsetForHorizontal(layout.getLineForVertical(y), x.toFloat())

        val link = spannable.getSpans(position, position, TouchableSpan::class.java)
        var touchedSpan: TouchableSpan? = null
        if (link.size > 0 && positionWithinTag(position, spannable, link[0])) {
            touchedSpan = link[0]
        }

        return touchedSpan
    }

    private fun positionWithinTag(position: Int, spannable: Spannable, tag: Any): Boolean {
        return position >= spannable.getSpanStart(tag) && position <= spannable.getSpanEnd(tag)
    }
}