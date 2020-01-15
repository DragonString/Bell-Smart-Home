package net.softbell.bsh.util;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 방울의 로그 규칙 라이브러리
 */
public class BellLog
{
	public static String getLogHead()
	{
		return "[" + Thread.currentThread().getStackTrace()[2].getMethodName() + "] ";
	}
}
