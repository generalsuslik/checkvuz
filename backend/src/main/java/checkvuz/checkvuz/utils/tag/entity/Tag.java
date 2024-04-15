package checkvuz.checkvuz.utils.tag.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(nullable = false, name = "title", unique = true)
    private String title;
}
