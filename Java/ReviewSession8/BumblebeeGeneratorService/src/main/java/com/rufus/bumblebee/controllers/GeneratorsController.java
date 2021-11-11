package com.rufus.bumblebee.controllers;

import com.rufus.bumblebee.services.dto.GeneratorDto;
import com.rufus.bumblebee.controllers.requests.GeneratorRequest;
import com.rufus.bumblebee.services.interfaces.GeneratorInformationService;
import com.rufus.bumblebee.services.interfaces.InitGeneratorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/generators")
@Api(value = "Controller for generators", tags = {"Controller for generators"})
@Validated
public class GeneratorsController {

    private final InitGeneratorService<GeneratorRequest> initGeneratorService;
    private final GeneratorInformationService<List<GeneratorDto>> informationService;

    @Autowired
    public GeneratorsController(InitGeneratorService<GeneratorRequest> initGeneratorService,
                                GeneratorInformationService<List<GeneratorDto>> informationService) {
        this.initGeneratorService = initGeneratorService;
        this.informationService = informationService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addGenerators(@RequestBody GeneratorRequest request) throws Exception {
        return ResponseEntity.ok(initGeneratorService.initGenerators(request));
    }

    @GetMapping(path = "/information")
    public ResponseEntity<List<GeneratorDto>> getGenerators() {
        return ResponseEntity.ok(informationService.getInformation());
    }
}

