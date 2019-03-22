package ch.zuehlke.fullstack.ConnectZuehlke.business;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightEmployeeService;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightSkillService;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeRankings {
    private final InsightEmployeeService employeeService;
    private final InsightSkillService skillService;


    public EmployeeRankings(InsightEmployeeService employeeService, InsightSkillService skillService) {
        this.employeeService = employeeService;
        this.skillService = skillService;
    }

    public List<EmployeeRating> getEmployeeRankings(List<SkillRating> projectSkills) {
        Map<Skill, Double> projectRatings = projectSkills.stream()
                .collect(Collectors.toMap(SkillRating::getSkill, SkillRating::getRating));

        List<Employee> allEmployees = employeeService.getEmployees();

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

            int adjustedRating = getAdjustedRating(rating);
            if (adjustedRating > 0) {
                employeeRatings.add(new EmployeeRating(employee, adjustedRating));
            }
        });
        return employeeRatings;
    }

    private int getAdjustedRating(double rating) {
        return (int) (rating * 100);
    }

    private Map<Employee, List<SkillExperience>> getEmployeeSkills(List<Employee> allEmployees) {
        return allEmployees.stream()
                .collect(Collectors.toMap(Function.identity(), skillService::getPersistedSkillsFor));
    }
}
