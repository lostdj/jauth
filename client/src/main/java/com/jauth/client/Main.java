package com.jauth.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Properties;
import com.google.gwt.user.client.Event;
import com.jauth.client.oauth2.Auth;

import static com.google.gwt.query.client.GQuery.$;

public class Main implements EntryPoint
{
	public static final GQuery body = $("body");
	public static final GQuery head = $("<div/>").attr("id", "head");

	public static final GQuery papers = $("<center/>").attr("id", "papers");
	static
	{
		papers.append("<b>Papers, please</b>");
		papers.append("<br/><br/>");
		for(Providers p : Providers.values())
			papers.append($(p.img).click(p.onclick));
	}

	public static final GQuery comments = $("<div/>").attr("id", "comments");
	static
	{
		comments.append("<b>Care to comment?</b>");
		comments.append("<br/><br/>");
	}

	public static final GQuery commentform = $("<input id=\"commentform\" type=\"text\">");
	public static final GQuery commentsubmit = $("<input id=\"commentsubmit\" type=\"button\" value=\"That's all I have to say\">");
	static
	{
		comments.append(commentform);
		comments.append(commentsubmit);

		commentsubmit.click(
			new Function()
			{
				@Override
				public boolean f(Event e)
				{
					GQuery.post("ajax", Properties.create()
						.$$("a", "postcomment")
						.$$("comm", commentform.val()),
						new Function()
						{
							public void f()
							{
								commentform.val("");
								$(".comment").remove();
								Main.loadComments();
							}
						});

					return true;
				}
			}
		);
	}

	public static final GQuery commentslist = $("<div/>").attr("id", "commentslist");
	static
	{
		comments.append(commentslist);
	}

	public static final Auth auth = Auth.get();

	@Override
	public void onModuleLoad()
	{
		Auth.export();

		body.append(head);
		head.append(papers);
		papers.hide();

		body.append(comments);
		comments.hide();

		// TODO: Logout.

		GQuery.post("ajax", Properties.create()
			.$$("a", "getcredentials"),
			new Function()
			{
				public void f()
				{
					if(this.getDataObject().toString().equalsIgnoreCase("ok"))
					{
						comments.show();
						loadComments();
					}
					else
					{
						papers.show();
					}
				}
			});
	}

	public static void loadComments()
	{
		GQuery.post("ajax", Properties.create()
			.$$("a", "getcomments"),
			new Function()
			{
				public void f()
				{
					Comments comms = GWT.create(Comments.class);
					comms.load(this.getDataObject().toString());
					for(Comment c : comms.getComments())
					{
						GQuery hc = $("<p/>").attr("class", "comment");
						hc.append("<img src=\"./" + c.getProvider() + ".png\" width=\"16\" height=\"16\"/>");
						hc.append(" " + c.getName());
						hc.append("<br/>");
						hc.append(c.getComment());
						commentslist.append(hc);
					}
				}
			});
	}
}
