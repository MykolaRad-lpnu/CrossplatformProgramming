package Services;

import Models.Grade;
import Models.PartTimeStudent;
import Models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentOperationsWithStreamsTest {
    private StudentOperationsWithStreams studentOperations;
    private List<Student> students;

    @BeforeEach
    void setUp() {
        studentOperations = new StudentOperationsWithStreams();
        students = new ArrayList<>();

        students.add(new Student("Alice", "Smith", "Group A", List.of(new Grade("Math", 90), new Grade("English", 85))));
        students.add(new Student("Bob", "Johnson", "Group B", List.of(new Grade("Math", 75), new Grade("Science", 88))));
        students.add(new PartTimeStudent("Charlie", "Williams", "Group A", List.of(new Grade("Math", 92), new Grade("Art", 80)), "Cafe"));
        students.add(new Student("David", "Brown", "Group C", List.of(new Grade("English", 95), new Grade("Science", 70))));
        students.add(new PartTimeStudent("Eva", "Davis", "Group B", List.of(new Grade("Math", 85), new Grade("Art", 92)), "Library"));
        students.add(new Student("Frank", "Miller", "Group A", List.of(new Grade("Math", 80), new Grade("English", 78))));
        students.add(new Student("Grace", "Wilson", "Group C", List.of(new Grade("Science", 88), new Grade("English", 90))));
        students.add(new PartTimeStudent("Hannah", "Moore", "Group A", List.of(new Grade("Math", 95), new Grade("Art", 88)), "Store"));
        students.add(new Student("Ivy", "Taylor", "Group B", List.of(new Grade("Science", 85), new Grade("Art", 90))));
        students.add(new PartTimeStudent("Jack", "Anderson", "Group C", List.of(new Grade("Math", 89), new Grade("English", 84)), "Warehouse"));
    }

    @Test
    void splitStudentsByType() {
        Map<String, List<Student>> result = studentOperations.splitStudentsByType(students);

        assertEquals(6, result.get("fullTime").size());
        assertEquals(4, result.get("partTime").size());
        assertTrue(result.get("fullTime").stream().noneMatch(student -> student instanceof PartTimeStudent));
        assertTrue(result.get("partTime").stream().allMatch(student -> student instanceof PartTimeStudent));
    }

    @Test
    void groupStudentsByGroup() {
        Map<String, List<Student>> result = studentOperations.groupStudentsByGroup(students);

        assertEquals(3, result.size());
        assertTrue(result.containsKey("Group A"));
        assertTrue(result.containsKey("Group B"));
        assertTrue(result.containsKey("Group C"));
    }

    @Test
    void listStudentsByGradeForSubject() {
        Map<Double, List<Student>> result = studentOperations.listStudentsByGradeForSubject(students, "Math");

        assertFalse(result.isEmpty());
        assertTrue(result.containsKey(90.0));
        assertTrue(result.containsKey(75.0));
        assertTrue(result.containsKey(92.0));
    }

    @Test
    void sortStudentsByAverageGrade() {
        List<Student> result = studentOperations.sortStudentsByAverageGrade(students);

        assertEquals(10, result.size());
        assertTrue(result.get(0).getAverageGrade() <= result.get(1).getAverageGrade());
        assertTrue(result.get(result.size() - 1).getAverageGrade() >= result.get(result.size() - 2).getAverageGrade());
    }

    @Test
    void printUniqueSubjects() {
        Set<String> result = studentOperations.printUniqueSubjects(students);

        assertEquals(4, result.size());
        assertTrue(result.contains("Math"));
        assertTrue(result.contains("English"));
        assertTrue(result.contains("Science"));
        assertTrue(result.contains("Art"));
    }

    @Test
    void findTopStudentBySubject() {
        Optional<Student> result = studentOperations.findTopStudentBySubject(students, "Math");

        assertTrue(result.isPresent());
        assertEquals("Hannah Moore", result.get().getFullName());
        assertEquals(95.0, result.get().calculateAverageGradeForSubject("Math"));
    }
}
