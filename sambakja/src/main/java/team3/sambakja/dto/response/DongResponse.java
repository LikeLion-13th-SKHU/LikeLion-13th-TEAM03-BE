package team3.sambakja.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DongResponse(String region, Report report) {

    public record Report(
            @JsonProperty("인구") Population population,
            @JsonProperty("업종") Business business,
            @JsonProperty("입지_특성") String locationFeatures
    ) {}

    public record Population(
            @JsonProperty("1인가구") String singleHousehold,
            @JsonProperty("상주인구") String stablePopulation,
            @JsonProperty("유동인구") String floatingPopulation,
            @JsonProperty("직장인구") String officePopulation
    ) {}

    public record Business(
            @JsonProperty("점포수") String storeCount,
            @JsonProperty("임대료_특징") String rentCharacteristics
    ) {}
}
