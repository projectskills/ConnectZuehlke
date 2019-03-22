package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.CurrentProjectDto;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.DescriptionDto;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.SkillDto;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Skill;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.SkillExperience;
import ch.zuehlke.fullstack.ConnectZuehlke.persistence.EmployeeEntity;
import ch.zuehlke.fullstack.ConnectZuehlke.persistence.EmployeeRepository;
import org.springframework.cache.annotation.Cacheable;
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
    private final EmployeeRepository employeeRepository;

    public InsightSkillServiceRemote(RestTemplate insightRestTemplate, EmployeeRepository employeeRepository) {
        this.insightRestTemplate = insightRestTemplate;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Cacheable("project_employee_skills")
    public List<Skill> getSkillsFor(Project project, Employee employee) {

        ResponseEntity<List<CurrentProjectDto>> response = this.insightRestTemplate
                .exchange("/employees/" + employee.getCode() + "/projects/current", GET, null, new ParameterizedTypeReference<List<CurrentProjectDto>>() {
                });
        Optional<CurrentProjectDto> currentProject = response.getBody().stream()
                .filter(currentProjectDto -> currentProjectDto.getProject().getCode().equals(project.getCode()))
                .findFirst();
        return currentProject.map(currentProjectDto -> currentProjectDto.getDescriptions().stream()
                .findFirst()
                .map(DescriptionDto::getSkills)
                .map(skillDtos -> skillDtos.stream()
                        .map(SkillDto::toSkill)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList())).orElse(Collections.emptyList());
    }

    private Optional<EmployeeEntity> getEmployeeEntity(Employee employee) {
        return employeeRepository.findByCode(employee.getCode()).stream().findFirst();
    }

    @Override
    public List<SkillExperience> getPersistedSkillsFor(Employee employee) {
        Optional<EmployeeEntity> employeeEntity = getEmployeeEntity(employee);

        return employeeEntity
                .map(employeeEnitity -> employeeEnitity.getSkills().stream()
                    .map(skillEntity -> new SkillExperience(
                            new Skill(skillEntity.getSkillId(), skillEntity.getName()), skillEntity.getExperience()))
                    .collect(Collectors.toList()))
                .orElseGet(() -> getSkillsFor(employee));
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
