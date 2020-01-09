package net.softbell.bsh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 스프링 애플리케이션 진입지점
 * 		-- 개발 환경에서 H2 DB 사용방법
 * 		1. Run Configurations에서 Profile을 dev로 설정 (안해도 정상 동작)
 * 		2. 실행 - 미리 정의된 JPA Entity 정보에 의해 자동으로 테이블이 생성됩니다.
 * 		3. 마음껏 테스트
 * 		4. 종료 - H2 DB가 중단되면서 In memory에서 모든 테이블이 삭제됩니다.
 * 
 * 		H2 DB 콘솔: http://localhost:8080/h2-console
 * 		H2 DB는 애플리케이션이 실행 중에만 동작하고 중단되면 모든 데이터가 사라집니다. (개발 중 테스트용)
 * 		콘솔 페이지에서 JDBC URL: jdbc:h2:mem:testdb 로 설정하십시오. (in memory mode로 동작하므로 JDBC 주소를 설정해 주어야 접속 가능)
 * 
 * 		production 환경에서는 전용 DB와 연결해서 사용하므로 로컬에서는 어떠한 테스트를 해도 괜찮습니다.
 * 
 * 		!로컬에 있는 MySQL과 연결하려면 profile을 prod 로 설정한 후 환경변수에 DB 정보를 등록해야 합니다.
 */
@SpringBootApplication
public class BellsmarthomeApplication
{
	public static void main(String[] args)
	{	
		SpringApplication.run(BellsmarthomeApplication.class, args);
	}
}
