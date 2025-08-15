package team3.sambakja.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.client.Client;
import team3.sambakja.dto.request.BizRequest;
import team3.sambakja.dto.response.BizResponse;

@RestController("/api/biz")
public class BizController {

    private final Client client;

    public BizController(Client client) {
        this.client = client;
    }

    @PostMapping("/recommendation")
    public BizResponse getBizRecommendation(@RequestBody BizRequest request) {
        return client.fetchBizRecommendation(request);
    }
}
