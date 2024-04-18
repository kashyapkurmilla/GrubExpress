package com.example.geminibot

import android.graphics.Bitmap
import com.example.geminibot.data.Chat

data class ChatState (
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)