package com.jauth.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.jauth.client.oauth2.Auth;

public class Main implements EntryPoint
{
	static final String def =
"\t<center>\n" +
 "\t\t<table>\n" +
 "\t\t\t<tbody>\n" +
 "\t\t\t\t<tr>\n" +
 "\t\t\t\t\t<td>\n" +
 "\t\t\t\t\t\tPapers, please<br/>\n" +
 "\t\t\t\t\t\t<br/>\n" +
 "\t\t\t\t\t\t<center>\n" +
 "\t\t\t\t\t\t\t<img src=\"./google.png\" width=\"16\" height=\"16\"/>\n" +
 "\t\t\t\t\t\t</center>\n" +
 "\t\t\t\t\t</td>\n" +
 "\t\t\t\t</tr>\n" +
 "\t\t\t</tbody>\n" +
 "\t\t</table>\n" +
 "\t</center>";

	@Override
	public void onModuleLoad()
	{
		Auth.export();

		BodyElement be = Document.get().getBody();

		be.setInnerHTML(def);
	}
}
