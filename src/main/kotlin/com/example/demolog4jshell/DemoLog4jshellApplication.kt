package com.example.demolog4jshell

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.naming.Context
import javax.naming.InitialContext
import javax.naming.spi.InitialContextFactory

@SpringBootApplication
class DemoLog4jshellApplication

fun main(args: Array<String>) {
	runApplication<DemoLog4jshellApplication>(*args)
}

@RestController
@RequestMapping("/demo/log4jshell")
class DemoController {

	companion object {
		val log = LoggerFactory.getLogger(DemoController::class.java)
	}

	@GetMapping("/index")
	fun index(): String {
		return "Hello Log4JShell..."
	}

	@PostMapping("/index")
	fun index(@RequestParam content: String): String {
		// print form post from input name=content
		log.info(content)

		/* How to configure JNDI */
		/*
		   1. use Properties class
		val props = Properties().apply {
			put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory")
			put("jboss.naming.client.ejb.context", true)
			put(Context.PROVIDER_URL, "http-remoting://localhost:8080")
			put(Context.SECURITY_PRINCIPAL, "")
			put(Context.SECURITY_CREDENTIALS, "")
		}
		val ctx = InitialContext(props)
		 */

		/*
   		   2. use jndi.properties in classpath
		 */
		var ctx = InitialContext()

		// remote jndi can be lookup by using relative name only
		val relativeName = ctx.lookup("a") // work
		// val fullName = ctx.lookup("jndi:java:a") // not work
		log.info("call by relativeName $relativeName")

		return "Hello Log4JShell..."
	}
}
