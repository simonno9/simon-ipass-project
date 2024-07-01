package nl.hu.bep.shopping.dao;

import nl.hu.bep.setup.DatabaseConnection;
import nl.hu.bep.shopping.model.CategoryService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryServiceDAO {
    private static final Logger logger = Logger.getLogger(CategoryServiceDAO.class.getName());

    public static List<CategoryService> getAllService() {
        List<CategoryService> categoryServices = new ArrayList<>();
        String query = "SELECT id, name, image, description FROM category_service"; // Updated query

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                CategoryService categoryService = new CategoryService(
                        resultSet.getInt("id"),
                        cleanString(resultSet.getString("name")),
                        cleanString(resultSet.getString("image")),
                        cleanString(resultSet.getString("description")) // Fetch description
                );
                categoryServices.add(categoryService);
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Error fetching data from database", e);
        }

        if (categoryServices.isEmpty()) {
            logger.log(Level.WARNING, "No data found in the category_service table");
        }

        return categoryServices;
    }

    private static String cleanString(String input) {
        return input != null ? input.replaceAll("\\s+", " ").trim() : null;
    }
}
