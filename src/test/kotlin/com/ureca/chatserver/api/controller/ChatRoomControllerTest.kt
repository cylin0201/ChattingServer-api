package com.ureca.chatserver.api.controller

import com.ureca.chatserver.domain.Users
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ChatRoomControllerTest {

    @Test
    fun createRoom() {
        transaction {
            Users.insert {
                it[username] = "user1"
                it[createdAt] = LocalDateTime.now()
            }
            Users.insert {
                it[username] = "user2"
                it[createdAt] = LocalDateTime.now()
            }
        }

    }

    @Test
    fun getChatRoomService() {
    }

}