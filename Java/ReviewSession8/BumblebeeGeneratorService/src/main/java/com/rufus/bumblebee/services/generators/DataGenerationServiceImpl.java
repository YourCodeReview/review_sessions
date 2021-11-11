package com.rufus.bumblebee.services.generators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rufus.bumblebee.generators.DataGenerator;
import com.rufus.bumblebee.repository.ContainerRepository;
import com.rufus.bumblebee.repository.TestDataRepository;
import com.rufus.bumblebee.repository.tables.Container;
import com.rufus.bumblebee.repository.tables.TestData;
import com.rufus.bumblebee.services.dto.ContainerStatus;
import com.rufus.bumblebee.services.dto.TestDataDto;
import com.rufus.bumblebee.services.interfaces.KafkaService;
import com.rufus.bumblebee.services.interfaces.DataGenerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataGenerationServiceImpl implements DataGenerationService {

    private static final Logger log = LoggerFactory.getLogger(DataGenerationServiceImpl.class);

    private final TestDataRepository repository;
    private final ContainerRepository containerRepository;
    private final KafkaService<List<TestDataDto>> kafkaService;

    @Autowired
    public DataGenerationServiceImpl(TestDataRepository repository, ContainerRepository containerRepository, KafkaService<List<TestDataDto>> kafkaService) {
        this.repository = repository;
        this.containerRepository = containerRepository;
        this.kafkaService = kafkaService;
    }

    @Async
    public void generateTestData(List<DataGenerator> generators, Container container){
        List<TestDataDto> dto = mapToDto(generators);
        kafkaService.sendTestData(dto, container);

        if (container.getHistoryOn()) {
            try {
                repository.saveAll(mapFromDto(dto, container.getId()));
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
                containerUpdateStatus(ContainerStatus.GENERATION_ERROR, container);
                return;
            }
        }
        containerUpdateStatus(ContainerStatus.GENERATION_COMPLETED, container);
    }


    private List<TestDataDto> mapToDto(List<DataGenerator> generators) {
        List<TestDataDto> dto = new ArrayList<>(generators.size());
        generators.forEach(
                g -> dto.add(new TestDataDto(g.getGeneratorName(), g.build()))
        );
        return dto;
    }

    private List<TestData> mapFromDto(List<TestDataDto> dtos, Long containerRef) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        List<TestData> testDataList = new ArrayList<>(dtos.size());
        for (TestDataDto dto : dtos) {
            testDataList.add(new TestData(
                    ow.writeValueAsString(dto.getData()), containerRef, dto.getGeneratorName())
            );
        }
        return testDataList;
    }

    private void containerUpdateStatus(ContainerStatus status, Container container) {
        container.setStatus(status);
        container.setUpdateDate(LocalDateTime.now());
        containerRepository.save(container);
    }
}
