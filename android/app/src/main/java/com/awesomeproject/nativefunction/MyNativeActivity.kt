package com.awesomeproject.nativefunction

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MyNativeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val message = intent.getStringExtra("message") ?: "No message passed"

        // Tạo giao diện đơn giản
        val textView = TextView(this)
        textView.text = message
        textView.textSize = 24f
        textView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

        // Đặt giao diện cho Activity
        setContentView(textView)
    }
}