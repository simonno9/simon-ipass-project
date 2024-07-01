package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.dao.UserDAO;
import nl.hu.bep.shopping.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
public class UserResource {
    @Context
    private HttpServletRequest request;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        User foundUser = UserDAO.getUser(user.getUsername());
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            // Create a session
            HttpSession session = request.getSession(true);
            session.setAttribute("username", foundUser.getUsername());

            return Response.ok("{\"message\":\"Login successful\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\":\"Invalid credentials\"}").build();
        }
    }

    @GET
    @Path("logout")
    public Response logout() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Response.ok("{\"message\":\"Logout successful\"}").build();
    }

    @GET
    @Path("checkSession")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkSession() {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            return Response.ok("{\"message\":\"Session active\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\":\"No active session\"}").build();
        }
    }
}
