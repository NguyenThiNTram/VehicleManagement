package service;

import dao.VehicleDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Vehicle;

public class VehicleService {

    private final VehicleDAO dao;
    private final List<Vehicle> listVehicles = new ArrayList<>();
    private boolean isSaved = true;

    public VehicleService(VehicleDAO dao) {
        this.dao = dao;
        readFromFile();
    }

    public boolean isSaved() {
        return isSaved;
    }

    public boolean checkExist(String id) {
        return searchById(id) != null;
    }

    public void add(Vehicle x) {
        if (x != null && !checkExist(x.getIdVehicle())) {
            listVehicles.add(x);
            isSaved = false;
        }
    }

    public void update(Vehicle x) {
        for (int i = 0; i < listVehicles.size(); i++) {
            if (listVehicles.get(i).getIdVehicle().equalsIgnoreCase(x.getIdVehicle())) {
                listVehicles.set(i, x);
                isSaved = false;
                return;
            }
        }
    }

    public void delete(String id) {
        Vehicle v = searchById(id);
        if (v != null) {
            listVehicles.remove(v);
            isSaved = false;
        }
    }

    public Vehicle searchById(String id) {
        for (Vehicle v : listVehicles) {
            if (v.getIdVehicle().equalsIgnoreCase(id)) {
                return v;
            }
        }
        return null;
    }

    // Tên chứa chuỗi tìm kiếm, sắp xếp theo tên GIẢM DẦN
    public List<Vehicle> searchByName(String name) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : listVehicles) {
            if (v.getNameVehicle().toLowerCase().contains(name.toLowerCase())) {
                result.add(v);
            }
        }
        result.sort(Comparator.comparing(Vehicle::getNameVehicle,
                String.CASE_INSENSITIVE_ORDER).reversed());
        return result;
    }

    // Giá NHỎ HƠN giá nhập, sắp xếp theo giá GIẢM DẦN
    public List<Vehicle> searchByPrice(double price) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : listVehicles) {
            if (v.getPriceVehicle() < price) {
                result.add(v);
            }
        }
        result.sort(Comparator.comparingDouble(Vehicle::getPriceVehicle).reversed());
        return result;
    }

    public List<Vehicle> getAll() {
        return new ArrayList<>(listVehicles); // trả bản sao để bên ngoài không sửa trực tiếp
    }

    // Function 8: đọc thẳng từ file, không đụng đến danh sách đang làm việc
    public List<Vehicle> getFromFile() {
        try {
            return dao.readAll();
        } catch (IOException e) {
            System.out.println("Read file error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void readFromFile() {
        try {
            List<Vehicle> data = dao.readAll();
            listVehicles.clear();
            listVehicles.addAll(data);
            isSaved = true;
        } catch (IOException e) {
            System.out.println("Read file error: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try {
            dao.saveAll(listVehicles);
            isSaved = true;
            System.out.println("Save successfully!");
        } catch (IOException e) {
            System.out.println("Save file error: " + e.getMessage());
        }
    }

    public boolean checkExistInFile(String id) {
        for (Vehicle v : getFromFile()) {
            if (v.getIdVehicle().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }
}