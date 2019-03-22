package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.CurrentProjectDto;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.EmployeeDto;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.ListDto;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.ProjectDto;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.team.TeamMemberDto;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.team.PhaseDto.PHASE_STATE_SOLD;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.GET;

@Service
@Profile({"prod", "staging"})
public class InsightProjectServiceRemote implements InsightProjectService {
    private final RestTemplate insightRestTemplate;
    private Logger logger = LoggerFactory.getLogger(InsightProjectServiceRemote.class);

    public InsightProjectServiceRemote(RestTemplate insightRestTemplate) {
        this.insightRestTemplate = insightRestTemplate;
    }

    @Override
    @Cacheable("projects")
    public List<Project> getProjects() {
        ResponseEntity<ListDto<ProjectDto>> response = this.insightRestTemplate
                .exchange("/projects", GET, null, new ParameterizedTypeReference<ListDto<ProjectDto>>() {
                });

        logger.info("Counted in Response: " + response.getBody().getItems().size());
        return response.getBody().getItems().stream()
                .map(ProjectDto::toProject)
                .collect(toList());
    }

    @Override
    @Cacheable("projects")
    public Project getProject(String code) {
        ResponseEntity<ProjectDto> response = this.insightRestTemplate
                .getForEntity("/projects/" + code, ProjectDto.class);

        Project project = response.getBody().toProject();
        project.setTeamSize(getCurrentEmployeesFor(project).size());
        return project;
    }

    @Override
    public List<Project> getCurrentProjectsFor(Employee employee) {
        ResponseEntity<List<CurrentProjectDto>> response = this.insightRestTemplate
                .exchange("/employees/" + employee.getCode() + "/projects/current", GET, null, new ParameterizedTypeReference<List<CurrentProjectDto>>() {
                });
        return response.getBody().stream().map(CurrentProjectDto::getProject)
                .map(ProjectDto::toProject)
                .collect(toList());
    }

    @Cacheable("project_employees")
    @Override
    public List<Employee> getCurrentEmployeesFor(Project project) {
        String queryUrl = "/projects/" + project.getCode() + "/team/current";
        ResponseEntity<List<TeamMemberDto>> response = this.insightRestTemplate
                .exchange(queryUrl, GET, null, new ParameterizedTypeReference<List<TeamMemberDto>>() {
                });
        return response.getBody().stream()
                .filter(member -> member.getPhaseLink().stream()
                    .anyMatch(link -> link.getPhase().getState() >= PHASE_STATE_SOLD))
                .map(TeamMemberDto::getEmployee)
                .map(EmployeeDto::toEmployee)
                .filter(employee -> employee.getLastName() != null)
                .collect(toList());
    }

    @Override
    @Cacheable("picture")
    public byte[] getProjectPicture(String projectCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = this.insightRestTemplate
                .exchange("/projects/" + projectCode + "/picture", GET, entity, byte[].class, "1");
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        throw new IllegalStateException("Status code was not 200");
    }
}
