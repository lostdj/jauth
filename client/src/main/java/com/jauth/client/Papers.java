package com.jauth.client;

import com.google.gwt.query.client.GQuery;

import static com.google.gwt.query.client.GQuery.$;

public class Papers
{
	public static final GQuery papers = $("<center/>").attr("id", "papers");
	static
	{
		papers.append("<b>Papers, please</b>");
		papers.append("<br/><br/>");
		for(Providers p : Providers.values())
			papers.append($(p.img).click(p.onclick));
	}
}
