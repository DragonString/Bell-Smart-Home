package net.softbell.bsh.service;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import net.softbell.bsh.handler.ServiceHandler;

/**
 * The type Netty server.
 */
@Component
@PropertySource(value = "classpath:/application.properties")
public class NettyServer {
	// Global Field
    @Value("${tcp.port}")
    private int tcpPort; // The Tcp port.
 
    @Value("${boss.thread.count}")
    private int bossCount; // The Boss count.
 
    @Value("${worker.thread.count}")
    private int workerCount; // The Worker count.
    
    private static final ServiceHandler SERVICE_HANDLER = new ServiceHandler(); // The constant SERVICE_HANDLER.
    EventLoopGroup bossGroup = new NioEventLoopGroup(bossCount); // 클라이언트 연결을 수락하는 부모 스레드 그룹
    EventLoopGroup workerGroup = new NioEventLoopGroup(); // 연결된 클라이언트의 소켓으로 부터 데이터 입출력 및 이벤트를 담당하는 자식 스레드

    public void start() {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)                              //서버 소켓 입출력 모드를 NIO로 설정
                    .childHandler(new ChannelInitializer<SocketChannel>() {             //송수신 되는 데이터 가공 핸들러
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("frameDecoder", new LineBasedFrameDecoder(80));
                            pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(SERVICE_HANDLER);
                        }
                    });
 
            ChannelFuture channelFuture = b.bind(tcpPort).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }
    
    public void stop()
    {
    	bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
    
    @PreDestroy
    public void onDestroy() throws Exception {
    	stop();
    }
    
    public void broadcast(String msg)
    {
    	try {
			SERVICE_HANDLER.channelBroadcast(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}