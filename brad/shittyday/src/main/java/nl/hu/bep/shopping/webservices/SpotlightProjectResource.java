package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.dao.SpotlightProjectDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/spotlight")
public class SpotlightProjectResource {

    @GET
    @Path("/images")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSpotlightProjectImages() {
        List<String> images = SpotlightProjectDAO.getAllSpotlightProjectImages();
        return Response.ok(images).build();
    }
}
