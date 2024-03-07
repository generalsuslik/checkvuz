package checkvuz.checkvuz.university.faculty.entity;

import checkvuz.checkvuz.university.university.entity.University;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "faculties")
public class Faculty {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "expanded_title", nullable = false, unique = true)
    private String expanded_title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "faculty_tags_relation",
            joinColumns = @JoinColumn(name = "faculty_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "faculty_tag_id", referencedColumnName = "id")
    )
    private Set<FacultyTag> facultyTags;

}
