package com.ureca.chatserver.dto

data class RoomResponse(
    val roomId: Long,
    val name: String?,
    val isGroup: Boolean,
)
