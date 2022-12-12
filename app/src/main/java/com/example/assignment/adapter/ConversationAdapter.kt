package com.example.assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.ConversationItemBinding
import com.example.assignment.models.Messages

class ConversationViewHolder(val conversationItemBinding: ConversationItemBinding):
        RecyclerView.ViewHolder(conversationItemBinding.root)

class ConversationAdapter (var messageList: ArrayList<Messages>): RecyclerView.Adapter<ConversationViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val binding = ConversationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ConversationViewHolder(binding)
        return holder
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.conversationItemBinding.apply {
            val message = messageList[position]
            messages.text = message.body
            threadId.text = message.thread_id.toString()
        }
    }

    fun update( messageList: ArrayList<Messages>){
        this.messageList = messageList
        notifyDataSetChanged()
    }
    fun addMessage(messages: Messages){
        val size =itemCount
        this.messageList.add(messages)
        this.notifyItemInserted(size)
    }
    override fun getItemCount(): Int {
        return messageList.size
    }


}