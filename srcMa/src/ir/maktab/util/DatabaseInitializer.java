package ir.maktab.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public void createTables(Connection connection) throws SQLException {
        initUserTable(connection.createStatement());
        initCategoryTable(connection.createStatement());
        initArticleTable(connection.createStatement());
        initTagTable(connection.createStatement());
        initTakenTagsTable(connection.createStatement());
    }

    private void initArticleTable(Statement statement) throws SQLException {
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS " +
                        "articles(" +
                        "id INT AUTO_INCREMENT PRIMARY KEY ," +
                        "title VARCHAR(255) NOT NULL ," +
                        "brief VARCHAR(255) NOT NULL ," +
                        "content TEXT NOT NULL ," +
                        "created_date TIMESTAMP NOT NULL ," +
                        "last_updated_date TIMESTAMP NOT NULL, " +
                        "is_published BOOL NOT NULL ," +
                        "published_date TIMESTAMP, " +
                        "views INT," +
                        "user_id INT," +
                        "category_id INT," +
                        "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ," +
                        "FOREIGN KEY (category_id) REFERENCES categories(id))"
                );
        statement.close();
    }

    private void initUserTable(Statement statement) throws SQLException {
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS " +
                        "users(" +
                        "id INT AUTO_INCREMENT PRIMARY KEY , " +
                        "first_name VARCHAR(255) NOT NULL, " +
                        "last_name VARCHAR(255) NOT NULL, " +
                        "username VARCHAR(255) NOT NULL UNIQUE , " +
                        "password VARCHAR(255) NOT NULL, " +
                        "national_code CHAR(10) NOT NULL UNIQUE, " +
                        "birthday TIMESTAMP)");
        statement.close();
    }

    private void initCategoryTable(Statement statement) throws SQLException {
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS " +
                        "categories(" +
                        "id INT AUTO_INCREMENT PRIMARY KEY ," +
                        "title VARCHAR(255) UNIQUE , " +
                        "description VARCHAR(255))"
        );
        statement.close();
    }

    private void initTagTable(Statement statement) throws SQLException {
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS " +
                        "tags(" +
                        "id INT AUTO_INCREMENT PRIMARY KEY ," +
                        "title VARCHAR(255) UNIQUE)"
        );
        statement.close();
    }

    private void initTakenTagsTable(Statement statement) throws SQLException {
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS " +
                        "taken_tags(" +
                        "id INT AUTO_INCREMENT PRIMARY KEY ," +
                        "tag_id INT," +
                        "article_id INT," +
                        "FOREIGN KEY (tag_id) REFERENCES tags(id)," +
                        "FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE)"
        );
        statement.close();
    }
}
