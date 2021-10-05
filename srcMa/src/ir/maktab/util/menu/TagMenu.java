package ir.maktab.util.menu;

import ir.maktab.MainApp;
import ir.maktab.domain.Tag;
import ir.maktab.repo.TagRepo;
import ir.maktab.util.input.InputString;

import java.sql.SQLException;
import java.util.ArrayList;

public class TagMenu  extends Menu{
    private Tag[] tags;

    public TagMenu(String[] tagsTitles, Tag[] tags) {
        super(tagsTitles);
        this.tags = tags;
    }

    public Tag[] runMenu() throws SQLException {
        ArrayList<Tag> tagArrayList = new ArrayList<>();
        int lastTagId = tags.length > 0 ? tags.length - 1 : 0;
        while (true) {
            show();
            int chosenItem = choose();
            // If user choose to add new tag to tags
            if (chosenItem == getItems().length - 1) {
                Tag tag = new Tag(
                        ++lastTagId,
                        new InputString("Enter your tag title: ").getStringInput()
                );
                if (TagRepo.insertTag(MainApp.getConnection(), tag) != 0) {
                    System.out.println("Your tag added successfully.");
                    tagArrayList.add(tag);
                } else {
                    System.out.println("Your category exist in category list.\nPlease choose that or enter another category");
                }
            }else if(chosenItem == getItems().length) {
                return tagArrayList.toArray(new Tag[0]);
            } else {
                System.out.println("Your tag added successfully.");
                tagArrayList.add(tags[chosenItem - 1]);
            }
        }
    }
}
