# My Chatting Server Example

## 프로젝트 개요
간단한 채팅 서버를 Kotlin, Spring Boot, H2 DB, Exposed ORM을 활용하여 구현한 예제 프로젝트입니다.  
기본적으로 채팅방, 유저, 메시지 관리 기능을 포함하고 있으며, 테스트는 Kotest를 활용합니다.

---

## 기술 스택

### 언어
- Kotlin 1.9.25
- Java 21 (JVM)

### 프레임워크 / 라이브러리
- Spring Boot 3.5.7
    - spring-boot-starter-web
    - spring-boot-starter-websocket
    - spring-boot-starter-json
- Exposed 0.51.1 (Kotlin ORM)
    - exposed-core
    - exposed-dao
    - exposed-jdbc
    - exposed-java-time (datetime 지원)
- H2 Database 2.2.224 (인메모리 DB)
- Jackson Kotlin module (JSON 직렬화/역직렬화)

### 테스트
- Kotest 5.9.1
    - kotest-runner-junit5
    - kotest-assertions-core
- MockK 1.13.10
- Spring Boot Starter Test

---

## 프로젝트 구조
