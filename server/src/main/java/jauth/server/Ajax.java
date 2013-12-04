package jauth.server;

import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

public class Ajax implements HttpHandler
{
	@Override
	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception
	{
		System.out.println("in ajax");
		System.out.println(request.cookieValue("ssssid"));
		System.out.println(request.body());
		System.out.println("a=" + request.postParam("a"));

		Jauth.workers.execute(new Worker(request, response, control));
	}

	static class Worker implements Runnable
	{
		HttpRequest request;
		HttpResponse response;
		HttpControl control;

		public Worker(HttpRequest request, HttpResponse response, HttpControl control)
		{
			this.request = request;
			this.response = response;
			this.control = control;
		}

		@Override
		public void run()
		{
			switch(request.postParam("a"))
			{
				case "loggedin":
					response.content("Good for you!");
					break;
			}

			control.execute(new Result(request, response, control));
		}
	}

	static class Result implements Runnable
	{
		HttpRequest request;
		HttpResponse response;
		HttpControl control;

		public Result(HttpRequest request, HttpResponse response, HttpControl control)
		{
			this.request = request;
			this.response = response;
			this.control = control;
		}

		@Override
		public void run()
		{
			response.end();
		}
	}
}
