package com.pluralsight;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class StoreApp {
    public static void main(String[] args) {
        ArrayList<Product> inventory = getInventory();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1 - List all products");
            System.out.println("2 - Lookup a product by its id");
            System.out.println("3 - Find all products within a price range");
            System.out.println("4 - Add a new product");
            System.out.println("5 - Quit the application");
            System.out.print("Enter command: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Collections.sort(inventory, Comparator.comparing(Product::getName));
                    System.out.println("\nWe carry the following inventory:");
                    for (int i = 0; i < inventory.size(); i++) {
                        Product p = inventory.get(i);
                        System.out.printf("id: %d %s - Price: $%.2f%n", p.getId(), p.getName(), p.getPrice());
                    }
                    break;

                case 2:
                    System.out.print("Enter product ID: ");
                    int searchId = scanner.nextInt();
                    boolean found = false;
                    for (int i = 0; i < inventory.size(); i++) {
                        Product p = inventory.get(i);
                        if (p.getId() == searchId) {
                            System.out.printf("Found: id: %d %s - Price: $%.2f%n", p.getId(), p.getName(), p.getPrice());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Product not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter minimum price: ");
                    double min = scanner.nextDouble();
                    System.out.print("Enter maximum price: ");
                    double max = scanner.nextDouble();
                    System.out.println("\nProducts in price range:");
                    boolean rangeFound = false;
                    for (int i = 0; i < inventory.size(); i++) {
                        Product p = inventory.get(i);
                        if (p.getPrice() >= min && p.getPrice() <= max) {
                            System.out.printf("id: %d %s - Price: $%.2f%n", p.getId(), p.getName(), p.getPrice());
                            rangeFound = true;
                        }
                    }
                    if (!rangeFound) {
                        System.out.println("No products found in this price range.");
                    }
                    break;

                case 4:
                    System.out.print("Enter new product ID: ");
                    int newId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter product name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double newPrice = scanner.nextDouble();

                    inventory.add(new Product(newId, newName, newPrice));
                    System.out.println("Product added successfully!");
                    break;

                case 5:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid command.");
            }
        }
        scanner.close();
    }

    public static ArrayList<Product> getInventory() {
        ArrayList<Product> inventory = new ArrayList<Product>();
        File file = new File("inventory.csv");

        if (file.exists()) {
            try {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split("\\|");
                    if (parts.length == 3) {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        double price = Double.parseDouble(parts[2].trim());
                        inventory.add(new Product(id, name, price));
                    }
                }
                fileScanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error reading file.");
            }
        } else {
            inventory.add(new Product(4567, "10' 2x4 (grade B)", 9.99));
            inventory.add(new Product(1234, "Hammer", 19.49));
            inventory.add(new Product(2345, "Box of nails", 9.29));
            inventory.add(new Product(3456, "Screwdriver set", 14.99));
            inventory.add(new Product(7890, "Tape measure", 7.49));
        }

        return inventory;
    }
}