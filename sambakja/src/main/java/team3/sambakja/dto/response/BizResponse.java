package team3.sambakja.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record BizResponse(
        String biz_type,
        String biz_feature,
        List<Recommendation> recommendations
) {
    public record Recommendation(
            String region,
            Reason reason
    ) {
        public record Reason(
                @JsonProperty("유동인구") String floatingPopulation,
                @JsonProperty("직장인구") String officePopulation,
                @JsonProperty("연령층") String ageGroup,
                @JsonProperty("임대료") String rent,
                @JsonProperty("상권특징") String commercialFeature
        ) {}
    }
}
