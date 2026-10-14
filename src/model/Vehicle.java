package model;

import java.io.Serializable;

public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idVehicle;
    private String nameVehicle;
    private String colorVehicle;
    private double priceVehicle;
    private String brandVehicle;
    private String type;
    private int productYear;

    public Vehicle() {
    }

    public Vehicle(String idVehicle, String nameVehicle, String colorVehicle,
                   double priceVehicle, String brandVehicle, String type, int productYear) {
        this.idVehicle = idVehicle;
        this.nameVehicle = nameVehicle;
        this.colorVehicle = colorVehicle;
        this.priceVehicle = priceVehicle;
        this.brandVehicle = brandVehicle;
        this.type = type;
        this.productYear = productYear;
    }

    public String getIdVehicle() { return idVehicle; }
    public void setIdVehicle(String idVehicle) { this.idVehicle = idVehicle; }

    public String getNameVehicle() { return nameVehicle; }
    public void setNameVehicle(String nameVehicle) { this.nameVehicle = nameVehicle; }

    public String getColorVehicle() { return colorVehicle; }
    public void setColorVehicle(String colorVehicle) { this.colorVehicle = colorVehicle; }

    public double getPriceVehicle() { return priceVehicle; }
    public void setPriceVehicle(double priceVehicle) { this.priceVehicle = priceVehicle; }

    public String getBrandVehicle() { return brandVehicle; }
    public void setBrandVehicle(String brandVehicle) { this.brandVehicle = brandVehicle; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getProductYear() { return productYear; }
    public void setProductYear(int productYear) { this.productYear = productYear; }

    @Override
    public String toString() {
        return String.format("%-8s|%-15s|%-10s|%-12.2f|%-12s|%-10s|%-6d",
                idVehicle, nameVehicle, colorVehicle, priceVehicle,
                brandVehicle, type, productYear);
    }
}