package nl.hu.bep.shopping.dao;

import nl.hu.bep.setup.DatabaseConnection;
import nl.hu.bep.shopping.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public static void addReview(Review review) {
        String query = "INSERT INTO reviews (description, project_id, titel, rating) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, review.getDescription());
            statement.setInt(2, review.getProjectId());
            statement.setString(3, review.getTitel());
            statement.setInt(4, review.getRating());

            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding review", e);
        }
    }

    public static List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.*, p.img AS project_image, cp.name AS category_name " +
                "FROM reviews r " +
                "JOIN project p ON r.project_id = p.id " +
                "JOIN category_project cp ON p.category_id = cp.id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Review review = new Review(
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getInt("project_id"),
                        resultSet.getString("titel"),
                        resultSet.getInt("rating"),
                        resultSet.getString("project_image"),
                        resultSet.getString("category_name")
                );
                reviews.add(review);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing query: " + query);
            System.err.println("SQLException: " + e.getMessage());
            throw new RuntimeException("Error retrieving reviews", e);
        }

        if (reviews.isEmpty()) {
            System.out.println("No reviews found in the database.");
        } else {
            System.out.println("Reviews retrieved successfully.");
        }

        return reviews;
    }

    public static Review getReviewById(int id) {
        String query = "SELECT r.*, p.img AS project_image, cp.name AS category_name " +
                "FROM reviews r " +
                "JOIN project p ON r.project_id = p.id " +
                "JOIN category_project cp ON p.category_id = cp.id " +
                "WHERE r.id = ?";
        Review review = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    review = new Review(
                            resultSet.getInt("id"),
                            resultSet.getString("description"),
                            resultSet.getInt("project_id"),
                            resultSet.getString("titel"),
                            resultSet.getInt("rating"),
                            resultSet.getString("project_image"),
                            resultSet.getString("category_name")
                    );
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing query: " + query);
            System.err.println("SQLException: " + e.getMessage());
            throw new RuntimeException("Error retrieving review by ID", e);
        }

        return review;
    }
}
