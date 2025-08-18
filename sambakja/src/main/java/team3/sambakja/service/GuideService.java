package team3.sambakja.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import team3.sambakja.dto.response.StartupResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GuideService {

    @Value("${api.startup.key}")
    private String startupKey;

    @Value("${api.startup.url}")
    private String startupApiUrl;

    private final RestTemplate restTemplate;

    public Page<StartupResponse> getStartup(Pageable pageable) {

        String encodedSeoul = URLEncoder.encode("서울", StandardCharsets.UTF_8);

        String url = String.format("%s?serviceKey=%s&cond[supt_regin::LIKE]=%s&returnType=json&page=%d&perPage=%d",
            startupApiUrl, startupKey, encodedSeoul,
            pageable.getPageNumber(),
            pageable.getPageSize());

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return parseJsonToPage(response.getBody(), pageable);
    }

    private Page<StartupResponse> parseJsonToPage(String jsonResponse, Pageable pageable) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // 총 개수 정보 추출
            long totalCount = rootNode.get("totalCount").asLong();

            // data 배열에서 각 항목을 StartupResponse로 변환
            JsonNode dataNode = rootNode.get("data");
            List<StartupResponse> startupList = new ArrayList<>();

            if (dataNode != null && dataNode.isArray()) {
                for (JsonNode item : dataNode) {
                    StartupResponse startup = new StartupResponse(
                        getTextValue(item, "id") != null ? Long.valueOf(Objects.requireNonNull(getTextValue(item, "id"))) : null,
                        getTextValue(item, "pbanc_sn"),
                        getTextValue(item, "biz_pbanc_nm"),
                        getTextValue(item, "pbanc_ntrp_nm"),
                        getTextValue(item, "intg_pbanc_biz_nm"),
                        getTextValue(item, "pbanc_ctnt"),
                        getTextValue(item, "aply_trgt"),
                        getTextValue(item, "aply_trgt_ctnt"),
                        getTextValue(item, "biz_trgt_age"),
                        getTextValue(item, "biz_enyy"),
                        getTextValue(item, "supt_regin"),
                        getTextValue(item, "supt_biz_clsfc"),
                        getTextValue(item, "sprv_inst"),
                        getTextValue(item, "biz_prch_dprt_nm"),
                        getTextValue(item, "prch_cnpl_no"),
                        getTextValue(item, "pbanc_rcpt_bgng_dt"),
                        getTextValue(item, "pbanc_rcpt_end_dt"),
                        getTextValue(item, "detl_pg_url"),
                        getTextValue(item, "biz_gdnc_url"),
                        getTextValue(item, "biz_aply_url"),
                        getTextValue(item, "aply_mthd_onli_rcpt_istc"),
                        getTextValue(item, "aply_mthd_eml_rcpt_istc"),
                        getTextValue(item, "aply_mthd_fax_rcpt_istc"),
                        getTextValue(item, "aply_mthd_pssr_rcpt_istc"),
                        getTextValue(item, "aply_mthd_vst_rcpt_istc"),
                        getTextValue(item, "aply_mthd_etc_istc"),
                        getTextValue(item, "aply_excl_trgt_ctnt"),
                        getTextValue(item, "intg_pbanc_yn"),
                        getTextValue(item, "rcrt_prgs_yn"),
                        getTextValue(item, "prfn_matr")
                    );
                    startupList.add(startup);
                }
            }

            return new PageImpl<>(startupList, pageable, totalCount);

        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 오류: " + e.getMessage(), e);
        }
    }

    // null 값 처리를 위한 헬퍼 메서드
    private String getTextValue(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return (fieldNode != null && !fieldNode.isNull()) ? fieldNode.asText() : null;
    }

}
