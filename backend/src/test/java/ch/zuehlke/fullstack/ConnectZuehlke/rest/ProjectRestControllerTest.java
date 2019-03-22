package ch.zuehlke.fullstack.ConnectZuehlke.rest;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightEmployeeService;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightEmployeeServiceMocked;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightProjectService;
import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service.InsightSkillService;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Employee;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.Skill;
import ch.zuehlke.fullstack.ConnectZuehlke.domain.SkillExperience;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@ActiveProfiles("default")
@WebMvcTest(value = ProjectRestController.class)
public class ProjectRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InsightProjectService projectService;

    @MockBean
    private InsightEmployeeService employeeService;

    @MockBean
    private InsightSkillService skillService;

    @Test
    public void testGetProject() throws Exception {
        Project project = new Project("C23043", "Concordia Mobile App", "Bla");
        project.setTeamSize(5);
        when(projectService.getProject(any())).thenReturn(project);

        mockMvc.perform(get("/api/projects/C23043"))
                .andExpect(content().json(
                        "  {\n" +
                                "    \"code\": \"C23043\",\n" +
                                "    \"name\": \"Concordia Mobile App\",\n" +
                                "    \"description\": \"Bla\",\n" +
                                "    \"teamSize\": 5\n" +
                                "  }\n"));
    }

    @Test
    public void testGetProjectTeam() throws Exception {
        when(projectService.getProject(any())).thenReturn(new Project("C23043", "Concordia Mobile App", "Bla"));
        Employee employee = new Employee("Stefan", "Diegas", 2, "stdi");
        when(projectService.getCurrentEmployeesFor(any())).thenReturn(Arrays.asList(employee));
        mockMvc.perform(get("/api/projects/C23043/team"))
                .andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"firstName\": \"Stefan\",\n" +
                        "    \"lastName\": \"Diegas\",\n" +
                        "    \"id\": 2,\n" +
                        "    \"code\": \"stdi\"\n" +
                        "  }\n" +
                        "]"));
    }

    @Test
    public void testGetProjectSkills() throws Exception {
        when(projectService.getProject(any())).thenReturn(new Project("C23043", "Concordia Mobile App", "Bla"));
        Employee employee = new Employee("Stefan", "Diegas", 2, "stdi");
        when(projectService.getCurrentEmployeesFor(any())).thenReturn(Arrays.asList(employee));
        when(skillService.getSkillsFor(any(), any())).thenReturn(Arrays.asList(new Skill(1,"Android")));
        mockMvc.perform(get("/api/projects/C23043/skills"))
                .andExpect(content().json("[{\"skill\":{\"id\":1,\"name\":\"Android\"},\"rating\":1.0}]"));
    }

    @Test
    public void testGetProjectFittingEmployees() throws Exception {
        Employee employee = new Employee("Stefan", "Diegas", 2, "stdi");
        when(employeeService.getEmployees()).thenReturn(Arrays.asList(employee));
        SkillExperience skillExperience = new SkillExperience(new Skill(1, "Android"), 1);
        when(skillService.getSkillsFor(any())).thenReturn(Arrays.asList(skillExperience));
        mockMvc.perform(get("/api/projects/C23043/fittingemployees"))
                .andExpect(content().json("[{\"employee\":{\"firstName\":\"Stefan\",\"lastName\":\"Diegas\",\"id\":2,\"code\":\"stdi\"},\"rating\":0.0}]"));
    }
}
