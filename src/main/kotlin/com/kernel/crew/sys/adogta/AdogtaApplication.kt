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

	runApplication<AdogtaApplication>(*args)
}
