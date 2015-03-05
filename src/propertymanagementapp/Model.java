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
    private PropertyTableGateway propertyGateway;

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
}

