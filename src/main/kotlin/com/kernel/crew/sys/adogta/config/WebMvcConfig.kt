package com.kernel.crew.sys.adogta.config

import jakarta.servlet.MultipartConfigElement
import org.springframework.boot.servlet.MultipartConfigFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.unit.DataSize
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.file.Paths

@Configuration
class WebMvcConfig : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        val uploadsLocation = "file:" + Paths.get("uploads").toAbsolutePath().toString() + "/"
        registry.addResourceHandler("/uploads/**")
            .addResourceLocations(uploadsLocation)
    }

    @Bean
    fun multipartConfigElement(): MultipartConfigElement {
        val factory = MultipartConfigFactory()
        factory.setMaxFileSize(DataSize.ofMegabytes(10))
        factory.setMaxRequestSize(DataSize.ofMegabytes(10))
        return factory.createMultipartConfig()
    }
}
