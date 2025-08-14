// DongResponse.java
package team3.sambakja.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DongResponse(String region, Report report) {

    public record Report(
        @JsonProperty("인구") Population population,
        @JsonProperty("업종") Business business,
        @JsonProperty("입지_특성") String locationFeatures
    ) {
    }

    public record Population(
        @JsonProperty("연령별_성별_비율") String ageGenderRatio,
        @JsonProperty("인구_추이") String populationTrend
    ) {
    }

    public record Business(
        @JsonProperty("점포수_및_매출") String storeCountAndSales,
        @JsonProperty("임대료_특징") String rentCharacteristics
    ) {
    }
}
