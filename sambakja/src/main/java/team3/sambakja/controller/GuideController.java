package team3.sambakja.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.service.GuideService;

@RestController
@RequiredArgsConstructor
public class GuideController {

    private final GuideService guideService;

    @GetMapping("/guidebank")
    public ResponseEntity<String> getBank(@RequestParam(defaultValue = "1") int pageNo,
                                      @RequestParam(defaultValue = "10") int numOfRows) {
        String result = guideService.getBank(pageNo, numOfRows,2025);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/startup")
    public ResponseEntity<String> getStartup() {
        String response = guideService.getStartup();

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }

}
