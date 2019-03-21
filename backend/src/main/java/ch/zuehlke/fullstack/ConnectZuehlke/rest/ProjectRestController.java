package ch.zuehlke.fullstack.ConnectZuehlke.rest;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightEmployeeService;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightProjectService;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightSkillService;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Skill;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.SkillRating;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {
    private static final List<String> PROJECTS = Arrays.asList(
            "C23438", // SNB PRIMA
            "C23439", // SNB EASYR
            "C23440", // SNB ESIP
            "C22520", // SCS COMS
            "C22520", // SCS IAM
            "C23719", // SCS P2S
            "C23782", // VONTOBEL sky
            "C23781", // VONTOBEL RM
            "C23410", // SBB PRED MAINT
            "C23226", // SBB ETR610
            "C19834", // SBB automat
            "C23043" // CONCORDIA mobile app
    );
    private final InsightProjectService insightProjectService;
    private final InsightEmployeeService insightEmployeeService;
    private final InsightSkillService skillService;

    public ProjectRestController(InsightProjectService insightProjectService, InsightEmployeeService insightEmployeeService, InsightEmployeeService insightEmployeeService1, InsightSkillService skillService) {
        this.insightProjectService = insightProjectService;
        this.insightEmployeeService = insightEmployeeService1;
        this.skillService = skillService;
    }

    @GetMapping("")
    public List<Project> getProjects() {
        return PROJECTS.stream()
                .map(insightProjectService::getProject)
                .collect(Collectors.toList());
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

    @GetMapping("{code}/matching")
    public List<Employee> getProjectMatches(@PathVariable String code) {
        List<Employee> topMatches = new ArrayList<>();
        Map<Skill, Double> projectRatings = getProjectSkills(code).stream()
                .collect(Collectors.toMap(SkillRating::getSkill, SkillRating::getRating));
        List<Employee> allEmployees = insightEmployeeService.getEmployees();

        Map<Employee, List<Skill>> employeeSkills = allEmployees.stream().limit(50)
                .collect(Collectors.toMap(Function.identity(), skillService::getSkillsFor));

        HashMap<Employee, Double> employeeRatings = new HashMap<>();
        employeeSkills.forEach((key, value) -> {
            double rating = value.stream()
                    .filter(skill -> projectRatings.keySet().contains(skill))
                    .mapToDouble(skill -> skill.getExperience() * projectRatings.get(skill))
                    .sum();
            employeeRatings.put(key, rating);
        });

        return employeeRatings.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .limit(10)
                .collect(Collectors.toList());
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
