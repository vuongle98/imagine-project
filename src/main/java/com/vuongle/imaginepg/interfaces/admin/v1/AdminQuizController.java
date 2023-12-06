package com.vuongle.imaginepg.interfaces.admin.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/quiz")
public class AdminQuizController {

    @GetMapping
    public void searchQuiz() {}
}
