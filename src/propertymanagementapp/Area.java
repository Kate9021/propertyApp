package propertymanagementapp;

public class Area {
    
    private int id;
    private String name;
    private String description;
    private String facilities;
    private int noOfProperties;    
    
    public Area(int id, String n, String d, String f, int nP){
        this.id = id;
        this.name = n;
        this.description = d;
        this.facilities = f;
        this.noOfProperties = nP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public int getNoOfProperties() {
        return noOfProperties;
    }

    public void setNoOfProperties(int noOfProperties) {
        this.noOfProperties = noOfProperties;
    }
    
    
    
}
