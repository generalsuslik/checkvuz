package checkvuz.checkvuz.faculty.entity;

import checkvuz.checkvuz.common.entity.Tag;
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
