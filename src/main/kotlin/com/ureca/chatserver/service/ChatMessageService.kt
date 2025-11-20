package com.ureca.chatserver.service

import com.ureca.chatserver.domain.Messages
import com.ureca.chatserver.dto.ChatMessage
import com.ureca.chatserver.dto.MessageResponse
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatMessageService(
    private val template: SimpMessagingTemplate
) {
    fun saveAndBroadcast(msg: ChatMessage): MessageResponse {
        val id = transaction {
            Messages.insert {
                it[roomId] = msg.roomId
                it[senderId] = msg.senderId
                it[content] = msg.content
                it[createdAt] = LocalDateTime.now()
            } get Messages.id
        }

        val response = MessageResponse(
            id = id,
            roomId = msg.roomId,
            senderId = msg.senderId,
            content = msg.content,
            createdAt = LocalDateTime.now()
        )

        // room-specific topic으로 전송
        template.convertAndSend("/topic/room.${msg.roomId}", response)
        return response
    }

    fun listMessages(roomId: Long, limit: Int = 50): List<MessageResponse> =
        transaction {
            Messages.select { Messages.roomId eq roomId }
                .orderBy(Messages.id, SortOrder.DESC)
                .limit(limit)
                .map {
                    MessageResponse(
                        id = it[Messages.id],
                        roomId = it[Messages.roomId],
                        senderId = it[Messages.senderId],
                        content = it[Messages.content],
                        createdAt = it[Messages.createdAt]
                    )
                }
        }
}