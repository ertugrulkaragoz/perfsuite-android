package com.booking.perfsuite.app

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random.Default.nextLong

class MessageListAdapter(
    private val data: List<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return object : RecyclerView.ViewHolder(textView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Simulate expensive operations on the main thread with random delays
        val randomDelay = nextLong(10, 300)
        try {
            Thread.sleep(randomDelay)
        } catch (_: InterruptedException) {
            // do nothing
        }
        (holder.itemView as TextView).text = data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
