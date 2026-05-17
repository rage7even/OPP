package university.services;

import java.io.Serializable;

import university.core.University;

public interface UniversityDataStore extends Serializable {
    void save(University university);
    University load();
}
