package com.booking.perfsuite.app

import android.os.Bundle
import android.view.View.GONE
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.booking.perfsuite.app.monitoring.FragmentFrameMetricsListener
import com.booking.perfsuite.rendering.FragmentFrameMetricsTracker

class MainActivity : AppCompatActivity() {

    val fragmentFrameMetricsTracker = FragmentFrameMetricsTracker(FragmentFrameMetricsListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Register the frame metric tracker
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentFrameMetricsTracker, true)
        val textViewLoadingMessage = findViewById<TextView>(R.id.textViewLoadingMessage)

        textViewLoadingMessage.postDelayed({
            textViewLoadingMessage.visibility = GONE
            supportFragmentManager.beginTransaction().replace(
                R.id.fragmentContainerView,
                MessageListFragment(),
            ).commit()
            reportIsUsable()
        }, 1000)

    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager
            .unregisterFragmentLifecycleCallbacks(fragmentFrameMetricsTracker)
    }
}
