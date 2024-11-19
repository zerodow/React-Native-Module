package com.awesomeproject.nativefunction

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import android.app.AlertDialog
import com.facebook.react.bridge.Promise
import com.facebook.react.modules.core.DeviceEventManagerModule
import android.util.Log
import android.content.Intent

class MyNativeModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "MyNativeModule"
    }

    @ReactMethod
    fun showToast(message: String) {
        android.widget.Toast.makeText(reactApplicationContext, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    // Hàm trả về thời gian hiện tại
    @ReactMethod
    fun getCurrentTime(): String {
        val currentTime = java.util.Date().toString()
        return currentTime
    }

    @ReactMethod
    fun getCurrentTimeWithPromise(promise: Promise) {
        try {
            val currentTime = java.util.Date().toString()
            promise.resolve(currentTime)
        } catch (e: Exception) {
            promise.reject("GET_TIME_ERROR", "Could not get current time", e)
        }
    }

    @ReactMethod
    fun showAlert(title: String, message: String,promise: Promise) {
        val activity = currentActivity  // Lấy Activity hiện tại
        if (activity != null) {
            activity.runOnUiThread {  // Đảm bảo hiển thị trên UI thread
                val builder = AlertDialog.Builder(activity)
                builder.setTitle(title)
                builder.setMessage(message)

                // Set positive button và listener
                builder.setPositiveButton("OK") { dialog, _ ->
                    promise.resolve("OK Pressed")
                    dialog.dismiss() // Đóng dialog
                }

                // Set negative button (tuỳ chọn)
                builder.setNegativeButton("Cancel") { dialog, _ ->
                    promise.resolve("Cancel Pressed")
                    dialog.dismiss() // Đóng dialog
                }

                // Hiển thị dialog
                builder.create().show()
            }
        } else {
            Log.e("MyNativeModule", "Current Activity is null")
        }   
}

//     @ReactMethod
// fun showAlert(title: String, message: String) {
//     val builder = AlertDialog.Builder(reactApplicationContext)
//     builder.setTitle(title)
//     builder.setMessage(message)

//     // Set positive button and its click listener
//     builder.setPositiveButton("OK") { dialog, _ ->
//         dialog.dismiss() // Close the dialog
//     }

//     // Set negative button and its click listener (optional)
//     builder.setNegativeButton("Cancel") { dialog, _ ->
//         dialog.dismiss() // Close the dialog
//     }

//     // Create and show the alert dialog
//     val alertDialog = builder.create()
//     alertDialog.show()
// }

  // Hàm gửi sự kiện về JavaScript
    @ReactMethod
    fun triggerEvent(eventName: String, eventData: String) {
        val eventEmitter = reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
        eventEmitter.emit(eventName, eventData)
    }

    @ReactMethod
    fun addListener(type: String?) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    @ReactMethod
    fun removeListeners(type: Int?) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    @ReactMethod
    fun showNativeScreen(message:String) {
        val activity = currentActivity
        if (activity != null) {
            val intent = Intent(activity, MyNativeActivity::class.java)
            intent.putExtra("message", message)
            activity.startActivity(intent)
        }
    }
}