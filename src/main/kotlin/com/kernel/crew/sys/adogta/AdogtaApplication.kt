package com.kernel.crew.sys.adogta

import io.github.cdimascio.dotenv.dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AdogtaApplication

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

	System.setProperty("DOG_API_KEY", dotenv["DOG_API_KEY"])
	System.setProperty("CAT_API_KEY", dotenv["CAT_API_KEY"])

	System.setProperty("GOOGLE_MAPS_API_KEY", dotenv["GOOGLE_MAPS_API_KEY"])

	runApplication<AdogtaApplication>(*args)
}