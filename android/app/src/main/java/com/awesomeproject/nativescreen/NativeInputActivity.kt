package com.awesomeproject.nativescreen

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.awesomeproject.R

class NativeInputActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_input) // Sử dụng layout từ XML

        // Tham chiếu tới các View
        val inputField = findViewById<EditText>(R.id.inputField)
        val backButton = findViewById<Button>(R.id.btnBack)
        val submitButton = findViewById<Button>(R.id.btnSubmit)

        // Xử lý nút Back
        backButton.setOnClickListener {
            finish() // Quay lại màn hình trước đó
        }

        // Xử lý nút Submit
        submitButton.setOnClickListener {
            val inputText = inputField.text.toString()

            // Trả dữ liệu về React Native qua Intent
            val resultIntent = intent
            resultIntent.putExtra("inputData", inputText)
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Đóng Activity
        }
    }
}