package jauth.server;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Comments
{
	public List<Comment> comments = Collections.synchronizedList(new LinkedList<Comment>());
}
