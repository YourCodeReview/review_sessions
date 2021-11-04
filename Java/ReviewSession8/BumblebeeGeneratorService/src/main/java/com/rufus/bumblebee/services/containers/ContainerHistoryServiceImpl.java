package com.rufus.bumblebee.services.containers;

import com.rufus.bumblebee.repository.ContainerRepository;
import com.rufus.bumblebee.repository.tables.Container;
import com.rufus.bumblebee.repository.tables.TestData;
import com.rufus.bumblebee.services.dto.HistoryDto;
import com.rufus.bumblebee.services.interfaces.ContainerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ContainerHistoryServiceImpl implements ContainerHistoryService<HistoryDto, String> {

    private final ContainerRepository repository;

    @Autowired
    public ContainerHistoryServiceImpl(ContainerRepository repository) {
        this.repository = repository;
    }

    @Override
    public HistoryDto getHistory(String cuid) {
        Container container = repository.getContainerByCuid(cuid);
        return new HistoryDto(
                container.getName(),
                container.getStatus(),
                container.getData().stream().map(TestData::getGeneratorName).collect(Collectors.toList())
        );
    }
}
