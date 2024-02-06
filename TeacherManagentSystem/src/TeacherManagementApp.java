import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeacherManagementApp {
    static final List<Teacher> teachers = new ArrayList<>();

    public static void main(String[] args) {
        FileHandler.loadRecords();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            displayMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    showAllTeachers();
                    break;
                case "2":
                    addTeacher(scanner);
                    break;
                case "3":
                    filterTeachers(scanner);
                    break;
                case "4":
                    searchTeacher(scanner);
                    break;
                case "5":
                    updateTeacher(scanner);
                    break;
                case "6":
                    deleteTeacher(scanner);
                    break;
                case "7":
                    displayAverageNumberOfClasses();
                    break;
                case "8":
                    displayTeacherSalaries();
                    break;
                case "9":
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
        FileHandler.saveRecords();
    }


    private static void displayMenu() {
        System.out.println("\nTeacher Management Application");
        System.out.println("1. Show all teachers");
        System.out.println("2. Add a teacher");
        System.out.println("3. Filter teachers");
        System.out.println("4. Search for a teacher");
        System.out.println("5. Update a teacher's record");
        System.out.println("6. Delete a teacher");
        System.out.println("7. The average number of classes the teachers take.");
        System.out.println("8. Display teacher salaries");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void displayTeacherSalaries() {
        System.out.println("\nTeacher Salaries:");
        for (Teacher teacher : teachers) {
            double salary = teacher.calculateSalary();
            System.out.println(teacher.getFullName() + ": ₹" + salary);
        }
    }

    private static void showAllTeachers() {
        System.out.println("\nAll Teachers:");
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    private static int calculateAge(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        return Period.between(dateOfBirth, now).getYears();
    }

    private static void addTeacher(Scanner scanner) {
        try {
            System.out.println("\nAdding a new teacher:");
            System.out.print("Enter full name: ");
            String fullName = scanner.nextLine();

            // Validate name input
            if (fullName.trim().isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
                return;
            }

            LocalDate dateOfBirth = null;
            boolean validDate = false;
            while (!validDate) {
                System.out.print("Enter date of birth (YYYY-MM-DD): ");
                String dobInput = scanner.nextLine();
                try {
                    dateOfBirth = LocalDate.parse(dobInput);
                    validDate = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use the format YYYY-MM-DD.");
                }
            }

            int numberOfClasses = 0;
            boolean validNumberOfClasses = false;
            while (!validNumberOfClasses) {
                System.out.print("Enter number of classes: ");
                String classesInput = scanner.nextLine();
                try {
                    numberOfClasses = Integer.parseInt(classesInput);
                    if (numberOfClasses >= 0) {
                        validNumberOfClasses = true;
                    } else {
                        System.out.println("Number of classes cannot be negative. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            }

            System.out.print("Enter specialization: ");
            String specialization = scanner.nextLine();

            // Check if a teacher with the same attributes already exists
            for (Teacher teacher : teachers) {
                if (teacher.getFullName().equalsIgnoreCase(fullName) &&
                        teacher.getDateOfBirth().equals(dateOfBirth) &&
                        teacher.getNumberOfClasses() == numberOfClasses &&
                        teacher.getSpecialization().equalsIgnoreCase(specialization)) {
                    System.out.println("A teacher with the same attributes already exists. Please enter different attributes.");
                    return;
                }
            }

            // Calculate age
            int age = calculateAge(dateOfBirth);

            Teacher newTeacher = new Teacher(fullName, age, dateOfBirth, numberOfClasses, specialization);
            teachers.add(newTeacher);
            System.out.println("Teacher added successfully!");

            // Save the new teacher record to file
            FileHandler.saveRecords();
        } catch (Exception e) {
            System.out.println("An error occurred during adding the new teacher: " + e.getMessage());
        }
    }



    private static void filterTeachers(Scanner scanner) {
        System.out.println("\nFilter Teachers:");
        System.out.println("1. Filter by Age");
        System.out.println("2. Filter by Number of Classes");
        System.out.println("3. Filter by Specialization");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                filterByAge(scanner);
                break;
            case "2":
                filterByNumberOfClasses(scanner);
                break;
            case "3":
                filterBySpecialization(scanner);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    private static void filterByAge(Scanner scanner) {
        int lowerBoundAge;
        int upperBoundAge;
        boolean foundAgeRange = false;
        do {
            try {
                System.out.print("Enter lower bound of age range: ");
                lowerBoundAge = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter upper bound of age range: ");
                upperBoundAge = Integer.parseInt(scanner.nextLine());
                if (upperBoundAge <= lowerBoundAge) {
                    System.out.println("Upper bound must be greater than the lower bound. Please try again.");
                } else {
                    foundAgeRange = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                lowerBoundAge = upperBoundAge = 0;
            }
        } while (!foundAgeRange);

        System.out.println("Filtered Teachers by Age:");
        for (Teacher teacher : teachers) {
            int age = teacher.getAge();
            if (age >= lowerBoundAge && age <= upperBoundAge) {
                System.out.println(teacher);
                foundAgeRange = true;
            }
        }
        if (!foundAgeRange) {
            System.out.println("No teachers found.");
        }
    }

    private static void filterByNumberOfClasses(Scanner scanner) {
        int lowerBoundClasses;
        int upperBoundClasses;
        boolean foundClassRange = false;
        do {
            try {
                System.out.print("Enter lower bound of number of classes range: ");
                lowerBoundClasses = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter upper bound of number of classes range: ");
                upperBoundClasses = Integer.parseInt(scanner.nextLine());
                if (upperBoundClasses <= lowerBoundClasses) {
                    System.out.println("Upper bound must be greater than the lower bound. Please try again.");
                } else {
                    foundClassRange = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                lowerBoundClasses = upperBoundClasses = 0;
            }
        } while (!foundClassRange);

        System.out.println("Filtered Teachers by Number of Classes:");
        for (Teacher teacher : teachers) {
            int numClasses = teacher.getNumberOfClasses();
            if (numClasses >= lowerBoundClasses && numClasses <= upperBoundClasses) {
                System.out.println(teacher);
                foundClassRange = true;
            }
        }
        if (!foundClassRange) {
            System.out.println("No teachers found.");
        }
    }

    private static void filterBySpecialization(Scanner scanner) {
        System.out.print("Enter specialization to filter: ");
        String specialization = scanner.nextLine().trim().toLowerCase();
        boolean foundSpecialization = false;

        System.out.println("Filtered Teachers by Specialization:");
        for (Teacher teacher : teachers) {
            if (teacher.getSpecialization().toLowerCase().contains(specialization)) {
                System.out.println(teacher);
                foundSpecialization = true;
            }
        }
        if (!foundSpecialization) {
            System.out.println("No teachers found with the given specialization.");
        }
    }



    private static void searchTeacher(Scanner scanner) {
        try {
            System.out.println("\nSearch Teacher:");
            System.out.print("Enter full name or keyword to search for: ");
            String keyword = scanner.nextLine().toLowerCase(); // Convert input to lowercase for case-insensitive search
            boolean found = false;
            System.out.println("Matching Teachers:");
            for (Teacher teacher : teachers) {
                if (teacher.getFullName().toLowerCase().contains(keyword)) {
                    System.out.println(teacher);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No matching teachers found!");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during the search operation: " + e.getMessage());
        }
    }


    private static void displayAverageNumberOfClasses() {
        if (teachers.isEmpty()) {
            System.out.println("No teachers available.");
            return;
        }
        int totalNumberOfClasses = 0;
        for (Teacher teacher : teachers) {
            totalNumberOfClasses += teacher.getNumberOfClasses();
        }
        double averageNumberOfClasses = (double) totalNumberOfClasses / teachers.size();
        System.out.println("Average number of classes taken by teachers: " + averageNumberOfClasses);
    }


    private static void updateTeacher(Scanner scanner) {
        try {
            System.out.println("\nUpdate Teacher:");
            showAllTeachers();
            System.out.print("Enter full name of the teacher to update: ");
            String fullName = scanner.nextLine();
            boolean found = false;
            for (Teacher teacher : teachers) {
                if (teacher.getFullName().equalsIgnoreCase(fullName)) {
                    System.out.println("Select fields to update (enter 'done' when finished):");
                    System.out.println("1. Full Name");
                    System.out.println("2. Date of Birth");
                    System.out.println("3. Number of Classes");
                    System.out.println("4. Specialization");
                    String choice;
                    while (true) {
                        System.out.print("Enter field number to update: ");
                        choice = scanner.nextLine();
                        if (choice.equals("done")) {
                            break;
                        }
                        switch (choice) {
                            case "1":
                                System.out.print("Enter new full name: ");
                                String newFullName = scanner.nextLine();
                                if (!newFullName.isEmpty()) {
                                    teacher.setFullName(newFullName);
                                } else {
                                    System.out.println("Full name cannot be empty. Please try again.");
                                }
                                break;
                            case "2":
                                System.out.print("Enter new date of birth (YYYY-MM-DD): ");
                                try {
                                    LocalDate newDateOfBirth = LocalDate.parse(scanner.nextLine());
                                    teacher.setDateOfBirth(newDateOfBirth);
                                } catch (DateTimeParseException e) {
                                    System.out.println("Invalid date format. Please use the format YYYY-MM-DD.");
                                }
                                break;
                            case "3":
                                System.out.print("Enter new number of classes: ");
                                try {
                                    int newNumberOfClasses = Integer.parseInt(scanner.nextLine());
                                    if (newNumberOfClasses >= 0) {
                                        teacher.setNumberOfClasses(newNumberOfClasses);
                                    } else {
                                        System.out.println("Number of classes cannot be negative. Please try again.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid integer.");
                                }
                                break;
                            case "4":
                                System.out.print("Enter new specialization: ");
                                String newSpecialization = scanner.nextLine();
                                teacher.setSpecialization(newSpecialization);
                                break;
                            default:
                                System.out.println("Invalid choice!");
                                break;
                        }
                    }
                    System.out.println("Teacher updated successfully!");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Teacher not found!");
            }

            // Save the updated records to file
            FileHandler.saveRecords();
        } catch (Exception e) {
            System.out.println("An error occurred during the update operation: " + e.getMessage());
        }
    }



    private static void deleteTeacher(Scanner scanner) {
        showAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("No teachers available to delete.");
            return;
        }

        try {
            System.out.print("Enter the index of the teacher to delete (1-" + teachers.size() + "): ");
            int index = Integer.parseInt(scanner.nextLine());

            if (index < 1 || index > teachers.size()) {
                System.out.println("Invalid index! Please enter a number between 1 and " + teachers.size() + ".");
                return;
            }
            index--;
            Teacher deletedTeacher = teachers.remove(index);
            System.out.println(deletedTeacher.getFullName() + " deleted successfully!");

            FileHandler.saveRecords();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the teacher: " + e.getMessage());
        }
    }

}