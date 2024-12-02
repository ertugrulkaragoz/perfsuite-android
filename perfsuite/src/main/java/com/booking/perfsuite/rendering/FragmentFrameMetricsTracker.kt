package com.booking.perfsuite.rendering

import android.app.Activity
import android.os.Bundle
import android.util.SparseIntArray
import androidx.annotation.UiThread
import androidx.core.app.FrameMetricsAggregator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.booking.perfsuite.internal.nowMillis
import java.util.WeakHashMap

/**
 * Implementation of frames metric tracking based on
 * [androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks]
 * which automatically collects frame metrics
 */
@UiThread
public class FragmentFrameMetricsTracker(
    private val listener: Listener,
) : FragmentLifecycleCallbacks() {

    private val aggregator = FrameMetricsAggregator()
    private val fragmentStartTimes = WeakHashMap<Fragment, Long>()

    override fun onFragmentPreCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        aggregator.add(f.activity as Activity)
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        fragmentStartTimes[f] = nowMillis()
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        val metrics = aggregator.reset()
        if (metrics != null) {
            val foregroundTime = fragmentStartTimes.remove(f)?.let { nowMillis() - it }
            listener.onFramesMetricsReady(f, metrics, foregroundTime)
        }
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        try {
            aggregator.remove(f.activity as Activity)
        } catch (_: Exception) {
            // do nothing, aggregator.remove() may cause rare crashes on some devices
        }
    }

    /**
     * Listener interface providing notifications when the fragment's frame metrics are ready
     */
    public interface Listener {

        /**
         * Called everytime when foreground fragment goes to the "paused" state,
         * which means that frame metrics for this screen session are collected
         *
         * @param fragment current fragment
         * @param frameMetrics raw frame metrics collected by [FrameMetricsAggregator]
         * @param foregroundTime time in millis, spent by this activity in foreground state
         */
        public fun onFramesMetricsReady(
            fragment: Fragment,
            frameMetrics: Array<SparseIntArray>,
            foregroundTime: Long?
        )
    }
}
