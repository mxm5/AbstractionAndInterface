package ir.maktab;

import ir.maktab.domain.Category;
import ir.maktab.repo.CategoryRepo;
import ir.maktab.util.DatabaseInitializer;
import ir.maktab.util.menu.FirstMenu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainApp {
    private static Connection connection;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/article_app", "root", "milad5050062330");
        new DatabaseInitializer().createTables(connection);
        insertFirstCategories();
        new FirstMenu().runMenu();
    }

    public static Connection getConnection() {
        return connection;
    }

    private static final void insertFirstCategories() throws SQLException {
        Category[] categories = {
                new Category("Technology", "This is an Technology category."),
                new Category("History", "This is an History category."),
                new Category("Business", "This is an Business category."),
                new Category("Sport", "This is an Sport category."),
                new Category("Art", "This is an Art category.")
        };
        if(!CategoryRepo.checkAnyCategoryExists(connection)){
            for (Category category : categories) {
                CategoryRepo.insertCategory(connection, category);
            }
        }
    }
}








