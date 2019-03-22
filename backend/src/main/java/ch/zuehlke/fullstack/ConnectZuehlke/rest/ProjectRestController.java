package ch.zuehlke.fullstack.ConnectZuehlke.rest;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightProjectService;
import ch.zuehlke.fullstack.ConnectZuehlke.business.EmployeeRankings;
import ch.zuehlke.fullstack.ConnectZuehlke.business.ProjectSkills;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.EmployeeRating;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.SkillRating;
import ch.zuehlke.fullstack.ConnectZuehlke.persistence.ProjectEntity;
import ch.zuehlke.fullstack.ConnectZuehlke.persistence.ProjectRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {
    private final InsightProjectService insightProjectService;
    private final ProjectRepository projectRepository;

    private final EmployeeRankings employeeRankings;
    private final ProjectSkills projectSkills;

    public ProjectRestController(InsightProjectService insightProjectService, ProjectRepository projectRepository, EmployeeRankings employeeRankings, ProjectSkills projectSkills) {
        this.insightProjectService = insightProjectService;
        this.projectRepository = projectRepository;
        this.employeeRankings = employeeRankings;
        this.projectSkills = projectSkills;
    }

    @GetMapping("")
    public List<Project> getProjects() {
        return insightProjectService.getPersistedRunningProjects();
    }

    @GetMapping("persist")
    public void persistProjects() {
        List<Project> runningProjects = insightProjectService.getRunningProjects();
        List<ProjectEntity> projectEntities = runningProjects.stream()
                .map(ProjectEntity::fromProject)
                .collect(Collectors.toList());

        projectRepository.saveAll(projectEntities);
    }

    @GetMapping("{code}")
    public Project getProject(@PathVariable String code) {
        return insightProjectService.getProject(code);
    }

    @GetMapping("{code}/team")
    public List<Employee> getProjectTeam(@PathVariable String code) {
        Project project = insightProjectService.getProject(code);
        return insightProjectService.getCurrentEmployeesFor(project);
    }

    @GetMapping(value = "{code}/picture",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getProjectPicture(@PathVariable String code) throws IOException {
        return insightProjectService.getProjectPicture(code);
    }

    @GetMapping("{code}/fittingemployees")
    public List<EmployeeRating> getFittingEmployeesRanking(@PathVariable String code) {

        List<SkillRating> projectSkills = getProjectSkills(code);
        return employeeRankings.getEmployeeRankings(projectSkills);
    }


    @GetMapping("{code}/skills")
    public List<SkillRating> getProjectSkills(@PathVariable String code) {
        return projectSkills.calcProjectSkills(code);
    }


}
