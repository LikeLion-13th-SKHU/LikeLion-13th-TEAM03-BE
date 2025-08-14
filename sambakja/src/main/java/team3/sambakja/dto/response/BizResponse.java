package team3.sambakja.dto.response;

import java.util.List;

public record BizResponse(
        String biz_type,
        String biz_feature,
        List<Recommendation> recommendations
) {
    public record Recommendation(
            String region,
            String reason
    ) {}
}
