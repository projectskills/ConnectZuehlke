package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Skill;

import java.util.List;

import static java.util.Arrays.asList;

public class InsightSkillServcieMocked implements InsightSkillService {

    public static final List<Skill> SKILLS = asList(
            new Skill(1, "Android", 2),
            new Skill(2, "Kotlin", 3),
            new Skill(3, "MVVM", 1)
    );

    @Override
    public List<Skill> getSkillsFor(Project project, Employee employee) {
        return SKILLS;
    }

    @Override
    public List<Skill> getSkillsFor(Employee employee) {
        return SKILLS;
    }
}
