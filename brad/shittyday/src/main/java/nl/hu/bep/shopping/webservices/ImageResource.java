package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.dao.ImageDAO;
import nl.hu.bep.shopping.model.Image;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/images")
public class ImageResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addImages(Image image) {
        List<String> imageLinks = image.getImageLinks();
        ImageDAO.addImages(imageLinks);
        return Response.ok("{\"message\":\"Images added successfully\"}").build();
    }
}
