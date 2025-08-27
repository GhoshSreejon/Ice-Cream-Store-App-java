import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * IceCreamStore class manages flavors and their stock.
 * Functions: add flavor, order flavor, check stock, list flavors.
 */
public class IceCreamStore {
    // Inventory: flavor name → stock quantity
    private Map<String, Integer> inventory;

    // Constructor: initializes empty inventory
    public IceCreamStore() {
        inventory = new HashMap<>();
    }

    /**
     * Add a new flavor with stock
     * @param flavor name of the flavor
     * @param stock initial stock (must be >= 0)
     */

    public void addFlavor(String flavor, int stock) {
        if (stock < 0) 
            throw new IllegalArgumentException("Stock cannot be negative");
        inventory.put(flavor, stock);
    }

    /**
     * Order a given quantity of a flavor
     * @param flavor flavor name
     * @param quantity how many units to order
     * @throws IllegalArgumentException if flavor doesn’t exist or invalid quantity
     * @throws IllegalStateException if stock is insufficient
     */

    public void orderFlavor(String flavor, int quantity) {
        // Flavor must exist in inventory
        if (!inventory.containsKey(flavor)) {
            throw new IllegalArgumentException("Flavor not available");
        }

        // Quantity must be positive
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        int currentStock = inventory.get(flavor);

        // Check if enough stock is available
        if (quantity > currentStock) {
            throw new IllegalStateException("Not enough stock for " + flavor);
        }

        // Reduce stock after successful order
        inventory.put(flavor, currentStock - quantity);
    }

    /**
     * Get stock of a flavor
     * @param flavor flavor name
     * @return current stock OR -1 if flavor not found
     */

    public int getStock(String flavor) {
        return inventory.getOrDefault(flavor, -1);
    }

    /**
     * List all flavors in the store
     * @return comma-separated list of flavors
     */

    public String listFlavors() {
        return String.join(", ", inventory.keySet());
    }

    // -------------------- DEMO MAIN METHOD FOR TEST --------------------
    // public static void main(String[] args) {
    //     IceCreamStore store = new IceCreamStore();

    //     // Add some flavors
    //     store.addFlavor("Vanilla", 10);
    //     store.addFlavor("Chocolate", 5);

    //     // Show available flavors
    //     System.out.println("Available flavors: " + store.listFlavors());

    //     // Check stock
    //     System.out.println("Vanilla stock: " + store.getStock("Vanilla"));

    //     // Place an order
    //     store.orderFlavor("Vanilla", 3);
    //     System.out.println("Vanilla stock after order: " + store.getStock("Vanilla"));

    //     // Try an invalid order (more than stock)
    //     try {
    //         store.orderFlavor("Chocolate", 10);
    //     } catch (Exception e) {
    //         System.out.println("Error ordering Chocolate: " + e.getMessage());
    //     }

    //     // Add new flavor and check list again
    //     store.addFlavor("Mango", 8);
    //     System.out.println("Updated flavors: " + store.listFlavors());
    // }

    
    
    // ----------------- MAIN METHOD TO INTERACT --------------------------
    public static void main(String[] args) {
        IceCreamStore store = new IceCreamStore();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("\n🍦 Welcome to Ice Cream Store! 🍦");

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Flavor");
            System.out.println("2. Order Flavor");
            System.out.println("3. Check Stock");
            System.out.println("4. List Flavors");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1: // Add Flavor
                    System.out.print("Enter flavor name: ");
                    String flavor = sc.nextLine();
                    System.out.print("Enter stock quantity: ");
                    int stock = Integer.parseInt(sc.nextLine());
                    try {
                        store.addFlavor(flavor, stock);
                        System.out.println(flavor + " added with stock " + stock);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2: // Order Flavor
                    System.out.print("Enter flavor to order: ");
                    String orderFlavor = sc.nextLine();
                    System.out.print("Enter quantity: ");
                    int qty = Integer.parseInt(sc.nextLine());
                    try {
                        store.orderFlavor(orderFlavor, qty);
                        System.out.println("Order successful! Remaining stock: " + store.getStock(orderFlavor));
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3: // Check Stock
                    System.out.print("Enter flavor name: ");
                    String checkFlavor = sc.nextLine();
                    int available = store.getStock(checkFlavor);
                    if (available == -1)
                        System.out.println("Flavor not found.");
                    else
                        System.out.println(checkFlavor + " stock: " + available);
                    break;

                case 4: // List Flavors
                    String flavors = store.listFlavors();
                    if (flavors.isEmpty())
                        System.out.println("No flavors available.");
                    else
                        System.out.println("Available flavors: " + flavors);
                    break;

                case 5: // Exit
                    running = false;
                    System.out.println("\nGoodbye! 🍨");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        sc.close();
    }

}
