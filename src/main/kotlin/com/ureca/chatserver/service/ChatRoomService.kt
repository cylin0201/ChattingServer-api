package com.ureca.chatserver.service

import com.ureca.chatserver.domain.RoomMembers
import com.ureca.chatserver.domain.Rooms
import com.ureca.chatserver.domain.Users
import com.ureca.chatserver.dto.RoomResponse
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatRoomService {
    fun createOneToOneRoom(userAId: Long, userBId: Long): RoomResponse {
        val (a, b) = if (userAId <= userBId) userAId to userBId else userBId to userAId

        return transaction {
            // 먼저 같은 두 멤버로 된 non-group 방이 있는지 찾는다.
            val existingRoomId = Rooms
                .slice(Rooms.id)
                .select { Rooms.isGroup eq false }
                .map { it[Rooms.id] }
                .firstOrNull { roomId ->
                    val members = RoomMembers
                        .select { RoomMembers.roomId eq roomId }
                        .map { it[RoomMembers.userId] }
                        .toSet()
                    members.size == 2 && members.containsAll(setOf(a, b))
                }

            if (existingRoomId != null) {
                val row = Rooms.select { Rooms.id eq existingRoomId }.single()
                RoomResponse(existingRoomId, row[Rooms.name], row[Rooms.isGroup])
            } else {
                val newId = Rooms.insert {
                    it[name] = "1to1-$a-$b"
                    it[isGroup] = false
                    it[createdAt] = LocalDateTime.now()
                } get Rooms.id

                // 회원 추가 (두명)
                RoomMembers.insert {
                    it[roomId] = newId
                    it[userId] = a
                    it[role] = "MEMBER"
                    it[joinedAt] = LocalDateTime.now()
                }
                RoomMembers.insert {
                    it[roomId] = newId
                    it[userId] = b
                    it[role] = "MEMBER"
                    it[joinedAt] = LocalDateTime.now()
                }

                RoomResponse(newId, "1to1-$a-$b", false)
            }
        }
    }
}