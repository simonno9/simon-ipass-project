package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.dao.ReviewDAO;
import nl.hu.bep.shopping.model.Review;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/reviews")
public class ReviewResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReview(Review review) {
        try {
            ReviewDAO.addReview(review);
            return Response.ok("{\"message\":\"Review added successfully\"}").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error adding review\"}").build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReviews() {
        try {
            List<Review> reviews = ReviewDAO.getAllReviews();
            return Response.ok(reviews).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error retrieving reviews\"}").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviewById(@PathParam("id") int id) {
        try {
            Review review = ReviewDAO.getReviewById(id);
            if (review != null) {
                return Response.ok(review).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"Review not found\"}").build();
            }
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error retrieving review\"}").build();
        }
    }
}
