package com.movieticket.theatre.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public String health() {
        return "Theatre Service is running";
    }
}