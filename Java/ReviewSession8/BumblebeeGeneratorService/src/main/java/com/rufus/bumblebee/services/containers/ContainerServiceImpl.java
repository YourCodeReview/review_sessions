package com.rufus.bumblebee.services.containers;

import com.rufus.bumblebee.controllers.requests.ReportType;
import com.rufus.bumblebee.services.dto.ContainerDto;
import com.rufus.bumblebee.repository.ContainerRepository;
import com.rufus.bumblebee.repository.tables.Container;
import com.rufus.bumblebee.services.dto.ContainerStatus;
import com.rufus.bumblebee.services.interfaces.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.rufus.bumblebee.BumblebeeGeneratorService.KEY_STATUS;
import static com.rufus.bumblebee.BumblebeeGeneratorService.KEY_UID;

@Service
public class ContainerServiceImpl implements ContainerService {

    private final ContainerRepository repository;

    @Autowired
    public ContainerServiceImpl(ContainerRepository repository) {
        this.repository = repository;
    }

    @Override
    public ContainerDto createContainer(String name, boolean historyOn, ReportType type) throws Exception {
        Container container = new Container();
        container.setName(name);
        container.setHistoryOn(historyOn);
        container.setDate(LocalDateTime.now());
        container.setStatus(ContainerStatus.NEW);
        container.setType(type);
        container.setCuid(UUID.randomUUID());

        try {
            return getContainerDto(repository.save(container));
        } catch (DataAccessException exception) {
            throw new Exception("Error in the create container operation for name: " + name, exception);
        }
    }

    @Override
    public Map<String, String> removeContainer(String cuid) throws Exception {
        try {
            Container container = repository.getContainerByCuid(cuid);
            repository.delete(container);
        } catch (DataAccessException exception) {
            throw new Exception("Error in the remove container operation for cuid: " + cuid, exception);
        }

        return new HashMap<String, String>() {{
            put(KEY_UID, cuid);
            put(KEY_STATUS, ContainerStatus.CONTAINER_REMOVED.name());
        }};
    }

    @Override
    public ContainerDto getContainerByName(String name) throws Exception {
        try {
            return getContainerDto(repository.getContainerByName(name));
        } catch (DataAccessException exception) {
            throw new Exception("Error in the get container by name operation for name: " + name, exception);
        }
    }

    private ContainerDto getContainerDto(Container container) {
        ContainerDto dto = new ContainerDto();
        dto.setCuid(container.getCuid().toString());
        dto.setName(container.getName());
        dto.setStatus(container.getStatus());
        return dto;
    }
}
