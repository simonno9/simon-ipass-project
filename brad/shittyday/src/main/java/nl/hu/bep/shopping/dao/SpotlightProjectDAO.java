package nl.hu.bep.shopping.dao;

import nl.hu.bep.setup.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpotlightProjectDAO {

    public static List<String> getAllSpotlightProjectImages() {
        String query = "SELECT p.img FROM spotlight_project sp JOIN Project p ON sp.project_id = p.id";
        List<String> images = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                images.add(resultSet.getString("img"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return images;
    }
}
