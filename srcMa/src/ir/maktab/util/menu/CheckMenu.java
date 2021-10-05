package ir.maktab.util.menu;

public class CheckMenu extends Menu{
    private final String message;
    private boolean isAccepted;

    public CheckMenu(String message) {
        super(new String[]{"Yes", "No"});
        this.message = message;
    }

    public boolean runMenu() {
        System.out.println("\n" + message);
        while(true) {
            show();
            switch (choose()) {
                case 1:
                    return true;
                case 2:
                    return false;
            }
        }
    }
}
