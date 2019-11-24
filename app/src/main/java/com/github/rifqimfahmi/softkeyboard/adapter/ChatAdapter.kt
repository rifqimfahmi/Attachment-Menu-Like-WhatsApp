package com.github.rifqimfahmi.softkeyboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.rifqimfahmi.softkeyboard.R
import com.github.rifqimfahmi.softkeyboard.util.ChatUtil
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter : RecyclerView.Adapter<ItemChat>() {

    var chats = ChatUtil.createDummy()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemChat {
        return ItemChat.create(parent, viewType)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ItemChat, position: Int) {
        holder.bind(chats[position])
    }

}

class ItemChat(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(chat: Chat) {
        with(itemView) {
            tvMsg.text = chat.message
        }
    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): ItemChat {
            return ItemChat(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_chat,
                    parent,
                    false
                )
            )
        }
    }
}

data class Chat(
    val message: String
) {

}