import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FileHandler {
    //file-based system to store teacher records.
    private static final String FILE_PATH = "C:\\Users\\Siddarth\\Desktop\\TeacherManagentSystem\\src\\teachers.txt";

    static void loadRecords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String fullName = parts[0];
                int age = Integer.parseInt(parts[1]);
                LocalDate dateOfBirth = LocalDate.parse(parts[2]);
                int numberOfClasses = Integer.parseInt(parts[3]);
                String specialization = parts[4];
                TeacherManagementApp.teachers.add(new Teacher(fullName, age, dateOfBirth, numberOfClasses, specialization)); // Pass age directly
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DateTimeParseException e) {
            // Handle parsing error for date of birth
            System.out.println("Error: Unable to parse date of birth in the correct format. Check the file format.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // Handle parsing error for age or number of classes
            System.out.println("Error: Unable to parse age or number of classes. Check the file format.");
            e.printStackTrace();
        }
    }



    static void saveRecords() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Teacher teacher : TeacherManagementApp.teachers) {
                String dateOfBirthString = teacher.getDateOfBirth().toString();
                writer.write(teacher.getFullName() + "," + teacher.getAge() + "," + dateOfBirthString + "," + teacher.getNumberOfClasses() + "," + teacher.getSpecialization() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred while saving records.");
        }
    }

}
