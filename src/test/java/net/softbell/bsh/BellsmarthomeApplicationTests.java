package net.softbell.bsh;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : Spring Boot Test 진입지점
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BellsmarthomeApplicationTests
{
	// Global Field
	@Test
	public void contextLoads() throws Exception
	{
		log.info(BellLog.getLogHead() + "Test Starting..");
		assertEquals(true, true); // 두 값이 다르면 Test 스테이지가 중단됨.
		log.info(BellLog.getLogHead() + "Test Complete!!");
	}
}