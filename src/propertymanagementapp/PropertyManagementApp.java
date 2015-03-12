package propertymanagementapp;

import java.util.List;
import java.util.Scanner;

public class PropertyManagementApp {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int opt;
        
        Model model = Model.getInstance();
        
        Property p;
        do {
            System.out.println("1. Create new Property");
            System.out.println("2. Update existing Property");
            System.out.println("3. Delete existing Property");
            System.out.println("4. View all Properties");
            System.out.println("5. Exit");
            System.out.println();

            System.out.print("Enter option: ");
            String line = keyboard.nextLine();
            opt = Integer.parseInt(line);

            System.out.println("You chose option " + opt);
            switch (opt) {
                case 1: {
                    System.out.println("Creating property");
                    p = readProperty(keyboard);
                    if (model.addProperty(p)){
                        System.out.println("Property added to database");
                    }
                    else{
                        System.out.println("Property not added to database");
                    }
                    System.out.println();
                    break;
                }
                case 2: {
                    System.out.println("Editing property");
                    editProperty(keyboard, model);
                    break;
                }
                case 3: {
                    deleteProperty(keyboard, model);
                    break; 
                }
                case 4: {
                    System.out.println("Viewing properties");
                    viewProperties(model);
                    break;
                }
            }
        }
        while (opt != 5);
        System.out.println("Goodbye");
    }
    
    private static Property readProperty(Scanner keyb) {
        String address, description;
        int bedrooms;
        double rent;
        String line;

        address = getString(keyb, "Enter address: ");
        description = getString(keyb, "Enter Description: ");
        line = getString(keyb, "Enter rent: ");
        rent = Double.parseDouble(line);
        line = getString(keyb, "Enter number of bedrooms: ");
        bedrooms = Integer.parseInt(line);

        Property p = 
                new Property(address, description, rent, 
                        bedrooms);
        
        return p;
    }
    
    private static void deleteProperty(Scanner keyboard, Model model) {
        System.out.print("Enter the ID of the property to delete:");
        int id = Integer.parseInt(keyboard.nextLine());
        Property p;

        p = model.findPropertyById(id);
        if (p != null) {
            if (model.removeProperty(p)) {
                System.out.println("Property deleted");
            }
            else {
                System.out.println("Property not deleted");
            }
        }
        else {
            System.out.println("Property not found");
        }
    }


   private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private static void viewProperties(Model model) {
        List<Property> properties = model.getProperties();
        System.out.println();
        if (properties.isEmpty()){
            System.out.println("There are no properties in the database. ");
        }
        else {
            System.out.printf("%5s %20s %20s %10s %10s\n","id", "address", "description","rent", "bedrooms");
            for (Property pr : properties ) {
                System.out.printf("%5s %20s %20s %10s %10s\n",
                        pr.getId(),
                        pr.getAddress(),
                        pr.getDescription(),
                        pr.getRent(),
                        pr.getBedrooms());
            }
        }
        System.out.println();
    }

    private static void editProperty(Scanner kb, Model m) {
        System.out.print("Enter the id of the property to edit:");
        int id = Integer.parseInt(kb.nextLine());
        Property p;

        p = m.findPropertyById(id);
        if (p != null) {
            editPropertyDetails(kb, p);
            if (m.updateProperty(p)) {
                System.out.println("Property updated");
            }
            else {
                System.out.println("Property not updated");
            }
        }
        else {
            System.out.println("Property not found");
        }
    }
    
    private static void editPropertyDetails(Scanner keyboard, Property p) {
        String address, description;
        int bedrooms, id;
        double rent;
        String line; 
        
        address = getString(keyboard, "Enter address of property[" + p.getAddress() + "]: ");
        description = getString(keyboard, "Enter description of property[" + p.getDescription() + "]: ");
        String line1 = getString(keyboard, "Enter rent[" + p.getRent() + "]: ");
        String line2 = getString(keyboard, "Enter number of bedrooms[" + p.getBedrooms() + "]: ");
        
        if (address.length() !=0){
            p.setAddress(address);
        }
        if (description.length() !=0){
            p.setDescription(description);
        }
        if (line1.length() !=0){
            rent = Double.parseDouble(line1);
            p.setRent(rent);
        }
        if (line2.length() !=0){
            bedrooms = Integer.parseInt(line2);
            p.setBedrooms(bedrooms);   
        }
    }
}