package com.jauth.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Window;
import com.jauth.client.oauth2.Auth;

import static com.google.gwt.query.client.GQuery.$;

/*
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Selector;
import com.google.gwt.query.client.Selectors;
import static com.google.gwt.query.client.GQuery.*;
import static com.google.gwt.query.client.css.CSS.*;
*/

public class Main implements EntryPoint
{
	public static final GQuery body = $("body");
	public static final GQuery head = $("<div/>").attr("id", "head");
	public static final GQuery comments = $("<div/>").attr("id", "comments");

	public static final Auth auth = Auth.get();

	@Override
	public void onModuleLoad()
	{
		Auth.export();

		body.append(head);
		body.append(comments);

		head.append(Papers.papers);
	}

	public static void leclick(String s)
	{
		Window.alert(s);
	}
}
