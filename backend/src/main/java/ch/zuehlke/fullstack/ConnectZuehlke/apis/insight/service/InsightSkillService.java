package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Skill;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.SkillExperience;

import java.util.List;

public interface InsightSkillService {
    List<Skill> getSkillsFor(Project project, Employee employee);
    List<SkillExperience> getSkillsFor(Employee employee);

    default List<SkillExperience> getPersistedSkillsFor(Employee employee) {
        // overwrite this to get persisted data
        return getSkillsFor(employee);
    }
}
