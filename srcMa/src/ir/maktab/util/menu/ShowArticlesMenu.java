package ir.maktab.util.menu;

import ir.maktab.MainApp;
import ir.maktab.domain.Article;
import ir.maktab.repo.ArticleRepo;

import java.sql.SQLException;

public class ShowArticlesMenu extends Menu {
    private Article[] articles;
    private boolean isUser;
    private boolean isComeFromEditArticle;

    public ShowArticlesMenu(String[] articlesTitles, Article[] articles) throws SQLException {
        this(articlesTitles, articles, false);
    }

    public ShowArticlesMenu(String[] articleTitles, Article[] articles, boolean isUser) throws SQLException {
        this(articleTitles, articles, isUser, false);
    }

    public ShowArticlesMenu(String[] articlesTitles, Article[] articles, boolean isUser, boolean isComeFromEditArticle) throws SQLException {
        super(articlesTitles);
        this.isUser = isUser;
        this.articles = articles;
        this.isComeFromEditArticle = isComeFromEditArticle;
    }

    public Article runMenu() throws SQLException {
        while (true) {
            show();
            int chosenItem = choose();
            if(chosenItem == getItems().length) {
                return null;
            } else if (isComeFromEditArticle) {
                showArticle(chosenItem);
                return articles[chosenItem - 1];
            } else {
                showArticle(chosenItem);
            }
        }
    }

    private void showArticle(int item) throws SQLException {
        Article article = articles[item - 1];
        System.out.println(article.toString());
        if (!isUser)  {
            article.setViews(article.getViews() + 1);
            ArticleRepo.updateArticleViews(MainApp.getConnection(), article);
        }
    }
}
