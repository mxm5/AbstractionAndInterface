package ir.maktab.util.menu;

import ir.maktab.MainApp;
import ir.maktab.domain.User;
import ir.maktab.repo.UserRepo;
import ir.maktab.util.input.InputString;

import java.lang.reflect.Field;
import java.sql.SQLException;

public class EditUserInformationMenu extends Menu{
    User user;

    public EditUserInformationMenu(User user) {
        super(new String[]{"Edit first name", "Edit last name", "Edit username", "Edit password", "Edit national code", "Back"});
        this.user = user;
    }

    public void runMenu() throws SQLException {
        while (true) {
            show();
            switch (choose()) {
                case 1:
                    editFirstName();
                    break;
                case 2:
                    editLastName();
                    break;
                case 3:
                    editUsername();
                    break;
                case 4:
                    editPassword();
                    break;
                case 5:
                    editNationalCode();
                    break;
                case 6:
                    return;
            }
        }
    }



    private void editNationalCode() throws SQLException {
        user.setNationalCode(new InputString("Enter your national code\n" +
                "(Your password is your national code but you can change it): ",
                "Your national code must be 10 digit, at least one letter and one number.",
                "^\\d{10}$",
                UserRepo.getAllUsersNationalCodes(MainApp.getConnection())).getStringInput());
        UserRepo.updateUser(MainApp.getConnection(), user);

        Class<User> userClass = User.class;
        Field[] fields = userClass.getFields();

    }

    private void editFirstName() throws SQLException {
        user.setFirstName( new InputString("Enter your new first name: ",
                "Your first name must be 2 to 20 alphabetic characters.",
                "^[a-zA-Z]{2,20}$", null).getStringInput());
        UserRepo.updateUser(MainApp.getConnection(), user);
    }

    private void editLastName() throws SQLException {
        user.setLastName(new InputString("Enter your last name: ",
                "Your last name must be 2 to 20 alphabetic characters.",
                "^[a-zA-Z]{2,20}$", null).getStringInput());
        UserRepo.updateUser(MainApp.getConnection(), user);
    }

    private void editUsername() throws SQLException {
        user.setUsername(new InputString("Enter your username: ",
                "Your username not valid(use 8 to 20 alphanumeric characters).\nNotice: Maybe this username taken by other authors.",
                "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$",
                UserRepo.getAllUsersUsernames(MainApp.getConnection())).getStringInput());
        UserRepo.updateUser(MainApp.getConnection(), user);
    }

    private void editPassword() throws SQLException {
        user.setPassword(new InputString("Enter your password: ",
                "Your password must be minimum 8 characters, at least one letter and one number.",
                "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", null).getStringInput());
        UserRepo.updateUser(MainApp.getConnection(), user);
    }
}