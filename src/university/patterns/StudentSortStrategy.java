package university.patterns;

import java.io.Serializable;
import java.util.Comparator;

import university.users.Student;

public interface StudentSortStrategy extends Comparator<Student>, Serializable {
}
