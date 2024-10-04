package org.example;

import Models.Student;
import Services.StudentOperations;
import Services.StudentOperationsWithStreams;
import Services.StudentOperationsWithoutStreams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = "students.txt";
        ArrayList<Student> studentList =  new ArrayList<>(Student.readStudentsFromFile(fileName));

        for (Student student : studentList)
            System.out.println(student);
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter subject: ");
        String subject = scanner.nextLine();
        System.out.print("Enter 's' for stream methods or anything for simple ones: ");
        char answer = scanner.next().charAt(0);
        StudentOperations operations =
                answer == 's' ? new StudentOperationsWithStreams()
                        : new StudentOperationsWithoutStreams();

        var result = operations.splitStudentsByType(studentList);
        printMap(result);

        result = operations.groupStudentsByGroup(studentList);
        printMap(result);

        var studentsByGradeForSubject = operations.listStudentsByGradeForSubject(studentList, subject);
        printMap(studentsByGradeForSubject);

        var sortedStudents = operations.sortStudentsByAverageGrade(studentList);

        System.out.println("Students sorted by average grade: ");
        for (Student student : sortedStudents)
            System.out.println(student.getFullName() + "- Avarage grade: " + student.getAverageGrade());

        var uniqueSubjects = operations.printUniqueSubjects(studentList);
        System.out.println("Unique subjects: " + uniqueSubjects);

        var topStudent = operations.findTopStudentBySubject(studentList, subject);
        topStudent.ifPresentOrElse(
                student -> System.out.println("Top student for subject " + subject + ": " + student.getFullName() +
                        ", Average Grade: " + student.getAverageGrade()),
                () -> System.out.println("No students found for subject " + subject)
        );
    }

    private static <T> void printMap(Map<T, List<Student>> map) {
        for (Map.Entry<T, List<Student>> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            for (Student student : entry.getValue()) {
                System.out.println(" - " + student.getFullName());
            }
            System.out.println();
        }
    }
}
