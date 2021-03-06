package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;

import java.io.IOException;
import java.util.List;

public interface InsightProjectService {
    List<Project> getRunningProjects();

    default List<Project> getPersistedRunningProjects() {
        return getRunningProjects();
    }

    Project getProject(String code);

    List<Employee> getCurrentEmployeesFor(Project project);

    byte[] getProjectPicture(String projectCode) throws IOException;
}
