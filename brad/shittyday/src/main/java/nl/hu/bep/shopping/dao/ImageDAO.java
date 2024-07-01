package nl.hu.bep.shopping.dao;

import nl.hu.bep.setup.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ImageDAO {

    public static void addImages(List<String> imageLinks) {
        String imageQuery = "INSERT INTO image (image_link) VALUES (?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement imageStatement = connection.prepareStatement(imageQuery)) {

            for (String imageLink : imageLinks) {
                imageStatement.setString(1, imageLink);
                imageStatement.executeUpdate();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
