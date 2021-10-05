package ir.maktab.repo;

import ir.maktab.domain.Tag;

import java.sql.*;
import java.util.ArrayList;

public class TagRepo {

    public static Tag[] getAnArticleTags(Connection connection, int articleId) throws SQLException {
        ArrayList<Tag> tags = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM tags t " +
                        "JOIN taken_tags tt ON t.id = tt.tag_id " +
                        "WHERE tt.article_id = ?"
        );
        statement.setInt(1,  articleId);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            tags.add(new Tag(
                    result.getInt("tag_id"),
                    result.getString("title")
            ));
        }
        statement.close();
        return tags.toArray(new Tag[0]);
    }

    public static String[] getTagsTitles(Connection connection) throws SQLException {
        ArrayList<String> tags = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT title FROM tags");
        while(result.next()) {
            tags.add(result.getString("title"));
        }
        tags.add("I want to add new tag");
        tags.add("Exit");
        statement.close();
        return tags.toArray(new String[0]);
    }

    public static Tag[] getTags(Connection connection) throws SQLException {
        ArrayList<Tag> tags = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT * FROM tags ");
        while(result.next()) {
            tags.add(new Tag(
                    result.getInt("id"),
                    result.getString("title"))
            );
        }
        statement.close();
        return tags.toArray(new Tag[0]);
    }

    public static int insertTag(Connection connection, Tag tag) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO tags(title) " +
                        "VALUES (?)");
        statement.setString(1, tag.getTitle());
        int insertResult = statement.executeUpdate();
        statement.close();
        return insertResult;
    }
}
