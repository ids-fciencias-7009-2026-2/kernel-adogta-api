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
					.allowedOrigins("http://localhost:5432")  // Frontend
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

	System.setProperty("MAIL_HOST", dotenv["MAIL_HOST"])
    System.setProperty("MAIL_PORT", dotenv["MAIL_PORT"])
    System.setProperty("MAIL_USERNAME", dotenv["MAIL_USERNAME"])
    System.setProperty("MAIL_PASSWORD", dotenv["MAIL_PASSWORD"])
    System.setProperty("APP_BASE_URL", dotenv["APP_BASE_URL"])


	runApplication<AdogtaApplication>(*args)
}