package com.ureca.chatserver.dto

data class ChatMessage(
    val roomId: Long,
    val senderId: Long,
    val content: String,
    val config: String? = null
)
