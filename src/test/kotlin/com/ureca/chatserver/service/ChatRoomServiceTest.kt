package com.ureca.chatserver.service

import com.ureca.chatserver.domain.Messages
import com.ureca.chatserver.domain.RoomMembers
import com.ureca.chatserver.domain.Rooms
import com.ureca.chatserver.domain.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ChatRoomServiceTest {

    private val chatRoomService = ChatRoomService()

    @BeforeEach
    fun setup() {
        // H2 메모리 DB 연결
        Database.connect(
            url = "jdbc:h2:mem:test_chat;DB_CLOSE_DELAY=-1;",
            driver = "org.h2.Driver",
            user = "sa",
            password = ""
        )

        // 테이블 초기화
        transaction {
            SchemaUtils.create(Users, Rooms, RoomMembers, Messages)

            // 테스트용 유저 초기화
            if (Users.selectAll().empty()) {
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
    }

    @Test
    fun createOneToOneRoomTest() {
        transaction {
            // user1과 user2로 1:1 채팅방 생성
            val roomResponse = chatRoomService.createOneToOneRoom(1, 2)

            // 생성된 방이 존재하는지 확인
            assertNotNull(roomResponse.roomId)
            assertEquals("1to1-1-2", roomResponse.name)
            assertEquals(false, roomResponse.isGroup)

            // RoomMembers에 두 명이 추가되었는지 확인
            val members = RoomMembers.select { RoomMembers.roomId eq roomResponse.roomId }.map { it[RoomMembers.userId] }
            assertEquals(setOf(1L, 2L), members.toSet())
        }
    }
}