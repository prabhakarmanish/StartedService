package prabhakar.manish.startedservice

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log

class MyServices : Service() {
    private val handler = Handler()
    private var countDownTime = 60// 10 seconds countdown

    override fun onCreate() {
        super.onCreate()
        Log.d("MyServices", "Service Created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyServices", "Service Started")
        startCountdown()
        return START_STICKY // Service will be restarted if killed
    }

    private fun startCountdown() {
        handler.post(object : Runnable {
            override fun run() {
                if (countDownTime > 0) {
                    Log.d("MyServices", "Countdown: $countDownTime seconds remaining")
                    countDownTime--
                    handler.postDelayed(this, 1000) // Repeat every second
                } else {
                    Log.d("MyServices", "Countdown Complete")
                    stopSelf() // Stop the service when done
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyServices", "Service Destroyed")
        handler.removeCallbacksAndMessages(null) // Clean up handler callbacks
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // Not binding this service
    }
}
