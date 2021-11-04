package com.rufus.bumblebee.controllers;

import com.rufus.bumblebee.services.dto.ContainerDto;
import com.rufus.bumblebee.controllers.requests.ContainerRequest;
import com.rufus.bumblebee.services.dto.HistoryDto;
import com.rufus.bumblebee.services.interfaces.ContainerService;
import com.rufus.bumblebee.services.interfaces.ContainerHistoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Map;


@RestController
@RequestMapping(path = "/containers")
@Api(value = "Controller for containers", tags = {"Controller for containers"})
@Validated
public class ContainerController {

    private final ContainerService service;
    private final ContainerHistoryService<HistoryDto, String> containerHistoryService;

    @Autowired
    public ContainerController(ContainerService service, ContainerHistoryService<HistoryDto, String> containerHistoryService) {
        this.service = service;
        this.containerHistoryService = containerHistoryService;
    }

    @PostMapping
    public ResponseEntity<ContainerDto> addContainer(@Valid @RequestBody ContainerRequest request) throws Exception {
        return ResponseEntity.ok(service.createContainer(request.getName(), request.getHistoryOn(), request.getReportType()));
    }

    @DeleteMapping(path = "/{cuid}")
    public ResponseEntity<Map<String, String>> removeContainer(@PathVariable("cuid") @NotEmpty String cuid) throws Exception {
        return ResponseEntity.ok(service.removeContainer(cuid));
    }

    @GetMapping(path = "/{cuid}/history")
    public ResponseEntity<HistoryDto> getHistory(@PathVariable("cuid") @NotEmpty String cuid) {
        return ResponseEntity.ok(containerHistoryService.getHistory(cuid));
    }

    @GetMapping(path = "/{name}/information")
    public ResponseEntity<ContainerDto> getContainerByName(@PathVariable("name") @NotEmpty String name) throws Exception {
        return ResponseEntity.ok(service.getContainerByName(name));
    }
}
