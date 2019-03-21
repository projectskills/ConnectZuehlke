package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
@Profile({"ci", "default"})
public class InsightProjectServiceMocked implements InsightProjectService{

    public static final List<Project> PROJECTS = asList(
            new Project("1", "Ergon", "some description"),
            new Project("2", "iTrain", "blabla"),
            new Project("3", "SwissLife", "some desc")
    );

    @Override
    public List<Project> getProjects() {
        return PROJECTS;
    }

    @Override
    public Project getProject(String code) {
        return PROJECTS.stream().filter(project -> project.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public List<Project> getCurrentProjectsFor(Employee employee) {
        return PROJECTS;
    }
}
