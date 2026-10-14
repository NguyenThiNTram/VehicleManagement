package dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Vehicle;

public class VehicleFileDAO implements VehicleDAO {

    private final String pathFile;

    public VehicleFileDAO(String pathFile) {
        this.pathFile = pathFile;
    }

    @Override
    public List<Vehicle> readAll() throws IOException {
        List<Vehicle> result = new ArrayList<>();
        File f = new File(pathFile);
        if (!f.exists()) {
            return result;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            while (true) {
                result.add((Vehicle) ois.readObject());
            }
        } catch (EOFException e) {
            // hết file -> kết thúc bình thường
        } catch (ClassNotFoundException e) {
            throw new IOException(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void saveAll(List<Vehicle> list) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathFile))) {
            for (Vehicle v : list) {
                oos.writeObject(v);
            }
        }
    }
}