package jauth.server;

import com.google.gson.Gson;
import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

import java.math.BigInteger;
import java.net.HttpCookie;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Jauth implements HttpHandler
{
	public static final Executor workers = Executors.newFixedThreadPool(2);
	public static final Map<String, Object> sessions = Collections.synchronizedMap(new HashMap<String, Object>());
	public static final Gson json = new Gson();

	@Override
	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception
	{
		if(request.cookieValue("ssssid") == null || !sessions.containsKey(request.cookieValue("ssssid")))
		{
			String ssssid = new BigInteger(130, new SecureRandom()).toString(32);
			sessions.put(ssssid, new Object());
			HttpCookie c = new HttpCookie("ssssid", ssssid);
			c.setMaxAge(100500);
			response.cookie(c);
		}

		System.out.println("in jauth");
		System.out.println(request.cookieValue("ssssid"));
		System.out.println(request.body());

		control.nextHandler();
	}
}
