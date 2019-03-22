package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.Project;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class ProjectDto {

    @JsonProperty("Code")
    private String code;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Description")
    private String description;

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Project toProject() {
        return new Project(
                getCode(),
                getTitle(),
                getDescription()
        );
    }

}
