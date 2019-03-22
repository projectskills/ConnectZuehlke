package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.team;

import ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class TeamMemberDto {
    @JsonProperty("Employee")
    private EmployeeDto employee;
    @JsonProperty("Links")
    private List<PhaseLinkDto> phaseLink;

    public EmployeeDto getEmployee() {
        return employee;
    }

    public List<PhaseLinkDto> getPhaseLink() {
        return phaseLink;
    }
}
