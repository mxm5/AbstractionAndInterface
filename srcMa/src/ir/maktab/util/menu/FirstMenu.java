package ir.maktab.util.menu;

import ir.maktab.MainApp;
import ir.maktab.domain.User;
import ir.maktab.repo.ArticleRepo;
import ir.maktab.repo.UserRepo;
import ir.maktab.util.input.InputString;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 *  FirstMenu class for starting program and first menu of the program.
*/
public class FirstMenu extends Menu {

    public FirstMenu() {
        super(new String[]{"Login", "Sign Up", "View Articles", "Exit"});
    }

    // This method do all the first menu tasks
    public void runMenu() throws SQLException {
        while (true) {
            show();
            switch (choose()) {
                case 1:
                    login();
                    break;
                case 2:
                    signUp(getUserInformation());
                    break;
                case 3:
                    new ShowArticlesMenu(
                            ArticleRepo.getPublishedArticlesTitles(MainApp.getConnection()),
                            ArticleRepo.getPublishedArticles(MainApp.getConnection())
                            ).runMenu();
                    break;
                case 4:
                    if(new CheckMenu("Are you sure you want to exit?").runMenu()) return;
                    else break;
            }
        }
    }

    // Login method for taking username and password from user and checking for true information
    private void login() throws SQLException {
        String username = new InputString("Enter your username: ").getStringInput();
        String password = new InputString("Enter your password: ").getStringInput();
        User user = UserRepo.getLogInUser(MainApp.getConnection(), username, password);
        if(Objects.isNull(user)) {
            System.out.println("Your username or password is wrong!\nMaybe your username taken by others");
        } else {
            new UserMenu(user).runMenu();
        }
    }

    // this method take all the information for sign an author and return an Author with that information
    private User getUserInformation() throws SQLException {
        return new User(
                getFirstName(),
                getLastName(),
                getUsername(),
                getNationalCode(),
                getBirthday()
        );
    }

    // Get user password for sign up
    private String getNationalCode() throws SQLException {
        return new InputString("Enter your national code\n" +
                "(Your password is your national code but you can change it): ",
                "Your national code must be 10 digit, at least one letter and one number.",
                "^\\d{10}$",
                UserRepo.getAllUsersNationalCodes(MainApp.getConnection())).getStringInput();
    }

    // Get user username for sign up
    private String getUsername() throws SQLException {
        return new InputString("Enter your username: ",
                "Your username not valid(use 8 to 20 alphanumeric characters).\nNotice: Maybe this username taken by other authors.",
                "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$",
                UserRepo.getAllUsersUsernames(MainApp.getConnection())).getStringInput();
    }

    // Get user last name for sign up
    private String getLastName() {
        return new InputString("Enter your last name: ",
                "Your last name must be 2 to 20 alphabetic characters.",
                "^[a-zA-Z]{2,20}$", null).getStringInput();
    }

    // Get user first name for sign up
    private String getFirstName() {
        return new InputString("Enter your first name: ",
                "Your first name must be 2 to 20 alphabetic characters.",
                "^[a-zA-Z]{2,20}$", null).getStringInput();
    }

    // Get user birthdate for sign up
    private Timestamp getBirthday() {
        String birthday = new InputString("Enter your birthdate like this format(4/11/1995)(day/month/year): ",
                "Your input is wrong",
                "^\\b([1-9]|1[0-2])\\b/\\b([1-9]|[1-2][1-9]|3[0-1])\\b/[1-2][0-9][0-9][0-9]$", null).getStringInput();
        String[] birthdayArray = birthday.split("/");
        return new Timestamp(new GregorianCalendar(
                Integer.parseInt(birthdayArray[2]),
                Integer.parseInt(birthdayArray[1]) - 1,
                Integer.parseInt(birthdayArray[0])
        ).getTime().getTime());
    }

    // This method take an author an add it to new Author list in database
    private void signUp(User user) throws SQLException {
        if(UserRepo.insertUser(MainApp.getConnection(), user) == 0) {
            System.out.println("Signed up is not successful");
        } else {
            System.out.println("You signed up successfully.\nNow you can login with your information.");
        }
    }
}
