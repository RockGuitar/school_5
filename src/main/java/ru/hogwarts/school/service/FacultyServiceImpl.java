package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    private FacultyRepository facultyRepository;

    public FacultyServiceImpl ( FacultyRepository facultyRepository ) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty ( Faculty faculty ) {
        return facultyRepository.save(faculty);
    }

    public Faculty readFaculty ( Long id ) {
        return facultyRepository.findById(id).orElseThrow();
    }

    public Faculty updateFaculty ( Faculty faculty ) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty ( Long id ) {
        facultyRepository.deleteById(id);
    }

    public Faculty findFacultyByColorOrNameIgnoreCase ( String color, String name ) {
        return facultyRepository.findFacultyByColorOrNameIgnoreCase(color, name);
    }
}
