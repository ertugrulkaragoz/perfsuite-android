package com.booking.perfsuite.app.monitoring

import android.util.Log
import android.util.SparseIntArray
import androidx.fragment.app.Fragment
import com.booking.perfsuite.rendering.FragmentFrameMetricsTracker
import com.booking.perfsuite.rendering.RenderingMetricsMapper

object FragmentFrameMetricsListener : FragmentFrameMetricsTracker.Listener {
    override fun onFramesMetricsReady(
        fragment: Fragment,
        frameMetrics: Array<SparseIntArray>,
        foregroundTime: Long?
    ) {
        val data = RenderingMetricsMapper.toRenderingMetrics(frameMetrics, foregroundTime) ?: return

        Log.d("PerfSuite", "Frame metrics for [$fragment::class.simpleName] are collected: $data")
    }
}
