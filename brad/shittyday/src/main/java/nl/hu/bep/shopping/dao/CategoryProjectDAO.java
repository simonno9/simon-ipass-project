package nl.hu.bep.shopping.dao;

import nl.hu.bep.setup.DatabaseConnection;
import nl.hu.bep.shopping.model.CategoryProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryProjectDAO {

    public static List<CategoryProject> getAllCategoryProjects() {
        String query = "SELECT id, name, description, image FROM category_project";
        List<CategoryProject> categoryProjects = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                CategoryProject categoryProject = new CategoryProject();
                categoryProject.setId(resultSet.getInt("id"));
                categoryProject.setName(resultSet.getString("name"));
                categoryProject.setDescription(resultSet.getString("description"));
                categoryProject.setImage(resultSet.getString("image")); // Set image
                categoryProjects.add(categoryProject);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving category projects: " + e.getMessage());
        }

        return categoryProjects;
    }

    public static void addCategoryProject(CategoryProject categoryProject) {
        String query = "INSERT INTO category_project (name, description, image) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, categoryProject.getName());
            statement.setString(2, categoryProject.getDescription());
            statement.setString(3, categoryProject.getImage()); // Add image field
            statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding category project: " + e.getMessage());
        }
    }

    public static CategoryProject getCategoryProjectById(int categoryId) {
        String query = "SELECT id, name, description, image FROM category_project WHERE id = ?";
        CategoryProject categoryProject = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    categoryProject = new CategoryProject();
                    categoryProject.setId(resultSet.getInt("id"));
                    categoryProject.setName(resultSet.getString("name"));
                    categoryProject.setDescription(resultSet.getString("description"));
                    categoryProject.setImage(resultSet.getString("image")); // Set image
                } else {
                    throw new RuntimeException("Category project not found for id: " + categoryId);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving category project by id: " + e.getMessage());
        }

        return categoryProject;
    }
}
