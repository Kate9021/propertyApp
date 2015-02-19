package propertymanagementapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    private static Model instance = null;

    public static /*synchronized*/ Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    private List<Property> properties;
    private PropertyTableGateway gateway;

    private Model() {
        
        try{
            Connection conn = DBConnection.getInstance();
            this.gateway = new PropertyTableGateway(conn);
            
            this.properties = this.gateway.getProperties();
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    public boolean addProperties(Property p) {
        boolean result = false;
        try {
            int id = this.gateway.insertProperty(
                    p.getAddress(), p.getType(),
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
    
    //public boolean removeProperties(Property p) {
      //  return this.properties.remove(p);
    //}

  
    
    public boolean removeProperties(Property p) {
        boolean removed = false;
        
        try {
            removed = this.gateway.deleteProperties(p.getId());
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
    
    boolean updateProperties(Property p) {
        boolean updated = false;
        
        try{
            updated = this.gateway.updateProperties(p);
        }
        catch (SQLException ex){
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updated;
    }
}

