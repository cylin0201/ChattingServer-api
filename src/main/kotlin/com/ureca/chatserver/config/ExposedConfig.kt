package com.ureca.chatserver.config

import com.ureca.chatserver.domain.Messages
import com.ureca.chatserver.domain.RoomMembers
import com.ureca.chatserver.domain.Rooms
import com.ureca.chatserver.domain.Users
import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
class ExposedConfig {
    @PostConstruct

    fun init() {
        Database.connect(
            url = "jdbc:h2:mem:chat;MODE=LEGACY;DB_CLOSE_DELAY=-1;",
            driver = "org.h2.Driver",
            user = "sa",
            password = ""
        )

        transaction {
            SchemaUtils.create(Users, Rooms, RoomMembers, Messages)

            if (Users.selectAll().empty()) {
                Users.insert {
                    it[username] = "user1"
                    it[createdAt] = LocalDateTime.now()
                }
                Users.insert {
                    it[username] = "user2"
                    it[createdAt] = LocalDateTime.now()
                }
                Users.insert {
                    it[username] = "user3"
                    it[createdAt] = LocalDateTime.now()
                }
            }
        }
    }
}