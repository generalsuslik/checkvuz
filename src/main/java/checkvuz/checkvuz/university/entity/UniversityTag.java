package checkvuz.checkvuz.university.entity;

import checkvuz.checkvuz.utils.entity.Tag;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "university_tags")
public class UniversityTag extends Tag {

    @ManyToMany(mappedBy = "universityTags")
    private Set<University> universities;
}
