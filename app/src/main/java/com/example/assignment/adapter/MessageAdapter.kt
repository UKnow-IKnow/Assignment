package com.example.assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.MessageItemBinding
import com.example.assignment.models.Messages

class MessageViewHolder(val messageItemBinding: MessageItemBinding) :
    RecyclerView.ViewHolder(messageItemBinding.root)
class MessageAdapter(var messageList: Array<Messages>,val onMessageClick:(Messages)->Unit) : RecyclerView.Adapter<MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder= MessageViewHolder(binding)
        binding.root.setOnClickListener {
            onMessageClick(messageList[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.messageItemBinding.apply {
            val message = messageList[position]
            messages.text = message.body
            threadId.text = message.thread_id.toString()
            timeStamp.text=message.timestamp
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun setData(messageList: Array<Messages>) {
        this.messageList = messageList
        notifyDataSetChanged()
    }


}