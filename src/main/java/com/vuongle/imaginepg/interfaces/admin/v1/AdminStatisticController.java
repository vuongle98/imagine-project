package com.vuongle.imaginepg.interfaces.admin.v1;

import com.vuongle.imaginepg.application.dto.statistic.StatisticDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/statistic")
public class AdminStatisticController {

    @GetMapping
    public ResponseEntity<StatisticDto> statistic(

    ) {
        return ResponseEntity.ok(new StatisticDto());
    }
}
