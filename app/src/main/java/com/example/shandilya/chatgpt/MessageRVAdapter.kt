package com.example.shandilya.chatgpt

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class MessageRVAdapter(private val messageList: ArrayList<MessageRVModal>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class UserMessageViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val userMsgTV: TextView = itemView.findViewById(R.id.idTVUser)
    }

    class BotMessageViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val botMsgTV: TextView = itemView.findViewById(R.id.idTVBot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}