package Models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Student implements Comparable<Student>{
    private String firstName;

    private String lastName;

    private String group;

    private List<Grade> grades;

    public Student(String firstName, String lastName, String group, List<Grade> grades) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        this.grades = grades;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGroup() {
        return group;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public double getAverageGrade() {
        return grades.stream()
                .mapToDouble(Grade::getValue)
                .average()
                .orElse(0);
    }

    public double calculateAverageGradeForSubject(String subject) {
        return grades.stream()
                .filter(grade -> grade.getSubject().equals(subject))
                .mapToDouble(Grade::getValue)
                .average()
                .orElse(0.0);
    }

    @Override
    public int compareTo(Student other) {
        return Double.compare(this.getAverageGrade(), other.getAverageGrade());
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    private String gradesToString() {
        StringBuilder sb = new StringBuilder();
        for (Grade grade : grades) {
            sb.append(grade.toString());
        }
        return sb.toString();
    }
    @Override
    public String toString() {
        return "Name: " + getFullName() +
                ", Group: " + group +
                ", Grades: " + gradesToString();
    }

    public static void writeStudentsToFile(String filename, List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
                writer.newLine();
            }
            System.out.println("Список студентів успішно записано до файлу " + filename);
        } catch (IOException e) {
            System.err.println("Помилка запису студентів до файлу: " + e.getMessage());
        }
    }

    public static List<Student> readStudentsFromFile(String filename) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0)
                    students.add(Student.createFromString(line));
            }
            System.out.println("Список студентів успішно зчитано з файлу " + filename);
        } catch (IOException e) {
            System.err.println("Помилка зчитування студентів з файлу: " + e.getMessage());
        }
        return students;
    }
    private static Student createFromString(String line) {
        String[] parts = line.split(", Group: ");
        String[] nameParts = parts[0].split("Name: ");
        String[] groupAndGrades = parts[1].split(", Grades: ");

        String[] fullNameParts = nameParts[1].split(" ");
        String firstName = fullNameParts[0];
        String lastName = fullNameParts[1];
        String group = groupAndGrades[0];

        String[] gradeParts = groupAndGrades[1].split(";");
        List<Grade> grades = new ArrayList<>();
        String workplace = null;

        for (String gradePart : gradeParts) {
            gradePart = gradePart.trim();
            if (!gradePart.isEmpty()) {
                if (gradePart.startsWith("Subject:")) {
                    grades.add(Grade.createFromString(gradePart));
                } else {
                    workplace = gradePart;
                    return new PartTimeStudent(firstName, lastName, group, grades, workplace);
                }
            }
        }
        return new Student(firstName, lastName, group, grades);
    }
}