package university.patterns;

import university.employees.Teacher;

public class SortTeacherByRatingStrategy implements TeacherSortStrategy {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Teacher a, Teacher b) {
        return Double.compare(b.getAverageRating(), a.getAverageRating());
    }
}
