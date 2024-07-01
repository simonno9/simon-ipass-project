package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.dao.ProjectDAO;
import nl.hu.bep.shopping.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/projects")
public class ProjectResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProject(Project project) {
        ProjectDAO.addProject(project);
        return Response.ok("{\"message\":\"Project and images added successfully\"}").build();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectById(@PathParam("id") int id) {
        Project project = ProjectDAO.getProjectById(id);
        if (project != null) {
            return Response.ok(project).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Project not found\"}").build();
        }
    }

    @GET
    @Path("/category/{category_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectsByCategoryId(@PathParam("category_id") int categoryId) {
        List<Project> projects = ProjectDAO.getProjectsByCategoryId(categoryId);
        if (!projects.isEmpty()) {
            return Response.ok(projects).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"No projects found for this category\"}").build();
        }
    }
}
