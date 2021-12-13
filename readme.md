# Demo project to demonstrate log4jshell vulnerability

## Setup wildfly 25 for remote jndi

1. edit standalone.xml, only java:jboss/exported can be access through remote jndi
>        <subsystem xmlns="urn:jboss:domain:naming:2.0">
>            <bindings>
>                <simple name="java:jboss/exported/a" value="100" type="int" />
>            </bindings>
>            <remote-naming/>
>        </subsystem>

## log4jshell can be exploit either through 

1. log4j2.xml configuration
>       <Pattern>%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - $${jndi:java:a} %m%n</Pattern>

2. directly print input without sanitizing. send input name=content with value
> @PostMapping("/index")
> 
> fun index(@RequestParam content: String): String {
> 
>    log.info(content)
> 
> }

## How to test
send input name=content with value ${jndi:java:a}
> curl -X POST localhost:8081/demo/log4jshell/index -d 'content=${jndi:java:a}'

log should show as following, the first 100 from pattern, the second 100 from form input
> 2021-12-13 19:58:28 INFO  DemoController:35 - 100 100

## How to fix the vulnerability
override log4j2 version in properties tag in pom.xml

	<name>demo-log4jshell</name>
	<description>Demo project for Spring Boot</description>
	<properties>
		<java.version>1.8</java.version>
		<kotlin.version>1.6.0</kotlin.version>
		<log4j2.version>2.15.0</log4j2.version>
	</properties>