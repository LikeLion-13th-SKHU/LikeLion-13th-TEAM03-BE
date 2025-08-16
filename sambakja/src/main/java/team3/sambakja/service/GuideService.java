package team3.sambakja.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class GuideService {

    @Value("${api.startup.key}")
    private String startupKey;

    @Value("${api.startup.url}")
    private String startupApiUrl;

    private final RestTemplate restTemplate;

    public String getStartup() {

        String encodedSeoul = URLEncoder.encode("서울", StandardCharsets.UTF_8);

        String url = String.format("%s?serviceKey=%s&cond[supt_regin::LIKE]=%s&returnType=json&page=1&perPage=10",
                startupApiUrl, startupKey, encodedSeoul);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return response.getBody();
    }

}
