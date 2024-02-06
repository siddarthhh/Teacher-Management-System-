import java.time.LocalDate;

public class Teacher {
    private String fullName;
    private int age;
    private LocalDate dateOfBirth;
    private int numberOfClasses;
    private String specialization; // Add specialization field
    private static final double BASE_SALARY_PER_CLASS = 1000;

    public Teacher(String fullName, int age, LocalDate dateOfBirth, int numberOfClasses, String specialization) {
        this.fullName = fullName;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.numberOfClasses = numberOfClasses;
        this.specialization = specialization; // Set specialization
    }

    // Getters and setters
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(int numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public double calculateSalary() {
        double baseSalary = numberOfClasses * BASE_SALARY_PER_CLASS;
        double additionalSalary = 0;
        if (numberOfClasses > 30) {
            additionalSalary = 3000;
        } else if (numberOfClasses > 20) {
            additionalSalary = 2000;
        } else if (numberOfClasses > 10) {
            additionalSalary = 1000;
        } else {
            additionalSalary = 4000;
        }
        return baseSalary + additionalSalary;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                ", numberOfClasses=" + numberOfClasses +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
