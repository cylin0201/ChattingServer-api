package com.ureca.chatserver.api.controller

import com.ureca.chatserver.dto.ChatMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class ChatController {
    @MessageMapping("/chat.send") // 클라이언트에서 메시지 전달
    @SendTo("/topic/public")
    fun sendMessage(message: ChatMessage) : ChatMessage{
        //DB 저장 로직
        println(message)
        return message
    }
}