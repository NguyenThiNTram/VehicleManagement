package controller;

import java.util.List;
import model.Vehicle;
import service.VehicleService;
import util.Acceptable;
import util.Inputter;
import view.Menu;
import view.VehicleView;

public class VehicleController {

    private final VehicleService service;
    private final VehicleView view;
    private final Menu menu;
    private final Inputter ndl;

    public VehicleController(VehicleService service, VehicleView view, Menu menu, Inputter ndl) {
        this.service = service;
        this.view = view;
        this.menu = menu;
        this.ndl = ndl;
    }

    public void run() {
        int choice;
        do {
            choice = menu.showMainMenu();
            switch (choice) {
                case 1: addVehicle(); break;
                case 2: checkExistVehicle(); break;
                case 3: updateVehicle(); break;
                case 4: deleteVehicle(); break;
                case 5: searchVehicle(); break;
                case 6: displayVehicle(); break;
                case 7: service.saveToFile(); break;
                case 8: view.showAll(service.getFromFile()); break;
                case 9: quit(); break;
            }
        } while (choice != 9);
    }

    // ---------- Function 1 ----------
    private void addVehicle() {
        do {
            String id;
            while (true) {
                id = ndl.getString("Enter ID (V + 3 digits): ", Acceptable.ID_VALID,
                        "ID must be V followed by 3 digits (e.g. V001)!");
                if (!service.checkExist(id)) {
                    break;
                }
                System.out.println("ID already exists!");
            }
            String name = ndl.getString("Enter name: ", Acceptable.NAME_VALID,
                    "Name must be 2-30 letters/digits!");
            String color = ndl.getString("Enter color: ", Acceptable.COLOR_VALID,
                    "Color must be 2-15 letters!");
            double price = inputPrice("Enter price: ");
            String brand = ndl.getString("Enter brand: ", Acceptable.BRAND_VALID,
                    "Brand must be 2-20 letters/digits!");
            String type = ndl.getString("Enter type: ", Acceptable.TYPE_VALID,
                    "Type must be 2-15 letters!");
            int year = Integer.parseInt(ndl.getString("Enter product year: ",
                    Acceptable.YEAR_VALID, "Year must be 1900-2099!"));

            service.add(new Vehicle(id, name, color, price, brand, type, year));
            System.out.println(service.checkExist(id) ? "Add vehicle successfully!" : "Add vehicle failed!");
        } while (ndl.getYesNo("Do you want to continue creating new vehicle?"));
    }

    // ---------- Function 2 ----------
    private void checkExistVehicle() {
        String id = ndl.getString("Enter ID to check: ");
        System.out.println(service.checkExistInFile(id) ? "Existed Vehicle" : "No Vehicle Found!");
    }

    // ---------- Function 3 ----------
    private void updateVehicle() {
        String id = ndl.getString("Enter ID to update: ");
        Vehicle old = service.searchById(id);
        if (old == null) {
            System.out.println("Vehicle does not exist");
            return;
        }
        System.out.println("Leave blank to keep the current value.");

        String name = ndl.getOptionalString("New name [" + old.getNameVehicle() + "]: ",
                Acceptable.NAME_VALID, "Name must be 2-30 letters/digits!");
        String color = ndl.getOptionalString("New color [" + old.getColorVehicle() + "]: ",
                Acceptable.COLOR_VALID, "Color must be 2-15 letters!");
        double price = inputOptionalPrice("New price [" + old.getPriceVehicle() + "]: ",
                old.getPriceVehicle());
        String brand = ndl.getOptionalString("New brand [" + old.getBrandVehicle() + "]: ",
                Acceptable.BRAND_VALID, "Brand must be 2-20 letters/digits!");
        String type = ndl.getOptionalString("New type [" + old.getType() + "]: ",
                Acceptable.TYPE_VALID, "Type must be 2-15 letters!");
        String yearStr = ndl.getOptionalString("New product year [" + old.getProductYear() + "]: ",
                Acceptable.YEAR_VALID, "Year must be 1900-2099!");

        Vehicle updated = new Vehicle(
                old.getIdVehicle(),
                name.isEmpty() ? old.getNameVehicle() : name,
                color.isEmpty() ? old.getColorVehicle() : color,
                price,
                brand.isEmpty() ? old.getBrandVehicle() : brand,
                type.isEmpty() ? old.getType() : type,
                yearStr.isEmpty() ? old.getProductYear() : Integer.parseInt(yearStr));

        service.update(updated);
        System.out.println("Update vehicle successfully!");
    }

    // ---------- Function 4 ----------
    private void deleteVehicle() {
        String id = ndl.getString("Enter ID to delete: ");
        if (!service.checkExist(id)) {
            System.out.println("Vehicle does not exist");
            return;
        }
        view.showAll(List.of(service.searchById(id)));
        if (ndl.getYesNo("Are you sure you want to delete this vehicle?")) {
            service.delete(id);
            System.out.println(service.checkExist(id) ? "Delete failed!" : "Delete vehicle successfully!");
        } else {
            System.out.println("Delete canceled!");
        }
    }

    // ---------- Function 5 ----------
    private void searchVehicle() {
        int choice = menu.showSearchMenu();
        if (choice == 1) {
            String id = ndl.getString("Enter ID to search: ");
            Vehicle v = service.searchById(id);
            view.showAll(v == null ? null : List.of(v));
        } else if (choice == 2) {
            String name = ndl.getString("Enter name to search: ");
            view.showAll(service.searchByName(name));
        }
    }

    // ---------- Function 6 ----------
    private void displayVehicle() {
        int choice = menu.showDisplayMenu();
        if (choice == 1) {
            view.showAll(service.getAll());
        } else if (choice == 2) {
            double price = inputPrice("Enter price: ");
            view.showAll(service.searchByPrice(price));
        }
    }

    // ---------- Quit ----------
    private void quit() {
        if (!service.isSaved() && ndl.getYesNo("Data has changed. Do you want to save before quitting?")) {
            service.saveToFile();
        }
        System.out.println("Goodbye!");
    }

    // ---------- Helpers ----------
    private double inputPrice(String mess) {
        while (true) {
            double price = ndl.getDouble(mess);
            if (price > 0) {
                return price;
            }
            System.out.println("Price must be greater than 0!");
        }
    }

    // Để trống = giữ giá cũ
    private double inputOptionalPrice(String mess, double oldPrice) {
        while (true) {
            String s = ndl.getOptionalString(mess, Acceptable.PRICE_VALID,
                    "Please enter a positive number!");
            if (s.isEmpty()) {
                return oldPrice;
            }
            double price = Double.parseDouble(s);
            if (price > 0) {
                return price;
            }
            System.out.println("Price must be greater than 0!");
        }
    }
}