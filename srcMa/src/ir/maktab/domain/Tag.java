package ir.maktab.domain;

public class Tag extends TitleizedObjects{

    public Tag(int id, String title) {
        super(id, title);
    }

    public Tag(String title) {
        this(0, title);
    }
}
