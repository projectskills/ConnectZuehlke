package ch.zuehlke.fullstack.ConnectZuehlke.rest;


import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightEmployeeService;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightSkillService;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.persistence.EmployeeEntity;
import ch.zuehlke.fullstack.ConnectZuehlke.persistence.EmployeeRepository;
import ch.zuehlke.fullstack.ConnectZuehlke.persistence.EmployeeSkill;
import ch.zuehlke.fullstack.ConnectZuehlke.persistence.EmployeeSkillRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeRestController {
    private final InsightEmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeSkillRepository employeeSkillRepository;
    private final InsightSkillService skillService;

    public EmployeeRestController(InsightEmployeeService employeeService, EmployeeRepository employeeRepository, EmployeeSkillRepository employeeSkillRepository, InsightSkillService skillService) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.employeeSkillRepository = employeeSkillRepository;
        this.skillService = skillService;
    }

    @GetMapping("/api/employees")
    public List<Employee> employeeList() {
        return employeeService.getEmployees();
    }

    @GetMapping("/api/employee/{code}")
    public Employee employee(@PathVariable(value = "code") String code) {
        return employeeService.getEmployee(code);
    }

    @GetMapping(value = "/api/employee/{id}/picture",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getEmployeePicture(@PathVariable(value = "id") int id) throws IOException {
        return employeeService.getEmployeePicture(id);
    }

    @GetMapping("/api/employee/persist")
    public void persistEmployeeSkills() {
        List<Employee> employees = employeeService.getEmployees();

        List<EmployeeEntity> employeeEntities = employees.stream()
                .map(this::toEmployeeEntity)
                .collect(Collectors.toList());

        employeeRepository.saveAll(employeeEntities);
    }

    private EmployeeEntity toEmployeeEntity(Employee employee) {
        List<EmployeeSkill> employeeSkills = getEmployeeSkills(employee);
        employeeSkillRepository.saveAll(employeeSkills);
        return new EmployeeEntity(employee.getCode(), employeeSkills);
    }

    private List<EmployeeSkill> getEmployeeSkills(Employee employee) {
        return skillService.getSkillsFor(employee).stream()
                .map(skillExperience -> new EmployeeSkill(skillExperience.getSkill().getId(), skillExperience.getSkill().getName(), skillExperience.getExperience()))
                .collect(Collectors.toList());
    }
}