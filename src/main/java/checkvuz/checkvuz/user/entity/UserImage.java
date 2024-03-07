package checkvuz.checkvuz.user.entity;

import checkvuz.checkvuz.utils.image.entity.Image;
import jakarta.persistence.*;

@Entity
@Table(name = "user_images")
public class UserImage extends Image {

    @OneToOne(mappedBy = "userImage")
    private User user;
}
