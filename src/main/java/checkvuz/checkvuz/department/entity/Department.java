package checkvuz.checkvuz.department.entity;

import checkvuz.checkvuz.faculty.entity.Faculty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "department")
public class Department {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "expanded_title", nullable = false, unique = true)
    private String expanded_title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "department_tags_relation",
            joinColumns = @JoinColumn(name = "department_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "department_tag_id", referencedColumnName = "id")
    )
    private Set<DepartmentTag> departmentTags;
}
