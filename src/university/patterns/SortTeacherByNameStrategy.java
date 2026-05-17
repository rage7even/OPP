package university.patterns;

import university.employees.Teacher;

public class SortTeacherByNameStrategy implements TeacherSortStrategy {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Teacher a, Teacher b) {
        return a.getName().compareToIgnoreCase(b.getName());
    }
}
