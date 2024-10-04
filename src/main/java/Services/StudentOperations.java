package Services;

import Models.Student;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface StudentOperations {
    Map<String, List<Student>> splitStudentsByType(List<Student> studentsList);

    Map<String, List<Student>> groupStudentsByGroup(List<Student> students);

    Map<Double, List<Student>> listStudentsByGradeForSubject(List<Student> students, String subject);

    List<Student> sortStudentsByAverageGrade(List<Student> students);

    Set<String> printUniqueSubjects(List<Student> students);

    Optional<Student> findTopStudentBySubject(List<Student> students, String subject);
}
