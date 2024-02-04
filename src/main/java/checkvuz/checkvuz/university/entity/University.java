package checkvuz.checkvuz.university.entity;
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
@Table(name = "universities")
public class University {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(nullable = false, name = "title", unique = true)
    private String title;

    @Column(nullable = false, name = "expanded_title", unique = true)
    private String expandedTitle;

    @Column(name = "description")
    private String description;

    @Column(nullable = false, name = "founding_year")
    private Integer foundingYear;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "university_tags_relation",
            joinColumns = @JoinColumn(name = "university_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "university_tag_id", referencedColumnName = "id")
    )
    private Set<UniversityTag> universityTags;

}
