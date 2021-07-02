package org.coral.redis.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.redis.RedisArrayAggregator;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;
import io.netty.handler.codec.redis.RedisDecoder;
import io.netty.handler.codec.redis.RedisEncoder;

/**
 * (1)、 初始化用于Acceptor的主"线程池"以及用于I/O工作的从"线程池"；
 * (2)、 初始化ServerBootstrap实例， 此实例是netty服务端应用开发的入口；
 * (3)、 通过ServerBootstrap的group方法，设置（1）中初始化的主从"线程池"；
 * (4)、 指定通道channel的类型，由于是服务端，故而是NioServerSocketChannel；
 * (5)、 设置ServerSocketChannel的处理器
 * (6)、 设置子通道也就是SocketChannel的处理器， 其内部是实际业务开发的"主战场"
 * (8)、 配置子通道也就是SocketChannel的选项
 * (9)、 绑定并侦听某个端口
 *
 * @author wuhao
 * @createTime 2021-06-15 09:40:00
 */

public class RedisServer {

	public void bind(int port) throws Exception {

		// 服务器端应用程序使用两个NioEventLoopGroup创建两个EventLoop的组，EventLoop这个相当于一个处理线程，是Netty接收请求和处理IO请求的线程。
		// 主线程组, 用于接受客户端的连接，但是不做任何处理，跟老板一样，不做事
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// 从线程组, 当boss接受连接并注册被接受的连接到worker时，处理被接受连接的流量。
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			//netty服务器启动类的创建, 辅助工具类，用于服务器通道的一系列配置
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {  // 子处理器，用于处理workerGroup
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							ChannelPipeline p = socketChannel.pipeline();
							p.addLast(new RedisDecoder());
							p.addLast(new RedisBulkStringAggregator());
							p.addLast(new RedisArrayAggregator());
							p.addLast(new RedisEncoder());
							p.addLast(new RedisServerHandler());

						}
					});

			// 启动server，绑定端口，开始接收进来的连接，设置8088为启动的端口号，同时启动方式为同步
			ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

			System.out.println("redis server started " + port);
			// 监听关闭的channel，等待服务器 socket 关闭 。设置位同步方式
			channelFuture.channel().closeFuture().sync();
		} finally {
			//退出线程组
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}