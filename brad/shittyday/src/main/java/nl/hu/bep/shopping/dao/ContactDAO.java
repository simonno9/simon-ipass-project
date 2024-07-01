package nl.hu.bep.shopping.dao;

import nl.hu.bep.shopping.model.Contact;
import nl.hu.bep.setup.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    public static int addContact(Contact contact) {
        String query = "INSERT INTO contacts (firstname, lastname, category, phonenumber, city, description) VALUES (?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, contact.getFirstname());
            statement.setString(2, contact.getLastname());
            statement.setString(3, contact.getCategory());
            statement.setString(4, contact.getPhonenumber());
            statement.setString(5, contact.getCity());
            statement.setString(6, contact.getDescription());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public static void addImages(int contactId, List<String> imageUrls) {
        String query = "INSERT INTO images (contact_id, url) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (String imageUrl : imageUrls) {
                statement.setInt(1, contactId);
                statement.setString(2, imageUrl);
                statement.addBatch();
            }

            statement.executeBatch();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Contact getContact(int contactId) {
        String query = "SELECT * FROM contacts WHERE id = ?";
        Contact contact = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, contactId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    contact = new Contact(
                            resultSet.getInt("id"),
                            resultSet.getString("firstname"),
                            resultSet.getString("lastname"),
                            resultSet.getString("category"),
                            resultSet.getString("phonenumber"),
                            resultSet.getString("city"),
                            resultSet.getString("description")
                    );
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }

    public static List<Contact> getAllContacts() {
        String query = "SELECT * FROM contacts";
        List<Contact> contacts = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Contact contact = new Contact(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("category"),
                        resultSet.getString("phonenumber"),
                        resultSet.getString("city"),
                        resultSet.getString("description")
                );
                contacts.add(contact);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}
