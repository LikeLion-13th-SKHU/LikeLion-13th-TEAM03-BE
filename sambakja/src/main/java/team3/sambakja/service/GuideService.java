package team3.sambakja.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuideService {

    @Value("${api.bank.key}")
    private String bankKey;

    @Value("${api.startup.key}")
    private String startupKey;

    @Value("${api.bank.url}")
    private String bankApiUrl;

    @Value("${api.startup.url}")
    private String startupApiUrl;

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;
    private final ObjectMapper jsonMapper;

    //한국산업은행_기금대출정보
    public String xmlData(int pageNo, int numOfRows, int bseYy) {
        String url = String.format("%s?serviceKey=%s&pageNo=%d&numOfRows=%d&bseYy=%d",
                bankApiUrl, bankKey, pageNo, numOfRows, bseYy);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return response.getBody();
    }

    public String convertXmlToJson(String xmlData) {
        try {
            Object data = xmlMapper.readValue(xmlData, Object.class);

            return jsonMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException("XML to JSON 변환 중 오류 발생", e);
        }
    }

    public String jsonData(int pageNo, int numOfRows, int bseYy) {
        String xmlData = xmlData(pageNo, numOfRows, bseYy);

        return convertXmlToJson(xmlData);
    }

    public String getBank(int pageNo, int numOfRows, int bseYy) {
        try {
            String jsonData = jsonData(pageNo, numOfRows, bseYy);

            Object dataObj = jsonMapper.readValue(jsonData, Object.class);

            if (dataObj instanceof Map) {
                Map<String, Object> dataMap = (Map<String, Object>) dataObj;
                Map<String, Object> body = (Map<String, Object>) dataMap.get("body");

                if (body != null && body.containsKey("items")) {
                    Map<String, Object> items = (Map<String, Object>) body.get("items");

                    if (items != null && items.containsKey("item")) {
                        List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");

                        List<Map<String, Object>> filteredItems = itemList.stream()
                            .filter(item -> {
                                String brwEdaNm = (String) item.get("brwEdaNm");
                                return brwEdaNm != null && brwEdaNm.contains("소상공인시장진흥공단");
                            })
                            .collect(Collectors.toList());

                        items.put("item", filteredItems);
                        body.put("totalCount", String.valueOf(filteredItems.size()));
                    }
                }
            }

            return jsonMapper.writeValueAsString(dataObj);

        } catch (Exception e) {
            throw new RuntimeException("소상공인시장진흥공단 기금 필터링 중 오류 발생", e);
        }
    }

    // 서울시 창업 프로그램 API
    public String getStartup() {

        String encodedSeoul = URLEncoder.encode("서울", StandardCharsets.UTF_8);

        String url = String.format("%s?serviceKey=%s&cond[supt_regin::LIKE]=%s&returnType=json&page=1&perPage=10",
                startupApiUrl, startupKey, encodedSeoul);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return response.getBody();
    }

}
