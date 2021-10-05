package ir.maktab.util.input;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * InputInt for taking String inputs from console.
 * */
public class InputString extends Input{

    private String regex;
    private String[] unAllowedValues;
    private String stringInput;

    public InputString(String inputMessage, String warningMessage, String regex, String[] unAllowedValues) {
        super(inputMessage, warningMessage);
        this.regex = regex;
        this.unAllowedValues = unAllowedValues;
    }

    public InputString(String inputMessage, String regex, String[] unAllowedValues) {
        this(inputMessage, "Input value is not valid.\nPlease try again!", regex, unAllowedValues);
    }

    public InputString(String inputMessage, String regex) {
        this(inputMessage, "Input value is not valid.\nPlease try again!", regex, null);
    }

    public InputString(String inputMessage, String[] unAllowedValues) {
        this(inputMessage, "Input value is not valid.\nPlease try again!", "", unAllowedValues);
    }

    public InputString(String inputMessage) {
        this(inputMessage, "Input value is not valid.\nPlease try again!", "", null);
    }

    private void setStringInput() {
        System.out.print(getInputMessage());
        this.stringInput = scanner.nextLine();
    }

    // This method validate String input
    @Override
    public boolean validations() {
        return validateRegex() &&
                !equalUnAllowedValues();
    }

    // This method matches String input with regex
    private boolean validateRegex() {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(stringInput);
        return matcher.find();
    }

    // Check that input equals to any of the unAllowedValues elements.
    private boolean equalUnAllowedValues() {
        if (unAllowedValues != null)
            for (String unAllowedValue : unAllowedValues) if (stringInput.equals(unAllowedValue)) return true;
        return false;
    }

    public String getStringInput() {
        while(true) {
            setStringInput();
            if(!validations()) System.out.println(getWarningMessage());
            else return stringInput;
        }
    }
}
