package netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/16
 */
public class MyNettyTest {

    @Test
    public void loopExecutor() {
        NioEventLoopGroup selector = new NioEventLoopGroup(1);

        selector.execute(() -> {
            for(;;) {
                System.out.println("hello world");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        LockSupport.park();
    }


    /**
     * 使用该命令启动 server :  nc -l 192.168.8.101 9090
     * @throws InterruptedException
     */
    @Test
    public void clientMode() throws InterruptedException {
        NioEventLoopGroup thread = new NioEventLoopGroup(1);
        NioSocketChannel client = new NioSocketChannel();
        thread.register(client);

        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(new MyClientHandler());

        ChannelFuture connectFuture = client.connect(new InetSocketAddress("192.168.8.101", 9090));
        ByteBuf byteBuf = Unpooled.copiedBuffer("grasswort".getBytes());
        ChannelFuture sync = connectFuture.sync();

        ChannelFuture sendFuture = client.writeAndFlush(byteBuf);
        sendFuture.sync();

        sync.channel().closeFuture().sync();
        System.out.println("client over ..");
    }


    /**
     * 使用该命令启动 client :  nc 192.168.8.101 9090
     * @throws InterruptedException
     */
    @Test
    public void serverMode() throws InterruptedException {
        NioEventLoopGroup thread = new NioEventLoopGroup(1);
        NioServerSocketChannel server = new NioServerSocketChannel();
        thread.register(server);

        ChannelPipeline pipeline = server.pipeline();
        pipeline.addLast(new MyAcceptHandler(thread, new MyClientHandler()));

        ChannelFuture bind = server.bind(new InetSocketAddress("192.168.8.101", 9090));
        bind.sync().channel().closeFuture().sync();
    }

    /**
     * netty client
     * @throws InterruptedException
     */
    @Test
    public void nettyClient() throws InterruptedException {
        NioEventLoopGroup loop = new NioEventLoopGroup(1);

        Bootstrap bs = new Bootstrap();
        ChannelFuture connectFuture = bs.group(loop)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new MyClientHandler());
                    }
                })
                .connect(new InetSocketAddress("192.168.8.101", 9090));

        ByteBuf byteBuf = Unpooled.copiedBuffer("grasswort".getBytes());
        Channel client = connectFuture.sync().channel();

        ChannelFuture sendFuture = client.writeAndFlush(byteBuf);
        sendFuture.sync();

        client.closeFuture().sync();
    }

    /**
     * netty server
     * @throws InterruptedException
     */
    @Test
    public void nettyServer() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        ServerBootstrap bs = new ServerBootstrap();
        ChannelFuture bind = bs.group(group, group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new MyClientHandler());
                    }
                })
                .bind(new InetSocketAddress("192.168.8.101", 9090));
        bind.sync().channel().closeFuture().sync();
    }


    @ChannelHandler.Sharable // 不标这个注解,不能共享(多个 SocketChannel 不能绑定同一个 handler)
    class MyClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channel registered .");
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channel actived .");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            CharSequence str = buf.getCharSequence(0, buf.readableBytes(), StandardCharsets.UTF_8);
            System.out.println(str);
            ctx.writeAndFlush(buf);
        }
    }

    class MyAcceptHandler extends ChannelInboundHandlerAdapter {

        private final NioEventLoopGroup selector;

        private final ChannelHandler handler;

        MyAcceptHandler(NioEventLoopGroup selector, ChannelHandler handler) {
            this.selector = selector;
            this.handler = handler;
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channel registed .");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            SocketChannel client = (SocketChannel) msg;
            selector.register(client);
            ChannelPipeline pipeline = client.pipeline();
            pipeline.addLast(handler);
        }
    }
}
