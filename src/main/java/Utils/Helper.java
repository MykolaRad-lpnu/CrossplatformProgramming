package Utils;

import Models.Grade;
import Models.PartTimeStudent;
import Models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Helper {
    private static final Random RANDOM = new Random();

    private static double generateGrade() {
        double grade = 50 + RANDOM.nextDouble() * 50;
        return Math.round(grade * 10.0) / 10.0;
    }

    private static List<Grade> createGrades(String[] subjects) {
        List<Grade> grades = new ArrayList<Grade>();
        for (int i = 0; i < 5; ++i)
        {
            grades.add(new Grade(subjects[i], generateGrade()));
        }

        return grades;
    }
    public static void writeStudentsIntoFile(String fileName) {
        List<Student> students = new ArrayList<Student>();

        String[] firstNames = {"Микола", "Назар", "Оля", "Василь", "Павло",
                "Надя", "Катя", "Денис", "Роман", "Назар", "Юля", "Наталя", "Віка", "Руслан", "Христина"};
        String[] lastNames = {"Гут", "Устрицький", "Березецька", "Біленський", "Вітков",
                "Валік", "Домбрич", "Ярмоловський", "Холод", "Косик", "Павлусів", "Чопко", "Гідей", "Ковальчук", "Карвацька"};
        String[] groups = {"SI-111", "PK-112", "OM-113"};

        String[] SIsubjects = {"OOP", "Math", "Physics", "RPZ", "KDM"};
        String[] PKsubjects = {"German", "Polish", "French", "English", "Chinese"};
        String[] OMsubjects = {"Chemistry", "PI", "Physiology", "Anatomy", "First Aid"};


        for (int i = 0; i < 15; ++i) {

            if (i % 3 == 0) {
                students.add(new Student(firstNames[i], lastNames[i], groups[0], createGrades(SIsubjects)));
            }
            else if (i % 2 == 0) {
                students.add(new Student(firstNames[i], lastNames[i], groups[1], createGrades(PKsubjects)));
            }
            else {
                students.add(new Student(firstNames[i], lastNames[i], groups[2], createGrades(OMsubjects)));
            }
        }

        students.add(new PartTimeStudent("John", "Adams", groups[0], createGrades(SIsubjects), "workplace1"));
        students.add(new PartTimeStudent("Nick", "Smith", groups[0], createGrades(SIsubjects), "workplace2"));
        students.add(new PartTimeStudent("Thom", "Johnson", groups[0], createGrades(SIsubjects), "workplace3"));
        students.add(new PartTimeStudent("Amanda", "Jonse", groups[1], createGrades(SIsubjects), "workplace4"));
        students.add(new PartTimeStudent("Victoria", "Williams", groups[1], createGrades(SIsubjects), "workplace5"));
        students.add(new PartTimeStudent("Emma", "Brown", groups[2], createGrades(SIsubjects), "workplace6"));

        Student.writeStudentsToFile(fileName, students);


    }

    /*public static List<Student> readStudentsFromFile(String fileName) {
        return Student.readStudentsFromFile(fileName);

    }*/


}

/*try {
            Student.writeStudentsToFile(students, fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }*/

/*List<Student> studentsList = new ArrayList<>();
        try {
            studentsList = Student.readStudentsFromFile("students.json");
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return studentsList;*/
