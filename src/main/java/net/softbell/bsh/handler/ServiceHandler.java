package net.softbell.bsh.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.softbell.bsh.libs.BellLog;

/**
 * The type Service handler.
 */
@ChannelHandler.Sharable
public class ServiceHandler extends ChannelInboundHandlerAdapter {
	// Static Field
	private static final Logger logger = LoggerFactory.getLogger(ServiceHandler.class);
	private static final char _STA = '@';
	private static final char _END = '%';
	
	//DataProcessor dp = new DataProcessor();
    Map<Integer, ChannelId> channelMap = new HashMap<Integer, ChannelId>();
    int cntChannel = 0;
 
    /**
     * The Channels.
     */
    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private ChannelId node;
 
    public static <K, V> K getKey(Map<K, V> map, V value) {
        // 찾을 hashmap 과 주어진 단서 value
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }
    
    /**
     * Channel active.
     *
     * @param ctx the ctx
     * @throws Exception the exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	sendNode(ctx, Integer.toString(cntChannel) + "번님 서버에 접속하신것을 환영함");
        channelMap.put(cntChannel++, ctx.channel().id());
        channels.add(ctx.channel());
        logger.info(BellLog.getLogHead() + "channel active id : " + ctx.channel().id());
        
        sendNode(ctx, procSendData("NODE", "GET", "INFO", "CHIPID"));
        sendNode(ctx, procSendData("NODE", "GET", "INFO", "ITEMS"));
        sendNode(ctx, procSendData("NODE", "GET", "MODE", "NODE"));
    }
    
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    	logger.info(BellLog.getLogHead() + "handlrRemoved Method Call : " + ctx.channel().id());
    	channels.remove(ctx.channel());
    	channelMap.remove(getKey(channelMap, ctx.channel().id()));
    }
 
    /**
     * Channel read.
     *
     * @param ctx the ctx
     * @param msg the msg
     * @throws Exception the exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	try {
    		logger.info(BellLog.getLogHead() + getKey(channelMap, ctx.channel().id()) + "번 클라이언트에게 수신된 데이터 : {" + msg.toString() + "}");
    		if (!procData(ctx, msg.toString().split(Character.toString(_STA))[1].split(Character.toString(_END))[0]))
    		{
    			String[] arrData = msg.toString().split("-");
    			if (arrData[0].equals("SEND"))
    			{
    				sendNode(channels.find(channelMap.get(Integer.parseInt(arrData[1]))).id(), arrData[2]);
    				logger.info(BellLog.getLogHead() + arrData[1] + "번 클라이언트(" + channelMap.get(Integer.parseInt(arrData[1])) + ")에게 데이터 전송 : " + arrData[2]);
    			}
    			else
    			{
    				logger.error(BellLog.getLogHead() + "처리 못하는 데이터임 : " + msg.toString());
    				sendNode(ctx, "처리 못하는 데이터 : " + msg.toString());
    			}
    		}
    	}
    	catch (Exception ex)
    	{
    		logger.error(BellLog.getLogHead() + "무슨 에러가 난거지?");
    	}
    	finally
    	{
    		ReferenceCountUtil.release(msg);
    	}
    }
    
    //@Obsolete
    public void channelBroadcast(String msg) throws Exception
    {
    	channels.writeAndFlush(_STA + msg + _END);
    }
    
    public void sendNode(ChannelId id, String msg)
    {
    	channels.find(id).writeAndFlush(_STA + msg + _END);
    }
    
    public void sendNode(ChannelHandlerContext ctx, String msg)
    {
    	ctx.writeAndFlush(_STA + msg + _END);
    }
    
    public String procSendData(String...args)
    {
    	StringBuilder sb = new StringBuilder();
    	
    	for (int i = 0; i < args.length; i++)
    	{
    		sb.append(args[i]);
    		if (i < args.length - 1)
    			sb.append("#");
    	}
    	
    	return sb.toString();
    }
    
    public boolean procData(ChannelHandlerContext ctx, String data)
	{
    	// true 반환시 이 메소드에서 처리 가능한 데이터
    	// false 반환시 이 메소드에서 처리 불가능한 데이터
		// Field
		String[] arrData = data.split("#");
		
		// Proc
		try {
			if (arrData[0].equals("SERVER"))
				switch(arrData[1])
				{
					case "SET":
						return procSET(ctx, arrData);
						
					case "SEND":
						return procSEND(arrData);
						
					case "EXIT":
						ctx.disconnect();
						break;
						
					case "THIS":
						node = ctx.channel().id();
						logger.info(BellLog.getLogHead() + node + "번 노드 설정 완료");
						break;
						
					case "ON":
						sendNode(node, procSendData("NODE", "SET", "VALUE", "ITEM", "1", "1"));
						logger.info(BellLog.getLogHead() + node + " LED ON");
						break;
						
					case "OFF":
						sendNode(node, procSendData("NODE", "SET", "VALUE", "ITEM", "1", "0"));
						logger.info(BellLog.getLogHead() + node + " LED OFF");
						break;
						
					case "LED_OFF":
						sendNode(node, procSendData("NODE", "SET", "VALUE", "ITEM", "2", "0"));
						logger.info(BellLog.getLogHead() + node + " Large LED OFF");
						break;
						
					case "LED_RED":
						sendNode(node, procSendData("NODE", "SET", "VALUE", "ITEM", "2", "R"));
						logger.info(BellLog.getLogHead() + node + " Large LED RED");
						break;
						
					case "LED_GREEN":
						sendNode(node, procSendData("NODE", "SET", "VALUE", "ITEM", "2", "G"));
						logger.info(BellLog.getLogHead() + node + " Large LED GREEN");
						break;
						
					case "LED_BLUE":
						sendNode(node, procSendData("NODE", "SET", "VALUE", "ITEM", "2", "B"));
						logger.info(BellLog.getLogHead() + node + " Large LED BLUE");
						break;
						
					case "LED_WHITE":
						sendNode(node, procSendData("NODE", "SET", "VALUE", "ITEM", "2", "W"));
						logger.info(BellLog.getLogHead() + node + " Large LED WHITE");
						break;
					
					case "REBOOT":
						sendNode(node, procSendData("NODE", "ACT", "SYS", "REBOOT"));
						logger.info(BellLog.getLogHead() + node + " System Reboot");
						break;
						
					case "LIST":
						logger.info(BellLog.getLogHead() + "[Channel List]");
						for (Channel ch : channels)
							logger.info(BellLog.getLogHead() + ch.id().toString());
						break;
						
					default:
						logger.error(BellLog.getLogHead() + "비정상 데이터 수신 (SERVER ?) : " + data);
						return false;
				}
			else
				return false;
		}
		catch (Exception ex)
		{
			logger.error(BellLog.getLogHead() + "비정상 데이터 수신" + data);
			return false;
		}
		
		return true;
	}
	
    //@Obsolete
	private boolean procSEND(String[] arrData)
	{
		String strReturn = "";
		for (int i = 2; i < arrData.length; i++)
			strReturn += arrData[i] + " ";
		sendNode(node, strReturn);
		logger.info(BellLog.getLogHead() + "반환 데이터 : " + strReturn);
		return true;
	}

	private boolean procSET(ChannelHandlerContext ctx, String[] arrData)
	{
		try {
			switch (arrData[2])
			{
				case "INFO":
					switch (arrData[3])
					{
						case "CHIPID":
							logger.info(BellLog.getLogHead() + "CHIPID INFO SET : " + arrData[4]);
							// DB에 칩 ID 검색해서 해시맵 키와 DB에 등록된 id와 일치시키는 코드
							break;
							
						case "ITEMS":
							logger.info(BellLog.getLogHead() + "ITEMS INFO SET : " + arrData[4]);
							String[] arrItems = arrData[4].split("-");
							for (int i = 0; i < arrItems.length; i++)
								sendNode(ctx, procSendData("NODE", "GET", "INFO", "ITEM", arrItems[i]));
							// DB에 아이템이 등록되어있지 않으면 리스트 1차 등록
							
							break;
							
						case "ITEM":
							logger.info(BellLog.getLogHead() + "ITEM INFO SET : " + arrData[4] + " / " + arrData[5] + " / " + arrData[6]);
							// DB에 아이템 상세정보가 등록되어있지 않으면 등록
							break;
							
						default:
							logger.error(BellLog.getLogHead() + "비정상 데이터 수신 (SERVER SET INFO ?) : " + arrData[3]);
							return false;
					}
					break;
					
				case "ITEM":
					logger.info(BellLog.getLogHead() + "ITEM VALUE SET : " + arrData[3] + " / " + arrData[4]);
					// 아이템 값 DB 등록 코드
					break;
					
				case "MODE":
					switch (arrData[3])
					{
						case "ITEM":
							logger.info(BellLog.getLogHead() + "ITEM MODE SET : " + arrData[4] + " / " + arrData[5]);
							// 아이템 모드 값 DB 등록 코드
							break;
							
						case "NODE":
							logger.info(BellLog.getLogHead() + "NODE MODE SET : " + arrData[4]);
							// 노드 모드 값 DB 등록 코드
							break;
						
						default:
							logger.error(BellLog.getLogHead() + "비정상 데이터 수신 (SERVER SET MODE ?");
							return false;
					}
					break;
					
				default:
					logger.error(BellLog.getLogHead() + "비정상 데이터 수신 (SERVER SET ?) : " + arrData[2]);
					return false;
			}
			
			return true;
		}
		catch (Exception ex)
		{
			logger.error(BellLog.getLogHead() + "비정상 데이터 수신 (분석 불가) : " + arrData.toString());
			return false;
		}
	}
}