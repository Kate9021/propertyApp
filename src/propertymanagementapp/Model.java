package propertymanagementapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    private static Model instance = null;

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    private List<Property> properties;
    private List<Area> areas;
    private PropertyTableGateway propertyGateway;
    private AreaTableGateway areaGateway;

    private Model() {
        
        try{
            Connection conn = DBConnection.getInstance();
            this.propertyGateway = new PropertyTableGateway(conn);
            
            this.properties = this.propertyGateway.getProperties();
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Property> getProperties() {
        return new ArrayList<Property>(this.properties);
    }

    public boolean addProperty(Property p) {
        boolean result = false;
        try {
            int id = this.propertyGateway.insertProperty(
                    p.getAddress(), p.getDescription(),
                    p.getRent(), p.getBedrooms());
            if (id != -1){
                p.setId(id);
                this.properties.add(p);
                result = true;
            }  
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public boolean removeProperty(Property p) {
        boolean removed = false;
        
        try {
            removed = this.propertyGateway.deleteProperties(p.getId());
            if (removed){
                removed = this.properties.remove(p);
            }
        }
        catch (SQLException ex){
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return removed;
    }
    
    public Property findPropertyById(int id) {
        Property p = null;
        int i = 0;
        boolean found = false;
        while (i < this.properties.size() && !found){
            p = this.properties.get(i);
            if (p.getId() == id){
                found = true;}
            else{
                 i++;
            }
        }
        if (!found) {
            p = null;
        }
        return p;
    }
    
    boolean updateProperty(Property p) {
        boolean updated = false;
        
        try{
            updated = this.propertyGateway.updateProperty(p);
        }
        catch (SQLException ex){
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updated;
    }
    
    public boolean addArea(Area a){
        boolean result = false;
        try {
            int id = this.areaGateway.insertArea(a.getName(), a.getDescription(), a.getFacilities());
            if(id != -1){
                a.setId(id);
                this.areas.add(a);
                result = true;
            }
        }
        catch (SQLException ex){
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
        public List<Area> getAreas() {
           return this.areas;
       }
        
        Area findAreaById(int id) {
        Area a = null;
        int i = 0;
        boolean found = false;
        while (i < this.areas.size() && !found) {
            a = this.areas.get(i);
            if (a.getId() == id) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            a = null;
        }
        return a;
    }
        
    boolean updateArea(Area a) {
        boolean updated = false;

        try {
            updated = this.areaGateway.updateArea(a);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updated;
    }
    
     public boolean removeArea(Area a) {
        boolean removed = false;
        
        try {
            removed = this.areaGateway.deleteArea(a.getId());
            if (removed){
                removed = this.areas.remove(a);
            }
        }
        catch (SQLException ex){
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return removed;
    }
}