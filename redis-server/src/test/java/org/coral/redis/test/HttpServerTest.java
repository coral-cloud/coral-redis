package org.coral.redis.test;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * @author wuhao
 * @createTime 2021-07-02 15:03:00
 */
public class HttpServerTest {
	public static void main(String[] arg) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
		server.createContext("/test", new TestHandler());
		server.start();
	}

	static class TestHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String response = "hello world";
			exchange.sendResponseHeaders(200, 0);
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}
