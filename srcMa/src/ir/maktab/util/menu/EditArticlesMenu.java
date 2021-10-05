package ir.maktab.util.menu;

import ir.maktab.MainApp;
import ir.maktab.domain.Article;
import ir.maktab.domain.Category;
import ir.maktab.domain.Tag;
import ir.maktab.repo.ArticleRepo;
import ir.maktab.repo.CategoryRepo;
import ir.maktab.repo.TagRepo;
import ir.maktab.util.input.InputString;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class EditArticlesMenu extends Menu {
    private Article userArticle;

    public EditArticlesMenu(Article userArticle) {
        super(new String[]{"Edit Title", "Edit Content", "Change Publish Status", "Change Category", "Add Tags", "Delete Tags", "Exit"});
        this.userArticle = userArticle;
    }

    public void runMenu() throws SQLException {
        while (true) {
            show();
            switch (choose()) {
                case 1:
                    editTitle();
                    break;
                case 2:
                    editContent();
                    break;
                case 3:
                    changePublishStatus();
                    break;
                case 4:
                    changeCategory();
                    break;
                case 5:
                    addTags();
                    break;
                case 6:
                    deleteTags();
                    break;
                case 7:
                    userArticle.setLastUpdateDate(new Timestamp(new Date().getTime()));
                    ArticleRepo.updateArticle(MainApp.getConnection(), userArticle);
                    return;
            }
        }
    }

    private void editTitle() throws SQLException {
        userArticle.setTitle(
                new InputString("Enter your new title: ").getStringInput()
        );
    }

    private void editContent() throws SQLException {
        userArticle.setContent(
                new InputString("Enter your new content: ").getStringInput()
        );
        userArticle.setBrief(userArticle.getContent().length() > 50 ?
                userArticle.getContent().substring(0, 50) : userArticle.getContent()
        );
    }

    private void changePublishStatus() throws SQLException {
        String message = String.format("Do you want to %s your article?",
                userArticle.isPublished() ? "unpublished" : "published");
        if (new CheckMenu(message).runMenu()) {
            userArticle.setPublished(!userArticle.isPublished());
            userArticle.setPublishedDate(new Timestamp(new Date().getTime()));
        } else return;
    }

    private void changeCategory() throws SQLException {
        Connection connection = MainApp.getConnection();
        Category category = new CategoryMenu(
                CategoryRepo.getCategoriesTitles(connection),
                CategoryRepo.getCategories(connection)
        ).runMenu();
        userArticle.setCategory(category);
    }

    private void addTags() throws SQLException {
        Connection connection = MainApp.getConnection();
        Tag[] newTags =  new TagMenu(
                TagRepo.getTagsTitles(connection),
                TagRepo.getTags(connection)
        ).runMenu();
        userArticle.setTags(combineTwoTagArray(newTags, userArticle.getTags()));
    }

    private Tag[] combineTwoTagArray(Tag[] newTags, Tag[] oldTags) {
        ArrayList<Tag> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(newTags));
        arrayList.addAll(Arrays.asList(oldTags));
        return arrayList.toArray(new Tag[0]);
    }

    private void deleteTags() throws SQLException {
        ArrayList<Tag> tagArrayList = new ArrayList<>(Arrays.asList(TagRepo.getAnArticleTags(MainApp.getConnection(), userArticle.getId())));
        while (true) {
            ArrayList<String> tagsTitles = new ArrayList<>();
            for (Tag tag : tagArrayList) {
                tagsTitles.add(tag.getTitle());
            }
            tagsTitles.add("Exit");
            Menu menu = new Menu(tagsTitles.toArray(new String[0]));
            menu.show();
            int chosenItem = choose();
            if(chosenItem != menu.getItems().length) {
                tagArrayList.remove(chosenItem - 1);
                userArticle.setTags(tagArrayList.toArray(new Tag[0]));
            } else {
                break;
            }
        }
    }
}
