package Models;

import java.util.List;

public class PartTimeStudent extends Student {
    private String workplace;

    public PartTimeStudent(String firstName, String lastName, String group,
                           List<Grade> grades, String workplace) {
        super(firstName, lastName, group, grades);
        this.workplace = workplace;
    }

    public String getWorkplace() {
        return workplace;
    }

    @Override
    public String toString() {
        return super.toString() + ", Workplace: " + workplace;
    }
}
