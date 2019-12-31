package net.softbell.bsh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.softbell.bsh.libs.BellLog;

@Service
public class IotService {
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	NettyServer nettyServer;
	
	public void test()
	{
		nettyServer.broadcast("IoT Center Test MSG ");
	}
	
	public void broadcast(String strData)
	{
		nettyServer.broadcast(strData);
	}
	
	public boolean procLEDs(String strMode, String strValue)
	{
		// Log
		G_Logger.info(BellLog.getLogHead() + "LED Update Process Start");
		
		// Field
		boolean isSuccess = false;
		
		// Process
		if (strMode.equalsIgnoreCase("circuit"))
			isSuccess = procLED_Circuit(strValue);
		else if (strMode.equalsIgnoreCase("bar"))
			isSuccess = procLED_Bar(strValue);
		
		G_Logger.info(BellLog.getLogHead() + "LED Update Process Finish");
		
		// Finish
		return isSuccess;
	}
	
	private boolean procLED_Circuit(String strValue)
	{
		if (strValue.equalsIgnoreCase("on"))
			nettyServer.broadcast("NODE#SET#VALUE#ITEM#1#1");
		else if (strValue.equalsIgnoreCase("off"))
			nettyServer.broadcast("NODE#SET#VALUE#ITEM#1#0");
		else
			return false;
		
		return true;
	}
	
	private boolean procLED_Bar(String strValue)
	{
		// Field
		String strMode;
		
		// Init
		if (strValue.equalsIgnoreCase("off"))
			strMode = "0";
		else if (strValue.equalsIgnoreCase("red"))
			strMode = "R";
		else if (strValue.equalsIgnoreCase("green"))
			strMode = "G";
		else if (strValue.equalsIgnoreCase("blue"))
			strMode = "B";
		else if (strValue.equalsIgnoreCase("white"))
			strMode = "W";
		else
			return false;
		
		// Process
		nettyServer.broadcast("NODE#SET#VALUE#ITEM#2#" + strMode);
		
		// Finish
		return true;
	}
}
