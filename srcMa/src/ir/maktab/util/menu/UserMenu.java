package ir.maktab.util.menu;

import ir.maktab.MainApp;
import ir.maktab.domain.Article;
import ir.maktab.domain.Category;
import ir.maktab.domain.Tag;
import ir.maktab.domain.User;
import ir.maktab.repo.ArticleRepo;
import ir.maktab.repo.CategoryRepo;
import ir.maktab.repo.TagRepo;
import ir.maktab.util.input.InputString;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class UserMenu extends Menu {
    private User user;

    public UserMenu(User user) {
        super(new String[]{"Show My Articles", "Add Article", "Edit Article", "Edit Profile and Info", "Log Out"});
        this.user = user;
        System.out.printf("\n\nWelcome to your work bench %s %s.%n", user.getFirstName(), user.getLastName());
    }

    public void runMenu() throws SQLException {
        while (true) {
            show();
            switch (choose()) {
                case 1:
                    new ShowArticlesMenu(
                            ArticleRepo.getUserArticlesTitles(MainApp.getConnection(), user),
                            ArticleRepo.getUserArticles(MainApp.getConnection(), user),
                            true
                    ).runMenu();
                    break;
                case 2:
                    if(ArticleRepo.insertArticle(MainApp.getConnection(), getArticleInformation()) != 0) {
                        System.out.println("Your article added successfully.");
                    }
                    break;
                case 3:
                    System.out.println("\nChoose your article for edit");
                    Article article = new ShowArticlesMenu(
                            ArticleRepo.getUserArticlesTitles(MainApp.getConnection(), user),
                            ArticleRepo.getUserArticles(MainApp.getConnection(), user),
                            true,
                            true
                    ).runMenu();
                    if(!Objects.isNull(article)) {
                        new EditArticlesMenu(article).runMenu();
                    }
                    break;
                case 4:
                    new EditUserInformationMenu(user).runMenu();
                    break;
                case 5:
                    if(new CheckMenu("Are you sure you want to log out?").runMenu()) return;
                    else break;
            }
        }
    }

    // This method take all the information for creating a new article an return that Article object
    private Article getArticleInformation() throws SQLException {
        Category category = getCategory();
        String title = new InputString("Enter your article title: ").getStringInput();
        String content = new InputString("Enter your article content: ").getStringInput();
        String brief = content.length() > 50 ? content.substring(0, 50) + "..." : content;
        boolean isPublished = new CheckMenu("Do you want to publish your article now?").runMenu();
        Timestamp now = new Timestamp(new Date().getTime());
        Tag[] tags = getTags();
        return new Article(
                title,
                brief,
                content,
                now,
                now,
                isPublished,
                now,
                0,
                category,
                user,
                tags
        );
    }

    // get category from defined categories chosen by user
    private Category getCategory() throws SQLException {
        Connection connection = MainApp.getConnection();
        return new CategoryMenu(
                CategoryRepo.getCategoriesTitles(connection),
                CategoryRepo.getCategories(connection)
        ).runMenu();
    }

    // get category from defined categories chosen by user
    private Tag[] getTags() throws SQLException {
        Connection connection = MainApp.getConnection();
        return new TagMenu(
                TagRepo.getTagsTitles(connection),
                TagRepo.getTags(connection)
        ).runMenu();
    }
}


