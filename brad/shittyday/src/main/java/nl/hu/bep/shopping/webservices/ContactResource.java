package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.dao.ContactDAO;
import nl.hu.bep.shopping.model.Contact;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/contact")
public class ContactResource {

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllContacts() {
        List<Contact> contacts = ContactDAO.getAllContacts();
        if (contacts != null && !contacts.isEmpty()) {
            return Response.ok(contacts).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"No contacts found\"}").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContact(@PathParam("id") int id) {
        Contact contact = ContactDAO.getContact(id);
        if (contact != null) {
            return Response.ok(contact).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Contact not found\"}").build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addContact(Contact contact) {
        int contactId = ContactDAO.addContact(contact);
        if (contactId != -1) {
            List<String> imageUrls = contact.getImageUrls();
            if (imageUrls != null && !imageUrls.isEmpty()) {
                ContactDAO.addImages(contactId, imageUrls);
            }
            return Response.ok("{\"message\":\"Contact added successfully\", \"contactId\":" + contactId + "}").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"Failed to add contact\"}").build();
        }
    }
}
