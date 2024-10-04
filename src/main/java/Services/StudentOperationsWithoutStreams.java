package Services;

import Models.Grade;
import Models.PartTimeStudent;
import Models.Student;

import java.util.*;

public class StudentOperationsWithoutStreams implements StudentOperations {

    @Override
    public Map<String, List<Student>> splitStudentsByType(List<Student> studentsList) {
        Map<String, List<Student>> result = new HashMap<>();
        result.put("fullTime", new ArrayList<>());
        result.put("partTime", new ArrayList<>());

        for (Student student : studentsList) {
            if (student instanceof PartTimeStudent) {
                result.get("partTime").add(student);
            } else {
                result.get("fullTime").add(student);
            }
        }

        return result;
    }

    @Override
    public Map<String, List<Student>> groupStudentsByGroup(List<Student> students) {
        HashMap<String, List<Student>> groupedByGroup = new HashMap<>();

        for (Student student : students) {
            String group = student.getGroup();
            groupedByGroup.computeIfAbsent(group, k -> new ArrayList<>()).add(student);
        }

        return groupedByGroup;
    }

    @Override
    public Map<Double, List<Student>> listStudentsByGradeForSubject(List<Student> students, String subject) {
        HashMap<Double, List<Student>> studentsByGrade = new HashMap<>();

        for (Student student : students) {
            for (Grade grade : student.getGrades()) {
                if (grade.getSubject().equalsIgnoreCase(subject)) {
                    double gradeValue = grade.getValue();
                    studentsByGrade.computeIfAbsent(gradeValue, k -> new ArrayList<>()).add(student);
                }
            }
        }

        return studentsByGrade;
    }

    @Override
    public List<Student> sortStudentsByAverageGrade(List<Student> students) {
        Collections.sort(students);
        return students;
    }

    @Override
    public Set<String> printUniqueSubjects(List<Student> students) {
        Set<String> uniqueSubjects = new HashSet<>();

        for (Student student : students) {
            for (Grade grade : student.getGrades()) {
                uniqueSubjects.add(grade.getSubject());
            }
        }

        return uniqueSubjects;
    }

    @Override
    public Optional<Student> findTopStudentBySubject(List<Student> students, String subject) {
        Optional<Student> topStudent = Optional.empty();
        double highestAverage = -1;

        for (Student student : students) {
            boolean hasSubject = false;
            for (Grade grade : student.getGrades()) {
                if (grade.getSubject().equalsIgnoreCase(subject)) {
                    hasSubject = true;
                    break;
                }
            }

            if (hasSubject) {
                double average = student.calculateAverageGradeForSubject(subject);
                if (average > highestAverage) {
                    highestAverage = average;
                    topStudent = Optional.of(student);
                }
            }
        }

        return topStudent;
    }
}
