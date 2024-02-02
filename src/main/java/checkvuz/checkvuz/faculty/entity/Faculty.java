package checkvuz.checkvuz.faculty.entity;

import checkvuz.checkvuz.university.entity.University;
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
@Table(name = "faculties")
public class Faculty {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "expanded_title", nullable = false)
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
