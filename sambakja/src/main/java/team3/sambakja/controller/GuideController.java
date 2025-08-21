package team3.sambakja.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.dto.response.StartupResponse;
import team3.sambakja.service.GuideService;

@RestController
@RequestMapping("/api/guide")
@RequiredArgsConstructor
@Tag(name = "스타트업/창업 정책 API")
public class GuideController {

    private final GuideService guideService;

    @Operation(
            summary = "스타트업/창업 정책 조회",
            description = "소상공인을 위한 창업 가이드 정보를 반환합니다."
    )
    @GetMapping("/startup")
    public Page<StartupResponse> getStartup(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "15") int size,
                                            @RequestParam(defaultValue = "created") String sortBy,
                                            @RequestParam(defaultValue = "desc") String sortOrder) {

        Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortOrder)));

        Pageable pageable = PageRequest.of(page, size, sort);

        return guideService.getStartup(pageable);

    }

}
