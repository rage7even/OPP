package university.app;

import java.util.ArrayList;
import java.util.List;

import university.core.University;
import university.users.Student;
import university.users.User;

public final class AppData {
    private AppData() {
    }

    static List<Student> students() {
        List<Student> students = new ArrayList<Student>();
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        return students;
    }
}

