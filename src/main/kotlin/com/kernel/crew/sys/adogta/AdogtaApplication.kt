package com.kernel.crew.sys.adogta

import io.github.cdimascio.dotenv.dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class AdogtaApplication {

	@Bean
	fun corsConfigurer(): WebMvcConfigurer {
		return object : WebMvcConfigurer {
			override fun addCorsMappings(registry: CorsRegistry) {
				registry.addMapping("/**")
					.allowedOrigins("http://localhost:5173")  // Frontend
					.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
					.allowedHeaders("*")
					.allowCredentials(true)
			}
		}
	}
}

fun main(args: Array<String>) {

	val dotenv = dotenv()

	System.setProperty("URL_DB", dotenv["URL_DB"])
	System.setProperty("USER_DB", dotenv["USER_DB"])
	System.setProperty("PASSWORD_DB", dotenv["PASSWORD_DB"])

	runApplication<AdogtaApplication>(*args)
}