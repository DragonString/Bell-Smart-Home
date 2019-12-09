package net.softbell.bsh.libs;

public class BellLog {
	public static String getLogHead()
	{
		return "[" + Thread.currentThread().getStackTrace()[2].getMethodName() + "] ";
	}
}
