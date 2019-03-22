package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Skill;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.SkillExperience;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
@Profile({"ci", "default"})
public class InsightSkillServiceMocked implements InsightSkillService {

    public static final List<Skill> SKILLS = asList(
            new Skill(1, "Android"),
            new Skill(2, "Kotlin"),
            new Skill(3, "MVVM")
    );

    public static final List<SkillExperience> SKILLSEXPERIENCE = asList(
            new SkillExperience(SKILLS.get(0), 1),
            new SkillExperience(SKILLS.get(0), 2),
            new SkillExperience(SKILLS.get(0), 3)
    );

    @Override
    public List<Skill> getSkillsFor(Project project, Employee employee) {
        return SKILLS;
    }

    @Override
    public List<SkillExperience> getSkillsFor(Employee employee) {
        return SKILLSEXPERIENCE;
    }
}
