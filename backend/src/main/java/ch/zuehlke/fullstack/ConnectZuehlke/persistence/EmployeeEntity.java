package ch.zuehlke.fullstack.ConnectZuehlke.persistence;

import javax.persistence.*;
import java.util.List;

@Entity
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;

    @OneToMany
    @JoinColumn(name = "employee")
    private List<EmployeeSkill> skills;

    public String getCode() {
        return code;
    }

    public List<EmployeeSkill> getSkills() {
        return skills;
    }

    public EmployeeEntity() {
    }

    public EmployeeEntity(String code, List<EmployeeSkill> skills) {
        this.code = code;
        this.skills = skills;
    }
}
