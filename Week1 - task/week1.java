import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    String name;
    double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    // getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Salary: " + salary;
    }
}

class EmployeeManagementSystem {
    private List<Employee> employees;
    private Scanner scanner;

    public EmployeeManagementSystem() {
        employees = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadEmployeesFromFile(); // Load existing employees from file
    }

    private void loadEmployeesFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("employees.dat"))) {
            employees = (List<Employee>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Ignore if the file is not found or cannot be read
        }
    }

    private void saveEmployeesToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employees.dat"))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNextEmployeeId() {
        return employees.isEmpty() ? 1 : employees.get(employees.size() - 1).getId() + 1;
    }

    private double getValidSalary() {
        while (true) {
            try {
                System.out.print("Enter employee salary: ");
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid salary.");
                scanner.next(); // Consume the invalid input to avoid an infinite loop
            }
        }
    }

    public void addEmployee() {
        int id = getNextEmployeeId();
        System.out.print("Enter employee name: ");
        String name = scanner.next();
        double salary = getValidSalary();

        Employee employee = new Employee(id, name, salary);
        employees.add(employee);
        System.out.println("Employee added successfully!");
        saveEmployeesToFile();
    }

    public void viewAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            employees.forEach(System.out::println);
        }
    }

    public void updateEmployee() {
        System.out.print("Enter the ID of the employee to update: ");
        int idToUpdate = scanner.nextInt();

        for (Employee employee : employees) {
            if (employee.getId() == idToUpdate) {
                System.out.print("Enter new name: ");
                employee.name = scanner.next();
                employee.salary = getValidSalary();
                System.out.println("Employee updated successfully!");
                saveEmployeesToFile();
                return;
            }
        }

        System.out.println("Employee not found with ID: " + idToUpdate);
    }

    public void searchEmployee() {
        System.out.print("Enter the ID of the employee to search: ");
        int idToSearch = scanner.nextInt();

        for (Employee employee : employees) {
            if (employee.getId() == idToSearch) {
                System.out.println("Employee found: " + employee);
                return;
            }
        }

        System.out.println("Employee not found with ID: " + idToSearch);
    }
}

public class week1 {
    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEmployee Management System Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Search Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input to avoid an infinite loop
                continue;
            }

            switch (choice) {
                case 1:
                    ems.addEmployee();
                    break;
                case 2:
                    ems.viewAllEmployees();
                    break;
                case 3:
                    ems.updateEmployee();
                    break;
                case 4:
                    ems.searchEmployee();
                    break;
                case 5:
                    System.out.println("Exiting Employee Management System. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
