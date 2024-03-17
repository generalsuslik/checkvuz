package checkvuz.checkvuz.university.program.entity;

import checkvuz.checkvuz.utils.tag.entity.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "program_tags")
public class ProgramTag extends Tag {

    @ManyToMany(mappedBy = "programTags")
    private Set<Program> programs;
}
