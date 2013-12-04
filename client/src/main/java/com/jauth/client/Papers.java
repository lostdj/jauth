package com.jauth.client;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;

import static com.google.gwt.query.client.GQuery.$;

public class Papers
{
	public static final GQuery papers = $("<center/>").attr("id", "papers");
	static
	{
		papers.append("<b>Papers, please</b>");
		papers.append("<br/><br/>");
		for(Providers p : Providers.values())
			papers.append($(p.img).click(
			 new Function()
			 {
				 public boolean f(Event e)
				 {
					 Main.leclick(e.getEventTarget().toString());

					 return true;
				 }
			 }));
	}
}
