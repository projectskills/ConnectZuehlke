package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class PhaseLinkDto {
    @JsonProperty("Phase")
    private PhaseDto phase;

    public PhaseDto getPhase() {
        return phase;
    }
}
