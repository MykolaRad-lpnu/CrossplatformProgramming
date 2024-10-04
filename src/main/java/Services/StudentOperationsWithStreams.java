package Services;

import Models.Grade;
import Models.PartTimeStudent;
import Models.Student;

import java.util.*;
import java.util.stream.Collectors;

public class StudentOperationsWithStreams implements StudentOperations {
    @Override
    public Map<String, List<Student>> splitStudentsByType(List<Student> studentsList) {
        return studentsList.stream()
                .collect(Collectors.partitioningBy(student -> student instanceof PartTimeStudent))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey() ? "partTime" : "fullTime",
                        Map.Entry::getValue));
    }

    @Override
    public Map<String, List<Student>> groupStudentsByGroup(List<Student> students) {
        return students.stream()
                .collect(Collectors.groupingBy(Student::getGroup));
    }

    @Override
    public Map<Double, List<Student>> listStudentsByGradeForSubject(List<Student> students, String subject) {
        return students.stream()
                .flatMap(student -> student.getGrades().stream()
                        .filter(grade -> grade.getSubject().equalsIgnoreCase(subject))
                        .map(grade -> Map.entry(grade.getValue(), student)))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    @Override
    public List<Student> sortStudentsByAverageGrade(List<Student> students) {
        return students.stream()
                .sorted(Comparator.comparingDouble(Student::getAverageGrade))
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> printUniqueSubjects(List<Student> students) {
        return students.stream()
                .flatMap(student -> student.getGrades().stream())
                .map(Grade::getSubject)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Student> findTopStudentBySubject(List<Student> students, String subject) {
        return students.stream()
                .filter(student -> student.getGrades().stream()
                        .anyMatch(grade -> grade.getSubject().equalsIgnoreCase(subject)))
                .max(Comparator.comparingDouble(student -> student.calculateAverageGradeForSubject(subject)));
    }
}
