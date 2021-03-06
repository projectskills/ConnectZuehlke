package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

@Service
@Profile({"ci", "default"})
public class InsightProjectServiceMocked implements InsightProjectService{

    private static final List<Project> PROJECTS = asList(
            new Project("1", "Ergon", "some description", 3),
            new Project("2", "iTrain", "blabla", 3),
            new Project("3", "SwissLife", "some desc", 3)
    );

    @Override
    public List<Project> getRunningProjects() {
        return PROJECTS;
    }

    @Override
    public Project getProject(String code) {
        return PROJECTS.stream().filter(project -> project.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public List<Employee> getCurrentEmployeesFor(Project project) {
        return InsightEmployeeServiceMocked.EMPLOYEES;
    }

    @Override
    public byte[] getProjectPicture(String projectCode) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("demo_picture.jpg");
        return IOUtils.toByteArray(classPathResource.getInputStream());
    }
}
