package propertymanagementapp;

public class Property {

    private int id;
    private String address;
    private String type;
    private double rent;
    private int bedrooms;

    public Property(int id, String n, String g, double r, int m) {
        this.id = id;
        this.address = n;
        this.type = g;
        this.rent = r;
        this.bedrooms = m;
    }

    public Property(String n, String g, double r, int m) {
        this.address = n;
        this.type = g;
        this.rent = r;
        this.bedrooms = m;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

}