import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.*;

public class project {

    // public static void main(String[] args) {
    // // Parse command-line arguments
    // if (args.length < 2) {
    // System.out.println("Usage: java project create|read|merge file1 file2
    // [outputFile]");
    // // return;
    // }

    // String operation = args[0];
    // String file1 = args[1];
    // String file2 = args[2];

    // switch (operation.toLowerCase()) {
    // case "create":
    // createFile(file1);
    // break;
    // case "read":
    // readFile(file1);
    // break;
    // case "merge":
    // if (args.length < 4) {
    // System.out.println("Please provide an output file for merge operation.");
    // return;
    // }
    // String outputFile = args[3];
    // mergeFiles(file1, file2, outputFile);
    // break;
    // default:
    // System.out.println("Invalid operation. Use create, read, or merge.");
    // }
    // }

    public static void createFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("This is a sample text file.\nYou can add more lines here.");
            System.out.println("File created successfully: " + filename);
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for detailed error information
        }
    }

    public static void readFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("Contents of " + filename + ":");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void mergeFiles(String file1, String file2, String outputFile) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1));
                BufferedReader reader2 = new BufferedReader(new FileReader(file2))) {

            String line;
            while ((line = reader1.readLine()) != null) {
                lines.add(line);
            }
            while ((line = reader2.readLine()) != null) {
                lines.add(line);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                for (String mergedLine : lines) {
                    writer.write(mergedLine);
                    writer.newLine();
                }
                System.out.println("Files merged successfully. Merged file: " + outputFile);
            } catch (IOException e) {
                System.out.println("Error writing to output file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Error reading input files: " + e.getMessage());
        }
    }
    // }

    public static void main(String[] args) {
        // Parse command-line arguments
        if (args.length < 2) {
            System.out.println("Usage: java project create|read|merge file1 file2 [outputFile]");
            return;
        }

        String operation = args[0];

        switch (operation.toLowerCase()) {
            case "create":
                if (args.length < 1) {
                    System.out.println("Please provide a filename for create operation.");
                    return;
                }
                String fileToCreate = args[1];
                createFile(fileToCreate);
                break;
            case "read":
                if (args.length < 1) {
                    System.out.println("Please provide a filename for read operation.");
                    return;
                }
                String fileToRead = args[1];
                readFile(fileToRead);
                break;
            case "merge":
                if (args.length < 2) {
                    System.out.println("Please provide two input files and an output file for merge operation.");
                    return;
                }
                String file1 = args[1];
                String file2 = args[2];
                String outputFile = args[3];
                mergeFiles(file1, file2, outputFile);
                break;
            default:
                System.out.println("Invalid operation. Use create, read, or merge.");
        }
    }
}
