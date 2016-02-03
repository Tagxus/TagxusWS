package com.tagxus.ws;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("/login")
public class LoginWS {

	@POST
	public void login(@Context HttpServletRequest req, @FormParam("username") String username,
			@FormParam("password") String password) {
		HttpSession session = req.getSession(true);
		session.setAttribute("username", username);
	}

	@GET
	@Produces("application/text")
	public String getUsername(@Context HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		Object username;
		if (session != null && (username = session.getAttribute("username")) != null)
			return username.toString();
		return null;
	}

}
