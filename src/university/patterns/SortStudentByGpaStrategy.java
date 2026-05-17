package university.patterns;

import university.users.Student;

public class SortStudentByGpaStrategy implements StudentSortStrategy {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Student a, Student b) {
        return Double.compare(b.getGpa(), a.getGpa());
    }
}
