package checkvuz.checkvuz.university.department.entity;

import checkvuz.checkvuz.utils.tag.entity.Tag;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "department_tag")
public class DepartmentTag extends Tag {

    @ManyToMany(mappedBy = "departmentTags")
    Set<Department> departments;
}
