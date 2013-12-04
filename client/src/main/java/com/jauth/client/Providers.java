package com.jauth.client;

public enum Providers
{
	GOOGLE,
	;

	public final String id;
	public final String img;

	Providers()
	{
		id = this.name().toLowerCase();
		img = "<img src=\"./" + id + ".png\" id=\"authwith-" + id + "\" width=\"16\" height=\"16\"/>";
	}
}
