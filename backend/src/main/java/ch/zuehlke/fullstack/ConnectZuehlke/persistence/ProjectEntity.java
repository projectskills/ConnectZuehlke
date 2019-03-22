package ch.zuehlke.fullstack.ConnectZuehlke.persistence;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class ProjectEntity {
    @Id
    private String code;

    private String name;

    @Column(length = 1000000)
    @Lob
    private String description;

    private int teamSize;

    public ProjectEntity() {
    }

    public ProjectEntity(String code, String name, String description, int teamSize) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.teamSize = teamSize;
    }

    public Project toProject() {
        return new Project(getCode(), getName(), getDescription(), getTeamSize());
    }

    public static ProjectEntity fromProject(Project project) {
        return new ProjectEntity(project.getCode(), project.getName(), project.getDescription(), project.getTeamSize());
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTeamSize() {
        return teamSize;
    }
}
