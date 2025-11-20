package com.ureca.chatserver.dto

data class CreateRoomRequest(
    val userAId: Long,
    val userBId: Long,
    )