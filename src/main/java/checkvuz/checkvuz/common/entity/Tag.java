package checkvuz.checkvuz.common.entity;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(nullable = false, name = "title")
    private String title;
}
