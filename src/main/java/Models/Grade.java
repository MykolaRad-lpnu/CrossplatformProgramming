package Models;

public class Grade {
    private String subject;
    private double value;

    public Grade(String subject, double value) {
        this.subject = subject;
        this.value = value;
    }
    public String getSubject() {
        return subject;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Subject: " + subject +
                ", Grade: " + value + "; ";
    }

    public static Grade createFromString(String line) {
        String[] parts = line.split(", Grade: ");
        String subject = parts[0].split("Subject: ")[1];
        double value = Double.parseDouble(parts[1].replace("; ", ""));
        return new Grade(subject, value);
    }
}
