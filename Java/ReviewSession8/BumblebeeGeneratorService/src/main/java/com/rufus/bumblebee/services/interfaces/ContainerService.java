package com.rufus.bumblebee.services.interfaces;

import com.rufus.bumblebee.services.dto.ContainerDto;
import com.rufus.bumblebee.controllers.requests.ReportType;

import java.util.Map;

/**
 * Class : сервис для работы с контейнерами
 */
public interface ContainerService {

    ContainerDto createContainer(String name, boolean auth, ReportType type) throws Exception;

    Map<String, String> removeContainer(String cuid) throws Exception;

    ContainerDto getContainerByName(String name) throws Exception;

}
