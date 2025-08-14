package team3.sambakja.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record RegionListResponse(
        @JsonProperty("available_dongs") List<String> availableDongs
) {
}
