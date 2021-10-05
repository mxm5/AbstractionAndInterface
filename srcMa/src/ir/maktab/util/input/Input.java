package ir.maktab.util.input;

import java.util.Scanner;
/*
* Input an abstract class for creating other input taking classes. validation() method is only one abstract method of this class
* for validating input
* */
public abstract class Input {
    public Scanner scanner;
    private final String inputMessage;
    private final String warningMessage;

    public Input(String inputMessage, String warningMessage) {
        scanner = new Scanner(System.in);
        this.inputMessage = inputMessage;
        this.warningMessage = warningMessage;
    }

    public abstract boolean validations();

    public String getWarningMessage() {
        return warningMessage;
    }

    public String getInputMessage() {
        return inputMessage;
    }
}
