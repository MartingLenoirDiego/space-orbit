package com.space.orbit.controller;

import com.space.orbit.dto.TleDTO;
import com.space.orbit.service.TleService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/satellites")
public class SatelliteController {

    private final TleService tleService;

    public SatelliteController(TleService tleService) {
        this.tleService = tleService;
    }

    @GetMapping
    public Mono<List<TleDTO>> getSatellites() {
        return tleService.fecthActiveSatellites();
    }

}
