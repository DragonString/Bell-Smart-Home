import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
	id("org.springframework.boot") version "2.3.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	kotlin("plugin.jpa") version "1.3.72"
}

group = "net.softbell"
version = "1.0.0-ALPHA"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Laungage
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// DB
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.3.4.RELEASE") // JPA
	implementation("org.springframework.boot:spring-boot-starter-jdbc:2.3.4.RELEASE") // JDBC
	implementation("mysql:mysql-connector-java:8.0.21") // MySQL Connector
	implementation("com.h2database:h2:1.4.200") // H2
	implementation("org.hibernate:hibernate-search-orm:5.11.5.Final") // JPA Search Engine

	// Security
	implementation("org.springframework.boot:spring-boot-starter-security:2.3.4.RELEASE") // Security
	testImplementation("org.springframework.security:spring-security-test:5.4.0") // Security Test
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5:3.0.4.RELEASE") // Thymeleaf Spring Security 5
	implementation("io.jsonwebtoken:jjwt:0.9.1") // JWT
	implementation("org.springframework.security:spring-security-messaging:5.4.0") // Message Security

	// View
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf:2.3.4.RELEASE") // Thymeleaf
	implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:2.5.1") // Thymeleaf Layout

	// API
	implementation("io.springfox:springfox-swagger2:2.6.1") // Swagger
	implementation("io.springfox:springfox-swagger-ui:2.6.1") // Swagger UI

	// Server
	implementation("org.springframework.boot:spring-boot-starter-web:2.3.4.RELEASE") // Web (tomcat)

	// Websocket
	implementation("org.springframework.boot:spring-boot-starter-websocket:2.3.4.RELEASE") // Websocket

	// Library
	implementation("io.github.microutils:kotlin-logging:2.0.3") // Kotlin Logging
	developmentOnly("org.springframework.boot:spring-boot-devtools:2.3.4.RELEASE") // Devtools
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.3.4.RELEASE") {
//		exclude(group = "org.junit.vintage", module = "junit-vintage-engine") // Test
	}
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2") // Jackson
	implementation("com.google.code.gson:gson-parent:2.8.6") // Gson
}

tasks.withType<BootRun> {
	environment("SPRING_PROFILES_ACTIVE", "dev")
}

tasks.withType<Test> {
	useJUnitPlatform()

	testLogging {
		// test jvm의 standard out and standard error을 console에 출력한다.
		showStandardStreams = true // standard out, standard error 를 로깅
		showCauses = true // showException가 true여야만 활성화, 테스트 수행 시 exception이 발생 했을 때 causes 정보 출력
		showExceptions = true // 테스트 수행 시 exception이 발생 했을 때 exception정보를 로깅한다. 보통 "Failed" 이벤트 발생 시 수행
		showStackTraces = true // 테스트 수행 시 exception이 발생 했을 때 showStackTraces정보 출력
		exceptionFormat = TestExceptionFormat.FULL // showStackTrace가 true 여야만 활성화, 로깅하려는 test exception 포맷
		//displayGranularity = 2 // 로그로 기록되는 이벤트의 표시 단위. 0-2
		events ("FAILED", "PASSED", "SKIPPED", "STANDARD_ERROR", "STANDARD_OUT", "STARTED") // 로깅될 이벤트
	}

	environment("SPRING_PROFILES_ACTIVE", "test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
