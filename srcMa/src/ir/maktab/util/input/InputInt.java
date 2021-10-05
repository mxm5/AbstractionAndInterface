package ir.maktab.util.input;
/*
* InputInt for taking int inputs from console.
* */
public class InputInt extends Input {

    private int maxValue, minValue;
    private int[] unAllowedIntValues;
    private int intInput;

    public InputInt(String inputMessage, String warningMessage, int maxValue, int minValue,
                    int[] unAllowedIntValues) {
        super(inputMessage, warningMessage);
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.unAllowedIntValues = unAllowedIntValues;
    }

    public InputInt(String inputMessage, int maxValue, int minValue,
                    int[] unAllowedIntValues) {
        this(inputMessage, "Input value is not valid.\nPlease try again!", maxValue, minValue, unAllowedIntValues);
    }

    private void setIntInput() {
        System.out.print(getInputMessage());
        this.intInput = scanner.nextInt();
    }

    // This method validate int input
    @Override
    public boolean validations() {
        return this.intInput >= minValue &&
                this.intInput <= maxValue &&
                !equalUnAllowedValues();
    }

    // Check that input equals to any of the unAllowedValues elements.
    private boolean equalUnAllowedValues() {
        if (unAllowedIntValues != null)
            for (int unAllowedValue : unAllowedIntValues) if (intInput == unAllowedValue) return true;
        return false;
    }

    public int getIntInput() {
        while(true) {
            setIntInput();
            if(!validations()) System.out.println(getWarningMessage());
            else return intInput;
        }
    }
}
