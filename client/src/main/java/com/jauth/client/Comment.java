package com.jauth.client;

import com.google.gwt.query.client.builders.JsonBuilder;

public interface Comment extends JsonBuilder
{
	public String getName();
	public String getUrl();
	public String getComment();
	public String getProvider();
}
