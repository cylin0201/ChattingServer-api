package com.ureca.chatserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class ChatserverApplication

fun main(args: Array<String>) {
	runApplication<ChatserverApplication>(*args)
}
