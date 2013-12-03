package jauth.server;

import org.webbitserver.WebServer;
import org.webbitserver.handler.StaticFileHandler;
import org.webbitserver.handler.logging.LoggingHandler;
import org.webbitserver.handler.logging.SimpleLogSink;

import static org.webbitserver.WebServers.createWebServer;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		WebServer webServer = createWebServer(80)
		 .add(new LoggingHandler(new SimpleLogSink("")))
		 .add(new StaticFileHandler("./server/war"))
		 .add(new StaticFileHandler("./out/client/dev/war"))
		 .start().get();

		System.out.println("Running on: " + webServer.getUri());
	}
}
