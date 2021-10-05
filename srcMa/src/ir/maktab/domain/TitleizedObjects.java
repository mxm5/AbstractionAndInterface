package ir.maktab.domain;

public class TitleizedObjects {
    private int id;
    private String title;

    public TitleizedObjects(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
