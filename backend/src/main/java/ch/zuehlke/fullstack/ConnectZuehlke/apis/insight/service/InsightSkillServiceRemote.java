package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.CurrentProjectDto;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.DescriptionDto;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.SkillDto;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Skill;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.SkillExperience;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.GET;

@Service
@Profile({"prod", "staging"})
public class InsightSkillServiceRemote implements InsightSkillService {
    private final RestTemplate insightRestTemplate;

    public InsightSkillServiceRemote(RestTemplate insightRestTemplate) {
        this.insightRestTemplate = insightRestTemplate;
    }

    @Override
    public List<Skill> getSkillsFor(Project project, Employee employee) {
        ResponseEntity<List<CurrentProjectDto>> response = this.insightRestTemplate
                .exchange("/employees/" + employee.getCode() + "/projects/current", GET, null, new ParameterizedTypeReference<List<CurrentProjectDto>>() {
                });
        Optional<CurrentProjectDto> currentProject = response.getBody().stream()
                .filter(currentProjectDto -> currentProjectDto.getProject().getCode().equals(project.getCode()))
                .findFirst();
        if (!currentProject.isPresent()) {
            throw new IllegalArgumentException("Project does not exist");
        }
        return currentProject.get().getDescriptions().stream()
                .findFirst()
                .map(DescriptionDto::getSkills)
                .map(skillDtos -> skillDtos.stream()
                        .map(SkillDto::toSkill)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<SkillExperience> getSkillsFor(Employee employee) {
        ResponseEntity<List<SkillDto>> response = this.insightRestTemplate
                .exchange("/employees/" + employee.getCode() + "/skills", GET, null, new ParameterizedTypeReference<List<SkillDto>>() {
                });

        return response.getBody().stream()
                .map(SkillDto::toSkillExperience)
                .collect(Collectors.toList());
    }
}
