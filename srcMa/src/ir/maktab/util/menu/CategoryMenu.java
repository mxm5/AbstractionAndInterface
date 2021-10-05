package ir.maktab.util.menu;

import ir.maktab.MainApp;
import ir.maktab.domain.Category;
import ir.maktab.repo.CategoryRepo;
import ir.maktab.util.input.InputString;

import java.sql.SQLException;

public class CategoryMenu extends Menu {
    private Category[] categories;

    public CategoryMenu(String[] categoriesTitles, Category[] categories) {
        super(categoriesTitles);
        this.categories = categories;
    }

    public Category runMenu() throws SQLException {
        while (true) {
            show();
            int chosenItem = choose();
            // If user choose to add new category to categories
            if (chosenItem == getItems().length) {
                Category category = new Category(
                        categories[categories.length - 1].getId() + 1,
                        new InputString("Enter your category title: ").getStringInput(),
                        new InputString("Enter your category description: ").getStringInput()
                );
                if (CategoryRepo.insertCategory(MainApp.getConnection(), category) != 0) {
                    System.out.println("Your category added successfully.");
                    return category;
                } else {
                    System.out.println("Your category exist in category list.\nPlease choose that or enter another category");
                }
            } else {
                return categories[chosenItem - 1];
            }
        }
    }
}
