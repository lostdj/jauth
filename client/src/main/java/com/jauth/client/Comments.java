package com.jauth.client;

import com.google.gwt.query.client.builders.JsonBuilder;

public interface Comments extends JsonBuilder
{
	public Comment[] getComments();
}
