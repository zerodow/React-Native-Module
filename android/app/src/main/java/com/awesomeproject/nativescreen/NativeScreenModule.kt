package com.awesomeproject.nativescreen

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import android.app.AlertDialog
import com.facebook.react.bridge.Promise
import com.facebook.react.modules.core.DeviceEventManagerModule
import android.util.Log
import android.content.Intent
import android.app.Activity
import com.facebook.react.bridge.ActivityEventListener
import com.facebook.react.bridge.BaseActivityEventListener

class NativeScreenModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private var promise: Promise? = null

    init {
        // Thêm ActivityEventListener với BaseActivityEventListener
        reactContext.addActivityEventListener(object : BaseActivityEventListener() {
            override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
                if (requestCode == 1234) { // Kiểm tra requestCode
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        val result = data.getStringExtra("inputData")
                        promise?.resolve(result) // Trả kết quả về JS
                    } else {
                        promise?.reject("RESULT_ERROR", "No data received or operation cancelled")
                    }
                }
            }
        })
    }

    override fun getName(): String {
        return "NativeScreenModule"
    }

    @ReactMethod
    fun openNativeInputScreen(promise: Promise) {
        val activity = currentActivity
        if (activity != null) {
            this.promise = promise // Lưu Promise để sử dụng sau khi nhận kết quả
            val intent = Intent(activity, NativeInputActivity::class.java)
            activity.startActivityForResult(intent, 1234) // Bắt đầu Activity với requestCode
        } else {
            promise.reject("NO_ACTIVITY", "Current activity is null")
        }
    }
}