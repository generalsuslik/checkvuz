package checkvuz.checkvuz.university.university.entity;

import checkvuz.checkvuz.utils.tag.entity.Tag;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "university_tags")
public class UniversityTag extends Tag {

    @ManyToMany(mappedBy = "universityTags")
    private Set<University> universities;
}
