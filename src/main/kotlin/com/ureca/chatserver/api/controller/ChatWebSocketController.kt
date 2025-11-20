package com.ureca.chatserver.api.controller

import com.ureca.chatserver.dto.ChatMessage
import com.ureca.chatserver.service.MessageService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

@Controller
class ChatWebSocketController(
    private val template: SimpMessagingTemplate,
    private val messageService: MessageService) {

    @MessageMapping("/chat.send")
    fun receiveMessage(message: ChatMessage) {
        val saved = messageService.saveMessage(message)

        template.convertAndSend("/topic/room.${message.roomId}", saved)
    }
}