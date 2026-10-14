package main;

import controller.VehicleController;
import dao.VehicleFileDAO;
import service.VehicleService;
import util.Inputter;
import view.Menu;
import view.VehicleView;

public class Program {

    public static void main(String[] args) {
        Inputter ndl = new Inputter();                       // chỉ 1 Scanner cho toàn chương trình
        VehicleService service = new VehicleService(new VehicleFileDAO("vehicle.dat"));
        VehicleView view = new VehicleView();
        Menu menu = new Menu(ndl);

        new VehicleController(service, view, menu, ndl).run();
    }
}