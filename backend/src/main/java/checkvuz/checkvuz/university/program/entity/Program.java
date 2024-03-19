package checkvuz.checkvuz.university.program.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "programs")
public class Program {

    // Class represents a study program for bachelor or master degree

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "description", unique = true, nullable = false)
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "program_tags_relation",
            joinColumns = @JoinColumn(name = "program_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "program_tag_id", referencedColumnName = "id")
    )
    private Set<ProgramTag> programTags;
}
