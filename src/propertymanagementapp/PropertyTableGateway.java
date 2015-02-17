package propertymanagementapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PropertyTableGateway {
    private static final String TABLE_NAME = "property";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_RENT = "rent";
    private static final String COLUMN_BEDROOMS = "bedrooms";

    private Connection mConnection;

    public PropertyTableGateway(Connection connection) {
        mConnection = connection;
    }

    public int insertProperty(String n, String e, double m, int sn) throws SQLException {
        Property p = null;

        String query;       // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;
        int id = -1;

        // the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_ADDRESS + ", " +
                COLUMN_TYPE + ", " +
                COLUMN_RENT + ", " +
                COLUMN_BEDROOMS + ") "
                + "VALUES (?, ?, ?, ?)";

        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, n);
        stmt.setString(2, e);
        stmt.setDouble(3, m);
        stmt.setInt(4, sn);

        //  execute the query and make sure that one and only one row was inserted into the database
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            // if one row was inserted, retrieve the id assigned to that row and create a Property object to return
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();

            id = keys.getInt(1);
        }

        // return the id of the property created
        return id;
    }
    
    /*boolean deleteProperties(int id) {
        
    }*/
    
    boolean deleteProperties(int id)throws SQLException{
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        
        //the required SQL DELETE statement with place holders for the ID of the property
        query = "DELETE FROM" + TABLE_NAME + "WHERE" + COLUMN_ID + " = ";
        
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1,id);
        
        numRowsAffected = stmt.executeUpdate();
        
        return (numRowsAffected == 1);
    }
    
    public List<Property> getProperties() throws SQLException {
        String query;                   // the SQL query to execute
        Statement stmt;                 // the java.sql.Statement object used to execute the SQL query
        ResultSet rs;                   // the java.sql.ResultSet representing the result of SQL query
        List<Property> properties;   // the java.util.List containing the Property objects created for each row
                                        // in the result of the query the id of a programmer

        String address, type;
        int id, bedrooms;
        double rent;

        Property p;                   // a Property object created from a row in the result of the query

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

        // iterate through the result set, extracting the data from each row
        // and storing it in a Property object, which is inserted into an initially
        // empty ArrayList
        properties = new ArrayList<Property>();
        
        while (rs.next()) {
            id = rs.getInt(COLUMN_ID);
            address = rs.getString(COLUMN_ADDRESS);
            type = rs.getString(COLUMN_TYPE);
            rent = rs.getDouble(COLUMN_RENT);
            bedrooms = rs.getInt(COLUMN_BEDROOMS);

            p = new Property(id, address, type, rent, bedrooms);
            properties.add(p);
        }

        // return the list of Property objects retrieved
        return properties;
    }
}