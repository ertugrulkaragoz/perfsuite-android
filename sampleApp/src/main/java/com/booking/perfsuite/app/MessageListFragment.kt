package com.booking.perfsuite.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MessageListFragment : Fragment() {

    private val messageList = List(100) { "Message #$it" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = FrameLayout(requireContext())
        val recyclerView = RecyclerView(requireContext()).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MessageListAdapter(messageList)
            setHasFixedSize(true)
        }
        rootView.addView(recyclerView)
        return rootView
    }
}
