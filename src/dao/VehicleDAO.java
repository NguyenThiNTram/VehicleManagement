package dao;

import java.io.IOException;
import java.util.List;
import model.Vehicle;

public interface VehicleDAO {

    List<Vehicle> readAll() throws IOException;

    void saveAll(List<Vehicle> list) throws IOException;
}