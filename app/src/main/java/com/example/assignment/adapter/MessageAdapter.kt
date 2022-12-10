package com.example.assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.MessageItemBinding
import com.example.assignment.models.Messages

class MessageViewHolder(val messageItemBinding: MessageItemBinding) :
    RecyclerView.ViewHolder(messageItemBinding.root){
        fun bind(message: Messages){
            messageItemBinding.root.setOnClickListener{

            }
        }
    }

class MessageAdapter(var messageList: Array<Messages>) : RecyclerView.Adapter<MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.messageItemBinding.apply {
            val message = messageList[position]
            messages.text = message.body
            userId.text = message.user_id
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