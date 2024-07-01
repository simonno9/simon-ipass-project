package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.dao.CategoryProjectDAO;
import nl.hu.bep.shopping.model.CategoryProject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categoryprojects")
public class CategoryProjectResource {

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategoryProjects() {
        try {
            List<CategoryProject> categoryProjects = CategoryProjectDAO.getAllCategoryProjects();
            return Response.ok(categoryProjects).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategoryProject(CategoryProject categoryProject) {
        try {
            CategoryProjectDAO.addCategoryProject(categoryProject);
            return Response.ok("{\"message\":\"Category project added successfully\"}").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryProjectById(@PathParam("id") int id) {
        try {
            CategoryProject categoryProject = CategoryProjectDAO.getCategoryProjectById(id);
            return Response.ok(categoryProject).build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
            }
        }
    }
}
