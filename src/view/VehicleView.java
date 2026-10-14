package view;

import java.util.List;
import model.Vehicle;

public class VehicleView {

    private static final String LINE = "-".repeat(79);

    public void showAll(List<Vehicle> l) {
        if (l == null || l.isEmpty()) {
            System.out.println("No vehicle found!");
            return;
        }
        System.out.println(LINE);
        System.out.printf("%-8s|%-15s|%-10s|%-12s|%-12s|%-10s|%-6s%n",
                "ID", "Name", "Color", "Price", "Brand", "Type", "Year");
        System.out.println(LINE);
        for (Vehicle v : l) {
            System.out.println(v);
        }
        System.out.println(LINE);
    }
}