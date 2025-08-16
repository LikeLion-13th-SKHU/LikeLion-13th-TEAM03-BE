package team3.sambakja.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("어디가게? API")
                .description("서울 소상공인 맞춤 지역·업종 데이터 기반 종합 분석 리포트 AI 에이전트 '어디가게?' API 명세서");

        return new OpenAPI()
                .addServersItem(new Server().url("http://3.35.124.89:8082"))
                .info(info);
    }

    @Bean
    public org.springdoc.core.models.GroupedOpenApi allApi() {
        return GroupedOpenApi.builder()
                .group("all")
                .pathsToMatch("/**")
                .build();
    }

}
