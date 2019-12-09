package net.softbell.bsh;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.softbell.bsh.libs.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : Spring Boot Test 진입지점
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BellsmarthomeApplicationTests {
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void contextLoads() throws Exception {
		G_Logger.info(BellLog.getLogHead() + "Test Starting..");
		assertEquals(true, true); // 두 값이 다르면 Test 스테이지가 중단됨.
		G_Logger.info(BellLog.getLogHead() + "Test Complete!!");
	}

}