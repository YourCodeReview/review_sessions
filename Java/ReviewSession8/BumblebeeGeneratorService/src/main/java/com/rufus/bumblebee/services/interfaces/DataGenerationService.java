package com.rufus.bumblebee.services.interfaces;

import com.rufus.bumblebee.generators.DataGenerator;
import com.rufus.bumblebee.repository.tables.Container;

import java.util.List;

/**
 * Class : сервис генерации тестовых данных
 */
public interface DataGenerationService {
    void generateTestData(List<DataGenerator> generators, Container container);
}
