package com.kernel.crew.sys.adogta

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class AdogtaStartupLogger : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(AdogtaStartupLogger::class.java)

    override fun run(vararg args: String) {
        logger.info("Iniciando Adogta")
    }
}