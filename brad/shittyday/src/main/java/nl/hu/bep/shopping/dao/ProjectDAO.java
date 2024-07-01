package nl.hu.bep.shopping.dao;

import nl.hu.bep.setup.DatabaseConnection;
import nl.hu.bep.shopping.model.Project;
import nl.hu.bep.shopping.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    public static void addProject(Project project) {
        String projectQuery = "INSERT INTO Project (name, description, category_id) VALUES (?, ?, ?)";
        String imageQuery = "INSERT INTO image (project_id, image_link) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement projectStatement = connection.prepareStatement(projectQuery, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement imageStatement = connection.prepareStatement(imageQuery)) {

            // Insert project
            projectStatement.setString(1, project.getName());
            projectStatement.setString(2, project.getDescription());
            projectStatement.setInt(3, project.getCategoryId());
            projectStatement.executeUpdate();

            // Get generated project ID
            try (ResultSet generatedKeys = projectStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int projectId = generatedKeys.getInt(1);

                    // Insert each image link
                    for (String imageLink : project.getImageLinks()) {
                        imageStatement.setInt(1, projectId);
                        imageStatement.setString(2, imageLink);
                        imageStatement.executeUpdate();
                    }
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Project getProjectById(int projectId) {
        String projectAndReviewsQuery = "SELECT " +
                "p.id AS project_id, " +
                "p.name AS project_name, " +
                "p.description AS project_description, " +
                "p.category_id AS project_category_id, " +
                "p.img AS project_image_link, " +
                "r.id AS review_id, " +
                "r.description AS review_description, " +
                "r.project_id AS review_project_id, " +
                "r.titel AS review_title, " +
                "r.rating AS review_rating " +
                "FROM project p " +
                "LEFT JOIN reviews r ON p.id = r.project_id " +
                "WHERE p.id = ?";

        String imagesQuery = "SELECT url AS project_image_link " +
                "FROM images " +
                "WHERE project_id = ?";

        Project project = null;
        List<Review> reviews = new ArrayList<>();
        List<String> imageLinks = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement projectAndReviewsStmt = connection.prepareStatement(projectAndReviewsQuery);
             PreparedStatement imagesStmt = connection.prepareStatement(imagesQuery)) {

            // Log the query execution
            System.out.println("Executing query to fetch project and reviews for project ID: " + projectId);

            // Fetch project and reviews
            projectAndReviewsStmt.setInt(1, projectId);
            try (ResultSet resultSet = projectAndReviewsStmt.executeQuery()) {
                while (resultSet.next()) {
                    if (project == null) {
                        project = new Project();
                        project.setId(resultSet.getInt("project_id"));
                        project.setName(resultSet.getString("project_name"));
                        project.setDescription(resultSet.getString("project_description"));
                        project.setCategoryId(resultSet.getInt("project_category_id"));
                        project.setImg(resultSet.getString("project_image_link")); // Set the main project image
                        project.setImageLinks(new ArrayList<>());
                        project.setReviews(new ArrayList<>());
                    }

                    // Add review to the list if it exists
                    int reviewId = resultSet.getInt("review_id");
                    if (reviewId > 0) {
                        Review review = new Review();
                        review.setId(reviewId);
                        review.setDescription(resultSet.getString("review_description"));
                        review.setProjectId(resultSet.getInt("review_project_id"));
                        review.setTitel(resultSet.getString("review_title"));
                        review.setRating(resultSet.getInt("review_rating"));
                        reviews.add(review);
                    }
                }

                if (project != null) {
                    project.setReviews(reviews);
                }
            }

            // Fetch additional images
            imagesStmt.setInt(1, projectId);
            try (ResultSet resultSet = imagesStmt.executeQuery()) {
                while (resultSet.next()) {
                    String imageLink = resultSet.getString("project_image_link");
                    if (imageLink != null && !imageLinks.contains(imageLink)) {
                        imageLinks.add(imageLink);
                    }
                }

                if (project != null) {
                    project.setImageLinks(imageLinks);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return project;
    }
    public static List<Project> getProjectsByCategoryId(int categoryId) {
        String query = "SELECT id, name, description, category_id, img FROM project WHERE category_id = ?";
        List<Project> projects = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Project project = new Project();
                    project.setId(resultSet.getInt("id"));
                    project.setName(resultSet.getString("name"));
                    project.setDescription(resultSet.getString("description"));
                    project.setCategoryId(resultSet.getInt("category_id"));
                    project.setImg(resultSet.getString("img")); // Set img as a single varchar
                    // No need to initialize imageLinks and reviews
                    projects.add(project);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return projects;
    }
}
