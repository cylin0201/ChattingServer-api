package com.ureca.chatserver.service

import com.ureca.chatserver.domain.Messages
import com.ureca.chatserver.dto.ChatMessage
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MessageService {
    fun saveMessage(msg: ChatMessage): ChatMessage {
        val savedAt = LocalDateTime.now()

        transaction {
            Messages.insert {
                it[roomId] = msg.roomId
                it[senderId] = msg.senderId
                it[content] = msg.content
                it[createdAt] = savedAt
            }
        }

        return msg.copy(createdAt = savedAt)
    }

    fun getMessages(roomId: Long): List<ChatMessage> {
        return transaction {
            Messages.select { Messages.roomId eq  roomId }
                .orderBy(Messages.createdAt to SortOrder.DESC)
                .map {
                    ChatMessage(
                        roomId = it[Messages.roomId],
                        senderId = it[Messages.senderId],
                        content = it[Messages.content],
                        createdAt = it[Messages.createdAt]
                    )
                }
        }
    }

}