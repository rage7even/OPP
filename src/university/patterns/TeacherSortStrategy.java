package university.patterns;

import java.io.Serializable;
import java.util.Comparator;

import university.employees.Teacher;

public interface TeacherSortStrategy extends Comparator<Teacher>, Serializable {
}
