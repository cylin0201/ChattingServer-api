package com.ureca.chatserver.config

import com.ureca.chatserver.domain.Messages
import com.ureca.chatserver.domain.RoomMembers
import com.ureca.chatserver.domain.Rooms
import com.ureca.chatserver.domain.Users
import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Configuration

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
        }
    }
}