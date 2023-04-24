import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileListMaker {
    public static void main(String[] args) {
        // Initialize variables
        ArrayList<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean needsToBeSaved = false;
        String currentFilename = "";

        // Main program loop
        while (true) {
            // Display menu
            System.out.println("\nMenu:");
            System.out.println("A - Add an item to the list");
            System.out.println("D - Delete an item from the list");
            System.out.println("V - View the list");
            System.out.println("O - Open a list file from disk");
            System.out.println("S - Save the current list file to disk");
            System.out.println("C - Clear removes all the elements from the current list");
            System.out.println("Q - Quit the program");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().toUpperCase();

            // Handle user choices
            switch (choice) {
                case "A":
                    // Add an item to the list
                    System.out.print("Enter the item to add: ");
                    list.add(scanner.nextLine());
                    needsToBeSaved = true;
                    break;
                case "D":
                    // Delete an item from the list
                    System.out.print("Enter the index of the item to delete: ");
                    int index = Integer.parseInt(scanner.nextLine());
                    list.remove(index);
                    needsToBeSaved = true;
                    break;
                case "V":
                    // View the list
                    System.out.println("List:");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(i + ": " + list.get(i));
                    }
                    break;
                case "O":
                    // Open a list file from disk
                    if (needsToBeSaved) {
                        // Prompt to save the current list before opening a new one
                        System.out.print("Current list is unsaved. Do you want to save it? (Y/N): ");
                        String saveChoice = scanner.nextLine().toUpperCase();
                        if (saveChoice.equals("Y")) {
                            saveList(list, currentFilename, scanner);
                        }
                    }
                    System.out.print("Enter the filename to open: ");
                    currentFilename = scanner.nextLine();
                    list = loadList(currentFilename);
                    needsToBeSaved = false;
                    break;
                case "S":
                    // Save the current list file to disk
                    saveList(list, currentFilename, scanner);
                    needsToBeSaved = false;
                    break;
                case "C":
                    // Clear the current list
                    list.clear();
                    currentFilename = "";
                    needsToBeSaved = false;
                    break;
                case "Q":
                    // Quit the program
                    if (needsToBeSaved) {
                        // Prompt to save the current list before quitting
                        System.out.print("Current list is unsaved. Do you want to save it? (Y/N): ");
                        String saveChoice = scanner.nextLine().toUpperCase();
                        if (saveChoice.equals("Y")) {
                            saveList(list, currentFilename, scanner);
                        }
                    }
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    // Invalid option
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    // Load a list from a file
    public static ArrayList<String> loadList(String filename) {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line from the file and add it to the list
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return list;
    }

    // Save the list to a file
    public static void saveList(ArrayList<String> list, String currentFilename, Scanner scanner) {
        // If there is no current filename, prompt for a new one
        if (currentFilename.isEmpty()) {
            System.out.print("Enter a filename to save the list: ");
            currentFilename = scanner.nextLine();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(currentFilename))) {
            // Write each item from the list to the file
            for (String item : list) {
                bw.write(item);
                bw.newLine();
            }
            System.out.println("List saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}

