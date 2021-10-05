package ir.maktab.domain;

import java.sql.Timestamp;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String nationalCode;
    private Timestamp birthday;

    public User(int id, String firstName, String lastName, String username, String password, String nationalCode, Timestamp birthday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.nationalCode = nationalCode;
        this.birthday = birthday;
    }

    // constructor for the first time user sign up
    public User(String firstName, String lastName, String username, String nationalCode, Timestamp birthday) {
        this(0, firstName, lastName, username, nationalCode, nationalCode, birthday);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }
}
