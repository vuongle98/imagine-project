package com.vuongle.imaginepg.interfaces.admin.v1;

import com.vuongle.imaginepg.application.dto.statistic.StatisticDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/statistic")
public class AdminStatisticController {

    @GetMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public ResponseEntity<StatisticDto> statistic(

    ) {
        return ResponseEntity.ok(new StatisticDto());
    }
}
