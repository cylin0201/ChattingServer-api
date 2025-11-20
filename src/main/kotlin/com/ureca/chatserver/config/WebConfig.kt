package com.ureca.chatserver.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*") // 모든 origin 허용 (개발용)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    }
}