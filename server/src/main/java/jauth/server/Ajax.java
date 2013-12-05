package jauth.server;

import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

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
					Jauth.sessions.get(request.cookieValue("ssssid")).provider = request.postParam("prov");
					Jauth.sessions.get(request.cookieValue("ssssid")).token = request.postParam("tok");

					try
					{
						// There is also this
						// https://code.google.com/p/google-api-java-client/
						// https://developers.google.com/+/quickstart/java#step_1_enable_the_google_api
						URLConnection c = new URL(new URL("https://www.googleapis.com"),
							"/plus/v1/people/me?access_token=" + Jauth.sessions.get(request.cookieValue("ssssid")).token)
							.openConnection();

						BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
						StringBuffer response = new StringBuffer();
						String inputLine;
						while ((inputLine = in.readLine()) != null)
							response.append(inputLine);
						in.close();

						Gplus gp = Jauth.json.fromJson(response.toString(), Gplus.class);
						Jauth.sessions.get(request.cookieValue("ssssid")).name = gp.displayName;
					}
					catch(Exception e)
					{
						System.out.println(e);
						e.printStackTrace();
					}

					response.content(Jauth.sessions.get(request.cookieValue("ssssid")).name);

					break;

				case "getcredentials":
					if(Jauth.sessions.get(request.cookieValue("ssssid")).name != null)
						response.content("ok");
					else
						response.content("papers, please");

					break;

				case "postcomment":
					Comment c = new Comment();
					c.name = Jauth.sessions.get(request.cookieValue("ssssid")).name;
					c.comment = request.postParam("comm");
					c.provider = Jauth.sessions.get(request.cookieValue("ssssid")).provider;
					Jauth.comments.comments.add(c);

					response.content("ok");

					break;

				case "getcomments":
					response.content(Jauth.json.toJson(Jauth.comments, Comments.class));

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

	static class Gplus
	{
		String kind;
		String etag;
		String gender;
		String objectType;
		String id;
		String displayName;
	}
}
