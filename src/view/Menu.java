package view;

import util.Inputter;

public class Menu {

    private final Inputter ndl;

    public Menu(Inputter ndl) {
        this.ndl = ndl;
    }

    public int showMainMenu() {
        System.out.println();
        System.out.println("========= VEHICLE MANAGEMENT =========");
        System.out.println("1. Add new vehicle");
        System.out.println("2. Check exist vehicle");
        System.out.println("3. Update vehicle");
        System.out.println("4. Delete vehicle");
        System.out.println("5. Search vehicle");
        System.out.println("6. Display vehicle list");
        System.out.println("7. Save all vehicles to file");
        System.out.println("8. Print all vehicles from file");
        System.out.println("9. Quit");
        return getChoice("Your choice (1-9): ", 1, 9);
    }

    public int showSearchMenu() {
        System.out.println("--- SEARCH VEHICLE ---");
        System.out.println("1. Search by ID_Vehicle");
        System.out.println("2. Search by Name_Vehicle");
        System.out.println("3. Back to main menu");
        return getChoice("Your choice (1-3): ", 1, 3);
    }

    public int showDisplayMenu() {
        System.out.println("--- DISPLAY VEHICLE ---");
        System.out.println("1. Display all vehicle");
        System.out.println("2. Show by price");
        System.out.println("3. Back to main menu");
        return getChoice("Your choice (1-3): ", 1, 3);
    }

    private int getChoice(String mess, int min, int max) {
        while (true) {
            int choice = Integer.parseInt(
                    ndl.getString(mess, "^\\d{1,2}$", "Please enter a number!"));
            if (choice >= min && choice <= max) {
                return choice;
            }
            System.out.println("Choice must be from " + min + " to " + max + "!");
        }
    }
}