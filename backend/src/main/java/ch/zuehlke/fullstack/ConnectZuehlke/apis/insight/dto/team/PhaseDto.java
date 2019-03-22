package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.dto.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class PhaseDto {

    public static final int PHASE_STATE_SOLD = 20;
    public static final int PHASE_STATE_SOLD_WRITTEN = 21;

    @JsonProperty("State")
    private int state;

    public int getState() {
        return state;
    }
}
