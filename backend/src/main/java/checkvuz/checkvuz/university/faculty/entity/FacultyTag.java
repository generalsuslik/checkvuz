package checkvuz.checkvuz.university.faculty.entity;

import checkvuz.checkvuz.utils.tag.entity.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "faculty_tags")
public class FacultyTag extends Tag {

    @ManyToMany(mappedBy = "facultyTags")
    Set<Faculty> faculties;
}
