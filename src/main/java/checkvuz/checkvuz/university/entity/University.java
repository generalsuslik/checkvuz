package checkvuz.checkvuz.university.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "universities")
public class University {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "expanded_title")
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
