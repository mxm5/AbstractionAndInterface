package ir.maktab.repo;

import ir.maktab.MainApp;
import ir.maktab.domain.User;

import java.sql.*;
import java.util.ArrayList;

public class UserRepo {

    public static String[] getAllUsersUsernames(Connection connection) throws SQLException {
        ArrayList<String> usernames = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT username FROM users;");
        while(result.next()) {
            usernames.add(result.getString(1));
        }
        statement.close();
        return usernames.toArray(new String[0]);
    }

    public static String[] getAllUsersNationalCodes(Connection connection) throws SQLException {
        ArrayList<String> nationalCodes = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT national_code FROM users;");
        while(result.next()) {
            nationalCodes.add(result.getString(1));
        }
        statement.close();
        return nationalCodes.toArray(new String[0]);
    }

    public static User[] getAllUsers(Connection connection) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT * FROM users;");
        while(result.next()) {
            users.add(createUserFromResultSet(result));
        }
        statement.close();
        return users.toArray(new User[0]);
    }

    public static User getLogInUser(Connection connection, String username, String password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? && password = ?;");
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet result = statement.executeQuery();
        if(result.next()) {
            User user = createUserFromResultSet(result);
            statement.close();
            return user;
        } else {
            return null;
        }
    }

    public static int insertUser(Connection connection, User user) throws SQLException {
        PreparedStatement statement = MainApp.getConnection().prepareStatement(
                "INSERT INTO users(first_name, last_name, username, password, national_code, birthday) " +
                        "VALUES (?, ?, ?, ?, ?, ?)");
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getUsername());
        statement.setString(4, user.getNationalCode());
        statement.setString(5, user.getNationalCode());
        statement.setTimestamp(6, user.getBirthday());
        int insertResult = statement.executeUpdate();
        statement.close();
        return insertResult;
    }

    public static User getUserFromId(Connection connection, int userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?;");
        statement.setInt(1, userId);
        ResultSet result = statement.executeQuery();
        if(result.next()) {
            User user = createUserFromResultSet(result);
            statement.close();
            return user;
        } else {
            return null;
        }
    }

    public static void updateUser(Connection connection, User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, national_code = ?, birthday = ? " +
                        "WHERE id = ? ");
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getUsername());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getNationalCode());
        statement.setTimestamp(6, user.getBirthday());
        statement.setInt(7, user.getId());
        statement.executeUpdate();
    }

    private static User createUserFromResultSet(ResultSet result) throws SQLException {
        return new User(
                result.getInt("id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("username"),
                result.getString("password"),
                result.getString("national_code"),
                result.getTimestamp("birthday")
        );
    }
}
