package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class CurrentProjectDto {

    @JsonProperty("Project")
    private ProjectDto project;

    @JsonProperty("Descriptions")
    private List<DescriptionDto> descriptions;

    public ProjectDto getProject() {
        return project;
    }

    public List<DescriptionDto> getDescriptions() {
        return descriptions;
    }
}
