package com.jauth.client;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Properties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.jauth.client.oauth2.AuthRequest;
import com.jauth.client.oauth2.Callback;

public enum Providers
{
	GOOGLE
	 ("https://accounts.google.com/o/oauth2/auth",
		"55876346562-iv33jgm30u36e6bj6fjc1fq3hgif4015.apps.googleusercontent.com",
		"https://www.googleapis.com/auth/plus.me")
	,
	;

	public final String id;
	public final String img;

	public final String authurl;
	public final String clientid;
	public final String scope;

	public final Function onclick;

	Providers(String authurl, String clientid, String scope)
	{
		id = this.name().toLowerCase();
		img = "<img src=\"./" + id + ".png\" id=\"authwith-" + id + "\" width=\"16\" height=\"16\"/>";

		this.authurl = authurl;
		this.clientid = clientid;
		this.scope = scope;

		onclick = new Function()
		{
			@Override
			public boolean f(Event e)
			{
				final AuthRequest req = new AuthRequest(
				 Providers.this.authurl,
				 Providers.this.clientid)
				 .withScopes(Providers.this.scope);

				Main.auth.login(req, new Callback<String, Throwable>()
				{
					@Override
					public void onSuccess(String token)
					{
						GQuery.post("ajax", Properties.create()
							.$$("a", "loggedin")
							.$$("prov", id)
							.$$("tok", token),
							new Function()
							{
								public void f()
								{
									Window.alert("You've done it right, " + this.getDataObject().toString() + "!");
									Main.papers.hide();
									Main.comments.show();
									Main.loadComments();
								}
							});
					}

					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert("Error:\n" + caught.getMessage());
					}
				});

				return true;
			}
		};
	}
}
