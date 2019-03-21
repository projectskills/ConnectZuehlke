package ch.zuehlke.fullstack.ConnectZuehlke.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmployeeSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long skillId;
    private String name;
    private int experience;

    public EmployeeSkill() {
    }

    public EmployeeSkill(Long skillId, String name, int experience) {
        this.skillId = skillId;
        this.name = name;
        this.experience = experience;
    }

    public Long getSkillId() {
        return skillId;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }
}
