package com.ureca.chatserver.dto

import java.time.LocalDateTime

data class MessageResponse(
    val id: Long,
    val roomId: Long,
    val senderId: Long,
    val content: String,
    val createdAt: LocalDateTime,
)