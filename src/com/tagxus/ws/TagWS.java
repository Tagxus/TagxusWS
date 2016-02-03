package com.tagxus.ws;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.tagxus.dal.TagManager;
import com.tagxus.model.Tag;

@Path("/tags")
public class TagWS {

	TagManager manager = new TagManager();

	@Path("/all")
	@GET
	@Produces("application/json")
	public Response getAllTags() {
		List<Tag> tags = manager.getTags();
		return Response.status(200).entity(Tag.toJSON(tags).toString()).build();
		
		
	}
	
	@GET
	@Produces("application/json")
	public Response getTagsByUri(@QueryParam("uri") String uri) throws UnsupportedEncodingException {
		List<Tag> tags = manager.getTags(uri);
		return Response.status(200).entity(Tag.toJSON(tags).toString()).build();
	}
	
	@POST
	public Response addTag(@Context HttpServletRequest req, @FormParam("uri") String uri, @FormParam("name") String name,
			@FormParam("xpath") String xpath) {

		HttpSession session = req.getSession(true);
		Object username = session.getAttribute("username");
		if (username == null) {
			username = req.getRemoteAddr();
		} else
			System.out.println("username: " + username);

		Tag tag = manager.addTag(new Tag(uri, name, xpath, username.toString()));
		return Response.ok().entity(tag.toJSON().toString()).build();
	}
}