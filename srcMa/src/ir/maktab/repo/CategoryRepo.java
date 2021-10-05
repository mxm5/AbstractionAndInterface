package ir.maktab.repo;

import ir.maktab.domain.Category;

import java.sql.*;
import java.util.ArrayList;

public class CategoryRepo {

    public static boolean checkAnyCategoryExists(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        boolean existsCategory = statement.executeQuery("SELECT * FROM categories limit 1").next();
        statement.close();
        return existsCategory;
    }

    public static int insertCategory(Connection connection, Category category) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO categories(title, description) " +
                        "VALUES (?, ?)");
        statement.setString(1, category.getTitle());
        statement.setString(2, category.getDescription());
        int insertResult = statement.executeUpdate();
        return insertResult;
    }

    public static String[] getCategoriesTitles(Connection connection) throws SQLException {
        ArrayList<String> categories = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT title FROM categories");
        while(result.next()) {
            categories.add(result.getString("title"));
        }
        categories.add("I want to add new category");
        statement.close();
        return categories.toArray(new String[0]);
    }

    public static Category[] getCategories(Connection connection) throws SQLException {
        ArrayList<Category> articles = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT * FROM categories ");
        while(result.next()) {
            articles.add(createCategoryFromResultSet(result));
        }
        statement.close();
        return articles.toArray(new Category[0]);
    }

    public static Category getCategoryFromId(Connection connection, int categoryId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM categories WHERE id = ?;");
        statement.setInt(1, categoryId);
        ResultSet result = statement.executeQuery();
        if(result.next()) {
            Category category = createCategoryFromResultSet(result);
            statement.close();
            return category;
        } else {
            return null;
        }
    }

    private static Category createCategoryFromResultSet(ResultSet result) throws SQLException {
        return new Category(
                result.getInt("id"),
                result.getString("title"),
                result.getString("description")
        );
    }
}
