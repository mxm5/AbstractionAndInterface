package ir.maktab.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Article extends TitleizedObjects{
    private String brief;
    private String content;
    private Timestamp createdDate;
    private Timestamp lastUpdateDate;
    private boolean isPublished;
    private Timestamp publishedDate;
    private int views;
    private Category category;
    private User user;
    private Tag[] tags;

    public Article(int id, String title, String brief, String content, Timestamp createdDate, Timestamp lastUpdateDate,
                   boolean isPublished, Timestamp publishedDate, int views, Category category, User user, Tag[] tags) {
        super(id, title);
        this.brief = brief;
        this.content = content;
        this.createdDate = createdDate;
        this.lastUpdateDate = lastUpdateDate;
        this.isPublished = isPublished;
        this.publishedDate = publishedDate;
        this.views = views;
        this.category = category;
        this.user = user;
        this.tags = tags;
    }

    public Article(String title, String brief, String content, Timestamp createdDate, Timestamp lastUpdateDate,
                   boolean isPublished, Timestamp publishedDate, int views, Category category, User user, Tag[] tags) {
        this(0, title, brief, content, createdDate, lastUpdateDate, isPublished, publishedDate, views, category, user, tags);
    }

    @Override
    public String toString() {
        return String.format("-----------------------------------------------%n" +
                        "Title: %s%n%n" +
                        "%s%n%n" +
                        "Category: %s | User: %s %s%n" +
                        "Published Date: %s | Created Date: %s | Last Updated Date: %s%n" +
                        "Views: %s%n" +
                        "Tags: %s%n" +
                        "Published status : %s%n" +
                        "-----------------------------------------------------%n",
                getTitle(), content, category.getTitle(),
                user.getFirstName(), user.getLastName(),
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(publishedDate),
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(createdDate),
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(lastUpdateDate),
                views,
                tagsToString(),
                isPublished ? "Published" : "Not Published");
    }

    // Convert tags to String that we can use for print
    private String tagsToString() {
        StringBuilder result = new StringBuilder();
        for (Tag tag : tags) {
            result.append(tag.getTitle()).append(" | ");
        }
        return result.toString();
    }

    public Timestamp getPublishedDate() {
        return publishedDate;
    }

    public Tag[] getTags() {
        return tags;
    }

    public String getBrief() {
        return brief;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public int getViews() {
        return views;
    }

    public Category getCategory() {
        return category;
    }

    public User getUser() {
        return user;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public void setPublishedDate(Timestamp publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }
}
