package team3.sambakja.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.service.GuideService;

@RestController
public class GuideController {

    private final GuideService guideService;

    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @GetMapping("/guideBank")
    public ResponseEntity<String> getAllGuidesFromApi(@RequestParam(defaultValue = "1") int pageNo,
                                      @RequestParam(defaultValue = "10") int numOfRows) {
        try {
            String result = guideService.fetchSmallBusinessFunds(pageNo, numOfRows,2025);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("API 호출 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


}
