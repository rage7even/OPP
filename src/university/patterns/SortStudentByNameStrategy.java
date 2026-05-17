package university.patterns;

import university.users.Student;

public class SortStudentByNameStrategy implements StudentSortStrategy {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Student a, Student b) {
        return a.getName().compareToIgnoreCase(b.getName());
    }
}
