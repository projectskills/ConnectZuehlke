package ch.zuehlke.fullstack.ConnectZuehlke.business;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightProjectService;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightSkillService;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Skill;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.SkillRating;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProjectSkills {

    private final InsightProjectService projectService;
    private final InsightSkillService skillService;

    public ProjectSkills(InsightProjectService projectService, InsightSkillService skillService) {
        this.projectService = projectService;
        this.skillService = skillService;
    }

    public List<SkillRating> calcProjectSkills(@PathVariable String code) {
        Project project = projectService.getProject(code);
        List<Employee> employees = projectService.getCurrentEmployeesFor(project);

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
