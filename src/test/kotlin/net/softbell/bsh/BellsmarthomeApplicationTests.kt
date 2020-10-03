package net.softbell.bsh

import mu.KLogging
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

/**
 * @author : Bell(bell@softbell.net)
 * @description : Spring Boot Test 진입지점
 */
@SpringBootTest
class BellsmarthomeApplicationTests {
    @Test
    fun `context Loads`() {
        logger.info("Test Starting..")
        assertThat(true).isEqualTo(true) // 두 값이 다르면 Test 스테이지가 중단됨.
        logger.info("Test Complete!!")
    }

    companion object : KLogging()
}