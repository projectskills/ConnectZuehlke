package ch.zuehlke.fullstack.ConnectZuehlke.rest;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightEmployeeService;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightProjectService;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightSkillService;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.*;
import ch.zuehlke.fullstack.ConnectZuehlke.persistence.ProjectEntity;
import ch.zuehlke.fullstack.ConnectZuehlke.persistence.ProjectRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {
    private final InsightProjectService insightProjectService;
    private final InsightEmployeeService insightEmployeeService;
    private final InsightSkillService skillService;
    private final ProjectRepository projectRepository;

    public ProjectRestController(InsightProjectService insightProjectService, InsightEmployeeService insightEmployeeService, InsightSkillService skillService, ProjectRepository projectRepository) {
        this.insightProjectService = insightProjectService;
        this.insightEmployeeService = insightEmployeeService;
        this.skillService = skillService;
        this.projectRepository = projectRepository;
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

        Map<Skill, Double> projectRatings = getProjectSkills(code).stream()
                .collect(Collectors.toMap(SkillRating::getSkill, SkillRating::getRating));

        List<Employee> allEmployees = insightEmployeeService.getEmployees();

        Map<Employee, List<SkillExperience>> employeeSkills = getEmployeeSkills(allEmployees);
        List<EmployeeRating> employeeRatings = calculateEmployeeRatings(projectRatings, employeeSkills);

        return employeeRatings.stream()
                .sorted(Comparator.comparing(EmployeeRating::getRating).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    private List<EmployeeRating> calculateEmployeeRatings(
            Map<Skill, Double> projectRatings, Map<Employee, List<SkillExperience>> employeeSkills) {
        List<EmployeeRating> employeeRatings = new ArrayList<>();
        employeeSkills.forEach((employee, skills) -> {
            List<SkillExperience> filteredSkills = skills.stream()
                    .filter(skill -> projectRatings.keySet().contains(skill.getSkill()))
                    .collect(Collectors.toList());
            double rating = filteredSkills.stream()
                    .mapToDouble(skill -> skill.getExperience() * projectRatings.get(skill.getSkill()))
                    .sum();

            if (rating > 0) {
                employeeRatings.add(new EmployeeRating(employee, rating));
            }
        });
        return employeeRatings;
    }

    private Map<Employee, List<SkillExperience>> getEmployeeSkills(List<Employee> allEmployees) {
        return allEmployees.stream()
                .collect(Collectors.toMap(Function.identity(), skillService::getPersistedSkillsFor));
    }

    @GetMapping("{code}/skills")
    public List<SkillRating> getProjectSkills(@PathVariable String code) {
        Project project = insightProjectService.getProject(code);
        List<Employee> employees = insightProjectService.getCurrentEmployeesFor(project);

        Map<Skill, Long> skillCounts = employees.stream()
                .flatMap(employee -> skillService.getSkillsFor(project, employee).stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return skillCounts.entrySet().stream()
                .map(entry -> new SkillRating(entry.getKey(), calculateSkillRating(employees, entry.getValue())))
                .collect(Collectors.toList());
    }

    private double calculateSkillRating(List<Employee> employees, Long skillCount) {
        return (double) skillCount / employees.size();
    }
}
