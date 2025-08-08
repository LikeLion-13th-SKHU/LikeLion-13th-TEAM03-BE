package team3.sambakja.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GuideService {

    @Value("${api.bank.key}")
    private String key;

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;
    private final ObjectMapper jsonMapper;

    private static final String BANK_API_URL = "https://apis.data.go.kr/B190030/GetFundLoanInfoService/getFundLoanList";

    public GuideService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.xmlMapper = new XmlMapper();
        this.jsonMapper = new ObjectMapper();
    }

    public String fetchData(int pageNo, int numOfRows, int bseYy) {
        String url = String.format("%s?serviceKey=%s&pageNo=%d&numOfRows=%d&bseYy=%d",
            BANK_API_URL, key, pageNo, numOfRows, bseYy);

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

    public String fetchDataAsJson(int pageNo, int numOfRows, int bseYy) {
        String xmlData = fetchData(pageNo, numOfRows, bseYy);
        return convertXmlToJson(xmlData);
    }

    public String fetchSmallBusinessFunds(int pageNo, int numOfRows, int bseYy) {
        try {
            String jsonData = fetchDataAsJson(pageNo, numOfRows, bseYy);

            Object dataObj = jsonMapper.readValue(jsonData, Object.class);

            if (dataObj instanceof Map) {
                Map<String, Object> dataMap = (Map<String, Object>) dataObj;
                Map<String, Object> body = (Map<String, Object>) dataMap.get("body");

                if (body != null && body.containsKey("items")) {
                    Map<String, Object> items = (Map<String, Object>) body.get("items");

                    if (items != null && items.containsKey("item")) {
                        List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");

                        // 기금명이 소상공인시장진흥공단인 항목만 필터링
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

    // 여기까지 한국산업은행_기금대출정보



}
