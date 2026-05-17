package university.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import university.core.University;
import university.exceptions.UniversityException;

public class FileUniversityDataStore implements UniversityDataStore {
    private static final long serialVersionUID = 1L;

    private String path;

    public FileUniversityDataStore(String path) {
        this.path = path;
    }

    @Override
    public void save(University university) {
        try {
            File file = new File(path);
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(university);
            out.close();
        } catch (Exception e) {
            throw new UniversityException("Cannot save university data: " + e.getMessage());
        }
    }

    @Override
    public University load() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
            University university = (University) in.readObject();
            in.close();
            return university;
        } catch (Exception e) {
            throw new UniversityException("Cannot load university data: " + e.getMessage());
        }
    }
}
