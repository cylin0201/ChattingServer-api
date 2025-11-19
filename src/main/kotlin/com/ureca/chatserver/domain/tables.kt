package com.ureca.chatserver.domain

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Users : Table("users") {
    val id = long("id").autoIncrement()
    val username = varchar("username", 100).uniqueIndex()
    val createdAt = datetime("created_at")

    override val primaryKey = PrimaryKey(id)
}

object Rooms : Table("chat_rooms") {
    val id = long("id").autoIncrement()
    val name = varchar("room_name", 255)

    override val primaryKey = PrimaryKey(id)
}

object RoomMembers : Table("room_members") {
    val id = long("id").autoIncrement()
    val roomId = long("room_id").references(Rooms.id)
    val userId = long("user_id").references(Users.id)
    val role = varchar("role", 20)
    val joinedAt = datetime("joined_at")

    override val primaryKey = PrimaryKey(id)
}

object Messages : Table("messages") {
    val id = long("id").autoIncrement()
    val roomId = long("room_id").references(Rooms.id)
    val senderId = long("sender_id").references(Users.id)
    val content = text("content")
    val createdAt = datetime("created_at")

    override val primaryKey = PrimaryKey(id)
}
