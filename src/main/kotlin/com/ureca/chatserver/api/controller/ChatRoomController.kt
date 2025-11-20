package com.ureca.chatserver.api.controller

import com.ureca.chatserver.dto.CreateRoomRequest
import com.ureca.chatserver.dto.RoomResponse
import com.ureca.chatserver.service.ChatRoomService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatRoomController(val chatRoomService: ChatRoomService) {

    @PostMapping("/create")
    fun createRoom(@RequestBody request: CreateRoomRequest): RoomResponse {
        print("Creating new room")
        return chatRoomService.createOneToOneRoom(request.userAId, request.userBId)
    }
}