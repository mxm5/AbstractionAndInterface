package ir.maktab.repo;

import ir.maktab.MainApp;
import ir.maktab.domain.Article;
import ir.maktab.domain.Category;
import ir.maktab.domain.Tag;
import ir.maktab.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Set;

public class ArticleRepo {

    public static String[] getPublishedArticlesTitles(Connection connection) throws SQLException {
        ArrayList<String> articles = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT title, brief FROM articles WHERE is_published = true");
        while(result.next()) {
            articles.add(result.getString("title") + "\n" + result.getString("brief"));
        }
        articles.add("Exit");
        statement.close();
        return articles.toArray(new String[0]);
    }

    public static String[] getUserArticlesTitles(Connection connection, User user) throws SQLException {
        ArrayList<String> articles = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT title, brief FROM articles WHERE user_id = ?");
        statement.setInt(1, user.getId());
        ResultSet result = statement.executeQuery();
        while(result.next()) {
            articles.add(result.getString("title") + "\n" + result.getString("brief"));
        }
        articles.add("Exit");
        statement.close();
        return articles.toArray(new String[0]);
    }

    public static Article[] getUserArticles(Connection connection, User user) throws SQLException {
        ArrayList<Article> articles = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT *, c.title AS category_title FROM articles a " +
                "JOIN categories c ON a.category_id = c.id " +
                "JOIN users u ON a.user_id = u.id " +
                "WHERE u.id = ?");
        statement.setInt(1, user.getId());
        ResultSet result = statement.executeQuery();
        while(result.next()) {
            articles.add(createArticleFromResultSet(connection, result));
        }
        statement.close();
        return articles.toArray(new Article[0]);
    }

    public static Article[] getPublishedArticles(Connection connection) throws SQLException {
        ArrayList<Article> articles = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT *, c.title AS category_title FROM articles a " +
                        "JOIN categories c ON a.category_id = c.id " +
                        "JOIN users u ON a.user_id = u.id " +
                        "WHERE is_published = true");
        while(result.next()) {
            articles.add(createArticleFromResultSet(connection, result));
        }
        statement.close();
        return articles.toArray(new Article[0]);
    }

    public static int insertArticle(Connection connection, Article article) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO articles(title, brief, content, created_date, last_updated_date, is_published, published_date, " +
                        "category_id, user_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, article.getTitle());
        statement.setString(2, article.getBrief());
        statement.setString(3, article.getContent());
        statement.setTimestamp(4, article.getCreatedDate());
        statement.setTimestamp(5, article.getLastUpdateDate());
        statement.setBoolean(6, article.isPublished());
        statement.setTimestamp(7, article.getPublishedDate());
        statement.setInt(8, article.getCategory().getId());
        statement.setInt(9, article.getUser().getId());
        int insertResult = statement.executeUpdate();
        statement.close();
        insertTakenTags(connection, getLastArticleId(connection), article.getTags());
        return insertResult;
    }

    private static int getLastArticleId(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT id FROM articles ORDER BY id DESC LIMIT 1");
        if(result.next()) return result.getInt("id");
        return -1;
    }

    public static void insertTakenTags(Connection connection, int articleId, Tag[] tags) throws SQLException {
        for (Tag tag : tags) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO taken_tags(article_id, tag_id) " +
                            "VALUES (?, ?)");
            statement.setInt(1, articleId);
            statement.setInt(2, tag.getId());
            statement.executeUpdate();
            statement.close();
        }
    }

    private static Article createArticleFromResultSet(Connection connection, ResultSet result) throws SQLException {
        return new Article(
                result.getInt("id"),
                result.getString("title"),
                result.getString("brief"),
                result.getString("content"),
                result.getTimestamp("created_date"),
                result.getTimestamp("last_updated_date"),
                result.getBoolean("is_published"),
                result.getTimestamp("published_date"),
                result.getInt("views"),
                CategoryRepo.getCategoryFromId(connection,result.getInt("category_id")),
                UserRepo.getUserFromId(connection, result.getInt("user_id")),
                TagRepo.getAnArticleTags(connection, result.getInt("id"))
        );
    }

    public static void updateArticle(Connection connection, Article article) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE articles SET title = ?, brief = ?, content = ?, last_updated_date = ?, is_published = ?," +
                        "published_date = ?, category_id = ? " +
                        "WHERE id = ? ");
        statement.setString(1, article.getTitle());
        statement.setString(2, article.getBrief());
        statement.setString(3, article.getContent());
        statement.setTimestamp(4, article.getLastUpdateDate());
        statement.setBoolean(5, article.isPublished());
        statement.setTimestamp(6, article.getPublishedDate());
        statement.setInt(7, article.getCategory().getId());
        statement.setInt(8, article.getId());
        statement.executeUpdate();
        statement.close();
        updateTakenTags(connection, article.getId(), article.getTags());
    }

    public static void updateTakenTags(Connection connection, int articleId, Tag[] tags) throws SQLException {
        deleteAllTakenTags(connection, articleId);
        insertTakenTags(connection, articleId, tags);
    }

    public static void deleteOneTakenTag(Connection connection, int articleId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM taken_tags WHERE article_id = ? ");
        statement.setInt(1, articleId);
        statement.executeUpdate();
        statement.close();
    }

    public static void deleteAllTakenTags(Connection connection, int articleId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM taken_tags WHERE article_id = ? ");
        statement.setInt(1, articleId);
        statement.executeUpdate();
        statement.close();
    }

    public static void updateArticleViews(Connection connection, Article article) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE articles SET views = ? " +
                        "WHERE id = ? ");
        statement.setInt(1, article.getViews());
        statement.setInt(2, article.getId());
        statement.executeUpdate();
    }
}
