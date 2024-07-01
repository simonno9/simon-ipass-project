package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.dao.CategoryServiceDAO;
import nl.hu.bep.shopping.model.CategoryService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/retrieveCategory")
public class ServiceResource {  // Ensure class name starts with uppercase
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response selectServiceCategory() {
        List<CategoryService> services = CategoryServiceDAO.getAllService();
        return Response.ok(services).build();
    }
}
